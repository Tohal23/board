package com.example.project.command;

public interface Executor {
    void start(Float x, Float y, Integer color);

    void move(Float x, Float y, Integer color);
}
