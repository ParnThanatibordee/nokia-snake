import java.util.ArrayList;

public class Snake {
    private int headX;
    private int headY;
    private final int initTailNum = 3;
    private int dx = 0;
    private int dy = 0;
    private int boardSize;

    ArrayList<ArrayList<Integer>> tail = new ArrayList<ArrayList<Integer>>();

    public Snake(int headX, int headY, int broadSize) {
        this.headX = headX;  // initialize
        this.headY = headY;  // initialize
        this.boardSize = broadSize;
        initTail();
    }

    public void initTail() {
        for (int i=0; i<initTailNum; i++) {
            ArrayList<Integer> temp = new ArrayList<Integer>();
            temp.add(headX - i - 1);
            temp.add(headY);
            tail.add(temp);
        }
    }

    public void increaseTail() {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.add(tail.get(tail.size()-1).get(0)-dx);
        temp.add(tail.get(tail.size()-1).get(1)-dy);
        tail.add(temp);
    }

    public void moveUp() {
        dx = 0;
        dy = -1;
    }

    public void moveDown() {
        dx = 0;
        dy = 1;
    }

    public void moveLeft() {
        dx = -1;
        dy = 0;
    }

    public void moveRight() {
        dx = 1;
        dy = 0;
    }

    public void updateTail(int headX, int headY) {
        int preX;
        int preY;
        int nextX = headX;
        int nextY = headY;

        for (ArrayList<Integer> p: tail) {
            preX = p.get(0);
            preY = p.get(1);

            p.set(0, nextX);
            p.set(1, nextY);

            nextX = preX;
            nextY = preY;
        }
    }

    public void update() {
        int tempX = headX;
        int tempY = headY;

        headX += dx;
        headY += dy;

        if (headX < 0) {
            headX = boardSize - 1;
        } else if (headX >= boardSize) {
            headX = 0;
        }

        if (headY < 0) {
            headY = boardSize - 1;
        } else if (headY >= boardSize) {
            headY = 0;
        }

        if (dx != 0 || dy != 0) {
            updateTail(tempX, tempY);
        }
    }

    public int getHeadX() {
        return headX;
    }

    public int getHeadY() {
        return headY;
    }

    public ArrayList<ArrayList<Integer>> getTail() {
        return tail;
    }
}
