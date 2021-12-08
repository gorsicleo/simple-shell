package hr.fer.oprpp1.jmbag0036523443.cryptoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.jmbag0036523443.crypto.Crypto;
import hr.fer.oprpp1.jmbag0036523443.util.Util;

public class CryptoTest {

	@Test
	public void digestTest() {
		Path path = Paths.get("./src/main/resources/hw05test.bin");
		String hash = "2e7b3a91235ad72cb7e7f6a721f077faacfeafdea8f3785627a5245bea112598";
		assertEquals(true,Crypto.checksha(hash, path));
	}
	
	
}
