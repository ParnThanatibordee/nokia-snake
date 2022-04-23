import javax.swing.*;
import java.awt.*;
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

    public Game() {
        // snake = new Snake();
        dot = new Dot(random.nextInt(boardSize), random.nextInt(boardSize));
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
                    gridUi.repaint();
                    // seedDot();
                    if (dot.isEaten()) {
                        seedDot();
                        // set new eaten
                    }
                    // check
                    // gameOver?
                    // collision
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

    @Override
    public void update(Graphics g) {
        super.update(g);
        // gridUi.repaint();



        // เช็ค status เกม
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

            //for (int row = 0; row < boardSize; row++) {
            //    for (int col = 0; col < boardSize; col++) {
            //        paintCell(g, row, col);
            //    }
            //}
        }

        private void paintCell(Graphics g, int row, int col) {
            int x = col * CELL_PIXEL_SIZE;
            int y = row * CELL_PIXEL_SIZE;


            g.drawRect(x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE);

        }

        private void drawDot(Graphics g) {
            int x = dot.getX() * CELL_PIXEL_SIZE;
            int y = dot.getY() * CELL_PIXEL_SIZE;

            g.setColor(Color.green);
            System.out.println(x + " " + y);
            g.fillOval(x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE);
        }
    }

    private void seedDot() {
        System.out.println("seedDot check");

        int r = random.nextInt(boardSize);
        dot.setX(r);

        r = random.nextInt(boardSize);
        dot.setY(r);

        // ถ้าเป็นช่องที่งูอยู่ในเริ่มใหม่ set ใหม่
        // ทำเป็น loop


    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
