import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DN05 {

    public static void main(String[] args) {
        if (args[0].equals("izpisi")) {
            izpisiSliko(preberiSliko(args[1]));
        }
    }

    public static int[][] preberiSliko(String ime) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(ime))) {
            // Check if empty
            if (Files.size(Path.of(ime)) == 0) {
                System.out.println("Napaka: Datoteka " + ime + " je prazna.");
                return new int[][]{};
            }
            // Check if correct format
            final String metaData = bufferedReader.readLine();
            Pattern pattern = Pattern.compile("P2: (?<x>\\S+) x (?<y>\\S+)");
            Matcher matcher = pattern.matcher(metaData);
            if (!matcher.matches()) {
                System.out.println("Napaka: datoteka " + ime + " ni v formatu P2.");
                return new int[][]{};
            }
            // Check if velikost slike v pravem formatu
            Pattern numberPattern = Pattern.compile("-?\\d+");
            if (!numberPattern.matcher(matcher.group("x")).matches() || !numberPattern.matcher(matcher.group("y")).matches()) {
                System.out.println("Napaka: datoteka " + ime + " ni v formatu P2 (velikost slike ni pravilna).");
                return new int[][]{};
            }
            // Check if 0 or negative
            int x;
            int y;
            try {
                x = Integer.parseInt(matcher.group("x"));
                y = Integer.parseInt(matcher.group("y"));
            } catch (NumberFormatException e) {
                System.out.println("Napaka: datoteka " + ime + " ni v formatu P2 (velikost slike ni pravilna).");
                return new int[][]{};
            }
            if (x <= 0 || y <= 0) {
                System.out.println("Napaka: datoteka " + ime + " ni v formatu P2 (velikost slike je 0 ali negativna).");
                return new int[][]{};
            }
            // Process bytes
            String pixelData = bufferedReader.readLine();
            if (pixelData == null) {
                System.out.println("Napaka: datoteka " + ime + " vsebuje premalo podatkov.");
                return new int[][]{};
            }
            String[] byteStrings = pixelData.split(" ");
            // Check if there are enough bytes
            if (byteStrings.length < x * y) {
                System.out.println("Napaka: datoteka " + ime + " vsebuje premalo podatkov.");
                return new int[][]{};
            }
            // Check if all bytes are valid
            int[][] pixels = new int[y][x];
            for (int i = 0; i < y; i++) {
                for (int j = 0; j < x; j++) {
                    int pixel = Integer.parseInt(byteStrings[i*x + j]);
                    if (pixel < 0 || pixel > 255) {
                        System.out.println("Napaka: datoteka " + ime + " vsebuje podatke izven obsega 0 do 255.");
                        return new int[][]{};
                    }
                    pixels[i][j] = pixel;
                }
            }
            return pixels;
        } catch (FileNotFoundException e) {
            System.out.println("Napaka: datoteka " + ime + " ne obstaja.");
            return new int[][]{};
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new int[0][];
    }

    private static void izpisiSliko(int[][] slika) {
        if (slika.length == 0) {
            return;
        }
        System.out.printf("velikost slike: %d x %d\n", slika[0].length, slika.length);
        for (int[] row : slika) {
            for (int pixelValue : row) {
                System.out.printf("%3d ", pixelValue);
            }
            System.out.println();
        }
    }

}
