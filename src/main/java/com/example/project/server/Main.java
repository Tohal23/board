package com.example.project.server;

import com.example.project.server.Interaction.InteractionFIle;
import com.example.project.server.endPoint.Client;
import com.example.project.server.handler.Handler;
import com.example.project.server.pusher.PusherManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Main {

    public static void main(String[] args) throws IOException {
        new Handler().start();
        new Thread(() -> {
            InteractionFIle.ExecuteInteraction(args[0]);
        }).start();

        try (ServerSocket socketWait = new ServerSocket(29288)) {
            while (true) {
                Socket socket = null;
                while (socket == null) {
                    socket = socketWait.accept();
                }
                Client client = new Client(socket);
                client.build();
                PusherManager.subscribe(client);
            }
        } catch (SocketException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
