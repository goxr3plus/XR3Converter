package main.java.com.goxr3plus.xr3converter.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FilenameUtils;

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
	
}
