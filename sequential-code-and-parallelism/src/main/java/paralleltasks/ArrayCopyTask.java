package paralleltasks;

import cse332.exceptions.NotYetImplementedException;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

// todo: implement
// All class and function signatures in parallel tasks can be changed. The distributed code is just a guideline.
public class ArrayCopyTask extends RecursiveAction {
    public static final ForkJoinPool pool = new ForkJoinPool();
    private final int[] src, dst;
    private final int lo, hi;
    public static final int CUTOFF = 1;

    // constructor
    public ArrayCopyTask(int[] src, int[] dst, int lo, int hi) {
        this.src = src;
        this.dst = dst;
        this.lo = lo;
        this.hi = hi;
    }

    @SuppressWarnings("ManualArrayCopy")
    protected void compute() {
        if (hi - lo <= CUTOFF) {
            dst[lo] = src[lo];
        } else {
            int mid = lo + (hi - lo) / 2;
            ArrayCopyTask left = new ArrayCopyTask(src, dst, lo, mid);
            ArrayCopyTask right = new ArrayCopyTask(src, dst, mid, hi);
            left.fork();
            right.compute();
            left.join();
        }
    }

    public static int[] copy(int[] src) { // the main copy method
        int[] dst = new int[src.length];
        pool.invoke(new ArrayCopyTask(src, dst, 0, src.length));
        return dst;
    }
}