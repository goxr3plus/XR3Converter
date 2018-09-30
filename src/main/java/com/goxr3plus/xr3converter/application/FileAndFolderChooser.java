package main.java.com.goxr3plus.xr3converter.application;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import main.java.com.goxr3plus.xr3converter.tools.general.FileTypeTool;

/**
 * An implementation which combines FileChooser and DirectoryChooser.
 *
 * @author GOXR3PLUS
 */
public class FileAndFolderChooser {
	
	private static SimpleObjectProperty<File> lastKnownDirectoryProperty = new SimpleObjectProperty<>();
	private static SimpleObjectProperty<File> lastKnownMediaDirectoryProperty = new SimpleObjectProperty<>();
	
	private DirectoryChooser directoryChooser = new DirectoryChooser();
	private FileChooser mediaFileChooser = new FileChooser();
	
	private final ExtensionFilter audioFilter;
	private final ExtensionFilter videoFilter;
	private final ExtensionFilter audioVideoFilter;
	
	/**
	 * Constructor
	 */
	public FileAndFolderChooser() {
		directoryChooser.initialDirectoryProperty().bindBidirectional(lastKnownDirectoryProperty);
		mediaFileChooser.initialDirectoryProperty().bindBidirectional(lastKnownMediaDirectoryProperty);
		
		//Special Audio Files Filter
		audioFilter = new FileChooser.ExtensionFilter("Audio Files",
				Stream.of(FileTypeTool.POPULAR_AUDIO_EXTENSIONS_LIST).flatMap(List::stream).map(m -> "*." + m).collect(Collectors.toList()));
		
		//Special Video Files Filter
		videoFilter = new FileChooser.ExtensionFilter("Video Files",
				Stream.of(FileTypeTool.POPULAR_VIDEO_EXTENSIONS_LIST).flatMap(List::stream).map(m -> "*." + m).collect(Collectors.toList()));
		
		//Special Audio+Video Files Filter
		audioVideoFilter = new FileChooser.ExtensionFilter("Audio/Video Files", Stream.of(FileTypeTool.POPULAR_AUDIO_EXTENSIONS_LIST, FileTypeTool.POPULAR_VIDEO_EXTENSIONS_LIST)
				.flatMap(List::stream).map(m -> "*." + m).collect(Collectors.toList()));
		
	}
	
	// -----------------------------------------------------------------------------------------------------------------/
	
	/**
	 * Show's a dialog that allows user to select any directory from the operating system
	 *
	 * @param window
	 *            the window
	 * @return the file
	 */
	public File selectFolder(Stage window) {
		directoryChooser.setTitle("Select a Folder");
		File file = directoryChooser.showDialog(window);
		if (file != null) {
			// Set the property to the directory of the chosenFile so the
			// fileChooser will open here next
			lastKnownDirectoryProperty.setValue(file.getParentFile());
		}
		return file;
	}
	
	// -----------------------------------------------------------------------------------------------------------------/
	
	/**
	 * Prepares to import multiple Song Files and Folders.
	 *
	 * @param window
	 *            the window
	 * @return the list
	 */
	public List<File> importAudioFiles(Stage window) {
		mediaFileChooser.getExtensionFilters().clear();
		mediaFileChooser.getExtensionFilters().addAll(audioFilter);
		mediaFileChooser.setTitle("Select or Drag and Drop Files || Folders into the List");
		List<File> files = mediaFileChooser.showOpenMultipleDialog(window);
		if (files != null) {
			// Set the property to the directory of the chosenFile so the
			// fileChooser will open here next
			lastKnownMediaDirectoryProperty.setValue(files.get(0).getParentFile());
		}
		return files;
		
	}
	
	/**
	 * Prepares to import multiple Song Files and Folders.
	 *
	 * @param window
	 *            the window
	 * @return the list
	 */
	public List<File> importVideoFiles(Stage window) {
		mediaFileChooser.getExtensionFilters().clear();
		mediaFileChooser.getExtensionFilters().addAll(videoFilter);
		mediaFileChooser.setTitle("Select or Drag and Drop Files || Folders into the List");
		List<File> files = mediaFileChooser.showOpenMultipleDialog(window);
		if (files != null) {
			// Set the property to the directory of the chosenFile so the
			// fileChooser will open here next
			lastKnownMediaDirectoryProperty.setValue(files.get(0).getParentFile());
		}
		return files;
		
	}
	
	/**
	 * Prepares to import multiple Song Files and Folders.
	 *
	 * @param window
	 *            the window
	 * @return the list
	 */
	public List<File> importAudioAndVideoFiles(Stage window) {
		mediaFileChooser.getExtensionFilters().clear();
		mediaFileChooser.getExtensionFilters().addAll(audioVideoFilter);
		mediaFileChooser.setTitle("Select or Drag and Drop Files || Folders into the List");
		List<File> files = mediaFileChooser.showOpenMultipleDialog(window);
		if (files != null) {
			// Set the property to the directory of the chosenFile so the
			// fileChooser will open here next
			lastKnownMediaDirectoryProperty.setValue(files.get(0).getParentFile());
		}
		return files;
		
	}
	
}
