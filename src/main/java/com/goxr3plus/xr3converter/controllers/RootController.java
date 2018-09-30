package main.java.com.goxr3plus.xr3converter.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import main.java.com.goxr3plus.xr3converter.storage.RunTimeVars;

public class RootController extends StackPane {
	
	//--------------------------------------------------------------
	
	@FXML
	private BorderPane borderPane;
	
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
		
	}
	
	public BorderPane getBorderPane() {
		return borderPane;
	}
	
}
