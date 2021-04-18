package prog1.game;

import java.awt.*;
import java.util.Random;

import static prog1.game.GamePanel.COLOR_DOOR;
import static prog1.game.GamePanel.UNIT_SIZE;

public class Door {
    Position position;

    public Door(int x, int y) {
        this.position = new Position(x,y);
    }

    public Door() {
        Random r = new Random();
        this.position = new Position(
                r.nextInt(GamePanel.COLUMNS),
                r.nextInt(GamePanel.ROWS)
        );
    }

    public void paint(Graphics g) {
        Color lastColor = g.getColor();
        g.setColor(COLOR_DOOR);
        g.fillRect(UNIT_SIZE * position.x + 1, UNIT_SIZE * position.y + 1, UNIT_SIZE - 2, UNIT_SIZE - 2);
        g.setColor(lastColor);
    }
}
