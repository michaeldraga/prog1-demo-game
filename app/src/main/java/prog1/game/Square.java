package prog1.game;

import java.awt.*;

import static prog1.game.GamePanel.UNIT_SIZE;

public class Square {
    public Position position;
    public Color color = Color.black;

    public Square(Position position) {
        this.position = position;
    }

    public Square(Position position, Color color) {
        this.position = position;
        this.color = color;
    }

    public void paint(Graphics g) {
        Color lastColor = g.getColor();
        g.setColor(color);
        g.fillRect(position.x + 1, position.y + 1, UNIT_SIZE - 2, UNIT_SIZE - 2);
        g.setColor(lastColor);
    }

    public void resetColor() {
        this.color = Color.black;
    }
}
