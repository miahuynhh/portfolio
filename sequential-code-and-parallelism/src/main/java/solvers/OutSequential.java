package solvers;

import cse332.exceptions.NotYetImplementedException;
import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;

import java.util.HashMap;
import java.util.List;

// todo: DONE
// first version of the Bellman-Ford algorithm.
// In every version, you will implement the BellmanFordSolver interface,
// which means you will write the body of the solve() method

/* THREE MAIN PARTS
    1) Preparing:
    parsing the adjacency matrix (adjMatrix) to create adjacency lists (using the method you wrote in Parser.java)
    and initializing the necessary data structures (i.e. the D and P arrays).
    This will be sequential in all versions.
 */

public class OutSequential implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        // should I remove Object and put array of hashmap type?
        HashMap<Integer, Integer>[] adjList = Parser.parse(adjMatrix); // adjList stored here
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
            for (int v = 0; v < n; v++) { // copy D1 to D2
                D2[v] = D1[v];
            }
            for (int v = 0; v < n; v++) {
                for (int w = 0; w < n; w++) {
                    if (adjList[v].get(w) != null && D2[v] != Integer.MAX_VALUE) {
                        if (D1[w] > D2[v] + adjList[v].get(w)) { // is cost(v,w) // D1[2] > D2[1] + adjList[1].get(2)
                            D1[w] = D2[v] + adjList[v].get(w);
                            P[w] = v;
                        }
                    }
                }
            }
        }
        return GraphUtil.getCycle(P);
    }
}