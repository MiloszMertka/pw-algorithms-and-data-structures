package pl.edu.pw.ee;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;
import pl.edu.pw.ee.services.MinSpanningTree;

public class PrimAlgorithm implements MinSpanningTree {

    private Double matrix[][];
    private Queue<Edge> edgesQueue = new PriorityQueue<>();
    private List<Integer> visitedNodes = new ArrayList<>();

    @Override
    public String findMST(String pathToFile) {
        readGraph();

        Double[][] mstMatrix = initializeMSTMatrix();
        String result = "";
        addEdgesToQueue(0);
        while (!edgesQueue.isEmpty()) {
            Edge edge  = edgesQueue.poll();
            
            if (!checkIfNodesAreVisited(edge)) {
                mstMatrix[edge.from][edge.to] = edge.wage;
                
                if (!result.isEmpty()) {
                    result += "|";
                }
                
                result += edge.from + "_" + edge.wage + "_" + edge.to;
            }
        }

        return result;
    }

    private void initializeMatrix(int size) {
        matrix = new Double[size][size];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = null;
            }
        }
    }

    private void readGraph() {
        initializeMatrix(5);

        matrix[0][1] = 3.0;
        matrix[0][2] = 5.0;
        matrix[0][3] = 7.0;
        matrix[1][2] = 1.0;
        matrix[3][4] = 7.0;
    }

    private void addEdgesToQueue(int nodeIndex) {
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[nodeIndex][i] != null) {
                Edge edge = new Edge(nodeIndex, i, matrix[nodeIndex][i]);
                edgesQueue.add(edge);
            }
        }
    }
    
    private boolean checkIfNodesAreVisited(Edge edge) {
        return visitedNodes.contains(edge.from) && visitedNodes.contains(edge.to);
    }
    
    private Double[][] initializeMSTMatrix() {
        int size = matrix.length;
        Double[][] mstMatrix = new Double[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mstMatrix[i][j] = null;
            }
        }
        
        return mstMatrix;
    }

    private static class Edge implements Comparable<Edge> {

        private int from;
        private int to;
        private double wage;

        public Edge(int from, int to, double wage) {
            this.from = from;
            this.to = to;
            this.wage = wage;
        }

        @Override
        public int compareTo(Edge other) {
            if (this.wage > other.wage) {
                return 1;
            }

            if (this.wage == other.wage) {
                return 0;
            }

            return -1;
        }

    }

}
