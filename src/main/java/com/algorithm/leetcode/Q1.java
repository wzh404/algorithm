package com.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzunhui on 2016/4/15.
 *
 * Question:
 *      Given an array of integers, find two numbers such that add up to a
 *      specific target number.
 *
 * O(n) runtime, O(n) space - hash table.
 */
public class Q1 {
    public int[] twoSum(int[] numbers, int target) throws IllegalAccessException {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < numbers.length; i++){
            int x = numbers[i];
            int v = target - x;
            if (map.containsKey(v)){
                return new int[] {map.get(v), i + 1};
            }

            map.put(x, i);
        }

        throw new IllegalAccessException("no two sum solution");
    }
}
