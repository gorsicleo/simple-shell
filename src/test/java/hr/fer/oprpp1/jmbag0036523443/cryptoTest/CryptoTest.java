package hr.fer.oprpp1.jmbag0036523443.cryptoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.jmbag0036523443.crypto.Crypto;

public class CryptoTest {

	@Test
	public void digestTest() {
		Path path = Paths.get("./src/main/resources/hw05test.bin");
		String hash = "2e7b3a91235ad72cb7e7f6a721f077faacfeafdea8f3785627a5245bea112598";
		assertEquals(true,Crypto.checksha(hash, path));
	}
	
	@Test
	@Disabled
	public void encryptTest() {
		Path source = Paths.get("./src/main/resources/hw05.pdf");
		Path destination = Paths.get("./src/main/resources/hw05.crypted.pdf");
		String keyText = "e52217e3ee213ef1ffdee3a192e2ac7e";
		String ivText = "000102030405060708090a0b0c0d0e0f";
		assertEquals(true, Crypto.encrypt(source, destination, keyText, ivText));
	}
	@Test
	@Disabled
	public void decryptTest() {
		Path source = Paths.get("./src/main/resources/hw05.crypted.pdf");
		Path destination = Paths.get("./src/main/resources/hw05orig.pdf");
		String keyText = "e52217e3ee213ef1ffdee3a192e2ac7e";
		String ivText = "000102030405060708090a0b0c0d0e0f";
		assertEquals(true, Crypto.decrypt(source, destination, keyText, ivText));
	}
	
	@Test
	public void decryptTest2() {
		Path source = Paths.get("./src/main/resources/hw05part2.bin");
		Path destination = Paths.get("./src/main/resources/hw05part2.pdf");
		String keyText = "e52217e3ee213ef1ffdee3a192e2ac7e";
		String ivText = "000102030405060708090a0b0c0d0e0f";
		assertEquals(true, Crypto.decrypt(source, destination, keyText, ivText));
	}
	
	
	
	
}
