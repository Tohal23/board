package com.example.project.server.endPoint;

import com.example.project.command.Command;

import java.io.IOException;

public interface EventClient {

    void notify(Command command) throws IOException;

}
