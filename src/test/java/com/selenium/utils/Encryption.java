package com.selenium.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Encryption {
	private static final String aesEncryptionAlgorithm = "AES";
	private static final String characterEncoding = "UTF-8";
	private String cipherText = "";	
	private String decryptedText = null;
	private SecretKey secretkey = null;
	private String textToBeEncrypted = null;
	public Encryption()	{;}

	// Constructors
	public Encryption(String textToBeEncrypted)
	{
		generateSecretKey();
		this.textToBeEncrypted = textToBeEncrypted;
		Encrypt(textToBeEncrypted,secretkey);
	}

	public Encryption(String textToBeDecrypted, String encryptionKey)
	{
		try {
			decrypt(textToBeDecrypted,encryptionKey);
		} catch (Exception e) {
			{
				PrintData.printErrorString("EXCEPTION OCCURED while converting the ciphertext: " + textToBeDecrypted + " with the key: " + encryptionKey);
				e.printStackTrace();
			}			
		}
	}
	public String decrypt(String cipherText, String encryptionKey)
	{
		try{
			// Instantiate the cipher
			//			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			//			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");		
			// Instantiate the cipher
			Cipher cipher = Cipher.getInstance(aesEncryptionAlgorithm);
			//	byte[] encodedKey = new Base64().decode(encryptionKey.getBytes(characterEncoding));
			//	secretkey = new SecretKeySpec(encodedKey,0,encodedKey.length,"AES");
			//			this.setSecretKey(encryptionKey);
			cipher.init(Cipher.DECRYPT_MODE, this.setSecretKey(encryptionKey));		    
			byte[] encryptedTextBytes = Base64.decodeBase64(cipherText);
			byte[] decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
			decryptedText = new String(decryptedTextBytes,Charset.forName(characterEncoding));
			return decryptedText;
		}
		catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException  e) {
			PrintData.printErrorString("EXCEPTION OCCURED while decrypting the ciphertext: " + cipherText + " with the key: " + encryptionKey);
			e.printStackTrace();
		}
		return null;
	}
	public String Encrypt(String textToBeEncrypted, SecretKey encryptionKey)
	{
		try{
			// Instantiate the cipher
			Cipher cipher = Cipher.getInstance(aesEncryptionAlgorithm);
			cipher.init(Cipher.ENCRYPT_MODE, encryptionKey);			
			byte[] encryptedTextBytes = cipher.doFinal(textToBeEncrypted.getBytes(characterEncoding));
			cipherText = new Base64().encodeAsString(encryptedTextBytes); 
			return cipherText;
		}
		catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | UnsupportedEncodingException e) {
			PrintData.printErrorString("EXCEPTION OCCURED while encrypting the ciphertext: " + cipherText + " with the key: " + getEncryptionKey());
			e.printStackTrace();
		}
		return null;
	}
	public String Encrypt(String textToBeEncrypted, String encryptionKey)
	{  
		try{
			// Instantiate the cipher
			byte[] encodedKey = new Base64().decode(encryptionKey.getBytes(characterEncoding));
			secretkey = new SecretKeySpec(encodedKey,0,encodedKey.length,aesEncryptionAlgorithm);
			Cipher cipher = Cipher.getInstance(aesEncryptionAlgorithm);
			cipher.init(Cipher.ENCRYPT_MODE, secretkey);
			byte[] encryptedTextBytes = cipher.doFinal(textToBeEncrypted.getBytes(characterEncoding));
			cipherText = new Base64().encodeAsString(encryptedTextBytes); 
			return cipherText;
		}
		catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | UnsupportedEncodingException e) {
			PrintData.printErrorString("EXCEPTION OCCURED while encrypting the ciphertext: " + cipherText + " with the key: " + encryptionKey);
			e.printStackTrace();
		}
		return null;
	}
	public SecretKey generateSecretKey()
	{		
		try {
			SecureRandom random = new SecureRandom();
			byte[] key = new byte[16];
			random.nextBytes(key);
			secretkey =  new SecretKeySpec(key,aesEncryptionAlgorithm);		
			return secretkey;
		}catch (Exception e) {			
			PrintData.printErrorString("EXCEPTION occured while generating the encryption key for " + textToBeEncrypted);
			e.printStackTrace();
		}
		return null;
	}
	public String getCipherText()
	{
		return cipherText;
	}

	public String getDecryptedText()
	{
		return decryptedText;
	}

	public String getEncryptionKey()
	{
		return Base64.encodeBase64String(secretkey.getEncoded());
	}

	//Getters and setters
	public void setCipherText(String cipherText) {		
		this.cipherText = cipherText;
	}

	public SecretKey setSecretKey(String encryptionKey)
	{
		byte[] encodedKey=null;
		if(encryptionKey.getBytes().length==24)
		{
			try {
				encodedKey = new Base64().decode(encryptionKey.getBytes(characterEncoding));
				secretkey = new SecretKeySpec(encodedKey,0,encodedKey.length,aesEncryptionAlgorithm);
				return secretkey;
			} catch (UnsupportedEncodingException e) {
				PrintData.printErrorString("EXCEPTION occured while casting the encryption key " + encryptionKey);
				e.printStackTrace();
				return null;
			}
		}
		else
			throw new IllegalArgumentException("Invalid Encryption key length : " +  encryptionKey.getBytes().length + 
					". Encryption key " + encryptionKey + " should be of 24 bytes.");
	}
}