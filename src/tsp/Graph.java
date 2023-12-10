package tsp;

public class Graph {
    private final tsp.ArrayList<TSPGene> nodes;
    private final HashMap<TSPGene, HashMap<TSPGene, Double>> adjacencyMap;

    public Graph(tsp.ArrayList<TSPGene> nodes) {
        this.nodes = new tsp.ArrayList<>(nodes);
        this.adjacencyMap = new HashMap<>();

        for (TSPGene node : nodes) {
            adjacencyMap.put(node, new HashMap<>());
        }
    }

    public void addEdge(TSPGene node1, TSPGene node2, double distance) {
        adjacencyMap.get(node1).put(node2, distance);
        adjacencyMap.get(node2).put(node1, distance); // Assuming undirected graph
    }

    public tsp.ArrayList<TSPGene> getNodes() {
        return new tsp.ArrayList<>(nodes);
    }

    public double getDistance(TSPGene node1, TSPGene node2) {
        HashMap<TSPGene, Double> node1Edges = adjacencyMap.get(node1);
        if (node1Edges != null) {
            Double distance = node1Edges.get(node2);
            if (distance != null) {
                return distance;
            }
        }
        return Double.POSITIVE_INFINITY; // or throw an exception, depending on your requirements
    }

    
}
