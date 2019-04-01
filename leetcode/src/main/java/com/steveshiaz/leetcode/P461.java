package com.steveshiaz.leetcode;

/**
 * 461. Hamming Distance.
 */
public class P461 {
    public class Solution {
        public int hammingDistance(final int x, final int y) {
            int exc = x ^ y;
            int result = 0;
            while (exc != 0) {
                result += 1;
                exc &= (exc - 1);
            }
            return result;
        }
    }
}
