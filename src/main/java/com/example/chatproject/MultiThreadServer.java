package com.example.chatproject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer extends Thread{
    private int nbrClient=0;
    @Override
    public void run(){
        try{
            ServerSocket ss = new ServerSocket(1234);
            System.out.println("le serveur démarre .....!");

            while (true){
                System.out.println("Wait for connections");
                Socket s = ss.accept();
                ++nbrClient;
                System.out.println("Connection :"+ nbrClient);
                new Communication(s,nbrClient).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public class Communication extends Thread {
        private Socket s ;
        private int clientNumber;

        Communication(Socket s,int clientNumber ) {
            this.s=s;
            this.clientNumber=clientNumber;
        }
        @Override
        public void run(){
            try{
                InputStream is =s.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br= new BufferedReader(isr);

                OutputStream os = s.getOutputStream();
                String ipClient = s.getRemoteSocketAddress().toString();
                System.out.println("Client Number :"+clientNumber+" he's IP "+ipClient);
                PrintWriter pw = new PrintWriter(os,true);
                pw.println("vous etes le client "+nbrClient);
                while (true){
                    String UserRequest =br.readLine();
                    pw.println("size of Request"+UserRequest.length());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            finally {
                try{
                    s.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


   // public static void main(String[] args) {
    //    new MultiThreadServer().start();

    }

