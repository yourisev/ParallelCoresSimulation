package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Random;

public class Client {

    public final static int SOCKET_PORT = 13267;      // you may change this, it represent the Port Number
    public final static String SERVER = "127.0.0.1";  // put server's IP address here!
    public final static int[] sizes = {10, 100, 1000, 10000};


    public static void main (String [] args ) throws IOException {

        Socket clientSocket = null;//Socket used to communicate with the server
        int [] arrayToPass;//Array to send to the server
        try {
            clientSocket = new Socket(SERVER,SOCKET_PORT);//Trying connection to server
            DataOutputStream outClient = new DataOutputStream(clientSocket.getOutputStream());//Collecting OutputStream to write to Server
            DataInputStream inClient = new DataInputStream(clientSocket.getInputStream());//Collecting InputStream to receive input from Server
            System.out.println("Connecting...");

            //for (int size: sizes) { tried  a single size=10 first value of sizes
                //for (int i = 0; i < 2; i++) {
                        long start = System.nanoTime();//Start time before array generation
            //Array generation, a value of 1 as the second parameter generates an array with values from 0 to the first parameter randomly
            // a value different from 1 as the second parameter generates an array with values from - first parameter divided by 2 to first parameter divided by 2
                        arrayToPass = createArray(sizes[3],(2));
                        long end = System.nanoTime();
                    System.out.println("Time position after generation of array: " + end + " nS");
                    System.out.println("Time to generate array: " + (end - start)+" nS");// Displays time needed to generate the array
                        //if(size==10){ No ned to check now
                            //The block 36 to 40 displays the generated array
                            System.out.print("Array:[ ");
                            for (int val:arrayToPass) {
                                System.out.print(val  + " ");
                            }
                            System.out.println("]");
                        //}
                        //outClient.write(intArrayToByteArray(arrayToPass,size)); Old version
                    outClient.writeInt(sizes[3]);//send size of array first

                    sendArray(arrayToPass,outClient);//send Array to server
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