import java.util.ArrayList;

public class DN02 {
    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }
        int velikost = (int) Math.ceil(Math.sqrt(args[0].length()));
        // Create boxes
        final int amount = (velikost * velikost) - args[0].length();
        for (int i = 0; i < amount; i++) {
            args[0] = args[0].concat(".");
        }
        for (int i = 0; i < velikost * velikost; i += velikost) {
            ArrayList<String> line = new ArrayList<>();
            for (int j = 0; j < velikost; j++) {
                line.add(String.valueOf(args[0].charAt(i + j)));
            }
            System.out.println(String.join("  ", line));
        }
    }

}
