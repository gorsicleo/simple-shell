package hr.fer.oprpp1.jmbag0036523443.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
				System.out.println(numBytes);
				digestor.update(buffer,0,numBytes);
			}
			String digestedHash = Util.bytetohex(digestor.digest());
			System.out.println("Digested hash: "+digestedHash);
			return digestedHash.equalsIgnoreCase(hash);
			
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("Error happened");
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

}
