package main.java.com.goxr3plus.xr3converter.tools.general;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.java.com.goxr3plus.xr3converter.tools.io.FileTools;

public final class FileTypeTool {
	
	public static final List<String> POPULAR_AUDIO_EXTENSIONS_LIST = Arrays.asList("mp3", "wav", "ogg", "opus", "aac", "flac", "aiff", "au", "speex", "webm", "wma", "amr", "ape",
			"awb", "dct", "dss", "dvf", "aa", "aax", "act", "m4a", "m4b", "m4p", "mpc", "msv", "oga", "mogg", "raw", "tta", "aifc", "ac3", "spx");
	public static final List<String> POPULAR_VIDEO_EXTENSIONS_LIST = Arrays.asList("mp4", "flv", "avi", "wmv", "mov", "3gp", "webm", "mkv", "vob", "yuv", "m4v", "svi", "3g2",
			"f4v", "f4p", "f4a", "f4b", "swf");
	
	//Java 7 Way and back
	private static final Set<String> ACCEPTED_AUDIO_EXTENSIONS = new HashSet<>(POPULAR_AUDIO_EXTENSIONS_LIST);
	private static final Set<String> ACCEPTED_VIDEO_EXTENSIONS = new HashSet<>(POPULAR_VIDEO_EXTENSIONS_LIST);
	
	public static final Set<String> POPULAR_AUDIO_EXTENSIONS = new HashSet<>(POPULAR_AUDIO_EXTENSIONS_LIST);
	private static final Set<String> POPULAR_VIDEO_EXTENSIONS = new HashSet<>(POPULAR_VIDEO_EXTENSIONS_LIST);
	
	//---------------------------------------------------------------------------------
	
	private FileTypeTool() {
	}
	
	//---------------------------------------------------------------------------------
	
	/**
	 * 1)Checks if this file is <b>audio</b><br>
	 * 2)If is supported by the application.
	 * 
	 * @param fileName
	 *            The File Name
	 * @return True if the type is supported or else False
	 */
	public static boolean isAudioSupported(String fileName) {
		String extension = FileTools.getFileExtension(fileName);
		return extension != null && ACCEPTED_AUDIO_EXTENSIONS.contains(extension);
	}
	
	/**
	 * 1)Checks if this file is <b>video</b><br>
	 * 2)If is supported by the application.
	 * 
	 * @param fileName
	 *            The File Name
	 * @return True if the type is supported or else False
	 */
	public static boolean isVideoSupported(String fileName) {
		String extension = FileTools.getFileExtension(fileName);
		return extension != null && ACCEPTED_VIDEO_EXTENSIONS.contains(extension);
	}
	
	/**
	 * 1)Checks if this file is <b>Audio</b><br>
	 * 2)If is supported by the application.
	 * 
	 * @param fileName
	 *            The File Name
	 * @return True if the file is an Audio else false
	 */
	public static boolean isAudio(String fileName) {
		String extension = FileTools.getFileExtension(fileName);
		return extension != null && POPULAR_AUDIO_EXTENSIONS.contains(extension);
	}
	
	/**
	 * 1)Checks if this file is <b>Audio</b><br>
	 * 2)If is supported by the application.
	 * 
	 * @param extension
	 *            File extension
	 * @return True if the file is an Audio else false
	 */
	public static boolean isAudioCheckExtension(String extension) {
		return extension != null && POPULAR_AUDIO_EXTENSIONS.contains(extension);
	}
	
	/**
	 * 1)Checks if this file is <b>Video</b><br>
	 * 2)If is supported by the application.
	 * 
	 * @param fileName
	 *            The File Name
	 * @return True if the file is an Video else false
	 */
	public static boolean isVideo(String fileName) {
		String extension = FileTools.getFileExtension(fileName);
		return extension != null && POPULAR_VIDEO_EXTENSIONS.contains(extension);
	}
	
	/**
	 * 1)Checks if this file is <b>Video</b><br>
	 * 2)If is supported by the application.
	 * 
	 * @param extension
	 *            File extension
	 * @return True if the file is an Video else false
	 */
	public static boolean isVideoCheckExtension(String extension) {
		return extension != null && POPULAR_VIDEO_EXTENSIONS.contains(extension);
	}
	
}
