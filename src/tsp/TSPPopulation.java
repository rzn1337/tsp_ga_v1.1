package tsp;

import java.util.stream.IntStream;

public class TSPPopulation {

    private tsp.ArrayList<TSPChromosome> population;
    private final int initialSize;
    private final Graph graph;

    TSPPopulation(final Graph graph, final int initialSize) {
        this.population = init(graph, initialSize);
        this.initialSize = initialSize;
        this.graph = graph;
    }

    tsp.ArrayList<TSPChromosome> getPopulation() {
        return this.population;
    }

    TSPChromosome getAlpha() {
        return this.population.get(0);
    }

    private tsp.ArrayList<TSPChromosome> init(final Graph graph, final int initialSize) {
        final tsp.ArrayList<TSPChromosome> eden = new tsp.ArrayList<>();
        for (int i = 0; i < initialSize; i++) {
            final TSPChromosome chromosome = TSPChromosome.create(graph);
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
        IntStream.range(0, 1000).forEach(e -> this.population.add(TSPChromosome.create(graph)));
    }

    private void doMutation() {
        final tsp.ArrayList<TSPChromosome> newPopulation = new tsp.ArrayList<>();
        for (int i = 0; i < this.population.size() / 10; i++) {
            final TSPChromosome mutation = this.population.get(TSPUtils.randomIndex(this.population.size())).mutate();
            newPopulation.add(mutation);
        }
        this.population.addAll(newPopulation);
    }

    private void doCrossOver() {
        final tsp.ArrayList<TSPChromosome> newPopulation = new tsp.ArrayList<>();
        for (final TSPChromosome chromosome : this.population) {
            final TSPChromosome partner = getCrossOverPartner(chromosome);
            for (TSPChromosome child : chromosome.crossOver(partner)) {
                newPopulation.add(child);
            }
        }
        this.population.addAll(newPopulation);
    }


    private TSPChromosome getCrossOverPartner(final TSPChromosome chromosome) {
        TSPChromosome partner = this.population.get(TSPUtils.randomIndex(this.population.size()));
        while (chromosome == partner) {
            partner = this.population.get(TSPUtils.randomIndex(this.population.size()));
        }
        return partner;
    }
}
