package prog1.game;

import java.awt.*;

public class Action {

    Position position = new Position(0, 0);
    String msg = "";
    Color color;

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

    public Action(Color color) {
        this.color = color;
    }
}
