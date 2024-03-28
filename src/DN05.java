import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DN05 {

    public static void main(String[] args) {
        switch (args[0]) {
            case ("izpisi") -> izpisiSliko(preberiSliko(args[1]));
            case ("histogram") -> izpisiHistogram(preberiSliko(args[1]));
            case ("svetlost") -> System.out.printf("Srednja vrednost sivine na sliki %s je: %.2f\n", args[1], svetlostSlike(preberiSliko(args[1])));
            // 2. Naloga
            case ("zmanjsaj") -> izpisiSliko(zmanjsajSliko(preberiSliko(args[1])));
            case ("rotiraj") -> izpisiSliko(rotirajSliko(preberiSliko(args[1])));
            case ("zrcali") -> izpisiSliko(zrcaliSliko(preberiSliko(args[1])));
            case ("vrstica") -> System.out.printf("Max razlika svetlo - temno je v %d. vrstici.\n", poisciMaxVrstico(preberiSliko(args[1])) + 1);
            // 3. Naloga
            case ("barvna") -> izpisiBarvnoSliko(preberiBarvnoSliko(args[1]));
            case ("sivinska") -> izpisiSliko(pretvoriVSivinsko(preberiBarvnoSliko(args[1])));
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

    private static void izpisiHistogram(int[][] slika) {
        TreeMap<Integer, Integer> frequency = new TreeMap<>();
        Arrays.stream(slika).forEach(ia -> Arrays.stream(ia).forEach(i -> frequency.merge(i, 1, (v1, v2) -> v1 + 1)));
        // Print
        System.out.println("sivina : # pojavitev");
        frequency.forEach((k, v) -> System.out.printf("  %3d  :   %-3d\n", k, v));
    }

    private static double svetlostSlike(int[][] slika) {
        double sum = 0;
        for (int[] row : slika) {
            for (int pixel : row) {
                sum += pixel;
            }
        }
        return sum / (slika.length * slika[0].length);
    }

    // 2. Naloga

    private static int[][] zmanjsajSliko(int[][] slika) {
        if (slika[0].length < 3 || slika.length < 3) {
            return slika;
        }
        final int x = (int) (slika[0].length / 2.0d);
        final int y = (int) (slika.length / 2.0d);
        int[][] pomanjsanaSlika = new int[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                pomanjsanaSlika[i][j] = (slika[i*2][j*2] + slika[i*2 + 1][j*2] + slika[i*2][j*2 + 1] + slika[i*2 + 1][j*2 + 1]) / 4;
            }
        }
        return pomanjsanaSlika;
    }

    private static int[][] rotirajSliko(int[][] slika) {
        int[][] obrnjenaSlika = new int[slika[0].length][slika.length];
        for (int i = 0; i < slika.length; i++) {
            for (int j = 0; j < slika[0].length; j++) {
                obrnjenaSlika[j][slika.length - i - 1] = slika[i][j];
            }
        }
        return obrnjenaSlika;
    }

    private static int[][] zrcaliSliko(int[][] slika) {
        int[][] zrcaljenaSlika = new int[slika.length][slika[0].length];
        for (int i = 0; i < slika.length; i++) {
            for (int j = 0; j < slika[0].length; j++) {
                zrcaljenaSlika[i][slika[0].length - 1 - j] = slika[i][j];
            }
        }
        return zrcaljenaSlika;
    }

    private static int poisciMaxVrstico(int[][] slika) {
        int index = 0, diff = 0;
        for (int i = 0; i < slika.length; i++) {
            int min = 255;
            int max = 0;
            for (int pixel : slika[i]) {
                if (pixel > max) {
                    max = pixel;
                }
                if (pixel < min) {
                    min = pixel;
                }
            }
            if (diff < max - min) {
                index = i;
                diff = max - min;
            }
        }
        return index;
    }

    // 3. Naloga

    private static int[][][] preberiBarvnoSliko(String ime) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(ime))) {
            // Check if empty
            if (Files.size(Path.of(ime)) == 0) {
                System.out.println("Napaka: Datoteka " + ime + " je prazna.");
                return new int[0][][];
            }
            // Check if correct format
            final String metaData = bufferedReader.readLine();
            Pattern pattern = Pattern.compile("P2B: (?<x>\\S+) x (?<y>\\S+)");
            Matcher matcher = pattern.matcher(metaData);
            if (!matcher.matches()) {
                System.out.println("Napaka: datoteka " + ime + " ni v formatu P2B.");
                return new int[0][][];
            }
            // Check if velikost slike v pravem formatu
            Pattern numberPattern = Pattern.compile("-?\\d+");
            if (!numberPattern.matcher(matcher.group("x")).matches() || !numberPattern.matcher(matcher.group("y")).matches()) {
                System.out.println("Napaka: datoteka " + ime + " ni v formatu P2B (velikost slike ni pravilna).");
                return new int[0][][];
            }
            // Check if 0 or negative
            int x;
            int y;
            try {
                x = Integer.parseInt(matcher.group("x"));
                y = Integer.parseInt(matcher.group("y"));
            } catch (NumberFormatException e) {
                System.out.println("Napaka: datoteka " + ime + " ni v formatu P2B (velikost slike ni pravilna).");
                return new int[0][][];
            }
            if (x <= 0 || y <= 0) {
                System.out.println("Napaka: datoteka " + ime + " ni v formatu P2B (velikost slike je 0 ali negativna).");
                return new int[0][][];
            }
            // Process bytes
            String pixelData = bufferedReader.readLine();
            if (pixelData == null) {
                System.out.println("Napaka: datoteka " + ime + " vsebuje premalo podatkov.");
                return new int[0][][];
            }
            String[] byteStrings = pixelData.split(" ");
            // Check if there are enough bytes
            if (byteStrings.length < x * y) {
                System.out.println("Napaka: datoteka " + ime + " vsebuje premalo podatkov.");
                return new int[0][][];
            }
            // Check if all bytes are valid
            int[][][] pixels = new int[y][x][3];
            for (int i = 0; i < y; i++) {
                for (int j = 0; j < x; j++) {
                    int pixel = Integer.parseInt(byteStrings[i*x + j]);
                    for (int k = 0; k < 3; k++) {
                        int maska = 1023 << 10 * k;
                        final int colorBits = (pixel & maska) >> 10 * k;
                        if (colorBits < 0 || colorBits > 1023) {
                            System.out.println("Napaka: datoteka " + ime + " vsebuje podatke izven obsega 0 do 67108863.");
                            return new int[0][][];
                        }
                        pixels[i][j][k] = colorBits;
                    }
                }
            }
            return pixels;
        } catch (FileNotFoundException e) {
            System.out.println("Napaka: datoteka " + ime + " ne obstaja.");
            return new int[0][][];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new int[0][][];
    }

    private static void izpisiBarvnoSliko(int[][][] slika) {
        if (slika.length == 0) {
            return;
        }
        System.out.printf("velikost slike: %d x %d\n", slika[0].length, slika.length);
        for (int[][] row : slika) {
            for (int[] pixel : row) {
                System.out.printf("(%4d,%4d,%4d) ", pixel[2], pixel[1], pixel[0]);
            }
            System.out.println();
        }
    }

    private static int[][] pretvoriVSivinsko(int[][][] slika) {
        int[][] sivinskaSlika = new int[slika.length][slika[0].length];
        final float ratio = 255.0f / 1023.0f;
        for (int i = 0; i < slika.length; i++) {
            for (int j = 0; j < slika[0].length; j++) {
                sivinskaSlika[i][j] = (int) (ratio * ((int) ((slika[i][j][0] + slika[i][j][1] + slika[i][j][2]) / 3)));
            }
        }
        return sivinskaSlika;
    }



}
