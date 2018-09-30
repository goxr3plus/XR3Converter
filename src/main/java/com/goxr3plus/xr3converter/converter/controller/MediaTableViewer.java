package main.java.com.goxr3plus.xr3converter.converter.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import main.java.com.goxr3plus.xr3converter.converter.Media;
import main.java.com.goxr3plus.xr3converter.storage.RunTimeVars;

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
	
	/**
	 * Constructor.
	 */
	public MediaTableViewer() {
		
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
	}
	
}
