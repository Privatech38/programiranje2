public class DN06 {

    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setTitle("Sudoku");
        StdDraw.setPenRadius(0.001);
        char[] chars = args[0].toCharArray();
        for (int i = 0; i < chars.length; i++) {
            double x = 0.1 * ((i % 9) + 1);
            double y = 0.9 - (0.1 * (i / 9));
            StdDraw.square(x, y, 0.05);
            if (Character.getNumericValue(chars[i]) > 0) {
                StdDraw.text(x, y, String.valueOf(chars[i]));
            }
        }
        // Bold bounding boxes
        StdDraw.setPenRadius(0.005);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                StdDraw.square(0.2 + i * 0.3, 0.2 + j * 0.3, 0.15);
            }
        }
        StdDraw.show();
    }

}
