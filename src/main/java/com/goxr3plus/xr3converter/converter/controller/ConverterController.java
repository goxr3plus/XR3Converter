package main.java.com.goxr3plus.xr3converter.converter.controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.java.com.goxr3plus.xr3converter.converter.service.ConverterService;
import main.java.com.goxr3plus.xr3converter.converter.service.InputService;
import main.java.com.goxr3plus.xr3converter.storage.RunTimeVars;
import main.java.com.goxr3plus.xr3converter.tools.fx.JavaFXTools;
import main.java.com.goxr3plus.xr3converter.tools.fx.NotificationType;

public class ConverterController extends StackPane {
	
	//--------------------------------------------------------------
	
	@FXML
	private BorderPane borderPane;
	
	@FXML
	private HBox searchBarHBox;
	
	@FXML
	private MenuButton toolsMenuButton;
	
	@FXML
	private ContextMenu toolsContextMenu;
	
	@FXML
	private MenuItem addAudioFiles;
	
	@FXML
	private MenuItem addVideoFiles;
	
	@FXML
	private MenuItem addFolder;
	
	@FXML
	private MenuItem clearList;
	
	@FXML
	private HBox searchBarHBox1;
	
	@FXML
	private TextField outputFolderTextField;
	
	@FXML
	private MenuButton outputExtension;
	
	@FXML
	private JFXButton browserFolderButton;
	
	@FXML
	private JFXButton convertButton;
	
	@FXML
	private VBox loadingVBox;
	
	@FXML
	private Label descriptionLabel;
	
	@FXML
	private ProgressBar loadingProgressBar;
	
	@FXML
	private Button cancelButton;
	
	@FXML
	private TextArea descriptionArea;
	
	// ---------Services---------------------------
	
	private final InputService inputService;
	
	private final ConverterService converterService;
	
	// --------------------------------------------
	
	private MediaTableViewer tableViewer;
	
	// ---------Security---------------------------
	
	public enum WorkOnProgress {
		NONE, INSERTING_FILES, DELETE_FILES, RENAMING_LIBRARY, UPDATING_PLAYLIST, SEARCHING_FILES, EXPORTING_FILES;
	}
	
	public volatile WorkOnProgress workOnProgress = WorkOnProgress.NONE;
	
	// --------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public ConverterController() {
		this.inputService = new InputService(this);
		this.converterService = new ConverterService(this);
		
		// ------------------------------------FXMLLOADER ----------------------------------------
		FXMLLoader loader = new FXMLLoader(getClass().getResource(RunTimeVars.FXMLS + "ConverterController.fxml"));
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
		
		// Initialize TableViewer
		tableViewer = new MediaTableViewer(this);
		
		// BorderPane
		borderPane.setCenter(tableViewer);
		
		// ToolsMenuButton
		toolsMenuButton.setOnMouseReleased(m -> {
			Bounds bounds = toolsMenuButton.localToScreen(toolsMenuButton.getBoundsInLocal());
			toolsContextMenu.show(toolsMenuButton, bounds.getMaxX(), bounds.getMinY());
		});
		
		// loadingVBox
		loadingVBox.setVisible(false);
		
		// ConvertButton
		convertButton.setOnAction(a -> {
			if (outputFolderTextField.getText().isEmpty())
				JavaFXTools.showNotification("No output folder", "Please select an output folder", Duration.seconds(5), NotificationType.INFORMATION);
			else
				converterService.convert();
		});
	}
	
	/**
	 * Checks if any updates are on progress in the controller.
	 *
	 * @param showMessage
	 *            the show message
	 * @return true->if yes<br>
	 *         false->if not
	 */
	public boolean isFree(boolean showMessage) {
		boolean isFree = ( workOnProgress == WorkOnProgress.NONE );
		
		//Check if any work is already in progress
		if (!isFree && showMessage)
			showMessage(workOnProgress.toString());
		
		return isFree;
	}
	
	/**
	 * Unbind.
	 */
	public void unbind() {
		loadingVBox.visibleProperty().unbind();
		loadingVBox.setVisible(false);
		loadingProgressBar.progressProperty().unbind();
	}
	
	/**
	 * Show message.
	 *
	 * @param reason
	 *            the reason
	 */
	private void showMessage(String reason) {
		JavaFXTools.showNotification("Message", "[" + reason + "] is working on:\n " + toString() + "\n\t retry as soon as it finish.", Duration.millis(2000),
				NotificationType.INFORMATION);
	}
	
	public VBox getLoadingVBox() {
		return loadingVBox;
	}
	
	public Label getDescriptionLabel() {
		return descriptionLabel;
	}
	
	public ProgressBar getLoadingProgressBar() {
		return loadingProgressBar;
	}
	
	public Button getCancelButton() {
		return cancelButton;
	}
	
	public TextArea getDescriptionArea() {
		return descriptionArea;
	}
	
	public InputService getInputService() {
		return inputService;
	}
	
	public MediaTableViewer getTableViewer() {
		return tableViewer;
	}
	
	public void setDescriptionArea(TextArea descriptionArea) {
		this.descriptionArea = descriptionArea;
	}

	public TextField getOutputFolderTextField() {
		return outputFolderTextField;
	}

	
}
