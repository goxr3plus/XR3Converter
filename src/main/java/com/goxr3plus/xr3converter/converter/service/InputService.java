package main.java.com.goxr3plus.xr3converter.converter.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import main.java.com.goxr3plus.xr3converter.converter.controller.ConverterController;
import main.java.com.goxr3plus.xr3converter.converter.controller.ConverterController.WorkOnProgress;
import main.java.com.goxr3plus.xr3converter.converter.model.Media;
import main.java.com.goxr3plus.xr3converter.tools.general.FileTypeTool;
import main.java.com.goxr3plus.xr3converter.tools.io.IOTool;

/**
 * Manages the input operations of the SmartController.
 *
 * @author GOXR3PLUS
 */
public class InputService extends Service<Void> {
	
	/** The list. */
	private List<File> givenList;
	
	/** The job. */
	private String job;
	
	/** The counter. */
	private int progress;
	
	/** The total files. */
	private int totalFiles;
	
	private final ConverterController controller;
	
	private List<Media> finalList;
	
	/**
	 * Constructor.
	 */
	public InputService(ConverterController controller) {
		this.controller = controller;
		
		setOnSucceeded(s -> done());
		setOnCancelled(c -> done());
		setOnFailed(c -> done());
	}
	
	/**
	 * Start the Service.
	 *
	 * @param filesList
	 *            List that contains the files to be added
	 */
	public void start(List<File> filesList) {
		
		//Check
		if (!Platform.isFxApplicationThread() || !controller.isFree(true) || isRunning())
			return;
		
		// Security
		job = "upload from system";
		
		// We need only directories or media files
		this.givenList = filesList.stream()
				//Find real path for symbolic links etc
				.map(file -> new File(IOTool.getRealPathFromFile(file.getAbsolutePath()).getFileAbsolutePath()))
				//Filter only the files we want
				.filter(file -> file.isDirectory() || ( file.isFile() && FileTypeTool.isAudioSupported(file.getAbsolutePath()) ))
				//Collect everything to a list
				.collect(Collectors.toList());
		// Security Value
		controller.workOnProgress = WorkOnProgress.INSERTING_FILES;
		
		// System.out.println(this.list)
		
		//Clear the text of imformation text field
		controller.getDescriptionArea().clear();
		
		// Binds
		controller.getLoadingVBox().visibleProperty().bind(runningProperty());
		controller.getLoadingProgressBar().progressProperty().bind(progressProperty());
		controller.getCancelButton().setDisable(false);
		controller.getDescriptionLabel().setText("Counting...");
		controller.getCancelButton().setOnAction(e -> {
			super.cancel();
			controller.getCancelButton().setDisable(true);
		});
		
		// ....
		reset();
		start();
		
	}
	
	/**
	 * When the work is done.
	 */
	private void done() {
		
		//Unbind
		controller.unbind();
		
		//Erase the lists
		givenList = null;
		
		//Unbind etc...
		controller.getCancelButton().setDisable(true);
		
		//Set the data to the TableView
		this.controller.getTableViewer().getTableView().setItems(FXCollections.observableArrayList(finalList));
		
		// Security Value
		controller.workOnProgress = WorkOnProgress.NONE;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see javafx.concurrent.Service#createTask()
	 */
	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			/*
			 * (non-Javadoc)
			 * @see javafx.concurrent.Task#call()
			 */
			@Override
			protected Void call() throws Exception {
				
				//Keep Important Variables here
				totalFiles = progress = 0;
				
				// Update informationTextArea
				Platform.runLater(() -> controller.getDescriptionArea().appendText("\nCounting files from ....\n"));
				
				// Start the insert work
				if ("upload from system".equals(job)) {
					
					//Count the total files to be inserted
					for (File file : givenList) {
						
						//File exists?
						if (!file.exists())
							continue;
						
						// Update informationTextArea
						Platform.runLater(() -> controller.getDescriptionArea().appendText( ( !file.isDirectory() ? "File" : "Folder" ) + ": " + file.getName()));
						
						int previousTotal = totalFiles;
						// File or Folder exists?
						if (isCancelled())
							break;
						
						totalFiles += countFiles(file);
						
						// Update informationTextArea
						Platform.runLater(() -> controller.getDescriptionArea().appendText("\n\t-> Total: [ " + ( totalFiles - previousTotal ) + " ]\n"));
						
					}
					
					// Update informationTextArea and cancel button
					if (!isCancelled())
						Platform.runLater(() -> {
							controller.getDescriptionLabel().setText("Inserting...");
							controller.getDescriptionArea().appendText("\nInserting: [ " + totalFiles + " ] Files...\n");
						});
					
					//Now initialise the final list
					finalList = new ArrayList<>(totalFiles);
					
					//Add all Files absolute paths to the database table
					for (File file : givenList)
						if (file.exists() && !isCancelled())
							try {
								Files.walkFileTree(Paths.get(file.getPath()), new HashSet<FileVisitOption>(Arrays.asList(FileVisitOption.FOLLOW_LINKS)), Integer.MAX_VALUE,
										new SimpleFileVisitor<Path>() {
											@Override
											public FileVisitResult visitFile(Path filePath , BasicFileAttributes attrs) throws IOException {
												
												// supported?
												if (FileTypeTool.isAudioSupported(filePath + ""))
													finalList.add(new Media(filePath.toString()));
												
												// update progress
												updateProgress(++progress, totalFiles);
												
												return isCancelled() ? FileVisitResult.TERMINATE : FileVisitResult.CONTINUE;
											}
											
											@Override
											public FileVisitResult visitFileFailed(Path filePath , IOException e) throws IOException {
												System.err.printf("Visiting failed for %s\n", filePath);
												
												return FileVisitResult.SKIP_SUBTREE;
											}
											
											@Override
											public FileVisitResult preVisitDirectory(Path dir , BasicFileAttributes attrs) throws IOException {
												return isCancelled() ? FileVisitResult.TERMINATE : FileVisitResult.CONTINUE;
											}
										});
							} catch (IOException e) {
								e.printStackTrace();
							}
						
				}
				
				return null;
				
			}
			
			/**
			 * Count files in a directory (including files in all sub directories)
			 * 
			 * @param directory
			 *            The Full path of the Directory
			 * @return Total number of files contained in this folder
			 */
			private int countFiles(File dir) {
				int[] count = { 0 };
				
				//Folder exists?
				if (dir.exists())
					try {
						Files.walkFileTree(Paths.get(dir.getPath()), new HashSet<>(Arrays.asList(FileVisitOption.FOLLOW_LINKS)), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
							@Override
							public FileVisitResult visitFile(Path filePath , BasicFileAttributes attrs) throws IOException {
								
								if (FileTypeTool.isAudioSupported(filePath + ""))
									++count[0];
								
								return isCancelled() ? FileVisitResult.TERMINATE : FileVisitResult.CONTINUE;
							}
							
							@Override
							public FileVisitResult visitFileFailed(Path filePath , IOException e) throws IOException {
								System.err.printf("Visiting failed for %s\n", filePath);
								
								return FileVisitResult.SKIP_SUBTREE;
							}
							
							@Override
							public FileVisitResult preVisitDirectory(Path dir , BasicFileAttributes attrs) throws IOException {
								return isCancelled() ? FileVisitResult.TERMINATE : FileVisitResult.CONTINUE;
							}
						});
					} catch (IOException e) {
						e.printStackTrace();
					}
				
				return count[0];
			}
			
		};
	}
	
}
