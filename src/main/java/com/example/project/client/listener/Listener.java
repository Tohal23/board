package com.example.project.client.listener;

import com.example.project.command.Command;
import com.example.project.command.Executor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Listener {

    private Socket socket;
    private Executor executor;
    private ObjectInputStream inputStream;

    public Listener(Socket socket, Executor executor) {
        this.socket = socket;
        this.executor = executor;
    }

    public void init() throws IOException {
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void execute() {
        while (true)
            try {
                Command command = (Command) inputStream.readObject();
                command.execute(executor);
            } catch (IOException | ClassNotFoundException e) {
            }
    }
}
