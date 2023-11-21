package security;

import java.util.*;

public class Role {
	String name;
	String description;
	Map<String, Permission> permissions = new HashMap<>();
	
	public Role(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public void addPermission(Permission permission) {
		permissions.put(permission.name, permission);
	}
	
	public Boolean hasPermission(Permission permission) {
		return (permissions.containsKey(permission.name));
	}
}
