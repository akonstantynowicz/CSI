package pl.edu.ug.structures;

public class Key implements Comparable<Key>{

    private final int x;
    private final int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Key(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return x == key.x && y == key.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y +")";
    }

    @Override
    public int compareTo(Key key) {
        if (key.x == this.x && key.y == this.y) return 0;
        if(this.x > key.x) return 1;
        if(this.x == key.x && this.y > key.y) return 1;
        if(this.x < key.x) return -1;
        if(this.x == key.x && this.y < key.y) return -1;
        return 0;
    }
}
