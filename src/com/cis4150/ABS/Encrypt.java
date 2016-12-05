package com.cis4150.ABS;
//This code was taken and modified from http://www.codejava.net/coding/file-encryption-and-decryption-simple-example


import org.apache.commons.io.FileUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Encryption Class to encrypt and decrypt files being backed up
 * @author David C Walsh
 * 12-1-16
 */
public class Encrypt
{
	public static void main (String [] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException
	{
		String key = "This key is weak"; // note this key has to be exactly 16 bytes
		File inputFile = FileUtils.getFile("C:\\Users\\david\\Documents\\test.txt");
		File outputFile = FileUtils.getFile("C:\\Users\\david\\Documents\\test.txt");
		
		// encryptFile(key,inputFile, outputFile);
		decryptFile(key, inputFile,outputFile);
		
	}
	/**
	 * Encrypt the file that is being backedup
	 * @param key the key used to encrypt the file
	 * @param inputFile the file being encrypted
	 * @param outputFile the encrypted inputFile
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IOException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	 public static void encryptFile (String key, File inputFile, File outputFile) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IOException, IllegalBlockSizeException, BadPaddingException
	 {
		 Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
		 Cipher cipher = Cipher.getInstance("AES");
         cipher.init(Cipher.ENCRYPT_MODE, secretKey);
         
		 FileInputStream inputStream = new FileInputStream(inputFile);
		 byte[] inputBytes = new byte[(int) inputFile.length()];
         inputStream.read(inputBytes);
         
         byte[] outputBytes = cipher.doFinal(inputBytes);
         
         FileOutputStream outputStream = new FileOutputStream(outputFile);
         outputStream.write(outputBytes);
          
         inputStream.close();
         outputStream.close();
	 }
	 
	 /**
	  * Decrypt the file when it is being restored
	  * @param key the key used to decrypt the file
	  * @param inputFile the encrypted file that needs to be decrypted
	  * @param outputFile the decrypted file from the encrypted file
	  * @throws InvalidKeyException
	  * @throws IllegalBlockSizeException
	  * @throws BadPaddingException
	  * @throws IOException
	  * @throws NoSuchAlgorithmException
	  * @throws NoSuchPaddingException
	  */
	 public static void decryptFile (String key, File inputFile, File outputFile) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, NoSuchAlgorithmException, NoSuchPaddingException
	 {
		 Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
		 Cipher cipher = Cipher.getInstance("AES");
         cipher.init(Cipher.DECRYPT_MODE, secretKey);
         
		 FileInputStream inputStream = new FileInputStream(inputFile);
		 byte[] inputBytes = new byte[(int) inputFile.length()];
         inputStream.read(inputBytes);
         
         byte[] outputBytes = cipher.doFinal(inputBytes);
         
         FileOutputStream outputStream = new FileOutputStream(outputFile);
         outputStream.write(outputBytes);
          
         inputStream.close();
         outputStream.close();
	 }

	 
}
