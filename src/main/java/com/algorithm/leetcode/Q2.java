package com.algorithm.leetcode;

/**
 * Created by wangzunhui on 2016/4/15.
 *
 * Similar to Question[1, Two Sum], except that the input array is already sorted
 * in ascending order.
 *
 */
public class Q2 {
    private int binarySearch(int[] A, int key, int start){
        int L = start;
        int R = A.length - 1;

        while (L < R){
            int M = (L + R) / 2;
            if (A[M] < key){
                L = M + 1;
            }else{
                R = M;
            }
        }

        return (L == R && A[L] == key) ? L : -1;
    }

    public int[] twoSum(int[] numbers, int target) throws IllegalAccessException {
        for (int i = 0; i < numbers.length; i++){
            int j = binarySearch(numbers, target - numbers[i], i + 1);
            if (j != -1){
                return new int[] {i + 1, j + 1};
            }
        }
        throw new IllegalAccessException("no two sum solution");
    }
}
