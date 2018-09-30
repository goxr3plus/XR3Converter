package main.java.com.goxr3plus.xr3converter.converter.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import main.java.com.goxr3plus.xr3converter.storage.RunTimeVars;

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
	
	// -------------------------------------------------------------
	
	public MediaTableViewer tableViewer;
	
	/**
	 * Constructor.
	 */
	public ConverterController() {
		
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
		
		//Initialize TableViewer
		tableViewer = new MediaTableViewer();
		
		//BorderPane
		borderPane.setCenter(tableViewer);
	}
	
}
