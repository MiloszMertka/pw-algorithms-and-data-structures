package pl.edu.pw.ee;

import static org.junit.Assert.*;

import org.junit.Test;

public class LongestCommonSubsequenceTest 
{
    @Test
    public void itShouldCorrectlyFindLCS() {
        // given
        String topStr = "KANAPKI";
        String leftStr = "NAPISY";
        LongestCommonSubsequence LCS = new LongestCommonSubsequence(topStr, leftStr);
        
        // when
        String result = LCS.findLCS();
        
        // then
        String expected = "NAPI";
        assertEquals(expected, result);
    }
}
