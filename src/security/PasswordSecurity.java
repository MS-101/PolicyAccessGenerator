package security;

import java.security.*;

public class PasswordSecurity {
	public static String hashPassword(String password) {
        String hashedPassword = password;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashedBytes = md.digest(password.getBytes());
            
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes)
                sb.append(String.format("%02x", b));
            
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex);
        }
		
		return hashedPassword;
	}
}
