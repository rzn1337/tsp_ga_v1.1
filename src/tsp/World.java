package tsp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicInteger;

public class World extends JPanel {

    private final TSPPopulation population;
    private final AtomicInteger generation;

    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    private World() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.PINK);


        String[] options = {"Use File", "Generate Random Points"};
        int choice = JOptionPane.showOptionDialog(null, "Choose how to create data points:", "Input Option",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        Graph citiesGraph;

        if (choice == 0) {
            String filePath = "data.txt";
            citiesGraph = TSPUtils.generateDataFromFile(filePath);
        } else {
            String numCitiesStr = JOptionPane.showInputDialog("Enter the number of cities:");
            int numCities = Integer.parseInt(numCitiesStr);
            citiesGraph = TSPUtils.generateRandomData(numCities);
        }
        tsp.ArrayList<TSPGene> nodes = citiesGraph.getNodes();
        for (TSPGene node1 : nodes) {
            for (TSPGene node2 : nodes) {
                if (!node1.equals(node2)) {
                    double distance = node1.distance(node2);
                    citiesGraph.addEdge(node1, node2, distance);
                }
            }
        }

        this.population = new TSPPopulation(citiesGraph, 1000);
        this.generation = new AtomicInteger(0);

        final Timer timer = new Timer(5, (ActionEvent e) -> {
            this.population.update();
            repaint();
        });
        timer.start();
    }


    @Override
    public void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
        final Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.BLACK);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawString("gen : " + this.generation.incrementAndGet(), 350, 15);
        g.drawString("pop size : " + this.population.getPopulation().size(), 150, 15);
        g.drawString("shortest path : " + String.format("%.2f", this.population.getAlpha().getDistance()), 500, 15);

        // Draw evolving population
        drawPopulation(g);

        // Draw the best chromosome
        drawBestChromosome(g);
    }


    private void drawPopulation(final Graphics2D g) {
        final tsp.ArrayList<TSPChromosome> population = this.population.getPopulation();
        for (TSPChromosome chromosome : population) {
            drawChromosome(g, chromosome);
        }
    }
    private void drawChromosome(final Graphics2D g, final TSPChromosome chromosome) {
        final tsp.ArrayList<TSPGene> genes = chromosome.getChromosome();
        g.setColor(Color.LIGHT_GRAY);

        for (int i = 0; i < genes.size() - 1; i++) {
            TSPGene gene = genes.get(i);
            TSPGene neighbor = genes.get(i + 1);
            g.drawLine(gene.getX(), gene.getY(), neighbor.getX(), neighbor.getY());
        }
    }
    private void drawBestChromosome(final Graphics2D g) {
        final tsp.ArrayList<TSPGene> chromosome = this.population.getAlpha().getChromosome();
        g.setColor(Color.WHITE);
        for (int i = 0; i < chromosome.size() - 1; i++) {
            TSPGene gene = chromosome.get(i);
            TSPGene neighbor = chromosome.get(i + 1);
            g.drawLine(gene.getX(), gene.getY(), neighbor.getX(), neighbor.getY());
        }
        g.setColor(Color.RED);
        for (final TSPGene gene : chromosome) {
            g.fillOval(gene.getX(), gene.getY(), 5, 5);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setTitle("Genetic Algorithm");
            frame.setResizable(false);
            frame.add(new World(), BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
