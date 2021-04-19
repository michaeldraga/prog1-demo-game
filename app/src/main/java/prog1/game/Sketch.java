package prog1.game;

public class Sketch {

    Player player;

    public Sketch(Player player) {
        this.player = player;
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

    public void sketch() throws MaxMovesException {
        while (!isExitRight() && !isExitLeft() && !isOnExit()) {
            down();
        }
        if (isExitRight()) {
            while (!isOnExit()) {
                right();
            }
        } else {
            while (!isOnExit()) {
                left();
            }
        }
    }
}
