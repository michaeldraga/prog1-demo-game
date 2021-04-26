package prog1.game;

public class Position {
    int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position pos) {
        this.x = pos.x;
        this.y = pos.y;
    }

    public Position add(int c) {
        return new Position(x +c, y + c);
    }

    public Position add(int x, int y) {
        return new Position(this.x + x, this.y + y);
    }

    public Position mult(int c) {
        return new Position(x * c, y * c);
    }

    public Position mult(int x, int y) {
        return new Position(this.x * x, this.y * x);
    }

    public static boolean equals(Position p1, Position p2) {
        return p1.x == p2.x && p1.y == p2.y;
    }

    public boolean isEmpty() {
        return Position.equals(new Position(0,0), this);
    }

    public String toString() {
        return x + ", " + y;
    }
}
