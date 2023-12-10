package tsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TSPUtils {

    private final static Random R = new Random(10000);


    private TSPUtils() {
        throw new RuntimeException("No!");
    }

    static final Graph generateRandomData(int numDataPoints) {
        final tsp.ArrayList<TSPGene> data = new tsp.ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numDataPoints; i++) {
            data.add(new TSPGene(random.nextInt(800), random.nextInt(600)));
        }

        return new Graph(data);
    }

    static Graph generateDataFromFile(String filename) {
        final tsp.ArrayList<TSPGene> data = new tsp.ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] coordinates = line.split(",");
                if (coordinates.length == 2) {
                    int x = Integer.parseInt(coordinates[0].trim());
                    int y = Integer.parseInt(coordinates[1].trim());
                    data.add(new TSPGene(x, y));
                } else {
                    // Handle invalid data format in the file
                    System.err.println("Invalid data format in the file");
                }
            }
        } catch (IOException e) {
            // Handle file IO exception
            e.printStackTrace();
        }
        return new Graph(data);
    }

    static int randomIndex(final int limit) {
        return R.nextInt(limit);
    }

    static<T> List<T>[] split(final List<T> list) {
        final List<T> first = new ArrayList<>();
        final List<T> second = new ArrayList<>();
        final int size = list.size();
        final int partitionIndex = 1 + TSPUtils.randomIndex(list.size());
        IntStream.range(0, size).forEach(i -> {
            if (i < (size + 1) / partitionIndex) {
                first.add(list.get(i));
            } else {
                second.add(list.get(i));
            }
        });
        return (List<T>[]) new List[]{first, second};
    }
}

