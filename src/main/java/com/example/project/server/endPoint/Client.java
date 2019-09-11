package com.example.project.server.endPoint;

import com.example.project.command.Command;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements EventClient {

    private Socket socket;
    private ObjectOutputStream outputStream;

    public Client(Socket socket) {
        this.socket = socket;
    }

    public void build() throws IOException {
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public void notify(Command command) throws IOException {
        outputStream.writeObject(command);
    }
}
