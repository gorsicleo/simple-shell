package hr.fer.oprpp1.jmbag0036523443.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import hr.fer.oprpp1.jmbag0036523443.util.Util;

/**Class provides basic functionality for encrypting and decrypting files and checking SHA checksum.
 * @author gorsicleo
 */
public class Crypto {

	
	/**Method provides functionality for calculating SHA hash value for given file.
	 * @param path of file whose hash is going to be calculated
	 * @return digested hash or null in case of error.
	 */
	public static String checksha(Path path) {
		
		MessageDigest digestor = null;
		
		try {
			digestor = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		InputStream is = null;
		
		try {
			is = Files.newInputStream(path);
			byte[] buffer = new byte[4096];
			int numBytes;
			while ((numBytes = is.read(buffer)) != -1) {
				digestor.update(buffer,0,numBytes);
			}
			
			String digestedHash = Util.bytetohex(digestor.digest());
			return digestedHash;
			
		} catch (IOException ex)  {return null;} 
		
		finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignorable) {
				}
			}
		}
	}

	
	/**Method encrypts given file source with AES crypto-algorithm.
	 * @param source file that will be encrypted
	 * @param destination of encrypted file
	 * @param keyText key of encryption
	 * @param ivText initialization vector
	 * @return true if encryption succeeded, false otherwise
	 */
	public static boolean encrypt(Path source, Path destination,String keyText, String ivText) {
		
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = null;
		
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {return false;}
		
		try {
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) { return false; }
		
		InputStream is = null;
		OutputStream os = null;
		
		try {
			
			is = Files.newInputStream(source);
			os = Files.newOutputStream(destination);
			byte[] inBuffer = new byte[4096];
			byte[] outBuffer = new byte[4096];
			int numBytes;
			
			while ((numBytes = is.read(inBuffer)) != -1) {
				outBuffer = cipher.update(inBuffer,0,numBytes);
				os.write(outBuffer);
			}
			
			outBuffer = cipher.doFinal();
			os.write(outBuffer);
			return true;

		} catch (IOException | BadPaddingException | IllegalBlockSizeException e) { return false; }
		
		finally {
			if (is != null && os != null) {
				try {
					is.close();
					os.close();
				} catch (IOException ignorable) {
				}
			}
		}
	}


	/**Method decrypts given file source with AES crypto-algorithm.
	 * @param source file that will be decrypted
	 * @param destination of decrypted file
	 * @param keyText key of encryption
	 * @param ivText initialization vector
	 * @return true if decryption succeeded, false otherwise
	 */
	public static boolean decrypt(Path source, Path destination, String keyText, String ivText) {
		
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = null;
		
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) { return false; }
		
		try {
			cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) { return false; }
		
		InputStream is = null;
		OutputStream os = null;
		
		try {
			is = Files.newInputStream(source);
			os = Files.newOutputStream(destination);
			byte[] inBuffer = new byte[4096];
			byte[] outBuffer = new byte[4096];
			int numBytes;
			
			while ((numBytes = is.read(inBuffer)) != -1) {
				outBuffer = cipher.update(inBuffer,0,numBytes);
				os.write(outBuffer);
			}
			
			outBuffer = cipher.doFinal();
			os.write(outBuffer);
			return true;
			
		} catch (IOException | BadPaddingException | IllegalBlockSizeException e) { return false;}
		
		finally {
			if (is != null && os != null) {
				try {
					is.close();
					os.close();
				} catch (IOException ignorable) {
				}
			}
		}
	}
}
