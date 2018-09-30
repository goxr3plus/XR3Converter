package main.java.com.goxr3plus.xr3converter.application;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import main.java.com.goxr3plus.xr3converter.application.topbar.TopBar;
import main.java.com.goxr3plus.xr3converter.storage.RunTimeVars;
import main.java.com.goxr3plus.xr3converter.tools.JavaFXTools;

public class Main extends Application {
	
	//-------------------------------------------
	
	public static Stage window;
	
	public static BorderlessScene borderlessScene;
	
	//-------------------- Controllers ----------------------------
	
	public static RootController rootController;
	
	public static TopBar topBar;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//----- Window --------------
		window = primaryStage;
		window.getIcons().add(JavaFXTools.getImageFromResourcesFolder("icon.png"));
		window.setTitle("XR3Converter V." + RunTimeVars.APPLICATION_VERSION);
		window.setWidth(JavaFXTools.getVisualScreenWidth() * 0.55);
		window.setHeight(JavaFXTools.getVisualScreenHeight() * 0.55);
		window.setOnCloseRequest(c -> terminateApplication());
		window.centerOnScreen();
		
		//Init all the available controller instances
		initControllers();
		
		// Borderless Scene
		borderlessScene = new BorderlessScene(window, StageStyle.UNDECORATED, rootController, 500, 600);
		borderlessScene.getStylesheets().add(getClass().getResource(RunTimeVars.STYLES + RunTimeVars.APPLICATIONCSS).toExternalForm());
		borderlessScene.setTransparentWindowStyle("-fx-background-color:rgb(0,0,0,0.7); -fx-border-color:firebrick; -fx-border-width:2px;");
		borderlessScene.setMoveControl(topBar);
		window.setScene(borderlessScene);
		window.show();
		window.close();
		
		//Show the window
		window.show();
	}
	
	/**
	 * Create instances of all the appropriate controllers
	 */
	private void initControllers() {
		
		//TopBar
		topBar = new TopBar();
		
		//RootController
		rootController = new RootController();
		rootController.getBorderPane().setTop(topBar);
		
	}
	
	public void terminateApplication() {
		System.exit(0);
	}
	
	public BorderPane getRootBorderPane() {
		return rootController.getBorderPane();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
