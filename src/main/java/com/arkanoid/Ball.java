package com.arkanoid;

import com.abstracts.MovableObject;


public class Ball extends MovableObject {
    private int radius;

    public Ball() {
        super();
        this.radius = 0;
    }

    public Ball(int x, int y, int dx, int dy, int radius) {
        super(x, y, 0, 0, dx, dy);
        this.radius = radius;
    }

}
