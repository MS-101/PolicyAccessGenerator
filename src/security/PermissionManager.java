package security;

import java.nio.file.*;
import java.util.*;
import org.json.*;

public class PermissionManager {
	private static Map<String, Permission> permissions = new HashMap<>();
	private static boolean permissionsLoaded = false;
	
	private static void loadPermissions() {
		try {
	        String filePath = "config/permissions.json";
	        String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
	        JSONArray jsonArray = new JSONArray(jsonString);

	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONObject jsonObject = jsonArray.getJSONObject(i);

	            String name = jsonObject.getString("name");
	            String description = jsonObject.getString("description");

	            Permission permission = new Permission(name, description);
	            permissions.put(permission.name, permission);
	        }
		} catch (Exception ex) {
			System.out.println("Error occured while reading permission configuration.");
		} finally {
			permissionsLoaded = true;
		}
	}
	
	public static Permission getPermission(String name) {
		if (!permissionsLoaded)
			loadPermissions();
		return permissions.get(name);
	}
}
