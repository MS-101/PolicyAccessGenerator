package security;

import java.util.*;
import org.aspectj.lang.reflect.*;

public aspect Authenticator {
	private static User currentUser = null;
	
	private Scanner scanner = new Scanner(System.in);

	private pointcut voidMethods() : execution(void *(..)) && !within(security.*);
	private pointcut parserMethods(String command) : execution(boolean *(String)) && args(command) && !within(security.*);
	
	before() : voidMethods() {
		MethodSignature methodSignature = (MethodSignature)thisJoinPoint.getSignature();
		String method = methodSignature.getDeclaringTypeName() + "." + methodSignature.getName();
		
		// authenticate before starting program
		if (method.equals(BasicSettings.getMain())) {
			while (!requestLogin());	
		}
	}
	
	after() : voidMethods() {
		MethodSignature methodSignature = (MethodSignature)thisJoinPoint.getSignature();
		String method = methodSignature.getDeclaringTypeName() + "." + methodSignature.getName();
		
		// print extra help command for logout
		if (method.equals(BasicSettings.getHelp())) {
			System.out.println("logout - logout from the current account");	
		}
	}
	
	boolean around(String command) : parserMethods(command) {
		MethodSignature methodSignature = (MethodSignature)thisJoinPoint.getSignature();
		String method = methodSignature.getDeclaringTypeName() + "." + methodSignature.getName();
		
		// parse logout command
		if (method.equals(BasicSettings.getParser()) && command.equals("logout")) {
			logout();
			return true;
		}
		
		return proceed(command);
	}
	
	private void logout() {
		System.out.println("You have logged out of the current account!");
		while (!requestLogin());
	}
	
	private boolean requestLogin() {
		System.out.println("Enter your credentials:");
		System.out.print("name: ");
		String name = scanner.nextLine();
		System.out.print("password: ");
		String password = scanner.nextLine();
		
		if (login(name, PasswordSecurity.hashPassword(password))) {
			System.out.println("Login successful!");
			return true;
		}
		else {
			System.out.println("Incorrect name or password!");
			return false;
		}
	}
	
	private boolean login(String name, String hashedPassword) {
		User user = UserManager.getUser(name);
		if (user != null && user.hashedPassword.equals(hashedPassword)) {
			currentUser = user;
			return true;
		}
		else
			return false;
	}
	
	static User getCurrentUser() {
		return currentUser;
	}

	static boolean hasPermission(Permission permission) {
		return (permission == null || currentUser.hasPermission(permission));
	}
}
