package main;

import cse332.exceptions.NotYetImplementedException;
import java.util.HashMap;

// todo: DONE
// parse the graph given as input to the solve() methods.
// The input graph is represented as an adjacency matrix (adjMatrix),
// but it needs to be represented as an adjacency list instead to run the algorithm.
// The adjacency matrix should be transformed into an adjacency list of nodes reached through outgoing edges.
// This will be done sequentially (means for loop)
public class Parser {

    /**
     * Parse an adjacency matrix into an adjacency list.
     * @param adjMatrix Adjacency matrix
     * @return Adjacency list
     */
    public static HashMap<Integer, Integer>[] parse(int[][] adjMatrix) {
        int n = adjMatrix.length;
        HashMap<Integer, Integer>[] adjList = new HashMap[n];
        for (int v = 0; v < n; v++) { // looking through v, index
            adjList[v] = new HashMap<>(); // new HashMap at each index
            for (int w = 0; w < adjMatrix[v].length; w++) { // look through w, neighbors
                if (adjMatrix[v][w] != Integer.MAX_VALUE) { // if not infinity
                    adjList[v].put(w, adjMatrix[v][w]); // then is a neighbor: key is neighbor, value is cost
                }
            }
        }
        return adjList;
    }

    /**
     * Parse an adjacency matrix into an adjacency list with incoming edges instead of outgoing edges.
     * @param adjMatrix Adjacency matrix
     * @return Adjacency list with incoming edges
     */
    // no worries yet
    public static HashMap<Integer, Integer>[] parseInverse(int[][] adjMatrix) {
        int n = adjMatrix.length;
        HashMap<Integer, Integer>[] adjList = new HashMap[n];
        for (int w = 0; w < n; w++) {
            adjList[w] = new HashMap<>();
            for (int v = 0; v < n; v++) {
                if (adjMatrix[v][w] != Integer.MAX_VALUE) {
                    adjList[w].put(v, adjMatrix[v][w]);
                }
            }
        }
        return adjList;
    }
}