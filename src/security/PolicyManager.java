package security;

import java.nio.file.*;
import java.util.*;
import org.json.*;

public class PolicyManager {
	private static Map<String, Policy> policies = new HashMap<>();
	private static boolean policiesLoaded = false;
	
	private static void loadPolicies() {
		try {
	        String filePath = "config/policies.json";
	        String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
	        JSONArray jsonArray = new JSONArray(jsonString);

	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONObject jsonObject = jsonArray.getJSONObject(i);

	            String method = jsonObject.getString("method");
	            JSONArray permissions = jsonObject.getJSONArray("permissions");

	            Policy policy = new Policy(method);
                for (int j = 0; j < permissions.length(); j++) {
                    String permissionName = permissions.getString(j);
                    Permission permission = PermissionManager.getPermission(permissionName);
                    if (permission != null)
                    	policy.addPermission(permission);
                }
                policies.put(policy.method, policy);
	        }
		} catch (Exception ex) {
			System.out.println("Error occured while reading policy configuration.");
		} finally {
			policiesLoaded = true;
		}
	}
	
	public static Policy getPolicy(String name) {
		if (!policiesLoaded)
			loadPolicies();
		return policies.get(name);
	}
}
