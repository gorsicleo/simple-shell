package hr.fer.oprpp1.jmbag0036523443.crypto;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**Class provides user friendly interface for functionality of Crypto class
 * @author gorsicleo
 *
 */
public class CryptoDemo {

	
	private static final String DECRYPTION_SUCCESS = "Decryption completed. Generated file %s based on file %s.";
	private static final String ERROR_MESSAGE = "Error occured!";
	private static final String ENCRYPTION_SUCCESS = "Encryption completed. Generated file %s based on file %s";
	private static final String IV_PROMPT = "Please provide initialization vector as hex-encoded text (32 hex-digits): \n >";
	private static final String SHA_PROMPT = "Please provide expected sha-256 digest for hw05test.bin: \n >";
	private static final String PASSWORD_PROMPT = "Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits): \n >";
	private static final String DIGEST_SUCCESS = "Digesting completed. Digest of hw05test.bin matches expected digest.";
	private static final String DIGEST_FAIL = "Digesting completed. Digest of hw05test.bin does not match the expected digest. Digest was: ";

	public static void main(String[] args) {
		
		if (args.length < 2 || args.length > 3) {
			System.out.println("Inavlid number of arguments!");
			return;
		}
		
		Scanner sc = new Scanner(System.in);
		
		String func = args[0];
		String src = args[1];
		String dest = null;
		String password;
		String iv;
		
		
		
		if (args.length == 3) {
			dest = args[2];
		}
		
		switch (func) {
		
		case "checksha":
			System.out.print(SHA_PROMPT);
			String hash = sc.nextLine();
			String digest = Crypto.checksha(Paths.get(src));
			
			if (digest.equals(hash.trim())) {
				System.out.println(DIGEST_SUCCESS);
			} else {
				System.out.println(DIGEST_FAIL+digest);
			}
			break;
			
		case "encrypt":
			System.out.print(PASSWORD_PROMPT);
			password = sc.nextLine();
			System.out.print(IV_PROMPT);
			iv = sc.nextLine();
			if (Crypto.encrypt(Paths.get(src), Paths.get(dest), password, iv)) {
				System.out.println(String.format(ENCRYPTION_SUCCESS, dest, src));
			} else {
				System.out.println(ERROR_MESSAGE);
			}
			break;
			
		case "decrypt":
			System.out.print(PASSWORD_PROMPT);
			password = sc.nextLine();
			System.out.print(IV_PROMPT);
			iv = sc.nextLine();
			if (Crypto.decrypt(Paths.get(src), Paths.get(dest), password, iv)) {
				System.out.println(String.format(DECRYPTION_SUCCESS, dest, src));
			} else {
				System.out.println(ERROR_MESSAGE);
			}
			break;

		default:
			break;
		}
		
		sc.close();
		
		
	}
}
