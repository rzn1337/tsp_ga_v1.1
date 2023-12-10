import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Utils {

    private final static Random R = new Random(10000);


    private Utils() {
        throw new RuntimeException("No!");
    }

    static final Graph generateRandomData(int numDataPoints) {
        final ArrayList<Gene> data = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numDataPoints; i++) {
            data.add(new Gene(random.nextInt(800), random.nextInt(600)));
        }
        return new Graph(data);
    }

    static Graph generateDataFromFile(String filename) {
        final ArrayList<Gene> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] coordinates = line.split(",");
                if (coordinates.length == 2) {
                    int x = Integer.parseInt(coordinates[0].trim());
                    int y = Integer.parseInt(coordinates[1].trim());
                    data.add(new Gene(x, y));
                } else {
                    System.err.println("Invalid data format in the file");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Graph(data);
    }

    static int randomIndex(final int limit) {
        return R.nextInt(limit);
    }
}

