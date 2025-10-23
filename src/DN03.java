import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class DN03 {
    public static void main(String[] args) {
        int stevilo_besed;
        String[] besede;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]))) {
            stevilo_besed = Integer.parseInt(bufferedReader.readLine());
            besede = new String[stevilo_besed];
            for (int i = 0; i < stevilo_besed; i++) {
                besede[i] = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StringBuilder geslo = new StringBuilder();
        Random random = new Random(Integer.parseInt(args[2]));
        for (int i = 0; i < Integer.parseInt(args[1]); i++) {
            String beseda = besede[random.nextInt(stevilo_besed)];
            geslo.append(beseda.charAt(random.nextInt(beseda.length())));
        }
        System.out.println(geslo);
    }

}
