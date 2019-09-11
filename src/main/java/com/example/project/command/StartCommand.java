package com.example.project.command;

import java.io.Serializable;

public class StartCommand implements Command, Serializable {

    private String id;
    private Float x;
    private Float y;
    private Integer color;

    public StartCommand(String id, String x, String y, String color) throws NumberFormatException {
        this.id = id;
        this.x = Float.parseFloat(x);
        this.y = Float.parseFloat(y);
        this.color = Integer.parseInt(color);
    }

    @Override
    public void execute(Executor executor) {
        executor.start(x, y, color);
    }
}
