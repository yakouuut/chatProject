package com.example.chatproject;

import java.io.*;
import java.net.Socket;


public class MultiThreadClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);

            // Flux de sortie pour envoyer des requêtes au serveur
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);

            // Flux d'entrée pour recevoir les réponses du serveur
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            // Lecture du message de bienvenue du serveur
            String serverMessage = br.readLine();
            System.out.println(serverMessage);

            // Envoi de requêtes au serveur
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String userRequest;
            do {
                System.out.print("Enter a request (or 'exit' to quit): ");
                userRequest = userInput.readLine();
                pw.println(userRequest);

                // Lecture de la réponse du serveur
                String serverResponse = br.readLine();
                System.out.println("Server response: " + serverResponse);
            } while (!userRequest.equals("exit"));

            // Fermeture des flux et de la socket
            pw.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

