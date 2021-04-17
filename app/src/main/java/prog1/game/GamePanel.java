package prog1.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {

    static final int DELAY = 20;
    static final int SCREEN_WIDTH = 500;
    static final int SCREEN_HEIGHT = 500;
    static final int UNIT_SIZE = 50;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int COLUMNS = 10;
    static final int ROWS = 10;
    static final int PLAYER_SIZE = 40;
    static final int start = UNIT_SIZE / 2 - PLAYER_SIZE / 2;
    static final Color COLOR_SQUARE = Color.black;
    static final Color COLOR_PLAYER = Color.blue;
    static final Color COLOR_DOOR = Color.red;

    boolean running = false;

    int xOff, yOff = 0;

    Timer timer;
    Graphics g;

    GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.startGame();
    }

    private void drawSquare(Graphics g, int x, int y, int size) {
        g.drawRect(x - size / 2, y - size / 2, size, size);
    }

    public void startGame() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        paintSquares(g);
        paintPlayer(g);
    }

    private void paintPlayer(Graphics g) {
        g.setColor(COLOR_PLAYER);
        g.fillOval(start + UNIT_SIZE * xOff, start + UNIT_SIZE * yOff, PLAYER_SIZE - 1, PLAYER_SIZE - 1);
    }

    public void paintSquares(Graphics g) {
        g.setColor(COLOR_SQUARE);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (j == 9 && i == 4) {
                    paintDoor(g, i, j);
                    continue;
                }
                g.fillRect(UNIT_SIZE * j + 1, UNIT_SIZE * i + 1, UNIT_SIZE - 2, UNIT_SIZE - 2);
            }
        }
    }

    private void paintDoor(Graphics g, int i, int j) {
        Color lastColor = g.getColor();
        g.setColor(COLOR_DOOR);
        g.fillRect(UNIT_SIZE * j + 1, UNIT_SIZE * i + 1, UNIT_SIZE - 2, UNIT_SIZE - 2);
        g.setColor(lastColor);
    }

    public void gameOver(Graphics g) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            checkBounds();
        }
        repaint();
    }

    private void checkBounds() {
        if (xOff >= COLUMNS) xOff = COLUMNS - 1;
        if (xOff < 0) xOff = 0;
        if (yOff >= ROWS) yOff = ROWS - 1;
        if (yOff < 0) yOff = 0;
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_DOWN:
                    yOff += 1;
                    break;
                case KeyEvent.VK_UP:
                    yOff -= 1;
                    break;
                case KeyEvent.VK_LEFT:
                    xOff -= 1;
                    break;
                case KeyEvent.VK_RIGHT:
                    xOff += 1;
                    break;
            }
        }
    }
}
