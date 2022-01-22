import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trialsNum;
    private double[] thresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        if (trials <= 0) {
            throw new IllegalArgumentException();
        }
        trialsNum = trials;
        thresholds = new double[trialsNum];

        int openSitesNum;
        double percThreshold;
        for (int i = 0; i < trialsNum; i++) {
            Percolation percolation = new Percolation(n);
            openSitesNum = 0;
            while (!percolation.percolates()) {
                // pick a random (row,col) on the interval [1,n]
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;

                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    openSitesNum++;
                }
            }
            percThreshold = (double) openSitesNum / (n * n);
            thresholds[i] = percThreshold;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double x = mean();
        double s = stddev();

        return (x - 1.96 * s / Math.sqrt(trialsNum));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double x = mean();
        double s = stddev();

        return (x + 1.96 * s / Math.sqrt(trialsNum));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);

        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = "
                               + stats.confidenceLo() + ", "
                               + stats.confidenceHi());
    }
}
