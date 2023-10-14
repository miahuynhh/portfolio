package paralleltasks;

import cse332.exceptions.NotYetImplementedException;

import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

// todo: implement
// All class and function signatures in paralleltasks can be changed. The distributed code is just a guideline.

public class RelaxOutTaskBad extends RecursiveAction {

    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;
    private static int[] D1, D2, P;
    private static HashMap<Integer, Integer>[] adjList;
    private final int lo, hi;

    public RelaxOutTaskBad(int[] D1, int[] D2, int[] P, HashMap<Integer, Integer>[] adjList, int lo, int hi) {
        this.D1 = D1;
        this.D2 = D2;
        this.P = P;
        this.adjList = adjList;
        this.lo = lo;
        this.hi = hi;
    }

    protected void compute() {
        if (hi - lo <= CUTOFF) {
            sequential(D1, D2, P, adjList, lo, hi);
        } else {
            int mid = lo + (hi - lo) / 2;
            RelaxOutTaskBad left = new RelaxOutTaskBad(D1, D2, P, adjList, lo, mid);
            RelaxOutTaskBad right = new RelaxOutTaskBad(D1, D2, P, adjList, mid, hi);
            left.fork();
            right.compute();
            left.join();
        }
    }

    public static void sequential(int[] D1, int[] D2, int[] P, HashMap<Integer, Integer>[] adjList, int lo, int hi) {
        for (int w = 0; w < adjList.length; w++) {
            if (adjList[lo].get(w) != null && D2[lo] != Integer.MAX_VALUE) {
                if (D1[w] > D2[lo] + adjList[lo].get(w)) { // is cost(v,w) // D1[2] > D2[1] + adjList[1].get(2)
                    D1[w] = D2[lo] + adjList[lo].get(w);
                    P[w] = lo;
                }
            }
        }
    }

    public static int[] parallel(int[] D1, int[] D2, int[] P, HashMap<Integer, Integer>[] adjList) {
        pool.invoke(new RelaxOutTaskBad(D1, D2, P, adjList, 0, adjList.length));
        return P;
    }

}