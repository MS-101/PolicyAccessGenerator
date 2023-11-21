package security;

import java.nio.file.*;
import org.json.*;

public class BasicSettings {
	private static String main = "";
	private static String help = "";
	private static String parser = "";
	private static boolean settingsLoaded = false;
	
	private static void loadSettings() {
		try {
	        String filePath = "config/basic.json";
	        String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
	        JSONObject jsonObject = new JSONObject(jsonString);

	        main = jsonObject.getString("main");
	        help = jsonObject.getString("help");
	        parser = jsonObject.getString("parser");
		} catch (Exception ex) {
			System.out.println("Error occured while reading basic settings.");
		} finally {
			settingsLoaded = true;
		}
	}
	
	public static String getMain() {
		if (!settingsLoaded)
			loadSettings();
		return main;
	}
	
	public static String getHelp() {
		if (!settingsLoaded)
			loadSettings();
		return help;
	}
	
	public static String getParser() {
		if (!settingsLoaded)
			loadSettings();
		return parser;
	}
}
