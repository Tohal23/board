package com.example.project.client;

import com.example.project.client.dto.Point;
import com.example.project.command.Executor;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class Window extends JFrame implements Executor {

    private ArrayList<Point> Points = new ArrayList<>();
    private Path2D path = new Path2D.Float();
    private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    private int counter;

    public Window() throws HeadlessException {
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(0, 0, dimension.width, dimension.height);
        setBackground(Color.WHITE);
        setVisible(true);
    }

    @Override
    public void move(Float x, Float y, Integer color) {
        if (counter % 2 > 0) {
            ArrayList<Point> bezierCurve = getBezierCurve(Points);
            drawLines(bezierCurve);
            paint(super.getGraphics(), Color.WHITE);
            path.reset();
            Point point = Points.get(0);
            path.moveTo(point.getX() * dimension.width, point.getY() * dimension.height);
            Points.add(new Point(x, y));

            ArrayList<Point> bezierCurveUp = getBezierCurve(Points);
            drawLines(bezierCurveUp);
            paint(super.getGraphics(), new Color(color));
        }
        counter++;
    }

    @Override
    public void start(Float x, Float y, Integer color) {
        if (!Points.isEmpty()) {
            path.reset();
            Points.clear();
        }
        counter = 0;
        Points.add(new Point(x, y));
        path.moveTo((x * dimension.width), (y * dimension.height));
    }

    private ArrayList<Point> getBezierCurve(ArrayList<Point> arr) {
        ArrayList<Point> bezierCurve = new ArrayList<>();

        for (float t = 0; t <= 1; t += 0.01) {
            int index = bezierCurve.size();
            bezierCurve.add(new Point(0.0f, 0.0f));
            for (int i = 0; i < arr.size(); i++) {
                float b = getBezierBasis(i, arr.size() - 1, t);
                Point point = bezierCurve.get(index);
                Point plus = arr.get(i);
                bezierCurve.set(index, new Point(point.getX() + plus.getX() * b, point.getY() + plus.getY() * b));
            }
        }
        return bezierCurve;
    }

    private float getBezierBasis(int i, int n, float t) {

        UnaryOperator<Integer> factorial = (Integer a) -> {
            int result = 1;
            for (int j = 1; j <= a; j++) {
                result = result * j;
            }
            return result;
        };

        long bezie1 = (factorial.apply(n) / (factorial.apply(i) * factorial.apply(n - i)));
        float bezie2 = (float) Math.pow(t, i);
        float bezie3 = (float) Math.pow(1 - t, n - i);
        return bezie1 * bezie2 * bezie3;
    }

    private void drawLines(ArrayList<Point> points) {
        for (Point p : points) {
            path.lineTo(p.getX() * dimension.width, p.getY() * dimension.height);
        }
    }

    private void paint(Graphics g, Color color) {
        g.setColor(color);
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.draw(path);
    }
}