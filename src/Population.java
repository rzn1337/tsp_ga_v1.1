import java.util.stream.IntStream;

public class Population {

    private ArrayList<Chromosome> population;
    private final int initialSize;
    private final Graph graph;

    Population(final Graph graph, final int initialSize) {
        this.population = init(graph, initialSize);
        this.initialSize = initialSize;
        this.graph = graph;
    }

    ArrayList<Chromosome> getPopulation() {
        return this.population;
    }

    Chromosome getAlpha() {
        return this.population.get(0);
    }

    private ArrayList<Chromosome> init(final Graph graph, final int initialSize) {
        final ArrayList<Chromosome> eden = new ArrayList<>();
        for (int i = 0; i < initialSize; i++) {
            final Chromosome chromosome = Chromosome.create(graph);
            eden.add(chromosome);
        }
        return eden;
    }

    void update() {
        doCrossOver();
        doMutation();
        doSpawn();
        doSelection();
    }


    private void doSelection() {
        elementsSortByDistance();
        limitPopulationSize();
    }

    private void elementsSortByDistance() {
        for (int i = 0; i < population.size() - 1; i++) {
            for (int j = i + 1; j < population.size(); j++) {
                if (population.get(i).getDistance() > population.get(j).getDistance()) {
                    population.swap(i, j);
                }
            }
        }
    }

    private void limitPopulationSize() {
        while (population.size() > initialSize) {
            population.setSize(population.getSize() - 1);
        }
    }

    private void doSpawn() {
        IntStream.range(0, 1000).forEach(e -> this.population.add(Chromosome.create(graph)));
    }

    private void doMutation() {
        final ArrayList<Chromosome> newPopulation = new ArrayList<>();
        for (int i = 0; i < this.population.size() / 10; i++) {
            final Chromosome mutation = this.population.get(Utils.randomIndex(this.population.size())).mutate();
            newPopulation.add(mutation);
        }
        this.population.addAll(newPopulation);
    }

    private void doCrossOver() {
        final ArrayList<Chromosome> newPopulation = new ArrayList<>();
        for (final Chromosome chromosome : this.population) {
            final Chromosome partner = getCrossOverPartner(chromosome);
            for (Chromosome child : chromosome.crossOver(partner)) {
                newPopulation.add(child);
            }
        }
        this.population.addAll(newPopulation);
    }


    private Chromosome getCrossOverPartner(final Chromosome chromosome) {
        Chromosome partner = this.population.get(Utils.randomIndex(this.population.size()));
        while (chromosome == partner) {
            partner = this.population.get(Utils.randomIndex(this.population.size()));
        }
        return partner;
    }
}
