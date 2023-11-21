package security;

import java.util.*;

public class Policy {
	String method;
	Map<String, Permission> permissions = new HashMap<>();
	
	public Policy(String method) {
		this.method = method;
	}
	
	public void addPermission(Permission permission) {
		permissions.put(permission.name, permission);
	}
}
