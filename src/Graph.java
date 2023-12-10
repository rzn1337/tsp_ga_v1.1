public class Graph {
    private final ArrayList<Gene> nodes;
    private final HashMap<Gene, HashMap<Gene, Double>> adjacencyMap;

    public Graph(ArrayList<Gene> nodes) {
        this.nodes = new ArrayList<>(nodes);
        this.adjacencyMap = new HashMap<>();

        for (Gene node : nodes) {
            adjacencyMap.put(node, new HashMap<>());
        }
    }

    public void addEdge(Gene node1, Gene node2, double distance) {
        adjacencyMap.get(node1).put(node2, distance);
        adjacencyMap.get(node2).put(node1, distance);
    }

    public ArrayList<Gene> getNodes() {
        return new ArrayList<>(nodes);
    }

    public double getDistance(Gene node1, Gene node2) {
        HashMap<Gene, Double> node1Edges = adjacencyMap.get(node1);
        if (node1Edges != null) {
            Double distance = node1Edges.get(node2);
            if (distance != null) {
                return distance;
            }
        }
        return Double.POSITIVE_INFINITY;
    }
}
