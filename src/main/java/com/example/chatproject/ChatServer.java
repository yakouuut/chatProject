package com.example.chatproject;

import javafx.application.Platform;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private int clientCount = 0;
    private List<CommunicationThread> connectedClients = new ArrayList<>();

    public void startServer() {

        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Le serveur démarre...");

            while (true) {
                System.out.println("En attente de connexions...");
                Socket clientSocket = serverSocket.accept();
                clientCount++;
                System.out.println("Connexion établie avec le client " + clientCount);
                CommunicationThread clientThread = new CommunicationThread(clientSocket, clientCount);
                connectedClients.add(clientThread);
                clientThread.start();
               // Platform.runLater(() -> ChatServerApp.this.logTextArea.appendText("" + "\n"));


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public class CommunicationThread extends Thread {
        private Socket clientSocket;
        private int clientNumber;
        private BufferedReader reader;
        private PrintWriter writer;

        public CommunicationThread(Socket clientSocket, int clientNumber) {
            this.clientSocket = clientSocket;
            this.clientNumber = clientNumber;
        }

        @Override
        public void run() {
            try {

                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream(), true);

                writer.println("Bienvenue, vous etes le client " + clientNumber);
                writer.println("Ecrivez votre message.");

                String clientMessage;
                while ((clientMessage = reader.readLine()) != null) {
                    String[] parts = clientMessage.split(":");
                    if (parts.length == 2) {
                        String recipient = parts[0].trim();
                        String message = parts[1].trim();
                        if (!recipient.equals("all")) {
                            sendDirectMessage(recipient,message);
                        } else {
                            broadcastMessage("Client " + clientNumber + ": " + message);
                        }
                    }
                    else sendMessage("Mauvais format de message :");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        private void broadcastMessage(String message) {
            for (CommunicationThread clientThread : connectedClients) {
                if (clientThread != this) {
                    clientThread.sendMessage(message);
                }
            }
        }
        private void sendMessage(String message) {
            writer.println(message);
        }
        private void sendDirectMessage(String recipient, String message) {
            int recipientNum=-1;
            try{
                 recipientNum = Integer.valueOf(recipient);
            }
            catch (NumberFormatException e){
                e.printStackTrace();
            }
            boolean find = false ;

            String formattedMessage = "Client " + clientNumber + " (prive) : " + message;
            for (CommunicationThread clientThread : connectedClients) {
                if (clientThread != this && clientThread.clientNumber==recipientNum && recipientNum!=-1 ) {
                    find =true ;
                    clientThread.sendMessage(formattedMessage);
                    sendMessage("Message prive envoye au Client " + recipient + ": " + message);
                }
            }
            if (recipientNum!=1&& !find){
                sendMessage("Le client "+recipientNum+" n'est pas connecte");
            }
            if (recipientNum==-1) sendMessage("Format de client errone : (clientNum | all):'votre message'");

        }
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.startServer();
    }
}
