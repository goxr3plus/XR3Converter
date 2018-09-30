package main.java.com.goxr3plus.xr3converter.converter.service;

import java.io.File;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Duration;
import main.java.com.goxr3plus.xr3converter.converter.controller.ConverterController;
import main.java.com.goxr3plus.xr3converter.tools.fx.JavaFXTools;
import main.java.com.goxr3plus.xr3converter.tools.fx.NotificationType;
import main.java.com.goxr3plus.xr3converter.tools.io.FileTools;
import main.java.com.goxr3plus.xr3converter.tools.io.FileType;
import ws.schild.jave.AudioAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderProgressListener;
import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;

/**
 * Used by XR3Player to convert all unsupported audio formats to .mp3
 * 
 * @author GOXR3PLUSSTUDIO
 *
 */
public class ConverterService extends Service<Boolean> {
	
	/**
	 * The full path of audio file
	 */
	private String newFileAsbolutePath;
	private ConvertProgressListener listener = new ConvertProgressListener();
	private final SimpleDoubleProperty convertProgress = new SimpleDoubleProperty();
	private Encoder encoder;
	
	/**
	 * The XPlayerController
	 */
	private final ConverterController controller;
	
	/**
	 * Constructor
	 */
	public ConverterService(ConverterController controller) {
		this.controller = controller;
		
		this.setOnSucceeded(s -> done());
		this.setOnCancelled(c -> done());
		this.setOnFailed(c -> done());
	}
	
	/**
	 * Start the Service for the given file
	 * 
	 * @param <convertProgress>
	 * 
	 * @param fileAbsolutePath
	 */
	public void convert() {
		
		//Try to abort
		if (encoder != null)
			encoder.abortEncoding();
		
		//Set Progress to 0
		convertProgress.set(-1.0);
		
		// Binds
		controller.getLoadingVBox().visibleProperty().bind(runningProperty());
		controller.getLoadingProgressBar().progressProperty().bind(convertProgress);
		controller.getCancelButton().setDisable(false);
		controller.getDescriptionLabel().setText("Converting...");
		controller.getCancelButton().setOnAction(e -> {
			super.cancel();
			controller.getCancelButton().setDisable(true);
		});
		
		//Restart the Service
		restart();
	}
	
	/**
	 * When the Service is done
	 */
	private void done() {
		
		//Unbind
		controller.unbind();
		
	}
	
	@Override
	protected Task<Boolean> createTask() {
		return new Task<Boolean>() {
			@Override
			protected Boolean call() throws Exception {
				boolean succeeded = true;
				
				//Set Message
				Platform.runLater(
						() -> controller.getDescriptionArea().setText("\nConverting: [ " + controller.getTableViewer().getTableView().getItems().size() + " ] Files...\n"));
				
				//Create the media folder if not existing
				String folderName = controller.getOutputFolderTextField().getText() + File.separator + "Media";
				if (!FileTools.createFileOrFolder(folderName, FileType.DIRECTORY)) {
					JavaFXTools.showNotification("Internal Error", "Can't create output folder.", Duration.seconds(4), NotificationType.WARNING);
					succeeded = false;
					return false;
				}
				
				//Check if it is already .mp3
				controller.getTableViewer().getTableView().getItems().forEach(media -> {
					String absolutePath = media.getFilePath();
					
					if (!"mp3".equals(FileTools.getFileExtension(absolutePath))) {
						newFileAsbolutePath = folderName + File.separator + FileTools.getFileTitle(FileTools.getFileName(absolutePath)) + ".mp3";
						
						//Convert any audio format to .mp3
						try {
							
							//Files
							File source = new File(absolutePath);
							File target = new File(newFileAsbolutePath);
							
							//Audio Attributes
							AudioAttributes audio = new AudioAttributes();
							audio.setCodec("libmp3lame");
							audio.setBitRate(128000);
							audio.setChannels(2);
							audio.setSamplingRate(44100);
							
							//Encoding attributes
							EncodingAttributes attrs = new EncodingAttributes();
							attrs.setFormat("mp3");
							attrs.setAudioAttributes(audio);
							
							//Encode          
							if (encoder != null)
								encoder.abortEncoding();
							encoder = encoder != null ? encoder : new Encoder();
							encoder.encode(new MultimediaObject(source), target, attrs, listener);
							
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				
				//Set Message
				super.updateMessage("Convert finished...");
				
				//System.out.println("After the error")
				
				return true;
			}
		};
	}
	
	public class ConvertProgressListener implements EncoderProgressListener {
		int current = 1;
		
		public ConvertProgressListener() {
		}
		
		public void message(String m) {
			//      if ((ConverterFrame.this.inputfiles.length > 1) && 
			//        (this.current < ConverterFrame.this.inputfiles.length)) {
			//        ConverterFrame.this.encodingMessageLabel.setText(this.current + "/" + ConverterFrame.this.inputfiles.length);
			//      }
		}
		
		public void progress(int p) {
			
			double progress = p / 1000.00;
			//System.out.println(progress);
			
			Platform.runLater(() -> convertProgress.set(progress));
			//      ConverterFrame.this.encodingProgressLabel.setText(progress + "%");
			//      if (p >= 1000) {
			//        if (ConverterFrame.this.inputfiles.length > 1)
			//        {
			//          this.current += 1;
			//          if (this.current > ConverterFrame.this.inputfiles.length)
			//          {
			//            ConverterFrame.this.encodingMessageLabel.setText("Encoding Complete!");
			//            ConverterFrame.this.convertButton.setEnabled(true);
			//          }
			//        }
			//        else if (p == 1001)
			//        {
			//          ConverterFrame.this.encodingMessageLabel.setText("Encoding Failed!");
			//          ConverterFrame.this.convertButton.setEnabled(true);
			//        }
			//        else
			//        {
			//          ConverterFrame.this.encodingMessageLabel.setText("Encoding Complete!");
			//          ConverterFrame.this.convertButton.setEnabled(true);
			//        }
		}
		
		public void sourceInfo(MultimediaInfo m) {
		}
	}
	
}
