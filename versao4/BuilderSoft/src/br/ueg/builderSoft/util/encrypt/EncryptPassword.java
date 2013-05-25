package br.ueg.builderSoft.util.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
   
 public class EncryptPassword {     
	 
      public static String encrypt(String password) {                
                MessageDigest algorithm;
				try {
					algorithm = MessageDigest.getInstance("SHA-512");
					byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));                
                    
	                StringBuilder hexString = new StringBuilder();
	                for (byte b : messageDigest) {
	                  hexString.append(String.format("%02X", 0xFF & b));
	                }
	                String senha = hexString.toString();   
	                return senha;
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				return null;                         
      }      
 }   


