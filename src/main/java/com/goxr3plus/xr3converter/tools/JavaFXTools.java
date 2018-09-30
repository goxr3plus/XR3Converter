/**
 * 
 */
package main.java.com.goxr3plus.xr3converter.tools;

import java.io.File;
import java.util.List;

import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.util.Duration;
import main.java.com.goxr3plus.xr3converter.application.Main;
import main.java.com.goxr3plus.xr3converter.converter.model.Media;
import main.java.com.goxr3plus.xr3converter.storage.RunTimeVars;

/**
 * This class has some functions that are not there by default in JavaFX 8
 * 
 * @author GOXR3PLUS
 *
 */
public final class JavaFXTools {
	
	private JavaFXTools() {
	}
	
	/**
	 * Show a notification.
	 *
	 * @param title
	 *            The notification title
	 * @param text
	 *            The notification text
	 * @param duration
	 *            The duration that notification will be visible
	 * @param notificationType
	 *            The notification type
	 */
	public static void showNotification(String title , String text , Duration duration , NotificationType notificationType) {
		Platform.runLater(() -> showNotification(title, text, duration, notificationType, null));
	}
	
	/**
	 * Show a notification.
	 *
	 * @param title
	 *            The notification title
	 * @param text
	 *            The notification text
	 * @param duration
	 *            The duration that notification will be visible
	 * @param notificationType
	 *            The notification type
	 */
	public static void showNotification(String title , String text , Duration duration , NotificationType notificationType , Node graphic) {
		
		try {
			
			//Check if it is JavaFX Application Thread
			if (!Platform.isFxApplicationThread()) {
				Platform.runLater(() -> showNotification(title, text, duration, notificationType, graphic));
				return;
			}
			
			//Show the notification
			showNotification2(title, text, duration, notificationType, graphic);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * Just a helper method for showNotification methods
	 */
	private static void showNotification2(String title , String text , Duration duration , NotificationType notificationType , Node graphic) {
		Notifications notification1;
		
		//Set graphic
		if (graphic == null)
			notification1 = Notifications.create().title(title).text(text).hideAfter(duration).darkStyle().position(Pos.BOTTOM_RIGHT);
		else
			notification1 = Notifications.create().title(title).text(text).hideAfter(duration).darkStyle().position(Pos.BOTTOM_RIGHT).graphic(graphic);
		
		//Show the notification
		switch (notificationType) {
			case CONFIRM:
				notification1.graphic(JavaFXTools.getFontIcon("fas-question-circle", Color.web("#ad14e2"), 32)).show();
				break;
			case ERROR:
				notification1.graphic(JavaFXTools.getFontIcon("fas-times", Color.web("#f83e3e"), 32)).show();
				break;
			case INFORMATION:
				notification1.graphic(JavaFXTools.getFontIcon("fas-info-circle", Color.web("#1496e5"), 32)).show();
				break;
			case SIMPLE:
				notification1.show();
				break;
			case WARNING:
				notification1.graphic(JavaFXTools.getFontIcon("fa-warning", Color.web("#d74418"), 32)).show();
				break;
			case SUCCESS:
				notification1.graphic(JavaFXTools.getFontIcon("fas-check", Color.web("#64ff41"), 32)).show();
				break;
			default:
				break;
		}
	}
	
	/**
	 * Set Graphic Font Icon
	 * 
	 * @param icon
	 * @param iconLiteral
	 * @param color
	 */
	public static void setFontIcon(Labeled node , FontIcon icon , String iconLiteral , Color color) {
		icon.setIconLiteral(iconLiteral);
		icon.setIconColor(color);
		if (node != null)
			node.setGraphic(icon);
	}
	
	/**
	 * Get the requested Font Icon
	 * 
	 * @param iconLiteral
	 * @param color
	 * @param size
	 * @return
	 */
	public static FontIcon getFontIcon(String iconLiteral , Color color , int size) {
		
		//Create the Icon
		FontIcon icon = new FontIcon(iconLiteral);
		
		//Set Icon Color
		icon.setIconColor(color);
		
		//Set Size
		if (size != 0)
			icon.setIconSize(size);
		
		return icon;
	}
	
	/**
	 * Gets the screen width.
	 *
	 * @return The screen <b>Width</b> based on the <b> bounds </b> of the Screen.
	 */
	public static double getScreenWidth() {
		return Screen.getPrimary().getBounds().getWidth();
	}
	
	/**
	 * Gets the screen height.
	 *
	 * @return The screen <b>Height</b> based on the <b> bounds </b> of the Screen.
	 */
	public static double getScreenHeight() {
		return Screen.getPrimary().getBounds().getHeight();
	}
	
	/**
	 * Gets the visual screen width.
	 *
	 * @return The screen <b>Width</b> based on the <b>visual bounds</b> of the Screen.These bounds account for objects in the native windowing system
	 *         such as task bars and menu bars. These bounds are contained by Screen.bounds.
	 */
	public static double getVisualScreenWidth() {
		return Screen.getPrimary().getVisualBounds().getWidth();
	}
	
	/**
	 * Gets the visual screen height.
	 *
	 * @return The screen <b>Height</b> based on the <b>visual bounds</b> of the Screen.These bounds account for objects in the native windowing
	 *         system such as task bars and menu bars. These bounds are contained by Screen.bounds.
	 */
	public static double getVisualScreenHeight() {
		return Screen.getPrimary().getVisualBounds().getHeight();
	}
	
	/**
	 * Use this method to retrieve an image from the resources of the application.
	 *
	 * @param imageName
	 *            the image name
	 * @return Returns an image which is already into the resources folder of the application
	 */
	public static Image getImageFromResourcesFolder(String imageName) {
		return new Image(JavaFXTools.class.getResourceAsStream(RunTimeVars.IMAGES + imageName));
	}
	
	/**
	 * Use this method to retrieve an ImageView from the resources of the application.
	 *
	 * @param imageName
	 *            the image name
	 * @return Returns an ImageView using method getImageFromResourcesFolder(String imageName);
	 */
	public static ImageView getImageViewFromResourcesFolder(String imageName) {
		return new ImageView(getImageFromResourcesFolder(imageName));
	}
	
	/**
	 * This method is used for the drag view of Media
	 * 
	 * @param dragBoard
	 * @param media
	 */
	public static void setDragView(Dragboard dragBoard , Media media) {
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		dragBoard.setDragView(Main.dragViewer.updateMedia(media).snapshot(params, new WritableImage(150, 150)), 50, 0);
	}
	
	/**
	 * This view is used for plain text drag view
	 * 
	 * @param dragBoard
	 * @param title
	 */
	public static void setPlainTextDragView(Dragboard dragBoard , String title) {
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		dragBoard.setDragView(Main.dragViewer.updateDropboxMedia(title).snapshot(params, new WritableImage(150, 150)), 50, 0);
	}
	
	/**
	 * Set System Clipboard
	 * 
	 * @param items
	 */
	public static void setClipBoard(List<File> items) {
		//Get Native System ClipBoard
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		
		// PutFiles
		content.putFiles(items);
		
		//Set the Content
		clipboard.setContent(content);
		
		showNotification("Copied to Clipboard",
				"Files copied to clipboard,you can paste them anywhere on the your system.\nFor example in Windows with [CTRL+V], in Mac[COMMAND+V]", Duration.seconds(3.5),
				NotificationType.INFORMATION);
	}
	
}
