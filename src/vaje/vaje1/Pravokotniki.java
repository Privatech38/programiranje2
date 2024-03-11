package vaje.vaje1;

public class Pravokotniki {

    public static void main(String[] args) {
        pravokotnikStevil(7, 3);
        pravokotnikStevil(12, 3);
        pravokotnik(5,7,3);
    }

    public static void pravokotnikStevil(int sirina, int visina) {
        for (int i = 1; i <= visina; i++) {
            System.out.print("\n");
            for (int j = 1; j <= sirina; j++) {
                int current = i;
                if (sirina >= 10) {
                    current = j % 10;
                }
                System.out.print(current);
            }
        }
    }

    public static void pravokotnik(int odmik, int sirina, int visina) {
        for (int i = 0; i < visina; i++) {
            System.out.print("\n");
            for (int j = 0; j < odmik; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < sirina; j++) {
                System.out.print("X");
            }
        }
    }

}
