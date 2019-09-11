package com.example.project.command;

import java.awt.*;
import java.io.Serializable;

public class MoveCommand implements Command, Serializable {

    private String id;
    private Float x;
    private Float y;
    private Integer color;

    public MoveCommand(String id, String x, String y, String color) throws NumberFormatException {
        this.id = id;
        this.x = Float.parseFloat(x);
        this.y = Float.parseFloat(y);
        this.color = Integer.parseInt(color);
    }

    @Override
    public void execute(Executor executor) {
        executor.move(x, y, color);
    }
}
