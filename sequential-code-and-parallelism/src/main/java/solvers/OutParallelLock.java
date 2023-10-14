package solvers;

import cse332.exceptions.NotYetImplementedException;
import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;
import paralleltasks.ArrayCopyTask;
import paralleltasks.RelaxOutTaskBad;
import paralleltasks.RelaxOutTaskLock;
import java.util.concurrent.locks.ReentrantLock;


import java.util.HashMap;
import java.util.List;

// todo: implement

public class OutParallelLock implements BellmanFordSolver {
    private static ReentrantLock lk[];

    public List<Integer> solve(int[][] adjMatrix, int source) {
        HashMap<Integer, Integer>[] adjList = Parser.parse(adjMatrix); // adjList stored here
        this.lk = new ReentrantLock[adjList.length];
        for (int i = 0; i < adjList.length; i++) {
            lk[i] = new ReentrantLock();
        }
        int n = adjList.length;
        int[] D1 = new int[n];
        int[] D2 = new int[n];
        int[] P = new int[n];
        for (int v = 0; v < n; v++) { // initialize D1
            D1[v] = Integer.MAX_VALUE;
        }
        D1[source] = 0; // source node
        for (int p = 0; p < n; p++) { // initialize P
            P[p] = -1;
        }
        for (int i = 0; i < n; i++) {
            D2 = ArrayCopyTask.copy(D1);
            P = RelaxOutTaskLock.parallel(D1, D2, P, adjList, lk);
        }
        return GraphUtil.getCycle(P);
    }

}