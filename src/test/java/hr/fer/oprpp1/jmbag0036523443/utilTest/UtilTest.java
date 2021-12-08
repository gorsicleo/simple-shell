package hr.fer.oprpp1.jmbag0036523443.utilTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.jmbag0036523443.util.Util;


public class UtilTest {
    
    @Test
    public void testHexToByteValid(){
        String hexCode1 = "01aE22";
        String hexCode2 = "1D08A";
        byte[] result1 = {1, -82, 34};
        byte[] result2 = {1, -48, -118};
        byte[] obtainedResult1 = Util.hextobyte(hexCode1);
        byte[] obtainedResult2 = Util.hextobyte(hexCode2);
        for (int i = 0;i<2;i++){
            assertEquals(result1[i],obtainedResult1[i]);
            assertEquals(result2[i], obtainedResult2[i]);
        }
    }

    @Test
    public void testHexToByteInvalid(){
        String notHexCode = "grdhj";
        String notHexCode2 = "1D08AP";

        assertThrows(IllegalArgumentException.class, () -> Util.hextobyte(notHexCode) );
        assertThrows(IllegalArgumentException.class, () -> Util.hextobyte(notHexCode2) );
    }

    @Test
    public void testByteToHex(){
        assertEquals("01ae22", Util.bytetohex(new byte[] {1, -82, 34}));
    }
}
