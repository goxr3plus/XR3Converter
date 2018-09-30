package main.java.com.goxr3plus.xr3converter.tools.io;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javafx.util.Duration;
import main.java.com.goxr3plus.xr3converter.tools.fx.JavaFXTools;
import main.java.com.goxr3plus.xr3converter.tools.fx.NotificationType;

public class FileTools {
	
	private FileTools() {
	}
	
	/**
	 * Returns the Date the File Created in Format `dd/mm/yyyy`
	 * 
	 * @param file
	 *            The File to be given
	 * @return the Date the File Created in Format `dd/mm/yyyy`
	 */
	public static String getFileCreationDate(File file) {
		Path path = Paths.get(file.getAbsolutePath());
		BasicFileAttributes attr;
		try {
			attr = Files.readAttributes(path, BasicFileAttributes.class);
			
			return new SimpleDateFormat("dd/MM/yyyy").format(attr.creationTime().toMillis());
			
		} catch (IOException e) {
			e.printStackTrace();
			return "oops error! ";
		}
	}
	
	/**
	 * Returns the Time the File Created in Format `h:mm a`
	 * 
	 * @param file
	 *            The File to be given
	 * @return the Time the File Created in Format `HH:mm:ss`
	 */
	public static String getFileCreationTime(File file) {
		Path path = Paths.get(file.getAbsolutePath());
		BasicFileAttributes attr;
		try {
			attr = Files.readAttributes(path, BasicFileAttributes.class);
			
			return new SimpleDateFormat("h:mm a").format(attr.creationTime().toMillis());
			
		} catch (IOException e) {
			e.printStackTrace();
			return "oops error! ";
		}
	}
	
	/**
	 * Gets the file size edited in format "x MiB , y KiB"
	 *
	 * @param file
	 *            the file
	 * @return <b> a String representing the file size in MB and kB </b>
	 */
	public static String getFileSizeEdited(File file) {
		return !file.exists() ? "file missing" : getFileSizeEdited(file.length());
	}
	
	/**
	 * Gets the file size edited in format "x MiB , y KiB"
	 *
	 * @param bytes
	 *            File size in bytes
	 * @return <b> a String representing the file size in MB and kB </b>
	 */
	public static String getFileSizeEdited(long bytes) {
		
		//Find it	
		int kilobytes = (int) ( bytes / 1024 ) , megabytes = kilobytes / 1024;
		if (kilobytes < 1024)
			return kilobytes + " KiB";
		else if (kilobytes > 1024)
			return megabytes + "." + ( kilobytes - ( megabytes * 1024 ) ) + " MiB";
		
		return "error";
		
	}
	
	/**
	 * Returns the creation time. The creation time is the time that the file was created.
	 *
	 * <p>
	 * If the file system implementation does not support a time stamp to indicate the time when the file was created then this method returns an
	 * implementation specific default value, typically the {@link #lastModifiedTime() last-modified-time} or a {@code FileTime} representing the
	 * epoch (1970-01-01T00:00:00Z).
	 *
	 * @param absolutePath
	 *            The File absolute path
	 * @return The File Creation Date in String Format
	 */
	public static String getFileCreationDate(String absolutePath) {
		File file = new File(absolutePath);
		//exists?
		if (!file.exists())
			return "file missing";
		
		BasicFileAttributes attr;
		try {
			attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
		} catch (IOException ex) {
			ex.printStackTrace();
			return "error";
		}
		return ( attr.creationTime() + "" ).replaceAll("T|Z", " ");
	}
	
	/**
	 * Returns the time of last modification.
	 *
	 * <p>
	 * If the file system implementation does not support a time stamp to indicate the time of last modification then this method returns an
	 * implementation specific default value, typically a {@code FileTime} representing the epoch (1970-01-01T00:00:00Z).
	 *
	 * @param absolutePath
	 *            The File absolute path
	 * @return The File Creation Date in String Format
	 */
	public static String getFileLastModifiedDate(String absolutePath) {
		File file = new File(absolutePath);
		//exists?
		if (!file.exists())
			return "file missing";
		
		BasicFileAttributes attr;
		try {
			attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
		} catch (IOException ex) {
			ex.printStackTrace();
			return "error";
		}
		return ( attr.lastModifiedTime() + "" ).replaceAll("T|Z", " ");
	}
	
	/**
	 * Returns the title of the file for example if file name is <b>(club.mp3)</b> it returns <b>(club)</b>
	 *
	 * @param absolutePath
	 *            The File absolute path
	 * @return the File title
	 */
	public static String getFileTitle(String absolutePath) {
		return FilenameUtils.getBaseName(absolutePath);
	}
	
	/**
	 * Returns the name of the file for example if file path is <b>(C:/Give me more/no no/media.ogg)</b> it returns <b>(media.ogg)</b>
	 *
	 * @param absolutePath
	 *            the path
	 * @return the File title+extension
	 */
	public static String getFileName(String absolutePath) {
		return FilenameUtils.getName(absolutePath);
		
	}
	
	/**
	 * Returns the extension of file(without (.)) for example <b>(ai.mp3)->(mp3)</b> and to lowercase (Mp3 -> mp3)
	 *
	 * @param absolutePath
	 *            The File absolute path
	 * @return the File extension
	 */
	public static String getFileExtension(String absolutePath) {
		return FilenameUtils.getExtension(absolutePath).toLowerCase();
		
		// int i = path.lastIndexOf('.'); // characters contained before (.)
		//
		// if the name is not empty
		// if (i > 0 && i < path.length() - 1) 
		// return path.substring(i + 1).toLowerCase()
		//
		// return null
	}
	
	/**
	 * Creates the given File or Folder if not exists and returns the result
	 * 
	 * @param absoluteFilePath
	 *            The absolute path of the File|Folder
	 * @param fileType
	 *            Create DIRECTORY OR FILE ?
	 * @return True if exists or have been successfully created , otherwise false
	 */
	public static boolean createFileOrFolder(String absoluteFilePath , FileType fileType) {
		return createFileOrFolder(new File(absoluteFilePath), fileType);
	}
	
	/**
	 * Creates the given File or Folder if not exists and returns the result
	 * 
	 * @param absoluteFilePath
	 *            The absolute path of the File|Folder
	 * @param fileType
	 *            Create DIRECTORY OR FILE ?
	 * @return True if exists or have been successfully created , otherwise false
	 */
	public static boolean createFileOrFolder(File file , FileType fileType) {
		//Already exists?
		if (file.exists())
			return true;
		//Directory?
		if (fileType == FileType.DIRECTORY)
			return file.mkdir();
		//File?
		try {
			return file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Opens the file with the System default file explorer.
	 *
	 * @param path
	 *            the path
	 */
	public static void openFileInExplorer(String path) {
		
		// Open the Default Browser
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			JavaFXTools.showNotification("Message", "Opening in File Explorer:\n" + getFileName(path), Duration.millis(1500), NotificationType.INFORMATION);
			
			//START: --NEEDS TO BE FIXED!!!!!!----------------NOT WORKING WELL-----
			
			path = path.trim().replaceAll(" +", " ");
			String selectPath = "/select," + path;
			
			//START: Strip one SPACE among consecutive spaces
			LinkedList<String> list = new LinkedList<>();
			StringBuilder sb = new StringBuilder();
			boolean flag = true;
			
			for (int i = 0; i < selectPath.length(); i++) {
				if (i == 0) {
					sb.append(selectPath.charAt(i));
					continue;
				}
				
				if (selectPath.charAt(i) == ' ' && flag) {
					list.add(sb.toString());
					sb.setLength(0);
					flag = false;
					continue;
				}
				
				if (!flag && selectPath.charAt(i) != ' ')
					flag = true;
				
				sb.append(selectPath.charAt(i));
			}
			
			list.add(sb.toString());
			
			list.addFirst("explorer.exe");
			//END: Strip one SPACE among consecutive spaces
			
			//END: --NEEDS TO BE FIXED!!!!!!----------------NOT WORKING WELL-----
			
			try {
				//Open in Explorer and Highlight
				new ProcessBuilder(list).start();
			} catch (IOException ex) {
				ex.printStackTrace();
				JavaFXTools.showNotification("Folder Explorer Fail", "Failed to open file explorer.", Duration.millis(1500), NotificationType.WARNING);
			}
		} else { //For MacOS and Linux
			try {
				Desktop.getDesktop().browseFileDirectory(new File(path));
			} catch (Exception ex) {
				ex.printStackTrace();
				JavaFXTools.showNotification("Not Supported", "This function is only supported in Windows \n I am trying my best to implement it and on other operating systems :)",
						Duration.millis(1500), NotificationType.WARNING);
			}
		}
		
	}
	
	/**
	 * Copy a file from source to destination.
	 *
	 * @param source
	 *            the source
	 * @param destination
	 *            the destination
	 * @return True if succeeded , False if not
	 */
	public static boolean copy(String source , String destination) {
		boolean succeess = true;
		
		//System.out.println("Copying ->" + source + "\n\tto ->" + destination)
		
		try {
			Files.copy(Paths.get(source), Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			ex.printStackTrace();
			succeess = false;
		}
		
		return succeess;
		
	}
	
	/**
	 * Copy a file from source to destination.
	 *
	 * @param source
	 *            the source
	 * @param destination
	 *            the destination
	 * @return True if succeeded , False if not
	 */
	public static boolean copy(InputStream source , String destination) {
		boolean succeess = true;
		
		//System.out.println("Copying ->" + source + "\n\tto ->" + destination)
		
		try {
			System.out.println(Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING));
		} catch (IOException ex) {
			ex.printStackTrace();
			succeess = false;
		}
		
		return succeess;
	}
	
	/**
	 * Moves a file to a different location.
	 *
	 * @param source
	 *            the source
	 * @param destination
	 *            the dest
	 * @return true, if successful
	 */
	public static boolean move(String source , String destination) {
		boolean succeess = true;
		
		//System.out.println("Moving ->" + source + "\n\tto ->" + destination)
		
		try {
			Files.move(Paths.get(source), Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			ex.printStackTrace();
			succeess = false;
		}
		
		return succeess;
	}
	
	/**
	 * Deletes Directory of File.
	 *
	 * @param source
	 *            The File to be deleted | either if it is directory or File
	 * @return true, if successful
	 */
	public static boolean deleteFile(File source) {
		
		if (source.isDirectory())  // Directory
			try {
				FileUtils.deleteDirectory(source);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		else if (source.isFile() && !source.delete()) { // File
			JavaFXTools.showNotification("Message", "Can't delete file:\n(" + source.getName() + ") cause is in use by a program.", Duration.millis(2000),
					NotificationType.WARNING);
			return false;
		}
		
		return true;
	}
	
}
