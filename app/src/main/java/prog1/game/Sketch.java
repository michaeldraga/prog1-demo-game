package prog1.game;

import java.awt.*;

public class Sketch {

    private Player player;
    private GamePanel game;

    public Sketch(Player player, GamePanel game) {
        this.player = player;
        this.game = game;
    }

    private void up() throws MaxMovesException {
        player.up();
    }

    private void down() throws MaxMovesException {
        player.down();
    }

    private void right() throws MaxMovesException {
        player.right();
    }

    private void left() throws MaxMovesException {
        player.left();
    }

    private boolean isExitRight() throws  MaxMovesException {
        return player.isExitRight();
    }

    private boolean isExitLeft() throws  MaxMovesException {
        return player.isExitLeft();
    }

    private boolean isExitUp() throws  MaxMovesException {
        return player.isExitUp();
    }

    private boolean isExitDown() throws  MaxMovesException {
        return player.isExitDown();
    }

    private boolean isOnExit() {
        return player.isOnExit();
    }

    private boolean won() {
        return player.won;
    }

    private void colorField(String color) {
        switch (color) {
            case "blau" -> player.moves.add(new Action(Color.blue));
            case "grün" -> player.moves.add(new Action(Color.green));
        }
    }

    public void sketch() throws MaxMovesException {
        while (!isExitRight() && !isExitLeft() && !isOnExit()) {
            colorField("blau");
            down();
        }
        if (isExitRight()) {
            while (!isOnExit()) {
                colorField("grün");
                right();
            }
        } else {
            while (!isOnExit()) {
                colorField("grün");
                left();
            }
        }
    }
}
