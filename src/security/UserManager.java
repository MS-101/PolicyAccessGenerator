package security;

import java.util.*;
import java.nio.file.*;
import org.json.*;

public class UserManager {
	private static Map<String, User> users = new HashMap<>();
	private static boolean usersLoaded = false;
	
	private static void loadUsers() {
		try {
	        String filePath = "config/users.json";
	        String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
	        JSONArray jsonArray = new JSONArray(jsonString);

	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONObject jsonObject = jsonArray.getJSONObject(i);

	            String username = jsonObject.getString("username");
	            String password = jsonObject.getString("password");
	            JSONArray roles = jsonObject.getJSONArray("roles");

	            User user = new User(username, password);
                for (int j = 0; j < roles.length(); j++) {
                    String roleName = roles.getString(j);
                    Role role = RoleManager.getRole(roleName);
                    if (role != null)
                    	user.addRole(role);
                }
                users.put(user.username, user);
	        }
		} catch (Exception ex) {
			System.out.println("Error occured while reading user configuration.");
		} finally {
			usersLoaded = true;
		}
	}
	
	public static User getUser(String username) {
		if (!usersLoaded)
			loadUsers();
		return users.get(username);
	}
}
