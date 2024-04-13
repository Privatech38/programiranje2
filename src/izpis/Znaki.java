package izpis;

public class Znaki {

    private static final char crnaPika = '\u2B1B'; // črn kvadratek
    private static final char belaPika = '\u2B1C'; // prazen (bel) kvadratek

    private static final char[] abeceda = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' '};

    private static final char[] slovenska_abeceda = {
            'A', 'B', 'C', 'Č', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'Š', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Ž',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '!', ',', '.', ':', ' '};

    private static final short[] kodeZnakov16bit = {
            (short) 0b1111100111111001, // A
            (short) 0b1100101011011010, // B
            (short) 0b1111100010001111, // C
            (short) 0b1110100110011110, // D
            (short) 0b1111111010001111, // E
            (short) 0b1111100011101000, // F
            (short) 0b1111100010111111, // G
            (short) 0b1001100111111001, // H
            (short) 0b1111010001001111, // I
            (short) 0b1111000110011111, // J
            (short) 0b1011110010101001, // K
            (short) 0b1000100010001111, // L
            (short) 0b1111101110011001, // M
            (short) 0b1101101110011001, // N
            (short) 0b1111100110011111, // O
            (short) 0b1111100111111000, // P
            (short) 0b1111100110111111, // Q
            (short) 0b1111100111111010, // R
            (short) 0b1111100011110111, // S
            (short) 0b1111010001000100, // T
            (short) 0b1001100110011111, // U
            (short) 0b1001100110010110, // V
            (short) 0b1001100110111111, // W
            (short) 0b1001011001101001, // X
            (short) 0b1001100111110100, // Y
            (short) 0b1111001001001111, // Z
            (short) 0b0110100110010110, // 0
            (short) 0b0110001000101111, // 1
            (short) 0b1110001001001111, // 2
            (short) 0b1111011100011111, // 3
            (short) 0b1000100111110001, // 4
            (short) 0b1111100011110111, // 5
            (short) 0b1000111110011111, // 6
            (short) 0b1111000100010001, // 7
            (short) 0b1110101111010111, // 8
            (short) 0b1111100111110001, // 9
            0                           // presledek
    };

    // Code

    public static void main(String[] args) {
        izpisi16bit((short) 0b1111100111111001);
        izpisi16bit((short) 0b1111100111111000);
        izpisi16bit((short) 0b1110001001001111);
        izpisi16bit(new short[] {(short)0b1111100011101000, (short)0b1111100111111010, (short)0b1111010001001111});
        izpisi16bit(kodeZnakov16bit);
        izpisi16bit("Programiranje 2 je zakon");
        // 64 bit
        izpisi64bit(0b0001100000100100010000100100001001111110010000100100001011100111L);
        izpisi64bit(kodeZnakov64bit[15]);
        izpisi64bit(kodeZnakov64bit[28]);
        izpisi64bit(new long[] {0b1111111101000001010000010100100001111000010010000100000011100000L, 0b0011000001010000000100000001000000010000000100000001000011111111L});
        izpisi64bit("P2 je super");
        // 64 bit liki
        izpisi64bit(4342219536296657468L);
        izpisi64bit(1746410238858002085L);
        // 64 bit slovenska abeceda
        izpisi64bit("Če programiram, še bolj uživam!");
    }

    // 32 - bit

    private static void izpisi16bit(short kodaZnaka) {
        for (int i = 12; i >= 0; i -= 4) {
            for (int j = 3; j >= 0; j--) {
                int maska = 1 << i + j;
                System.out.print(((kodaZnaka & maska) > 0 ? crnaPika : belaPika));
            }
            System.out.println();
        }
    }

    private static void izpisi16bit(short[] nizZnakov) {
        for (int i = 12; i >= 0; i -= 4) {
            for (short kodaZnaka : nizZnakov) {
                for (int j = 3; j >= 0; j--) {
                    int maska = 1 << i + j;
                    System.out.print(((kodaZnaka & maska) > 0 ? crnaPika : belaPika));
                }
                System.out.print(belaPika);
            }
            System.out.println();
        }
    }

    private static void izpisi16bit(String niz) {
        niz = niz.toUpperCase();
        short[] nizZnakov = new short[niz.length()];
        for (int i = 0; i < niz.length(); i++) {
            int index = 35;
            for (int j = 0; j < abeceda.length; j++) {
                if (abeceda[j] == niz.charAt(i)) {
                    index = j;
                    break;
                }
            }
            nizZnakov[i] = kodeZnakov16bit[index];
        }
        izpisi16bit(nizZnakov);
    }

    // 64 - bit
    private static final long[] kodeZnakov64bit = {
            0b0001100000100100010000100100001001111110010000100100001011100111L, // A
            0b1111110001000010010001000111111001000001010000010100000111111110L, // B
            0b0011110001000010100000011000000010000000100000010100001000111100L, // C
            0b0010010000011000001111000100001010000000100000000100001000111100L, // Č
            0b1111110001000010010000010100000101000001010000010100001011111100L, // D
            0b1111111101000001010000000111110001000000010000000100000111111111L, // E
            0b1111111101000001010000010100100001111000010010000100000011100000L, // F
            0b0011111101000001100000001001111110010001100000010100000100111110L, // G
            0b1110011101000010010000100111111001000010010000100100001011100111L, // H
            0b1111111100010000000100000001000000010000000100000001000011111111L, // I
            0b0011111100000100000001000000010011000100100001001000010001111000L, // J
            0b1110111101000100010010000111000001001000010001000100001011100111L, // K
            0b1110000001000000010000000100000001000000010000010100000111111111L, // L
            0b1100011101101010010100100101001001000010010000100100001011100111L, // M
            0b1100011101100010010100100100101001000110010000100100001011100111L, // N
            0b0011110001000010100000011000000110000001100000010100001000111100L, // O
            0b1111111001000001010000010100000101111110010000000100000011100000L, // P
            0b0111111010000001100000011000000110000001100010010111111000001000L, // Q
            0b1111111001000001010000010100000101111110010001000100001011100111L, // R
            0b0111110110000011100000010111110000000010100000011100000110111110L, // S
            0b0010010000011000011111101000000101110000000011101000000101111110L, // Š
            0b1111111110001001000010000000100000001000000010000000100000011100L, // T
            0b1110011110000001100000011000000110000001100000011000000101111110L, // U
            0b1110011101000010010000100100001000100010001001000001010000001000L, // V
            0b1110011101000010010000100100001001010010010100100101001000101100L, // W
            0b1110011101000010001001000001100000100100001001000100001011100111L, // X
            0b1110011101000010001001000001010000001000000010000000100000011100L, // Y
            0b1111111110000010100001000000100000010000001000010100000111111111L, // Z
            0b0010010000011000011111100000010000001000000100000010000001111110L, // Ž
            0b0011110001000010100001011000100110010001101000010100001000111100L, // 0
            0b0011000001010000000100000001000000010000000100000001000011111111L, // 1
            0b0111111010000001000000010000011000011000011000011000000111111111L, // 2
            0b0111111010000001100000010000111000000001100000011000000101111110L, // 3
            0b0000011000001010000100100010001001000010111111110000001000000111L, // 4
            0b1111111110000001100000001111111000000001100000011000000101111110L, // 5
            0b0111111010000001100000001111111010000001100000011000000101111110L, // 6
            0b1111111110000001000000100000010000001000000100000001000000111000L, // 7
            0b0111111010000001100000010111111010000001100000011000000101111110L, // 8
            0b0111111010000001100000011000000101111111000000011000000101111110L, // 9
            0b0110000001100000011000000110000001100000000000000110000001100000L, // !
            0b0000000000000000000000000000000000000000001100000111000001100000L, // ,
            0b0000000000000000000000000000000000000000000000000110000001100000L, // .
            0b0000000001100000011000000000000000000000011000000110000000000000L, // :
            0
    };

    private static void izpisi64bit(long kodaZnaka) {
        for (int i = 56; i >= 0; i -= 8) {
            for (int j = 7; j >= 0; j--) {
                long maska = 1L << (i + j);
                System.out.print(((kodaZnaka & maska) != 0 ? crnaPika : belaPika));
            }
            System.out.println();
        }
    }

    private static void izpisi64bit(long[] nizZnakov) {
        for (int i = 56; i >= 0; i -= 8) {
            for (long kodaZnaka : nizZnakov) {
                for (int j = 7; j >= 0; j--) {
                    long maska = 1L << (i + j);
                    System.out.print(((kodaZnaka & maska) != 0 ? crnaPika : belaPika));
                }
                System.out.print(belaPika);
            }
            System.out.println();
        }
    }

    public static void izpisi64bit(String niz) {
        niz = niz.toUpperCase();
        long[] nizZnakov = new long[niz.length()];
        for (int i = 0; i < niz.length(); i++) {
            int index = 41;
            for (int j = 0; j < slovenska_abeceda.length; j++) {
                if (slovenska_abeceda[j] == niz.charAt(i)) {
                    index = j;
                    break;
                }
            }
            nizZnakov[i] = kodeZnakov64bit[index];
        }
        izpisi64bit(nizZnakov);
    }



}