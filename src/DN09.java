import dn09.Tekmovanje;
import dn09.UtezeniKriterij;

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
            case "izpisiTocke" -> Tekmovanje.izDatotek(args[1], args[2]).izpisiTocke();
            case  "najboljse" -> Tekmovanje.izDatotek(args[1], args[2]).izpisiRezultateUrejeno(Integer.parseInt(args[3]));
            case "utezeno" -> {
                Tekmovanje tekmovanje = Tekmovanje.izDatotek(args[1], args[2]);
                tekmovanje.setKriterij(new UtezeniKriterij(Float.parseFloat(args[4]), Float.parseFloat(args[5])));
                tekmovanje.izpisiRezultateUrejeno(Integer.parseInt(args[3]));
            }
        }
    }




}
