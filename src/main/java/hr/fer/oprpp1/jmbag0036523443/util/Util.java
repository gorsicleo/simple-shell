package hr.fer.oprpp1.jmbag0036523443.util;

import java.math.BigInteger;


/**Class provides functions for seamless conversion between hex strings and byte array.
 * @author gorsicleo
 *
 */
public class Util{
	
	private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
	
	
    /**Method converts given hex String to byte[].
     * @param hexString to be converted to byte[]
     * @throws IllegalArgumentException if String is not hexadecimal
     * @return byte[] of given String
     */
    public static byte[] hextobyte(String hexString) {
    	if (hexString.length() % 2 == 1) {
            throw new IllegalArgumentException(
              "Invalid hexadecimal String supplied.");
        }
        
        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
        }
        return bytes;
    }

    
    /**Method converts given hex String to byte.
     * @param hexString to be converted to byte
     * @throws IllegalArgumentException if String is not hexadecimal
     * @return byte of given String
     */
    private static byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    /**Method converts given hex char to int.
     * @param hexChar to be converted to int
     * @throws IllegalArgumentException if char is not hexadecimal
     * @return int value of given hex char
     */
    private static int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if(digit == -1) {
            throw new IllegalArgumentException(
              "Invalid Hexadecimal Character: "+ hexChar);
        }
        return digit;
    }

    /**Method converts given byte[] to hex String
     * @param byte[] that will be converted to byte[]
     * @return String representing hexadecimal value of byte[]
     */
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