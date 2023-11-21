package security;

import java.util.*;

public class User {
	String username;
	String hashedPassword;
	List<Role> roles = new LinkedList<>();
	
	public User(String username, String hashedPassword) {
		this.username = username;
		this.hashedPassword = hashedPassword;
	}
	
	protected void addRole(Role role) {
		roles.add(role);
	}
	
	public boolean hasPermission(Permission permission) {
		for (Role role : roles) {
			if (role.hasPermission(permission))
				return true;
		}
		
		return false;
	}
}
