package com.example.chatproject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
public class ClientController {
    @FXML
    private TextField port;
    @FXML
    private TextField host;
    @FXML
    private Button connectButton;
    @FXML
    private ListView log;
    @FXML
    private TextField message;
    @FXML
    private Button sendButton;
    private PrintWriter pw;
    @FXML
    private void connectToServer() throws Exception{
        String hostText = host.getText();
        int portNum = Integer.parseInt(port.getText());

        Socket s =  new Socket(hostText, portNum);
        InputStream is = s.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        OutputStream os = s.getOutputStream();

        pw = new PrintWriter(os, true);
        String ip = s.getRemoteSocketAddress().toString();

        new Thread(()->{
          while(true){
              try{
                  String reponse =br.readLine();
                  Platform.runLater(()->{
                      log.getItems().add(reponse);
                  });
              }catch (Exception e){
                  e.printStackTrace();
              }
          }

        }).start();

    }


    public void sendMessage() {
        String messageText =message.getText();
        pw.println(messageText);


    }
}
