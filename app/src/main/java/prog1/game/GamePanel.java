package prog1.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
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
    static final int N_OF_STARS = 10;
    static final Color COLOR_SQUARE = Color.black;
    static final Color COLOR_DOOR = Color.red;
    static final String PLAYER_IMG_PATH = "app/src/main/resources/player.png";
    static final String STAR_IMG_PATH = "app/src/main/resources/star.png";
    private final java.util.List<Star> stars = new ArrayList<>();
    private Button startStopButton;
    private Button resetButton;
    private Label scoreLabel;
    private JLabel gameOverLabel;

    long lastMove = 0;

    boolean running = false;
    boolean gameOver = false;
    private final Player player;
    private final Door door;
    int score = 0;

    Timer timer;

    GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.initStartStopButton();
        this.initResetButton();
        this.initScoreLabel();
        this.initGameOverLabel();
        this.setScore();
        this.door = new Door();
        this.player = new Player();
        this.player.setDoorPosition(door.position);
        this.setStars(N_OF_STARS);
        this.startGame();
    }

    private void initScoreLabel() {
        scoreLabel = new Label("");
        scoreLabel.setBounds(500, 60, 70, 20);
        this.add(scoreLabel);
    }

    private void initGameOverLabel() {
        gameOverLabel = new JLabel("You won!");
        gameOverLabel.setBounds(150, 200, 200, 100);
        gameOverLabel.setHorizontalAlignment(JLabel.CENTER);
        Font labelFont = gameOverLabel.getFont();
        gameOverLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 42));
        gameOverLabel.setForeground(Color.white);
    }

    private void initStartStopButton() {
        startStopButton = new Button("Play");
        startStopButton.setBounds(500, 0, 50, 20);
        startStopButton.addActionListener(e -> startStopMoves());
        this.add(startStopButton);
    }

    private void initResetButton() {
        resetButton = new Button("Reset");
        resetButton.setBounds(500, 30, 50, 20);
        resetButton.addActionListener(e -> resetEverything());
        this.add(resetButton);
    }

    private void resetEverything() {
        gameOver = false;
        running = false;
        this.remove(this.gameOverLabel);
        stars.clear();
        player.resetMoves();
        player.reset();
        setStars(N_OF_STARS);
        calculateSketch();
        player.reset();
        score = 0;
        setScore();
        this.grabFocus();
    }

    private void setStars(int nOfStars) {
        while (stars.size() < nOfStars) {
            Star newStar = new Star();
            if (stars.stream().anyMatch(s -> Position.equals(s.position, newStar.position)) ||
                    Position.equals(newStar.position, player.position) ||
                    Position.equals(newStar.position, door.position)) {
                continue;
            }
            stars.add(newStar);
        }
    }

    private void setScore() {
        scoreLabel.setText("Score: " + score);
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
        lastMove = System.currentTimeMillis();
        calculateSketch();
        this.grabFocus();
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
        if (gameOver) gameOver(g);
    }

    public void paintSquares(Graphics g) {
        g.setColor(COLOR_SQUARE);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                g.fillRect(UNIT_SIZE * j + 1, UNIT_SIZE * i + 1, UNIT_SIZE - 2, UNIT_SIZE - 2);
            }
        }
    }

    private void calculateSketch() {
        player.moves.clear();
        Position lastPosition = new Position(player.position);
        try {
            Sketch.sketch(player);
        } catch (MaxMovesException e) {
            System.out.println("Your sketch has exceeded the maximum number of allowed moves (10,000)." +
                    "This is most likely caused by an error in your sketch logic.");
            running = false;
        }
        player.position = lastPosition;
    }

    public void gameOver(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, 500, 500);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            if (running) {
                if (System.currentTimeMillis() - lastMove > MOVE_DELAY) {
                    lastMove = System.currentTimeMillis();
                    player.nextMove();
                    if (player.movesDone()) running = false;
                }
            }
            Optional<Star> overlapped = stars.stream().filter(s -> Position.equals(s.position, player.position)).findAny();
            if (overlapped.isPresent()) {
                stars.remove(overlapped.get());
                score += 1;
                setScore();
            }
            if (stars.size() == 0 && Position.equals(door.position, player.position)) {
                running = false;
                gameOver = true;
                this.add(gameOverLabel);
            }
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!gameOver) {
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
}
