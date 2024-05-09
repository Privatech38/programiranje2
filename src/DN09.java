import dn09.Tekmovanje;

public class DN09 {

    public static void main(String[] args) {
        // Split args
        final String ukaz = args[0];
        // Process commands
        switch (ukaz) {
            case "izpisiTekmovanje" -> {
                Tekmovanje tekmovanje = Tekmovanje.izDatotek(args[1], args[2]);
                tekmovanje.izpisiTekmovalce();
                System.out.println();
                tekmovanje.izpisiGlasove();
            }
        }
    }




}
