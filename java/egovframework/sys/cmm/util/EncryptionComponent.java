package egovframework.sys.cmm.util;

import java.security.MessageDigest;

import org.springframework.stereotype.Component;

@Component("encryptionComponent")
public class EncryptionComponent {
	
	/**
	 * 암호화
	 * SHA-256
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public String sha256(String password) throws Exception {
		
		StringBuffer hexString = new StringBuffer();
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(password.getBytes("UTF-8"));
		
		for(int i = 0; i < hash.length; i++) {
			
			String hex = Integer.toHexString(0xff & hash[i]);
			
			if(hex.length() == 1) {
				hexString.append("0");
			}
			
			hexString.append(hex);
		}
		
		return hexString.toString();
	}
}
