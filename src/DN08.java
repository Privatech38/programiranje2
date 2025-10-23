import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DN08 {

    public static HashSet<Lik> liki = new HashSet<>();

    public static void main(String[] args) {
        for (String arg : args) {
            // Kvadrat
            Matcher matcher = Pattern.compile("kvadrat:(\\d+)").matcher(arg);
            if (matcher.matches()) {
                liki.add(new Kvadrat(Double.parseDouble(matcher.group(1))));
                continue;
            }
            // Pravokotnik
            matcher = Pattern.compile("pravokotnik:(\\d+):(\\d+)").matcher(arg);
            if (matcher.matches()) {
                liki.add(new Pravokotnik(Double.parseDouble(matcher.group(1)), Double.parseDouble(matcher.group(2))));
                continue;
            }
            // NKotnik
            matcher = Pattern.compile("nkotnik:(\\d+):(\\d+)").matcher(arg);
            if (matcher.matches()) {
                liki.add(new NKotnik(Double.parseDouble(matcher.group(2)), Integer.parseInt(matcher.group(1))));
            }
        }
        System.out.println((int) skupniObseg());
    }

    public static double skupniObseg() {
        return liki.stream().mapToDouble(Lik::obseg).sum();
    }

    private static abstract class Lik {
        // To bi lahko bil interface SMH
        abstract public double obseg(); // Zakaj je to sploh double, če nikoli ni doubla kot argument?????
    }

    private static class Pravokotnik extends Lik {

        public Pravokotnik(double a, double b) {
            this.a = a;
            this.b = b;
        }

        private double a;
        private double b;

        @Override
        public double obseg() {
            return 2*a + 2*b;
        }
    }

    private static class Kvadrat extends Pravokotnik {
        public Kvadrat(double a) {
            super(a, a);
        }
    }

    private static class NKotnik extends Lik { // Pravilni

        public NKotnik(double stranica, int steviloKotov) {
            this.stranica = stranica;
            this.steviloKotov = steviloKotov;
        }

        private double stranica;
        private int steviloKotov;

        @Override
        public double obseg() {
            return stranica * steviloKotov;
        }
    }

}
