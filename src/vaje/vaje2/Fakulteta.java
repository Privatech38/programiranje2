package vaje.vaje2;

public class Fakulteta {

    public static void main(String[] args) {
        System.out.println("""
                  n              n!            Stirling(n)      napaka (%)
                ----------------------------------------------------------""");
        for (int i = 1; i <= 20; i++) {
            long fakulteta = fakultetaL(i);
            long stirling = stirlingL(i);
            System.out.printf("%2d %20d %20d %f\n", i, fakulteta, stirling, 100 * ((float) (fakulteta - stirling) / fakulteta));
        }
        // Najdi najvecjo fakulteto za n
        for (int i = 0; i < 100; i++) {
            long fakulteta = fakultetaL(i);
            if ((i + 1) > Long.MAX_VALUE / fakulteta) {
                System.out.println("Največji n je: " + i);
                break;
            }
        }
        // Double vrednosti
        System.out.println("""
                  n         n!            Stirling(n)     napaka (%)
                ----------------------------------------------------""");
        for (int i = 1; i <= 100; i++) {
            double fakulteta = fakultetaD(i);
            double stirling = stirlingD(i);
            System.out.printf("%3d %20E %20E %f\n", i, fakulteta, stirling, 100 * ((fakulteta - stirling) / fakulteta));
        }

    }

    public static long fakultetaL(int n) {
        long rezultat = 1;
        for (int i = 1; i <= n; i++) {
            rezultat *= i;
        }
        return rezultat;
    }

    public static long stirlingL(int n) {
        return Math.round(Math.sqrt(2 * Math.PI * n) * Math.pow((n / Math.E), n));
    }

    public static double fakultetaD(int n) {
        double rezultat = 1.0;
        for (int i = 1; i <= n; i++) {
            rezultat *= i;
        }
        return rezultat;
    }

    public static double stirlingD(int n) {
        return Math.sqrt(2 * Math.PI * n) * Math.pow((n / Math.E), n);
    }

}
