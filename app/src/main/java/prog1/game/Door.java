package prog1.game;

import java.awt.*;
import java.util.Random;

import static prog1.game.GamePanel.COLOR_DOOR;
import static prog1.game.GamePanel.UNIT_SIZE;

public class Door {
    int x,y;

    public Door(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Door() {
        Random r = new Random();
        this.x = r.nextInt(GamePanel.COLUMNS);
        this.y = r.nextInt(GamePanel.ROWS);
    }

    public void paint(Graphics g) {
        Color lastColor = g.getColor();
        g.setColor(COLOR_DOOR);
        g.fillRect(UNIT_SIZE * x + 1, UNIT_SIZE * y + 1, UNIT_SIZE - 2, UNIT_SIZE - 2);
        g.setColor(lastColor);
    }
}
