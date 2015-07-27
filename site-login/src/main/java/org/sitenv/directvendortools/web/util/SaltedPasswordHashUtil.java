package org.sitenv.directvendortools.web.util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.security.crypto.password.PasswordEncoder;

public class SaltedPasswordHashUtil implements PasswordEncoder{
	

	@Override
	public String encode(CharSequence rawPassword) {
	      return getSecurePassword(rawPassword.toString(), 32);
	}


	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// TODO Auto-generated method stub
		return validatePassword(rawPassword.toString(), encodedPassword);
	}
	
	public String getSecurePassword(String password, int saltLength) 
	{
		// Create random salt
		
		String securePassowrd = null;
		try 
		{
			String salt = generateSalt(saltLength);
			

			return getSecurePassword(password, salt);
		}
		catch(Exception e)
		{
			securePassowrd = "";
		}
		
		return securePassowrd;
		
	}
	
	
	private String getSecurePassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();
         
        PBEKeySpec spec = new PBEKeySpec(chars, saltBytes, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(saltBytes) + ":" + toHex(hash);
	}
	
	private String generateSalt(int saltLength) throws NoSuchAlgorithmException
	{
		
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[saltLength];
			
		sr.nextBytes(salt);
			
		return salt.toString();
		
	}
	
	private String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
	
	public  boolean validatePassword(String originalPassword, String storedPassword)
    {
		boolean validated = false;
		try
		{
	        String[] parts = storedPassword.split(":");
	        int iterations = Integer.parseInt(parts[0]);
	        byte[] salt = fromHex(parts[1]);
	        byte[] hash = fromHex(parts[2]);
	         
	        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
	        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	        byte[] testHash = skf.generateSecret(spec).getEncoded();
	         
	        int diff = hash.length ^ testHash.length;
	        for(int i = 0; i < hash.length && i < testHash.length; i++)
	        {
	            diff |= hash[i] ^ testHash[i];
	        }
	        validated = diff == 0 ? true :false;
   
		}
		catch (Exception e) 
		{
			
		}
		return validated;
    }

    private byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }


	
}
