package main.java.com.goxr3plus.xr3converter.converter;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import main.java.com.goxr3plus.xr3converter.storage.RunTimeVars;

public class ConverterController {
	
	//--------------------------------------------------------------
	
	// -------------------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public ConverterController() {
		
		// ------------------------------------FXMLLOADER ----------------------------------------
		FXMLLoader loader = new FXMLLoader(getClass().getResource(RunTimeVars.FXMLS + "name.fxml"));
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
	
}
