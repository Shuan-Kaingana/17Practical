//Claude AI was used to help structure and fix errors in this code

import java.util.LinkedList;
import java.util.Queue;

public class timedBTSMethods {
    private static final int REPS = 10;
    
    public static void main(String[]args){
        System.out.println("=== Correctness check: n = 4 ===");
        int  nTest   = 4;
        long maxTest = (1L << nTest) - 1;   // 15
 
        Tree T = new Tree();
        T.populatePerfect(1, maxTest);
        System.out.println("Nodes inserted (1.." + maxTest + "): " + T.size());
        System.out.println("isBST() before removal : " + T.isBST());
        System.out.print("In-order before removal: ");
        printInorder(T.getRoot());
        System.out.println();
 
        T.removeEvens();
        System.out.println("isBST() after  removal : " + T.isBST());
        System.out.print("In-order after  removal: ");
        printInorder(T.getRoot());
        System.out.println("\n(Only odd numbers should remain)\n");
 
        // ── 2. Timing test with n = 20 (1 048 575 nodes) ─────────────────────
        int  n      = 20;
        long maxKey = (1L << n) - 1;
        System.out.println("=== Timing test: n = " + n
                + ",  keys 1.." + maxKey + " ===");
        System.out.println("Running " + REPS + " repetitions ...\n");
 
        Tree tree = new Tree();
 
        double[] popTimes = new double[REPS];
        for (int i = 0; i < REPS; i++) {
            tree.clear();
            long t0 = System.nanoTime();
            tree.populatePerfect(1, maxKey);
            popTimes[i] = (System.nanoTime() - t0) / 1_000_000.0; }
 
        double[] remTimes = new double[REPS];
        for (int i = 0; i < REPS; i++) {
            tree.clear();
            tree.populatePerfect(1, maxKey);
            long t0 = System.nanoTime();
            tree.removeEvens();
            remTimes[i] = (System.nanoTime() - t0) / 1_000_000.0; }
 
        double popAvg = mean(popTimes), popStd = stdDev(popTimes, popAvg);
        double remAvg = mean(remTimes), remStd = stdDev(remTimes, remAvg);
 
        System.out.printf("%-30s %20s %20s%n",
                "Method", "Average time (ms)", "Standard Deviation");
        System.out.println("-".repeat(72));
        System.out.printf("%-30s %20.3f %20.3f%n",
                "Populate tree",          popAvg, popStd);
        System.out.printf("%-30s %20.3f %20.3f%n",
                "Remove evens from tree", remAvg, remStd);
        System.out.println("-".repeat(72));
        System.out.printf("%nNumber of keys n = %d  (total nodes = %,d)%n",
                n, maxKey);
    }
    
    static double mean(double[] d) {
        double s = 0;
        for (double v : d) s += v;
        return s / d.length;
    }

    static double stdDev(double[] d, double avg) {
        double s = 0;
        for (double v : d) s += (v - avg) * (v - avg);
        return Math.sqrt(s / d.length); 
    }
    
    static void printInorder(tNode n) {
        if (n == null) return;
        printInorder(n.getLeft());
        System.out.print(n.getKey() + " ");
        printInorder(n.getRight());
    }
}
