package com.app.bulliten_board_application;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * PasswordTools
 */

public class PasswordTools {
	
	public static final SecureRandom rand = new SecureRandom();
	
	public PasswordTools(){
		
	}
	
	public String GenerateSalt(){
		
		String salt = getNextSalt().toString();
		return salt;
		
	}
	public String get_SHA_512_SecurePassword(String passwordToHash, String salt)
	{
	    String generatedPassword = null;
	    try {
	        MessageDigest md = MessageDigest.getInstance("SHA-512");
	        md.update(salt.getBytes("UTF-8"));
	        byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
	        StringBuilder sb = new StringBuilder();
	        for(int i=0; i< bytes.length ;i++)
	        {
	            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        generatedPassword = sb.toString();
	    } 
	    catch (NoSuchAlgorithmException e) 
	    {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    return generatedPassword;
	}
	
	 private byte[] getNextSalt() {
		    byte[] salt = new byte[16];
		    rand.nextBytes(salt);
		    return salt;
		  }


}
