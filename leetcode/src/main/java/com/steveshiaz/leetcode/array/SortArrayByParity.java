package com.steveshiaz.leetcode.array;

/**
 * 905. Sort Array By Parity
 */
public class SortArrayByParity {

    public int[] sortArrayByParity(int[] A) {
        int start = 0;
        int end = A.length - 1;

        while (start < end) {
            if (A[start] % 2 != 0 && A[end] % 2 == 0) {
                swap(A, start, end);
            } else if (A[start] % 2 == 0) {
                start ++;
            } else if (A[end] % 2 != 0) {
                end --;
            }
        }

        return A;
    }

    private void swap(final int[] arr, int i, int j) {
        final int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
