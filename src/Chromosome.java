import java.util.Random;

public class Chromosome implements Comparable<Chromosome> {
    private final Graph graph;
    private final ArrayList<Gene> chromosome;
    private final double distance;

    public double getDistance() {
        return this.distance;
    }

    private Chromosome(final Graph graph, final ArrayList<Gene> chromosome) {
        this.graph = graph;
        this.chromosome = new ArrayList<>(chromosome);
        this.distance = calculateDistance();
    }
    static Chromosome create(final Graph graph) {
        ArrayList<Gene> nodes = new ArrayList<>(graph.getNodes());
        nodes.shuffle();
        return new Chromosome(graph, nodes);
    }
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (final Gene gene : this.chromosome) {
            builder.append(gene.toString()).append((" : "));
        }
        return builder.toString();
    }

    ArrayList<Gene> getChromosome() {
        return this.chromosome;
    }

    double calculateDistance() {
        double total = 0.0;
        for (int i = 0; i < this.chromosome.size() - 1; i++) {
            Gene currentGene = this.chromosome.get(i);
            Gene nextGene = this.chromosome.get(i + 1);
            total += this.graph.getDistance(currentGene, nextGene);
        }
        total += this.graph.getDistance(this.chromosome.get(chromosome.size() - 1), this.chromosome.get(0));
        return total;
    }
    Chromosome[] crossOver(final Chromosome other) {
        Graph graph = this.graph;
        ArrayList<Gene> myNodes = this.chromosome;
        ArrayList<Gene> otherNodes = other.getChromosome();
        ArrayList<Gene> firstCrossOver = new ArrayList<>();
        ArrayList<Gene> secondCrossOver = new ArrayList<>();
        int splitIndex = new Random().nextInt(myNodes.size());
        for (int i = 0; i < splitIndex; i++) {
            firstCrossOver.add(myNodes.get(i));
            secondCrossOver.add(otherNodes.get(i));
        }
        for (Gene gene : otherNodes) {
            if (!firstCrossOver.contains(gene)) {
                firstCrossOver.add(gene);
            }
        }
        for (Gene gene : myNodes) {
            if (!secondCrossOver.contains(gene)) {
                secondCrossOver.add(gene);
            }
        }
        if (firstCrossOver.size() != graph.getNodes().size() || secondCrossOver.size() != graph.getNodes().size()) {
            throw new RuntimeException("oops!");
        }
        return new Chromosome[]{
                new Chromosome(graph, firstCrossOver),
                new Chromosome(graph, secondCrossOver)
        };
    }


    Chromosome mutate() {
        Graph graph = this.graph;
        ArrayList<Gene> nodes = new ArrayList<>(this.chromosome);
        int indexA = Utils.randomIndex(nodes.size());
        int indexB = Utils.randomIndex(nodes.size());
        while (indexA == indexB) {
            indexB = Utils.randomIndex(nodes.size());
        }
        nodes.swap(indexA, indexB);
        if (nodes.size() != graph.getNodes().size()) {
            throw new RuntimeException("oops!");
        }
        return new Chromosome(graph, nodes);
    }

    @Override
    public int compareTo(Chromosome o) {
        return 0;
    }
}


