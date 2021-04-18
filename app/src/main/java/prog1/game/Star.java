package prog1.game;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Random;

import static prog1.game.GamePanel.UNIT_SIZE;

public class Star {

    static final int SIZE = 40;
    static final int START = UNIT_SIZE / 2 - SIZE / 2;
    Position position;
    private Image img;

    public Star() {
        Random r = new Random();
        this.position = new Position(
                r.nextInt(GamePanel.COLUMNS),
                r.nextInt(GamePanel.ROWS)
        );
        this.img = GamePanel.readImage(GamePanel.STAR_IMG_PATH);
    }

    public Star(int x, int y) {
        this.position = new Position(x, y);
        this.img = GamePanel.readImage(GamePanel.STAR_IMG_PATH);
    }

    public void paint(Graphics g) {
        ImageObserver observer = (img, infoflags, x, y, width, height) -> false;
        g.drawImage(this.img, START + UNIT_SIZE * position.x, START + UNIT_SIZE * position.y, SIZE, SIZE, observer);
    }
}
