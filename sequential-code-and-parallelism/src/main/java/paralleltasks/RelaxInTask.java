package paralleltasks;

import cse332.exceptions.NotYetImplementedException;

import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

// todo: implement
// All class and function signatures in paralleltasks can be changed. The distributed code is just a guideline.

public class RelaxInTask extends RecursiveAction {

    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;
    final int lo, hi;
    private static int[] D1, D2, P;
    private static HashMap<Integer, Integer>[] adjList;

    public RelaxInTask(int[] D1, int[] D2, int[] P, HashMap<Integer, Integer>[] adjList, int lo, int hi) {
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
            RelaxInTask left = new RelaxInTask(D1, D2, P, adjList, lo, mid);
            RelaxInTask right = new RelaxInTask(D1, D2, P, adjList, mid, hi);
            left.fork();
            right.compute();
            left.join();
        }
    }

    // todo: needs editing
    public static void sequential(int[] D1, int[] D2, int[] P, HashMap<Integer, Integer>[] adjList, int lo, int hi) {
        for (int v = 0; v < adjList.length; v++) {
            if (adjList[lo].get(v) != null && D2[v] != Integer.MAX_VALUE) {
                if (D1[lo] > D2[v] + adjList[lo].get(v)) { // is cost(v,w) // D1[2] > D2[1] + adjList[1].get(2)
                    D1[lo] = D2[v] + adjList[lo].get(v);
                    P[lo] = v;
                }
            }
        }
    }

    public static int[] parallel(int[] D1, int[] D2, int[] P, HashMap<Integer, Integer>[] adjList) {
        pool.invoke(new RelaxInTask(D1, D2, P, adjList, 0, adjList.length));
        return P;
    }
}