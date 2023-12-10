public class Gene implements Comparable<Gene> {
    private final int x;
    private final int y;

    public Gene(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gene tspGene = (Gene) o;
        return x == tspGene.x && y == tspGene.y;
    }

    @Override
    public int hashCode() {
        int result = 17; // A prime number to initialize the result
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }

    public double distance(Gene other) {
        return Math.sqrt(Math.pow(getX() - other.getX(), 2) + Math.pow(getY() - other.getY(), 2));
    }

    @Override
    public int compareTo(Gene o) {
        return 0;
    }
}
