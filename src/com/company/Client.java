package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Random;

public class Client {

    public final static int SOCKET_PORT = 13267;      // you may change this
    public final static String SERVER = "127.0.0.1";  // localhost
    public final static int[] sizes = {10, 100, 1000, 10000};
    // different name because i don't want to
    // overwrite the one used by server...

    public final static int FILE_SIZE = 6022386; // file size temporary hard coded
    // should bigger than the file to be downloaded

    public static void main (String [] args ) throws IOException {
        int bytesRead;
        int current = 0;
        Socket clientSocket = null;
        int [] arrayToPass;
        try {
            clientSocket = new Socket(SERVER,SOCKET_PORT);
            DataOutputStream outClient = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inClient = new DataInputStream(clientSocket.getInputStream());
            System.out.println("Connecting...");

            //for (int size: sizes) { tried  a single size=10 first value of sizes
                //for (int i = 0; i < 2; i++) {
                        long start = System.nanoTime();
                        arrayToPass = createArray(sizes[3],(2));
                    System.out.println("Time to generate array: " + (System.nanoTime() - start)+" nS");
                        //if(size==10){ No ned to check now
                            System.out.print("Array:[ ");
                            for (int val:arrayToPass) {
                                System.out.print(val  + " ");
                            }
                            System.out.println("]");
                        //}
                        //outClient.write(intArrayToByteArray(arrayToPass,size)); Old version
                    outClient.writeInt(sizes[3]);//send size of array first

                    sendArray(arrayToPass,outClient);
                //}
            //}


        }catch (IOException e){
            System.out.println("Problem with socket stream.");
        }
        finally {

            if (clientSocket != null) clientSocket.close();
        }
    }

    public static int[] createArray(int size, int type){
        Random random = new Random();
        int [] list = new int[size];
        int num;
        if(type == 1 ){
            // Array Size
            for(int i=0; i<size; i++){
                list[i] = random.nextInt(size+1);
            }
        }else{
            for (int i = 0; i < list.length; i++) {
                list[i]= random.nextInt(size+1) -(size/2);
            }
        }
        return list;
    }

    public static byte[] intArrayToByteArray(int [] array,int size){
        ByteBuffer byteBuffer = ByteBuffer.allocate(array.length*size);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(array);
        return byteBuffer.array();
    }

    public static void sendArray(int[] array,DataOutputStream out) throws IOException {
        for(int i=0;i<array.length;i++)
            out.writeInt(array[i]);
    }

}