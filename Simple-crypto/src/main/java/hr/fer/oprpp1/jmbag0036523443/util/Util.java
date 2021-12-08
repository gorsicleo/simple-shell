package hr.fer.oprpp1.jmbag0036523443.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class Util{
    
    public static byte[] hextobyte(String keyText) {

        try {
            Integer hexNumber = Integer.parseInt(keyText,16);
            System.out.println("Hex number is: "+hexNumber);
            BigInteger bigInt = BigInteger.valueOf(hexNumber);
            System.out.println("Byte array is: "+bigInt.toByteArray().toString());
            return bigInt.toByteArray();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("String is not valid hex code!");
        }
    }

    public static String bytetohex(byte[] bytearray){
        return new String(bytearray,StandardCharsets.UTF_8);
    }
}