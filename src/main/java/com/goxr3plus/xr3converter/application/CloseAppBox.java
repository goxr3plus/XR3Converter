package main.java.com.goxr3plus.xr3converter.application;

import java.io.IOException;

import org.kordamp.ikonli.javafx.StackedFontIcon;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import main.java.com.goxr3plus.xr3converter.storage.RunTimeVars;


public class CloseAppBox extends StackPane {
	
	//--------------------------------------------------------------
	
	@FXML
	private JFXButton minimize;
	
	@FXML
	private JFXButton maxOrNormalize;
	
	@FXML
	private StackedFontIcon sizeStackedFontIcon;
	
	@FXML
	private JFXButton exitApplication;
	
	// -------------------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public CloseAppBox() {
		
		// ------------------------------------FXMLLOADER ----------------------------------------
		FXMLLoader loader = new FXMLLoader(getClass().getResource(RunTimeVars.FXMLS + "CloseAppBox.fxml"));
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
		
		
		
		// minimize
		minimize.setOnAction(ac -> Main.window.setIconified(true));
		
		// maximize_normalize
		maxOrNormalize.setOnAction(ac -> Main.borderlessScene.maximizeStage());
		
		// close
		exitApplication.setOnAction(ac -> System.exit(0));
		
	}
	
}
