import java.util.ArrayList;
import java.util.Arrays;

public class Kviz2 {

    public static void main(String[] args) {
        izpisKoledarja(2019, 3);
        izpisKoledarja(2020,10);
        izpisKoledarja(2000,1);
        izpisKoledarja(2023,3);
    }

    public static int[] range(int a, int b, int c) {
        int[] result = new int[(b - a) / c];
        for (int i = 0; i < result.length; i++) {
            result[i] = a + i * c;
        }
        return result;
    }

    public static void rotiraj(int[] tabela, int k) {
        int[] novaTabela = new int[tabela.length];
        for (int i = 0; i < tabela.length; i++) {
            int j = i - (k % tabela.length);
            novaTabela[(j < 0 ? tabela.length + j : j)] = tabela[i];
        }
        System.arraycopy(novaTabela, 0, tabela, 0, novaTabela.length);
    }

    public static String binarnoSestej(String s, String b) {
        return Integer.toBinaryString(Integer.parseInt(s, 2) + Integer.parseInt(b, 2));
    }

    public static int vsotaStevk(String str) {
        int vsota = 0;
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                vsota += Character.getNumericValue(c);
            }
        }
        return vsota;
    }

    public static int [] duplikati(int [] tabela) {
        return java.util.Arrays.stream(tabela).distinct().toArray();
    }

    public static void izpisKoledarja(int leto, int mesec) {
        java.time.YearMonth yearMonth = java.time.YearMonth.of(leto, mesec);
        int steviloDni = yearMonth.lengthOfMonth();
        int prviDan    = java.time.LocalDate.of(leto, mesec, 01).getDayOfWeek().getValue();
        System.out.println("PO  TO  SR  ČE  PE  SO  NE");
        java.util.ArrayList<String> line = new java.util.ArrayList<>();
        java.util.ArrayList<Integer> days = new java.util.ArrayList<>();
        for (int dan = 1; dan < steviloDni + prviDan; dan++) {
            if (dan < prviDan) {
                line.add("  ");
            } else {
                line.add("%2d");
                days.add(dan - prviDan + 1);
            }
            if (dan % 7 == 0 || dan == steviloDni + prviDan - 1) {
                System.out.printf(String.join("  ", line) + "\n", days.toArray());
                days.clear();
                line.clear();
            }
        }
    }

}
