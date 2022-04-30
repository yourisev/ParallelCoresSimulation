package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.Phaser;

public class Server {

    public final static int[] numberOfThreads = {1,2,4,8,16};  // you may change this, I give a
    public final static int SOCKET_PORT = 13267;  // you may change this

    // Fill the initial array with random elements within range

    private static int[] sectionArray(int[] partition, int start, int end) {
        int[] newArray = new int[end-start];
        for (int i = 0, j=start; i < newArray.length && j<end ; i++,j++) {
            newArray[i]= partition[j];
        }
        return newArray;
    }

    private static  ArrayList<Integer> sortPartitions(SThread[] arrayOfThreads){
        ArrayList<int[]> compilePartitions = new ArrayList<>();
        for (int i = 0; i < arrayOfThreads.length ; i++) {
            compilePartitions.add(arrayOfThreads[i].getArrayPartition());
        }
        ArrayList<Integer> finalArray = new ArrayList<>();
        for (int[] part:compilePartitions) {
            for (int i = 0; i < part.length; i++) {
                finalArray.add(part[i]);
            }
        }
        Collections.sort(finalArray);
        return finalArray;
    }

    public static void main (String [] args ) throws IOException {
        DataInputStream inServer = null;
        DataOutputStream outServer = null;
        ServerSocket serverSocket = null;
        Socket sockFromClient = null;
        try {

            try {
                serverSocket = new ServerSocket(SOCKET_PORT);
                System.out.println("ServerRouter is Listening on port: " + SOCKET_PORT + ".");

            } catch (IOException e) {
                System.err.println("Could not listen on port: " + SOCKET_PORT + ".");
                System.exit(1);
            }
            System.out.println("Waiting...");

            sockFromClient = serverSocket.accept();
            System.out.println("Accepted connection : " + sockFromClient);

            DataInputStream inFromClient = new DataInputStream(sockFromClient.getInputStream());
            DataOutputStream outToClient = new DataOutputStream(sockFromClient.getOutputStream());
            int size = inFromClient.readInt();
            int[] arrFromClient = new int[size];
            for(int i=0;i<size;i++) {
                arrFromClient[i] = inFromClient.readInt();
            }
            SThread[] arrayOfThreadObjects;
            //for (int val: numberOfThreads) {
                arrayOfThreadObjects = new SThread[numberOfThreads[0]];
                Phaser phaser = new Phaser();
                //phaser.register(); //no need to add the main thread to this group it will be executed normally(this was valid before) val replaced with numberOfThreads[i]
                boolean pair = arrFromClient.length % numberOfThreads[0] == 0;
                int sizeOfPartition = arrFromClient.length / numberOfThreads[0];
                int start = 0, end = sizeOfPartition;
                for (int i = 0; i <numberOfThreads[0] ; i++) {
                    if(!pair && i == numberOfThreads[0]-1){
                        arrayOfThreadObjects[i] = new  SThread(sectionArray(arrFromClient,start,end+1),phaser,(i+1));
                    }else{
                        arrayOfThreadObjects[i] = new  SThread(sectionArray(arrFromClient,start,end),phaser,(i+1));
                    }
                    arrayOfThreadObjects[i].start();
                    start = end;
                    end +=sizeOfPartition;
                }
                //phaser.arriveAndAwaitAdvance();

                System.out.println("Final sorted array is: "+ sortPartitions(arrayOfThreadObjects));
                //System.out.println("Ran in: "+(endTime - startTime));
           // }

            System.out.println("Done at "+ System.nanoTime()+".");
        }
        finally {

            if (serverSocket!=null) serverSocket.close();
        }
    }
}

