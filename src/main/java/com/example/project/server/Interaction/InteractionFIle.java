package com.example.project.server.Interaction;

import com.example.project.server.handler.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class InteractionFIle {

    private InteractionFIle() {
    }

    public static void ExecuteInteraction(String path) {
        Path paths = Paths.get(path);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(paths)))) {
            while (reader.ready()) {
                Handler.addData(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}