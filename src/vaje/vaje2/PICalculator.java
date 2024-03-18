package vaje.vaje2;

public class PICalculator {

    public static void main(String[] args) {
        System.out.println("""
                  k    Math.PI             PI (Nilakantha)     razlika\s
                -----------------------------------------------------------------""");
        for (int i = 1; i <= 22; i++) {
            double izracunan_pi = izracunajPiNilakantha(i);
            System.out.printf("%2d   %.15f   %.15f   %+.15f\n", i, Math.PI, izracunan_pi, Math.PI - izracunan_pi);
        }
        System.out.println("""
                  k     Math.PI             PI (rekurzivno)     PI (iterativno)      razlika\s
                -------------------------------------------------------------------------------------""");
        for (int i = 1; i <= 22; i++) {
            double pi = izracunajPi(i);
            System.out.printf("%2d\t%.15f\t%.15f\t%.15f\t%+.15f\n", i, Math.PI, 4.0d / izracunajPiRekurzivno(i, 1), pi, Math.PI - pi);
        }
    }

    private static double izracunajPiNilakantha(int k) {
        double priblizek = 3.0d;
        for (int i = 2; i <= k; i++) {
            int x = 2 * i;
            int z = (x - 2) * (x - 1) * x;
            double y = 4.0d / z;
            if (i % 2 == 0) {
                priblizek += y;
            } else {
                priblizek -= y;
            }
        }
        return priblizek;
    }

    private static double izracunajPi(int k) {
        double deljitelj = 0.0d;
        for (int i = k; i > 0; i--) {
            if (deljitelj == 0.0d) {
                deljitelj = (i * 2 - 1);
                continue;
            }
            deljitelj = (i * 2 - 1) + Math.pow(i, 2) / deljitelj;
        }
        return 4.0d / deljitelj;
    }

    private static double izracunajPiRekurzivno(int k, int current) {
        if (current >= k) {
            return (2 * current - 1);
        }
        return (2 * current - 1) + (Math.pow(current, 2) / izracunajPiRekurzivno(k, current + 1));
    }

}
