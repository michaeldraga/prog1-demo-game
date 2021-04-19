package prog1.game;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import static prog1.game.GamePanel.*;

public class Player {

    static final int SIZE = 40;
    static final int START = UNIT_SIZE / 2 - SIZE / 2;
    Position position;
    Position doorPosition;
    final List<Action> moves = new ArrayList<>();
    boolean won = false;

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

    public String moveX(int amount) {
        int newX = position.x + amount;
        if (newX >= 0 && newX < COLUMNS) position.x = newX;
        if (amount < 0) return "Mino geht einen Schritt nach links";
        else if (amount > 0) return "Mino geht einen Schritt nach rechts";
        else return "";
    }

    public String moveX(int amount, boolean print) {
        int newX = position.x + amount;
        if (newX >= 0 && newX < COLUMNS) position.x = newX;
        if (print) {
            if (amount < 0) return "Mino geht einen Schritt nach links";
            else if (amount > 0) return "Mino geht einen Schritt nach rechts";
        }
        return "";
    }

    public String moveY(int amount) {
        int newY = position.y + amount;
        if (newY >= 0 && newY < ROWS) position.y = newY;
        if (amount < 0) return "Mino geht einen Schritt nach oben";
        else if (amount > 0) return "Mino geht einen Schritt nach unten";
        else return "";
    }

    public String moveY(int amount, boolean print) {
        int newY = position.y + amount;
        if (newY >= 0 && newY < ROWS) position.y = newY;
        if (print) {
            if (amount < 0) return "Mino geht einen Schritt nach oben";
            else if (amount > 0) return "Mino geht einen Schritt nach unten";
        }
        return "";
    }

    public void move(int x, int y) {
        moveX(x);
        moveY(y);
    }

    public void move(Position pos) {
        moveX(pos.x);
        moveY(pos.y);
    }

    public boolean isExitRight() throws MaxMovesException {
        boolean b = position.x < doorPosition.x && position.y == doorPosition.y;
        moves.add(new Action("Ist die Tür rechts zu sehen?"));
        moves.add(new Action(yesNo(b)));
        checkMoves();
        return b;
    }

    public boolean isExitLeft() throws MaxMovesException {
        boolean b = position.x > doorPosition.x && position.y == doorPosition.y;
        moves.add(new Action("Ist die Tür links zu sehen?"));
        moves.add(new Action(yesNo(b)));
        checkMoves();
        return b;
    }

    public boolean isExitDown() throws MaxMovesException {
        boolean b = position.y < doorPosition.y && position.x == doorPosition.x;
        moves.add(new Action("Ist die Tür unten zu sehen?"));
        moves.add(new Action(yesNo(b)));
        checkMoves();
        return b;
    }

    public boolean isExitUp() throws MaxMovesException {
        boolean b = position.y > doorPosition.y && position.x == doorPosition.x;
        moves.add(new Action("Ist die Tür rechts zu sehen?"));
        moves.add(new Action(yesNo(b)));
        checkMoves();
        return b;
    }

    private void simMoveX(int amount) throws MaxMovesException {
        String msg = moveX(amount);
        moves.add(new Action(new Position(amount, 0), msg));
        checkMoves();
    }

    private void simMoveY(int amount) throws MaxMovesException {
        String msg = moveY(amount);
        moves.add(new Action(new Position(0, amount), msg));
        checkMoves();
    }

    public void right() throws MaxMovesException {
        simMoveX(1);
    }

    public void left() throws MaxMovesException {
        simMoveX(-1);
    }

    public void down() throws MaxMovesException {
        simMoveY(1);
    }

    public void up() throws MaxMovesException {
        simMoveY(-1);
    }

    public void nextMove() {
        Action nextAction = moves.remove(0);
        if (!nextAction.msg.isEmpty()) System.out.println(nextAction.msg);
        if (!nextAction.position.isEmpty()) move(nextAction.position);
    }

    public boolean movesDone() {
        return moves.isEmpty();
    }

    public boolean isOnExit() {
        return Position.equals(position, doorPosition);
    }

    private void checkMoves() throws MaxMovesException {
        if (moves.size() > 10000) {
            throw new MaxMovesException();
        }
    }

    private static int restrict(int val, int min, int max) {
        if (val < min) return min;
        if (val > max - 1) return max - 1;
        return val;
    }

    private static String yesNo(boolean yes) {
        return yes ? "Ja" : "Nein";
    }

    public void reset() {
        position = new Position(0, 0);
    }

    public void resetMoves() {
        moves.clear();
    }

    public void setDoorPosition(Position doorPosition) {
        this.doorPosition = doorPosition;
    }
}
