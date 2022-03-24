package BD.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
/**
 * 
 * @author Sonya Ndomkeu
 */
public class SHA256 {
	
	private static final int SALT_SIZE = 32;
	
	
	private SHA256() {}
	
	
	/**
         * 
         * @param message
         * @return 
         */
        public static String hash(String message) {
		byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
		
		MessageDigest digest = null;
		try {
                    digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
		}
		
		byte[] hashedBytes = digest.digest(messageBytes);
		
		return new String(Base64.getUrlEncoder().encode(hashedBytes), StandardCharsets.UTF_8);
	}
	
	
	/**
         * 
         * @param hash
         * @param message
         * @return 
         */
	public static boolean checkHash(String hash, String message) {
                byte[] hashedBytes = Base64.getUrlDecoder().decode(hash.getBytes(StandardCharsets.UTF_8));
            
		byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
		
		MessageDigest digest = null;
		try {
                    digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
		}
		
		byte[] newHashedBytes = digest.digest(messageBytes);
		
		if (hashedBytes.length != newHashedBytes.length) {
                    return false;
		}
		
		for (int i = 0; i < hashedBytes.length; i++) {
                    if (hashedBytes[i] != newHashedBytes[i]) {
                        return false;
                    }
		}
		
		return true;
	}
        
        
        
      
	
}
