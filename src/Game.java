import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JFrame {
    private Snake snake;
    private Dot dot;
    private int boardSize = 20;
    private GridUi gridUi;
    private Random random = new Random();
    public static final int CELL_PIXEL_SIZE = 30;
    private Thread thread;
    private long delayed = 500;
    private boolean gameOver;
    private int staterXSnake = 3;
    private int staterYSnake = 3;

    public Game() {
        addKeyListener(new Controller());
        snake = new Snake(staterXSnake, staterYSnake, boardSize);
        int x = random.nextInt(boardSize);
        int y = random.nextInt(boardSize);
        while (checkCollision(x, y)) {
            x = random.nextInt(boardSize);
            y = random.nextInt(boardSize);
        }
        dot = new Dot(x, y);
        gameOver = false;
        gridUi = new GridUi();
        add(gridUi);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void start() {
        setVisible(true);

        thread = new Thread() {
            @Override
            public void run() {
                while(!gameOver) {
                    // run
                    snake.update();
                    gridUi.repaint();
                    // seedDot();
                    if (checkCollision(dot.getX(), dot.getY())) {
                        snake.increaseTail();
                        seedDot();
                    }
                    if (isGameOver()) {
                        System.out.println("You Lose");
                        break;
                    }
                    if (checkWin()) {
                        System.out.println("Win");
                        break;
                    }
                    waitFor(delayed);
                }
            }
        };
        thread.start();
    }

    private void waitFor(long delayed) {
        try {
            Thread.sleep(delayed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class GridUi extends JPanel {

        public GridUi() {
            setPreferredSize(new Dimension(boardSize * CELL_PIXEL_SIZE,
                    boardSize * CELL_PIXEL_SIZE));
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            drawDot(g);
            drawSnake(g);
        }

        private void drawDot(Graphics g) {
            int x = dot.getX() * CELL_PIXEL_SIZE;
            int y = dot.getY() * CELL_PIXEL_SIZE;

            g.setColor(Color.green);
            g.fillOval(x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE);
        }

        private void drawSnake(Graphics g) {
            int x = snake.getHeadX() * CELL_PIXEL_SIZE;
            int y = snake.getHeadY() * CELL_PIXEL_SIZE;

            g.setColor(Color.red);
            g.fillOval(x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE);

            ArrayList<ArrayList<Integer>> tail = snake.getTail();
            g.setColor(Color.gray);
            for (ArrayList<Integer> p: tail) {
                g.fillOval(p.get(0) * CELL_PIXEL_SIZE, p.get(1) * CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE);
            }
        }
    }

    class Controller extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_UP) {
                snake.moveUp();
            } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                snake.moveDown();
            } else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                snake.moveLeft();
            } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                snake.moveRight();
            }
        }
    }

    private void seedDot() {
        int x = random.nextInt(boardSize);
        int y = random.nextInt(boardSize);

        if (checkCollision(x, y)) {
            seedDot();
        }

        dot.setX(x);
        dot.setY(y);
    }

    private boolean checkCollision(int x, int y) {
        int snakeHeadX = snake.getHeadX();
        int snakeHeadY = snake.getHeadY();

        if (snakeHeadX == x && snakeHeadY == y) {
            return true;
        }

        ArrayList<ArrayList<Integer>> tail = snake.getTail();
        for (ArrayList<Integer> p: tail) {
            if (p.get(0) == x && p.get(1) == y) {
                return true;
            }
        }

        return false;
    }

    private boolean isGameOver() {
        int snakeHeadX = snake.getHeadX();
        int snakeHeadY = snake.getHeadY();

        ArrayList<ArrayList<Integer>> tail = snake.getTail();
        for (ArrayList<Integer> p: tail) {
            if (p.get(0) == snakeHeadX && p.get(1) == snakeHeadY) {
                return true;
            }
        }

        return false;
    }

    private boolean checkWin() {
        return snake.getTail().size() == boardSize * boardSize;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
