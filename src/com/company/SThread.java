package com.company;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.Phaser;


public class SThread extends Thread {
    private int[] arrayPartition; // partition of Array
    private Phaser phaser;
    private int threadId;

    // Constructor
    SThread(int[] partition, Phaser phaser,int id)  {
        arrayPartition = partition;
        this.phaser =phaser;
        phaser.register();
        threadId = id;
    }

    public int[] getArrayPartition() {
        return arrayPartition;
    }

    public void displayArray(int [] part){
        System.out.print("[ ");
        for (int i = 0; i < part.length; i++) {
            System.out.print(part[i]+ " ");
        }
        System.out.println(" ]");
    }

    // Run method (will run for each machine that connects to the ServerRouter)
    public void run() {


        try {
            phaser.arriveAndAwaitAdvance();
            long start = System.nanoTime();
            System.out.println("Thread #"+threadId+" started running at "+ start);
            Arrays.sort(arrayPartition);
            long end = System.nanoTime();
            //displayArray(arrayPartition);
            System.out.println("Thread #"+threadId+" finished sorting in  " + (end - start) + " nS");

        }// end try
        catch (IllegalStateException e) {
            System.err.println("Could not listen to socket.");
            System.exit(1);
        }
    }


}