import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static int top = 0;
    private int size;
    private int bottom;
    private int openSitesNum;
    private boolean[][] openSites;
    private WeightedQuickUnionUF ufData;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;

        bottom = n * n + 1;

        openSites = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            openSites[i][i] = false;
        }

        ufData = new WeightedQuickUnionUF(n * n + 2);

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if ((row < 1 || row > size) || (col < 1 || col > size)) {
            throw new IllegalArgumentException();
        }

        int rowIndex = row - 1;
        int colIndex = col - 1;
        if (!openSites[rowIndex][colIndex]) {
            openSites[rowIndex][colIndex] = true;
            openSitesNum++;

            int number = col + (row - 1) * size;
            int altNumber;

            // row 1 is always connected to the top if opened
            if (row == 1) {
                ufData.union(top, number);
            }
            // the last row is always connected to the bottom if opened
            if (row == size) {
                ufData.union(bottom, number);
            }

            // check if adjacent sites are in range and opened
            // if they are, connect them to the recently opened site
            if (row - 1 >= 1 && isOpen(row - 1, col)) {
                altNumber = col + (row - 2) * size;
                ufData.union(number, altNumber);
            }
            if (row + 1 <= size && isOpen(row + 1, col)) {
                altNumber = col + row * size;
                ufData.union(number, altNumber);
            }
            if (col - 1 >= 1 && isOpen(row, col - 1)) {
                altNumber = (col - 1) + (row - 1) * size;
                ufData.union(number, altNumber);
            }
            if (col + 1 <= size && isOpen(row, col + 1)) {
                altNumber = (col + 1) + (row - 1) * size;
                ufData.union(number, altNumber);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row < 1 || row > size) || (col < 1 || col > size)) {
            throw new IllegalArgumentException();
        }

        int rowIndex = row - 1;
        int colIndex = col - 1;
        if (openSites[rowIndex][colIndex]) return true;
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row < 1 || row > size) || (col < 1 || col > size)) {
            throw new IllegalArgumentException();
        }

        if (isOpen(row, col)) {
            int number = col + (row - 1) * size;

            int numberRoot = ufData.find(number);
            int topRoot = ufData.find(top);

            return numberRoot == topRoot;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesNum;
    }

    // does the system percolate?
    public boolean percolates() {
        int bottomRoot = ufData.find(bottom);
        int topRoot = ufData.find(top);
        return bottomRoot == topRoot;
    }

    public static void main(String[] args) {
        StdOut.println("Please, run PercolationStats.");
    }
}
