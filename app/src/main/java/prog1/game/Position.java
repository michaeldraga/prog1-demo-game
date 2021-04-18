package prog1.game;

public class Position {
    int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static boolean equals(Position p1, Position p2) {
        return p1.x == p2.x && p1.y == p2.y;
    }
}
