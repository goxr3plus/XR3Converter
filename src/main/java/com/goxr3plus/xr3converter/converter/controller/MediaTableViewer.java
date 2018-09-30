package main.java.com.goxr3plus.xr3converter.converter.controller;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import main.java.com.goxr3plus.xr3converter.converter.model.Media;
import main.java.com.goxr3plus.xr3converter.storage.RunTimeVars;
import main.java.com.goxr3plus.xr3converter.tools.fx.JavaFXTools;

public class MediaTableViewer extends StackPane {
	
	//--------------------------------------------------------------
	
	@FXML
	private TableView<Media> tableView;
	
	@FXML
	private TableColumn<Media,String> title;
	
	@FXML
	private TableColumn<Media,String> fileType;
	
	@FXML
	private TableColumn<Media,String> fileSize;
	
	@FXML
	private TableColumn<Media,String> filePath;
	
	@FXML
	private Label dragAndDropLabel;
	
	// -------------------------------------------------------------
	
	private final ConverterController controller;

	
	/**
	 * Constructor.
	 */
	public MediaTableViewer(ConverterController controller) {
		this.controller = controller;
		
		// ------------------------------------FXMLLOADER ----------------------------------------
		FXMLLoader loader = new FXMLLoader(getClass().getResource(RunTimeVars.FXMLS + "MediaTableViewer.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		
		try {
			loader.load();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * Called as soon as fxml is initialized
	 */
	@FXML
	private void initialize() {
		
		// title
		title.setCellValueFactory(new PropertyValueFactory<>("title"));
		
		// fileType
		fileType.setCellValueFactory(new PropertyValueFactory<>("fileType"));
		
		// fileSize
		fileSize.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
		
		// filePath
		filePath.setCellValueFactory(new PropertyValueFactory<>("filePath"));
		
		// --Drag Detected
		tableView.setOnDragDetected(event -> {
			if (getSelectedCount() != 0 && event.getScreenY() > tableView.localToScreen(tableView.getBoundsInLocal()).getMinY() + 30) {
				
				/* allow copy transfer mode */
				Dragboard db = tableView.startDragAndDrop(TransferMode.COPY, TransferMode.LINK);
				
				/* put a string on drag board */
				ClipboardContent content = new ClipboardContent();
				
				// PutFiles
				content.putFiles(tableView.getSelectionModel().getSelectedItems().stream().map(s -> new File(s.getFilePath())).collect(Collectors.toList()));
				
				// Single Drag and Drop ?
				if (content.getFiles().size() == 1)
					JavaFXTools.setDragView(db, tableView.getSelectionModel().getSelectedItem());
				// Multiple Drag and Drop ?
				else {
					JavaFXTools.setPlainTextDragView(db, "(" + content.getFiles().size() + ")Items");
				}
				
				db.setContent(content);
			}
			event.consume();
		});
		
		// dragAndDropLabel
		dragAndDropLabel.setVisible(false);
		
		// --Drag Over
		tableView.setOnDragOver(dragOver -> {
			
			// The drag must come from source other than the owner
			if (dragOver.getDragboard().hasFiles() && dragOver.getGestureSource() != tableView)
				dragAndDropLabel.setVisible(true);
			
		});
		
		dragAndDropLabel.setOnDragOver(dragOver -> {
			
			if (dragOver.getDragboard().hasFiles() && dragOver.getGestureSource() != tableView)
				dragOver.acceptTransferModes(TransferMode.LINK);
			
		});
		
		// --Drag Dropped
		dragAndDropLabel.setOnDragDropped(drop -> {
			// Has Files? + isFree()?
			if (drop.getDragboard().hasFiles())
				controller.getInputService().start(drop.getDragboard().getFiles());
			
			drop.setDropCompleted(true);
		});
		
		// Drag Exited
		dragAndDropLabel.setOnDragExited(drop -> dragAndDropLabel.setVisible(false));
		
	}
	
	/**
	 * Calculates the selected items in the table.
	 *
	 * @return An int representing the total selected items in the table
	 */
	public int getSelectedCount() {
		return tableView.getSelectionModel().getSelectedItems().size();
	}
	
	/**
	 * Copies all the selected media files to the Native System ClipBoard
	 */
	public void copySelectedMediaToClipBoard() {
		JavaFXTools.setClipBoard(tableView.getSelectionModel().getSelectedItems().stream().map(s -> new File(s.getFilePath())).collect(Collectors.toList()));
	}

	public TableView<Media> getTableView() {
		return tableView;
	}

}
