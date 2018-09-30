/*
 * 
 */
package main.java.com.goxr3plus.xr3converter.converter.model;

import java.io.File;

import javafx.beans.property.SimpleStringProperty;
import main.java.com.goxr3plus.xr3converter.tools.FileTools;

/**
 * Media Files Model
 *
 * @author GOXR3PLUS
 */
public abstract class Media {
	
	/** The title. */
	private SimpleStringProperty title;
	
	/** The file type. */
	private SimpleStringProperty fileType;
	
	/** The file type. */
	private SimpleStringProperty fileSize;
	
	/** The file type. */
	private SimpleStringProperty filePath;
	
	/**
	 * Constructor
	 * 
	 */
	public Media(String absolutePath) {
		
		this.title = new SimpleStringProperty(FileTools.getFileTitle(absolutePath));
		this.filePath = new SimpleStringProperty(absolutePath);
		this.fileType = new SimpleStringProperty(FileTools.getFileExtension(absolutePath));
		this.fileSize = new SimpleStringProperty(FileTools.getFileSizeEdited(new File(absolutePath)));
		
	}
	
	public SimpleStringProperty titleProperty() {
		return title;
	}
	
	public SimpleStringProperty fileTypeProperty() {
		return fileType;
	}
	
	public SimpleStringProperty fileSizeProperty() {
		return fileSize;
	}
	
	public SimpleStringProperty filePathProperty() {
		return filePath;
	}
	
	public String getFilePath() {
		return filePath.get();
	}
	
	public String getTitle() {
		return title.get();
	}
	
}
