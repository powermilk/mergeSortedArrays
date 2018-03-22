package com.powermilk;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MergeSortedArrays {
    public static int[] merge(int[] array1, int[] array2) {

        if(array1 == null || array2 == null) {
            throw new NullPointerException("Arguments cannot be null");
        }

        if (array1.length == 0 || array2.length == 0) {
            throw new IllegalArgumentException("Arrays cannot be empty");
        }

        if (!isArraySorted(array1) || !isArraySorted(array2)) {
            throw new IllegalArgumentException("Arrays have to be sorted");
        }

        final int length_of_array1 = array1.length;
        final int length_of_array2 = array2.length;

        int[] result = new int[length_of_array1 + length_of_array2];
        int i = 0, j = 0, k = 0;
        while (i < length_of_array1 && j < length_of_array2) {
            if (array1[i] < array2[j]) {
                result[k] = array1[i];
                i++;
            } else {
                result[k] = array2[j];
                j++;
            }
            k++;
        }

        if (i < length_of_array1) {
            System.arraycopy(array1, i, result, k, (length_of_array1 - i));
        }

        if (j < length_of_array2) {
            System.arraycopy(array2, j, result, k, (length_of_array2 - j));
        }
        return result;
    }
    
    public static boolean isArraySorted(int[] array) {
        List<Integer> list = Arrays.stream(array).boxed().collect(Collectors.toList());
        return list.stream().sorted().collect(Collectors.toList()).equals(list);
    }
}

