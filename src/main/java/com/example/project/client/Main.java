package com.example.project.client;

import com.example.project.client.listener.Listener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws Exception {
      try {
          Socket socket = new Socket(InetAddress.getLocalHost(), 29288);
          Listener listener = new Listener(socket, new Window());
          listener.init();
          listener.execute();
      } catch (IOException e) {
          System.exit(0);
      }
    }

}
