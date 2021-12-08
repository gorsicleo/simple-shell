package hr.fer.oprpp1.jmbag0036523443.util;

import java.math.BigInteger;


public class Util{
	
	private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
	
    public static byte[] hextobyte(String keyText) {

        try {
            Integer hexNumber = Integer.parseInt(keyText,16);
            BigInteger bigInt = BigInteger.valueOf(hexNumber);
            return bigInt.toByteArray();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("String is not valid hex code!");
        }
    }

    public static String bytetohex(byte[] bytearray){
    	
    	char[] hexChars = new char[bytearray.length * 2];
        for (int j = 0; j < bytearray.length; j++) {
            int v = bytearray[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}