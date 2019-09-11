package com.example.project.server.pusher;

import com.example.project.command.Command;
import com.example.project.server.endPoint.Client;
import com.example.project.server.endPoint.EventClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public final class PusherManager {

    private static final List<EventClient> CLIENTS = new ArrayList<>();
    private final static List<Command> COMMAND_LIST = new ArrayList<>();
    private static final Semaphore SEMAPHORE = new Semaphore(1);

    private PusherManager() {

    }

    public static void subscribe(Client client) throws InterruptedException, IOException {
        SEMAPHORE.acquire();
        CLIENTS.add(client);
        if (!COMMAND_LIST.isEmpty()) {
            for (Command command : COMMAND_LIST) {
                client.notify(command);
            }
        }
        SEMAPHORE.release();
    }

    public static void unsubscribe(Client client) {
        CLIENTS.remove(client);
    }

    public static boolean notify(Command command) throws IOException, InterruptedException {
        SEMAPHORE.acquire();
        if (CLIENTS.isEmpty() || command == null) {
            SEMAPHORE.release();
            return false;
        }
        COMMAND_LIST.add(command);
        for (EventClient client : CLIENTS) {
            client.notify(command);
        }
        SEMAPHORE.release();
        return true;
    }
}
