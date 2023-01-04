package pl.edu.pw.ee;

import java.io.File;
import java.io.IOException;
import java.util.*;

import pl.edu.pw.ee.services.MinSpanningTree;

public class PrimAlgorithm implements MinSpanningTree {

    private static final String FILE_FORMAT_ERROR = "File is not in correct format!";
    private static final String PATH_ERROR = "Path to file cannot be null or blank!";
    private static final String FILE_IO_ERROR = "Cannot read file ";
    private static final String GRAPH_CONNECTION_ERROR = "Graph is not connected!";

    private final List<Node> graph = new ArrayList<>();
    private final Queue<Edge> edgesQueue = new PriorityQueue<>();
    private final Set<Node> visitedNodes = new HashSet<>();

    @Override
    public String findMST(String pathToFile) {
        validatePathToFile(pathToFile);
        clearAlgorithmState();
        readFile(pathToFile);

        return runPrimAlgorithm();
    }

    private void validatePathToFile(String pathToFile) {
        if (pathToFile == null || pathToFile.isBlank()) {
            throw new IllegalArgumentException(PATH_ERROR);
        }
    }

    private void clearAlgorithmState() {
        graph.clear();
        edgesQueue.clear();
        visitedNodes.clear();
    }

    private String runPrimAlgorithm() {
        StringBuilder result = new StringBuilder();

        addEdgesToQueue(graph.get(0));
        visitedNodes.add(graph.get(0));

        while (!edgesQueue.isEmpty()) {
            Edge edge  = edgesQueue.poll();

            if (!checkIfNodesAreVisited(edge)) {
                appendEdgeToResult(result, edge);
            }
        }

        validateGraphIsConnected();

        return result.toString();
    }

    private void appendEdgeToResult(StringBuilder result, Edge edge) {
        if (result.length() > 0) {
            result.append("|");
        }

        Node from = findNodeByName(edge.from);
        Node to = findNodeByName(edge.to);
        result.append(from.name).append("_").append(edge.wage).append("_").append(to.name);

        addEdgesToQueue(to);
        visitedNodes.add(to);
    }

    private Node findNodeByName(String name) {
        return graph.stream().filter(node -> node.name.equals(name)).findFirst().get();
    }

    private void validateGraphIsConnected() {
        if (visitedNodes.size() != graph.size()) {
            throw new IllegalStateException(GRAPH_CONNECTION_ERROR);
        }
    }

    private void readFile(String pathToFile) {
        File file = new File(pathToFile);

        try (Scanner scanner = new Scanner(file)) {
            readGraph(scanner);
        } catch (IOException ioException) {
            throw new IllegalStateException(FILE_IO_ERROR + ioException);
        }
    }

    private void readGraph(Scanner scanner) {
        while (scanner.hasNextLine()) {
            Edge edge = readEdge(scanner);
            addEdgeToGraph(edge);
        }
    }

    private Edge readEdge(Scanner scanner) {
        validateNextTokenIsString(scanner);
        String from = scanner.next();

        validateNextTokenIsString(scanner);
        String to = scanner.next();

        validateNextTokenIsInt(scanner);
        int wage = scanner.nextInt();
        validateWageIsPositive(wage);

        Edge edge = new Edge(from, to, wage);
        validateEdgeHasDifferentNodes(edge);

        return edge;
    }

    private void validateNextTokenIsString(Scanner scanner) {
        if (!scanner.hasNext("[a-zA-Z]+")) {
            throw new IllegalStateException(FILE_FORMAT_ERROR);
        }
    }

    private void validateNextTokenIsInt(Scanner scanner) {
        if (!scanner.hasNextInt()) {
            throw new IllegalStateException(FILE_FORMAT_ERROR);
        }
    }

    private void validateWageIsPositive(int wage) {
        if (wage <= 0) {
            throw new IllegalStateException(FILE_FORMAT_ERROR);
        }
    }

    private void validateEdgeHasDifferentNodes(Edge edge) {
        if (edge.from.equals(edge.to)) {
            throw new IllegalStateException(FILE_FORMAT_ERROR);
        }
    }

    private void addEdgeToGraph(Edge edge) {
        Node fromNode = new Node(edge.from);
        fromNode.addEdge(edge);

        Node toNode = new Node(edge.to);
        toNode.addEdge(edge);

        addOrUpdateNodeInGraph(fromNode, edge);
        addOrUpdateNodeInGraph(toNode, edge);
    }

    private void addOrUpdateNodeInGraph(Node node, Edge edge) {
        if (graph.contains(node)) {
            Node nodeInGraph = graph.get(graph.indexOf(node));
            nodeInGraph.addEdge(edge);
        } else {
            graph.add(node);
        }
    }

    private void addEdgesToQueue(Node node) {
        edgesQueue.addAll(node.edges);
    }
    
    private boolean checkIfNodesAreVisited(Edge edge) {
        boolean fromPresent = checkIfNodeIsVisitedByName(edge.from);
        boolean toPresent = checkIfNodeIsVisitedByName(edge.to);

        return fromPresent && toPresent;
    }

    private boolean checkIfNodeIsVisitedByName(String name) {
        return visitedNodes.stream().anyMatch(node -> node.name.equals(name));
    }

    private static class Node {

        private final String name;
        private final Set<Edge> edges = new HashSet<>();

        public Node(String name) {
            this.name = name;
        }

        public void addEdge(Edge edge) {
            edges.add(edge);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }

            if (other == null || getClass() != other.getClass()) {
                return false;
            }

            Node node = (Node) other;
            return name.equals(node.name);
        }

    }

    private static class Edge implements Comparable<Edge> {

        private final String from;
        private final String to;
        private final int wage;

        public Edge(String from, String to, int wage) {
            this.from = from;
            this.to = to;
            this.wage = wage;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.wage, other.wage);
        }

    }

}
