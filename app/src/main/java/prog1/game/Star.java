package prog1.game;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Random;

import static prog1.game.GamePanel.UNIT_SIZE;

public class Star {

    static final int SIZE = 40;
    static final int START = UNIT_SIZE / 2 - SIZE / 2;
    int x, y;
    private Image img;

    public Star() {
        Random r = new Random();
        this.x = r.nextInt(GamePanel.COLUMNS);
        this.y = r.nextInt(GamePanel.ROWS);
        this.img = GamePanel.readImage(GamePanel.STAR_IMG_PATH);
    }

    public Star(int x, int y) {
        this.x = x;
        this.y = y;
        this.img = GamePanel.readImage(GamePanel.STAR_IMG_PATH);
    }

    public void paint(Graphics g) {
        ImageObserver observer = (img, infoflags, x, y, width, height) -> false;
        g.drawImage(this.img, START + UNIT_SIZE * x, START + UNIT_SIZE * y, SIZE, SIZE, observer);
    }

    public static boolean positionEquals(Star s1, Star s2) {
        return s1.x == s2.x && s1.y == s2.y;
    }

    public static boolean positionEquals(Star s, Player p) {
        return s.x == p.x && s.y == p.y;
    }

    public static boolean positionEquals(Star s, Door d) {
        return s.x == d.x && s.y == d.y;
    }
}
