package prog1.game;

public class Action {

    Position position;
    String msg;

    public Action(Position position, String msg) {
        this.position = position;
        this.msg = msg;
    }

    public Action(Position position) {
        this.position = position;
        this.msg = "";
    }

    public Action(String msg) {
        this.position = new Position(0, 0);
        this.msg = msg;
    }
}
