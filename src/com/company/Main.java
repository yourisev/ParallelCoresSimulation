package com.company;
import java.util.*;


public class Main {

    // Array Size
    private static Random random = new Random();
    private static final int size = random.nextInt(100);
    private static final Integer list[] = new Integer[size];
    // Fill the initial array with random elements within range
    static {
        for(int i=0; i<size; i++){
            // add a +ve offset to the generated random number and subtract same offset
            // from total so that the number shifts towards negative side by the offset.
            // ex: if random_num = 10, then (10+100)-100 => -10
            list[i] = random.nextInt(size+(size-1))-(size-1);
        }
    }
    // Test the sorting methods performance

    public static void main(String[] args) {
        System.out.print("Input = [");
        for (Integer each: list)
            System.out.print(each+", ");
        System.out.print("] \n" +"Input.length = " + list.length + '\n');

        // Test standard Arrays.sort() method
        Integer[] arr1 = Arrays.copyOf(list, list.length);
        long t = System.currentTimeMillis();
        Arrays.sort(arr1, (a,b)->a>b? 1: a==b? 0: -1);
        t = System.currentTimeMillis() - t;
        System.out.println("Time spent for system based Arrays.sort(): " + t + "ms");

        // Test custom single-threaded merge sort (recursive merge) implementation
        Integer[] arr2 = Arrays.copyOf(list, list.length);
        t = System.currentTimeMillis();
        MergeSort.mergeSort(arr2, 0, arr2.length-1);
        t = System.currentTimeMillis() - t;
        System.out.println("Time spent for custom single threaded recursive merge_sort(): " + t + "ms");

        // Test custom (multi-threaded) merge sort (recursive merge) implementation
        Integer[] arr = Arrays.copyOf(list, list.length);
        MergeSort.threadedSort(arr);
        System.out.print("Output = [");
        for (Integer each: arr)
            System.out.print(each+", ");
        System.out.print("]\n");
    }
}
