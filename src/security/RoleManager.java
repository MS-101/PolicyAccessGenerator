package security;

import java.nio.file.*;
import java.util.*;
import org.json.*;

public class RoleManager {
	private static Map<String, Role> roles = new HashMap<>();
	private static boolean rolesLoaded = false;
	
	private static void loadRoles() {
		try {
	        String filePath = "config/roles.json";
	        String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
	        JSONArray jsonArray = new JSONArray(jsonString);

	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONObject jsonObject = jsonArray.getJSONObject(i);

	            String name = jsonObject.getString("name");
	            String description = jsonObject.getString("description");
	            JSONArray permissions = jsonObject.getJSONArray("permissions");

	            Role role = new Role(name, description);
                for (int j = 0; j < permissions.length(); j++) {
                    String permissionName = permissions.getString(j);
                    Permission permission = PermissionManager.getPermission(permissionName);
                    if (permission != null)
                    	role.addPermission(permission);
                }
                roles.put(role.name, role);
	        }			
		} catch (Exception ex) {
			System.out.println("Error occured while reading role configuration.");
		} finally {
			rolesLoaded = true;
		}
	}
	
	public static Role getRole(String name) {
		if (!rolesLoaded)
			loadRoles();
		return roles.get(name);
	}
}
