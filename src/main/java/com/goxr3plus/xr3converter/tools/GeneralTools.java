package main.java.com.goxr3plus.xr3converter.tools;

import java.io.File;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class GeneralTools {
	
	private GeneralTools() {
	}
	
	/**
	 * Returns a String with a fixed number of letters.
	 *
	 * @param s
	 *            the string
	 * @param letters
	 *            the letters
	 * @return A substring(or the current given string) based on the letters that have to be cut plus "..."
	 */
	public static String getMinString(String s , int letters) {
		return s.length() < letters ? s : s.substring(0, letters) + "...";
	}
	
	/**
	 * Returns a String with a fixed number of letters without "..." at the end of String
	 *
	 * @param s
	 *            the string
	 * @param letters
	 *            the letters
	 * @return A substring(or the current given string) based on the letters that have to be cut without adding "..." to the end of string
	 */
	public static String getMinString2(String s , int letters) {
		return s.length() < letters ? s : s.substring(0, letters);
	}
	
	/**
	 * Returns a number with more than 3 digits [ Example 1000 as 1.000] with dots every 3 digits
	 * 
	 * @param number
	 * @return A number with more than 3 digits [ Example 1000 as 1.000] with dots every 3 digits
	 */
	public static String getNumberWithDots(int number) {
		return String.format(Locale.US, "%,d", number).replace(",", ".");
	}
	
	/**
	 * Returns the current hour in format h:mm a
	 *
	 * @return the Returns the current hour in format h:mm a
	 */
	public static String getLocalTime() {
		return LocalTime.now().format(DateTimeFormatter.ofPattern("h:mm a"));
	}
	
	/**
	 * Returns the Local Date in format dd/MM/yyyy
	 *
	 * @return the local date in format dd/MM/yyyy
	 */
	public static String getCurrentDate() {
		return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
	}
	
	/**
	 * Returns the absolute path of the current directory in which the given class file is.
	 * 
	 * @param classs
	 *            * @return The absolute path of the current directory in which the class file is. <b>[it ends with File.Separator!!]</b>
	 * @author GOXR3PLUS[StackOverFlow user] + bachden [StackOverFlow user]
	 */
	public static final String getBasePathForClass(Class<?> classs) {
		
		// Local variables
		File file;
		String basePath = "";
		boolean failed = false;
		
		// Let's give a first try
		try {
			file = new File(classs.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			
			basePath = ( file.isFile() || file.getPath().endsWith(".jar") || file.getPath().endsWith(".zip") ) ? file.getParent() : file.getPath();
		} catch (URISyntaxException ex) {
			failed = true;
			Logger.getLogger(classs.getName()).log(Level.WARNING, "Cannot firgue out base path for class with way (1): ", ex);
		}
		
		// The above failed?
		if (failed)
			try {
				file = new File(classs.getClassLoader().getResource("").toURI().getPath());
				basePath = file.getAbsolutePath();
				
				// the below is for testing purposes...
				// starts with File.separator?
				// String l = local.replaceFirst("[" + File.separator +
				// "/\\\\]", "")
			} catch (URISyntaxException ex) {
				Logger.getLogger(classs.getName()).log(Level.WARNING, "Cannot firgue out base path for class with way (2): ", ex);
			}
		
		// fix to run inside Eclipse
		if (basePath.endsWith(File.separator + "lib") || basePath.endsWith(File.separator + "bin") || basePath.endsWith("bin" + File.separator)
				|| basePath.endsWith("lib" + File.separator)) {
			basePath = basePath.substring(0, basePath.length() - 4);
		}
		// fix to run inside NetBeans
		if (basePath.endsWith(File.separator + "build" + File.separator + "classes")) {
			basePath = basePath.substring(0, basePath.length() - 14);
		}
		// end fix
		if (!basePath.endsWith(File.separator))
			basePath += File.separator;
		
		return basePath;
	}
	
	/**
	 * Checks if a web site is reachable using ping command.
	 *
	 * @param host
	 *            the host
	 * @return <b> true </b> if Connected on Internet,<b> false </b> if not.
	 */
	public static boolean isReachableByPing(String host) {
		try {
			
			// Start a new Process
			Process process = Runtime.getRuntime().exec("ping -" + ( System.getProperty("os.name").toLowerCase().startsWith("windows") ? "n" : "c" ) + " 1 " + host);
			
			//Wait for it to finish
			process.waitFor();
			
			//Check the return value
			return process.exitValue() == 0;
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
}
