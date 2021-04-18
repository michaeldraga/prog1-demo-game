package prog1.game;

import java.awt.*;
import java.awt.image.ImageObserver;

import static prog1.game.GamePanel.*;

public class Player {

    static final int SIZE = 40;
    static final int START = UNIT_SIZE / 2 - SIZE / 2;
    Position position;

    final Image img;

    public Player() {
        this.position = new Position(0, 0);
        this.img = GamePanel.readImage(GamePanel.PLAYER_IMG_PATH);
    }

    public Player(int x, int y) {
        this.position = new Position(x, y);
        this.img = GamePanel.readImage(GamePanel.PLAYER_IMG_PATH);
    }

    public void paint(Graphics g) {
        ImageObserver observer = (img, infoflags, x, y, width, height) -> false;
        g.drawImage(this.img, START + UNIT_SIZE * position.x, START + UNIT_SIZE * position.y, SIZE, SIZE, observer);
    }

    public void moveX(int amount) {
        int newX = position.x + amount;
        if (newX >= 0 && newX < COLUMNS) position.x = newX;
    }

    public void moveY(int amount) {
        int newY = position.y + amount;
        if (newY >= 0 && newY < ROWS) position.y = newY;
    }

    public void move(int x, int y) {
        this.position.x = restrict(this.position.x + x, 0, COLUMNS);
        this.position.y = restrict(this.position.y + y, 0, ROWS);
    }

    private static int restrict(int val, int min, int max) {
        if (val < min) return min;
        if (val > max - 1) return max - 1;
        return val;
    }

    public void reset() {
        position = new Position(0, 0);
    }
}
