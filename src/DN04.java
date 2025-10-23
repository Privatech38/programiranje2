public class DN04 {

    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args[0].length(); i += 8) {
            stringBuilder.append((char) Integer.parseInt(args[0].substring(i, i + 8), 2));
        }
        System.out.println(stringBuilder);
    }

}
