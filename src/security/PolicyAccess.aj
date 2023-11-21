package security;

import java.util.*;
import org.aspectj.lang.reflect.*;

public aspect PolicyAccess {
	private pointcut voidMethods() : execution(void *(..)) && !within(security.*);

	void around() : voidMethods() {
		MethodSignature methodSignature = (MethodSignature)thisJoinPoint.getSignature();
		String method = methodSignature.getName();
	    
		Policy policy = PolicyManager.getPolicy(method);
		if (policy != null) {
			boolean permissionMissing = false;
			
		    for (Map.Entry<String, Permission> entry : policy.permissions.entrySet()) {
		    	Permission permission = entry.getValue();
		    	if (!Authenticator.hasPermission(permission)) {
		    		System.out.println("You don't have the required permission - " + permission.name + " (" + permission.description + ") !");
		    		permissionMissing = true;
		    	}
		    }
		    
		    if (permissionMissing)
		    	return;
		}
	
		proceed();
	}
}
