package com.powermilk;

import org.junit.jupiter.api.*;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(JUnitPlatform.class)
@DisplayName("Testing MergeSortedArrays class")
@SelectPackages("com.powermilk")
class MergeSortedArraysTest {
    private static final Logger log = LoggerFactory.getLogger(MergeSortedArrays.class);

    @BeforeAll
    static void setup() {
        log.info("--- Test suite for MergeSortedArrays class started!");
    }

    @AfterAll
    static void finish() {
        log.info("--- Test suite for MergeSortedArrays class finished!");
    }

    @BeforeEach
    void init() {
        log.info("Test started!");
    }

    @AfterEach
    void tearDown() {
        log.info("Test finished!");
    }

    private static final int[] sorted_array1 = { 1, 18, 22, 100, 105, 1002 };
    private static final int[] sorted_array2 = { 16, 17, 19, 21, 1001 };

    private static final int[] not_sorted_array1 = { 1, 22, 18, 10, 1002 };
    private static final int[] not_sorted_array2 = { 17, 16, 1, 101, 11 };

    private static final int[] array_of_ones = IntStream.generate(() -> 1).limit(6).toArray();
    private static final int[] array_of_twos = IntStream.generate(() -> 2).limit(6).toArray();

    private static final int[] emptyArray = {};

    @Test
    @DisplayName("Test should throws NullPointerException (null as input)")
    void shouldThrowNullPointerExceptionForEmptyArray() {
        String expected = "Arguments cannot be null";
        Throwable firstArgumentIsNull = assertThrows(NullPointerException.class,
                () -> MergeSortedArrays.merge(null, sorted_array2));
        Throwable secondArgumentIsEmptyNull = assertThrows(NullPointerException.class,
                () -> MergeSortedArrays.merge(sorted_array1, null));
        Throwable bothArgumentsAreNull = assertThrows(NullPointerException.class,
                () -> MergeSortedArrays.merge(null, null));

        assertEquals(expected, firstArgumentIsNull.getMessage());
        assertEquals(expected, secondArgumentIsEmptyNull.getMessage());
        assertEquals(expected, bothArgumentsAreNull.getMessage());
    }

    @Test
    @DisplayName("Test should throws IllegalArgumentException (empty array as input)")
    void shouldThrowIllegalArgumentExceptionForEmptyArray() {
        String expected = "Arrays cannot be empty";
        Throwable firstArgumentIsEmpty = assertThrows(IllegalArgumentException.class,
                () -> MergeSortedArrays.merge(emptyArray, sorted_array2));
        Throwable secondArgumentIsEmpty = assertThrows(IllegalArgumentException.class,
                () -> MergeSortedArrays.merge(sorted_array1, emptyArray));
        Throwable bothArgumentsAreEmpty = assertThrows(IllegalArgumentException.class,
                () -> MergeSortedArrays.merge(emptyArray, emptyArray));

        assertEquals(expected, firstArgumentIsEmpty.getMessage());
        assertEquals(expected, secondArgumentIsEmpty.getMessage());
        assertEquals(expected, bothArgumentsAreEmpty.getMessage());
    }

    @Test
    @DisplayName("Test should throws IllegalArgumentException (not sorted array as input)")
    void shouldThrowIllegalArgumentExceptionForNotSortedArray() {
        String expected = "Arrays have to be sorted";
        Throwable firstArrayIsNotSorted = assertThrows(IllegalArgumentException.class,
                () -> MergeSortedArrays.merge(not_sorted_array1, sorted_array2));
        Throwable secondArrayIsNotSorted = assertThrows(IllegalArgumentException.class,
                () -> MergeSortedArrays.merge(sorted_array1, not_sorted_array2));
        Throwable bothArraysAreNotSorted = assertThrows(IllegalArgumentException.class,
                () -> MergeSortedArrays.merge(not_sorted_array1, not_sorted_array2));

        assertEquals(expected, firstArrayIsNotSorted.getMessage());
        assertEquals(expected, secondArrayIsNotSorted.getMessage());
        assertEquals(expected, bothArraysAreNotSorted.getMessage());
    }

    @Test
    @DisplayName("Test should merge two identical arrays")
    void shouldMergeTwoIdenticalArrays() {
        final int[] firstExpected = IntStream.generate(() -> 1).limit(12).toArray();
        final int[] firstActual = MergeSortedArrays.merge(array_of_ones, array_of_ones);

        final int[] secondExpected = IntStream.generate(() -> 2).limit(12).toArray();
        final int[] secondActual = MergeSortedArrays.merge(array_of_twos, array_of_twos);

        assertEquals(Arrays.toString(firstExpected), Arrays.toString(firstActual));
        assertEquals(Arrays.toString(secondExpected), Arrays.toString(secondActual));
    }

    @Test
    @DisplayName("Test should passed for two arrays with the same value")
    void shouldMergeTwoArraysWithTheSameValue() {
        final int[] expected = {1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2};
        final int[] actual = MergeSortedArrays.merge(array_of_ones, array_of_twos);

        assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    }

    @Test
    @DisplayName("Test should passed for two sorted arrays")
    void shouldMergeTwoSortedArrays() {
        final int[] expected = {1, 16, 17, 18, 19, 21, 22, 100, 105, 1001, 1002};
        final int[] actual = MergeSortedArrays.merge(sorted_array1, sorted_array2);

        assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    }

    @Test
    @Disabled("Theoretically can generate the same or sorted arrays")
    @DisplayName("Test should throws IllegalArgumentException for generated arrays")
    void shouldThrowExceptionForGeneratedArrays() {
        String expected = "Arrays have to be sorted";

        final int[] firstArray = IntStream.generate(() -> ThreadLocalRandom.current().nextInt(100)).limit(100).toArray();
        final int[] secondArray = IntStream.generate(() -> ThreadLocalRandom.current().nextInt(100)).limit(100).toArray();

        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> MergeSortedArrays.merge(firstArray, secondArray));

        assertEquals(expected, exception.getMessage());
    }
}