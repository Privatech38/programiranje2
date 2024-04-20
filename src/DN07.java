import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class DN07 {

    public static void main(String[] args) {
        final Planet[] planets = preberiPlanete(args[0]);
        final Set<String> pickedPlanets = Arrays.stream(args[1].split("\\+")).map(String::toLowerCase).collect(Collectors.toSet());
        System.out.printf("Povrsina planetov \"%s\" je %d milijonov km2\n", args[1], (int) Math.round(Arrays.stream(planets)
                .filter(p -> pickedPlanets.contains(p.ime.toLowerCase()))
                .mapToDouble(Planet::povrsina).sum() / 1000000));
    }

    private static Planet[] preberiPlanete(String datoteka) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(datoteka))) {
            return bufferedReader.lines().map(l -> new Planet(l.split(":")[0], Integer.parseInt(l.split(":")[1]))).toArray(Planet[]::new);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class Planet {

        private final String ime;
        private final int radius;

        public Planet(String ime, int radius) {
            this.ime = ime;
            this.radius = radius;
        }

        public double povrsina() {
            return 4 * Math.PI * Math.pow(this.radius, 2);
        }

    }

}
