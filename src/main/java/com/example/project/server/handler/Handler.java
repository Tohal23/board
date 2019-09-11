package com.example.project.server.handler;

import com.example.project.command.Command;
import com.example.project.command.MoveCommand;
import com.example.project.command.StartCommand;
import com.example.project.server.pusher.PusherManager;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class Handler extends Thread {

    private final static Queue<Command> COMMAND_QUEUE = new ConcurrentLinkedQueue<>();

    public static void addData(String strData) {
        Command command = null;
        try {
             command = Handler.createCommand(strData);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        if (command != null) {
            COMMAND_QUEUE.add(command);
        }
    }

    public void run() {
        while (!Thread.interrupted()) {
            Command command = COMMAND_QUEUE.peek();
            if (command == null) {
                try {
                    Thread.sleep(75);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            boolean response = false;
            try {
                response = PusherManager.notify(command);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            if (response) {
                COMMAND_QUEUE.remove();
            }
        }
    }

    private static Command createCommand(String strData) {
        String[] strings = strData.split(";");
        if (strings[1].equals("move")) {
            return new MoveCommand(strings[0], strings[2], strings[3], strings[4]);
        } else if (strings[1].equals("start")) {
            return new StartCommand(strings[0], strings[2], strings[3], strings[4]);
        } else {
            throw new RuntimeException("Несуществующий тип команды");
        }
    }
}