package tsp;

import java.util.Random;

public class TSPChromosome implements Comparable<TSPChromosome> {
    private final Graph graph;
    private final tsp.ArrayList<TSPGene> chromosome;
    private final double distance;

    public double getDistance() {
        return this.distance;
    }

    private TSPChromosome(final Graph graph, final tsp.ArrayList<TSPGene> chromosome) {
        this.graph = graph;
        this.chromosome = new tsp.ArrayList<>(chromosome);
        this.distance = calculateDistance();
    }
    static TSPChromosome create(final Graph graph) {
        tsp.ArrayList<TSPGene> nodes = new tsp.ArrayList<>(graph.getNodes());
        nodes.shuffle();
        return new TSPChromosome(graph, nodes);
    }
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (final TSPGene gene : this.chromosome) {
            builder.append(gene.toString()).append((" : "));
        }
        return builder.toString();
    }

    tsp.ArrayList<TSPGene> getChromosome() {
        return this.chromosome;
    }

    double calculateDistance() {
        double total = 0.0;
        for (int i = 0; i < this.chromosome.size() - 1; i++) {
            TSPGene currentGene = this.chromosome.get(i);
            TSPGene nextGene = this.chromosome.get(i + 1);
            total += this.graph.getDistance(currentGene, nextGene);
        }
        total += this.graph.getDistance(this.chromosome.get(chromosome.size() - 1), this.chromosome.get(0));
        return total;
    }
    TSPChromosome[] crossOver(final TSPChromosome other) {
        Graph graph = this.graph;
        tsp.ArrayList<TSPGene> myNodes = this.chromosome;
        tsp.ArrayList<TSPGene> otherNodes = other.getChromosome();
        tsp.ArrayList<TSPGene> firstCrossOver = new tsp.ArrayList<>();
        tsp.ArrayList<TSPGene> secondCrossOver = new tsp.ArrayList<>();
        int splitIndex = new Random().nextInt(myNodes.size());
        for (int i = 0; i < splitIndex; i++) {
            firstCrossOver.add(myNodes.get(i));
            secondCrossOver.add(otherNodes.get(i));
        }
        for (TSPGene gene : otherNodes) {
            if (!firstCrossOver.contains(gene)) {
                firstCrossOver.add(gene);
            }
        }
        for (TSPGene gene : myNodes) {
            if (!secondCrossOver.contains(gene)) {
                secondCrossOver.add(gene);
            }
        }
        if (firstCrossOver.size() != graph.getNodes().size() || secondCrossOver.size() != graph.getNodes().size()) {
            throw new RuntimeException("oops!");
        }
        return new TSPChromosome[]{
                new TSPChromosome(graph, firstCrossOver),
                new TSPChromosome(graph, secondCrossOver)
        };
    }


    TSPChromosome mutate() {
        Graph graph = this.graph;
        tsp.ArrayList<TSPGene> nodes = new tsp.ArrayList<>(this.chromosome);
        int indexA = TSPUtils.randomIndex(nodes.size());
        int indexB = TSPUtils.randomIndex(nodes.size());
        while (indexA == indexB) {
            indexB = TSPUtils.randomIndex(nodes.size());
        }
        nodes.swap(indexA, indexB);
        if (nodes.size() != graph.getNodes().size()) {
            throw new RuntimeException("oops!");
        }
        return new TSPChromosome(graph, nodes);
    }

    @Override
    public int compareTo(TSPChromosome o) {
        return 0;
    }
}


