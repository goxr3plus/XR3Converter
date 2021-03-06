package main.java.com.goxr3plus.xr3converter.application;

import java.io.IOException;

import com.jfoenix.controls.JFXTabPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import main.java.com.goxr3plus.xr3converter.converter.controller.ConverterController;
import main.java.com.goxr3plus.xr3converter.storage.RunTimeVars;

public class RootController extends StackPane {
	
	//--------------------------------------------------------------
	
	@FXML
	private BorderPane borderPane;
	
	@FXML
	private JFXTabPane tabPane;
	
	// -------------------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public RootController() {
		
		// ------------------------------------FXMLLOADER ----------------------------------------
		FXMLLoader loader = new FXMLLoader(getClass().getResource(RunTimeVars.FXMLS + "RootController.fxml"));
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
		
		tabPane.getTabs().add(new Tab("Space 1", new ConverterController()));
		tabPane.getTabs().add(new Tab("Space 2", new ConverterController()));
		tabPane.getTabs().add(new Tab("Space 3", new ConverterController()));
	}
	
	public BorderPane getBorderPane() {
		return borderPane;
	}
	
	public JFXTabPane getTabPane() {
		return tabPane;
	}
	
}
