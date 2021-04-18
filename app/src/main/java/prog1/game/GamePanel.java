package prog1.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GamePanel extends JPanel implements ActionListener {

    static final int DELAY = 20;
    static final int MOVE_DELAY = 500;
    static final int SCREEN_WIDTH = 580;
    static final int SCREEN_HEIGHT = 500;
    static final int UNIT_SIZE = 50;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int COLUMNS = 10;
    static final int ROWS = 10;
    static final Color COLOR_SQUARE = Color.black;
    static final Color COLOR_DOOR = Color.red;
    static final String PLAYER_IMG_PATH = "app/src/main/resources/player.png";
    static final String STAR_IMG_PATH = "app/src/main/resources/star.png";
    final Image starImg;
    final Image playerImg;
    private java.util.List<Star> stars = new ArrayList<>();
    final List<Move> moves = new ArrayList<>();
    private Button startStopButton;
    private Label label;

    long lastMove = 0;

    boolean running = false;
    private Player player;
    private Door door;
    int score = 0;

    Timer timer;

    GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.initButton();
        this.initLabel();
        this.setLayout(null);
        this.startGame();
        this.setScore();
        this.player = new Player();
        this.door = new Door(9, 4);
        this.playerImg = readImage(PLAYER_IMG_PATH);
        this.starImg = readImage(STAR_IMG_PATH);
        new Sketch(moves);
        this.setStars(10);
    }

    private void initLabel() {
        label = new Label("");
        label.setBounds(500, 30, 70, 20);
        this.add(label);
    }

    private void initButton() {
        startStopButton = new Button("Play");
        startStopButton.setBounds(500, 0, 50, 20);
        startStopButton.addActionListener(e -> startStopMoves());
        this.add(startStopButton);
    }

    private void setStars(int nOfStars) {
        while (stars.size() < nOfStars) {
            Star newStar = new Star();
            if (stars.stream().anyMatch(s -> Star.positionEquals(s, newStar)) ||
                Star.positionEquals(newStar, player) ||
                Star.positionEquals(newStar, door)) {
                continue;
            }
            stars.add(newStar);
        }
    }

    private void setScore() {
        label.setText("Score: " + score);
    }

    public static Image readImage(String path) {
        File imgFile = new File(path);
        BufferedImage img;
        try {
            img = ImageIO.read(imgFile);
        } catch (IOException e) {
            System.out.println("Couldn't read image. Exiting.");
            e.printStackTrace();
            System.exit(1);
            return null;
        }
        return img;
    }

    public void startGame() {
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void startStopMoves() {
        running = !running;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        paintSquares(g);
        door.paint(g);
        stars.forEach(s -> s.paint(g));
        player.paint(g);
    }

    public void paintSquares(Graphics g) {
        g.setColor(COLOR_SQUARE);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                g.fillRect(UNIT_SIZE * j + 1, UNIT_SIZE * i + 1, UNIT_SIZE - 2, UNIT_SIZE - 2);
            }
        }
    }

    public void gameOver(Graphics g) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            if (System.currentTimeMillis() - lastMove > MOVE_DELAY) {
                System.out.println("help");
                lastMove = System.currentTimeMillis();
                Move nextMove = moves.remove(0);
                System.out.println(nextMove.x + " " + nextMove.y);
                player.move(nextMove.x, nextMove.y);
                if (moves.size() == 0) running = false;
            }
        }
        Optional<Star> overlapped = stars.stream().filter(s -> Star.positionEquals(s, player)).findAny();
        if (overlapped.isPresent()) {
            stars.remove(overlapped.get());
            score += 1;
            setScore();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_DOWN:
                    player.moveY(1);
                    break;
                case KeyEvent.VK_UP:
                    player.moveY(-1);
                    break;
                case KeyEvent.VK_LEFT:
                    player.moveX(-1);
                    break;
                case KeyEvent.VK_RIGHT:
                    player.moveX(1);
                    break;
            }
        }
    }
}
