package solvers;

import cse332.exceptions.NotYetImplementedException;
import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;
import paralleltasks.ArrayCopyTask;
import paralleltasks.RelaxInTask;
import paralleltasks.RelaxOutTaskBad;

import java.util.HashMap;
import java.util.List;

// todo: implement

public class InParallel implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        HashMap<Integer, Integer>[] adjList = Parser.parseInverse(adjMatrix);
        int n = adjList.length;
        int[] D1 = new int[n];
        int[] D2 = new int[n];
        int[] P = new int[n];
        for (int w = 0; w < n; w++) { // initialize D1
            D1[w] = Integer.MAX_VALUE;
        }
        D1[source] = 0; // source node
        for (int p = 0; p < n; p++) { // initialize P
            P[p] = -1;
        }
        for (int i = 0; i < n; i++) {
            D2 = ArrayCopyTask.copy(D1);
            P = RelaxInTask.parallel(D1, D2, P, adjList);
        }
        return GraphUtil.getCycle(P);
    }
}