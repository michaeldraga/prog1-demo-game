package prog1.game;

import java.awt.*;
import java.awt.image.ImageObserver;

import static prog1.game.GamePanel.*;

public class Player {

    static final int SIZE = 40;
    static final int START = UNIT_SIZE / 2 - SIZE / 2;
    int x,y;

    final Image img;

    public Player() {
        this.x = 0;
        this.y = 0;
        this.img = GamePanel.readImage(GamePanel.PLAYER_IMG_PATH);
    }

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.img = GamePanel.readImage(GamePanel.PLAYER_IMG_PATH);
    }

    public void paint(Graphics g) {
        ImageObserver observer = (img, infoflags, x, y, width, height) -> false;
        g.drawImage(this.img, START + UNIT_SIZE * x, START + UNIT_SIZE * y, SIZE, SIZE, observer);
    }

    public void moveX(int amount) {
        int newX = x + amount;
        if (newX >= 0 && newX < COLUMNS) x = newX;
    }

    public void moveY(int amount) {
        int newY = y + amount;
        if (newY >= 0 && newY < ROWS) y = newY;
    }

    public void move(int x, int y) {
        this.x = restrict(this.x + x, 0, COLUMNS);
        this.y = restrict(this.y + y, 0, ROWS);
    }

    private static int restrict(int val, int min, int max) {
        if (val < min) return min;
        if (val > max) return max;
        return val;
    }
}
