package prog1.game;

import java.util.List;

public class Sketch {

    List<Move> moves;

    public Sketch(List moves) {
        this.moves = moves;
        sketch();
    }

    private void moveX(int amount) {
        moves.add(new Move(amount, 0));
    }

    private void moveY(int amount) {
        moves.add(new Move(0, amount));
    }

    private void right() {
        moveX(1);
    }

    private void left() {
        moveX(-1);
    }

    private void down() {
        moveY(1);
    }

    private void up() {
        moveY(-1);
    }

    public void sketch() {
        for (int i = 0; i < 4; i++) {
            down();
            right();
        }
    }
}
