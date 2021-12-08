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

public class Crypto {

	public static boolean checksha(String hash, Path path) {
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
			return digestedHash.equalsIgnoreCase(hash);
			
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
			// Obradi pogrešku
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignorable) {
				}
			}
		}
		

	}

	
	public static boolean encrypt(Path source, Path destination,String keyText, String ivText) {
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

			
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
			// Obradi pogrešku
		} catch (BadPaddingException ex) {
			ex.printStackTrace();
			return false;
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			if (is != null && os != null) {
				try {
					is.close();
					os.close();
				} catch (IOException ignorable) {
				}
			}
		}
		
		

	}


	public static Object decrypt(Path source, Path destination, String keyText, String ivText) {
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

			
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
			// Obradi pogrešku
		} catch (BadPaddingException ex) {
			ex.printStackTrace();
			return false;
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
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
