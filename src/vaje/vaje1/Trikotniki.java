package vaje.vaje1;

public class Trikotniki {

    public static void main(String[] args) {
        trikotnikStevil(3);
        trikotnikStevil(10);
        trikotnikStevilObrnjen(3);
        trikotnikStevilObrnjen(10);
        trikotnikStevilEnakokraki(5);
        trikotnik(5, 3);
        trikotnik(1, 5);
        trikotnikObrnjen(1, 5);
        romb(2, 5);
        smreka(3);
        smreka(4);
        rombA(1, 5);
        rombPrazen(3, 5);
    }

    public static void trikotnikStevil(int visina) {
        for (int i = 1; i <= visina; i++) {
            System.out.println();
            for (int j = 1; j <= i; j++) {
                if (visina >= 10) {
                    System.out.print(j % 10);
                    continue;
                }
                System.out.print(i);
            }
        }
    }

    public static void trikotnikStevilObrnjen(int visina) {
        for (int i = visina; i > 0; i--) {
            System.out.println();
            for (int j = 1; j <= i; j++) {
                if (visina >= 10) {
                    System.out.print(j % 10);
                    continue;
                }
                System.out.print(i);
            }
        }
    }

    public static void trikotnikStevilEnakokraki(int visina) {
        for (int i = 1; i <= visina; i++) {
            System.out.println();
            for (int j = 0; j < visina - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print(j % 10);
            }
        }
    }

    public static void trikotnik(int odmik, int visina) {
        for (int i = 1; i <= visina; i++) {
            System.out.println();
            for (int j = 0; j < odmik; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < visina - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print("*");
            }
        }
    }

    public static void trikotnikObrnjen(int odmik, int visina) {
        for (int i = visina; i > 0; i--) {
            System.out.println();
            for (int j = 0; j < odmik; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < visina - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print("*");
            }
        }
    }

    public static void romb(int odmik, int velikost) {
        trikotnik(odmik, velikost);
        trikotnikObrnjen(odmik + 1, velikost - 1);
    }

    public static void smreka(int velikost) {
        for (int i = 1; i <= velikost; i++) {
            trikotnik((velikost - i) * 2, 2 * i);
        }
        Pravokotniki.pravokotnik(velikost + 1, (velikost % 2 == 0 ? velikost + 1 : velikost), 2 * velikost);
    }

    public static void rombA(int odmik, int velikost) {
        for (int i = 1; i <= velikost; i++) {
            System.out.println();
            for (int j = 0; j < odmik; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < (velikost - i) * 2; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print("# ");
            }
        }
        for (int i = velikost - 1; i > 0; i--) {
            System.out.println();
            for (int j = 0; j < odmik + 2; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < (velikost - i - 1) * 2; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print("# ");
            }
        }
    }

    public static void rombPrazen(int odmik, int velikost) {
        System.out.println();
        for (int i = 0; i < odmik; i++) {
            System.out.print(" ");
        }
        for (int i = 0; i < velikost * 2 - 1; i++) {
            System.out.print("# ");
        }
        System.out.println();
        for (int i = 0; i < velikost - 1; i++) {
            for (int j = 0; j < odmik; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < velikost - 1 - i; j++) {
                System.out.print("# ");
            }
            for (int j = 0; j < 1 + 2 * i; j++) {
                System.out.print("  ");
            }
            for (int j = 0; j < velikost - 1 - i; j++) {
                System.out.print("# ");
            }
            System.out.println();
        }
        for (int i = velikost - 3; i >= 0; i--) {
            for (int j = 0; j < odmik; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < velikost - 1 - i; j++) {
                System.out.print("# ");
            }
            for (int j = 0; j < 1 + 2 * i; j++) {
                System.out.print("  ");
            }
            for (int j = 0; j < velikost - 1 - i; j++) {
                System.out.print("# ");
            }
            System.out.println();
        }
        for (int i = 0; i < odmik; i++) {
            System.out.print(" ");
        }
        for (int i = 0; i < velikost * 2 - 1; i++) {
            System.out.print("# ");
        }

    }

}
