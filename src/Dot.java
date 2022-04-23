public class Dot {
    private int x;
    private int y;
    private boolean eaten;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
        eaten = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isEaten() {
        return eaten;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }
}
