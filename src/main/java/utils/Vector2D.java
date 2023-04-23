package utils;

public class Vector2D {
    private double x;
    private double y;


    public Vector2D(double x, double y) {
        this.setX(x);
        this.setY(y);
    }


    public double dot(Vector2D other){
        return this.getX()*other.getX()+this.getY()*other.getY();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
