package dn09;

public class OsnovniKriterij implements Kriterij {
    @Override
    public int steviloTock(Tekmovanje tekmovanje, String drzava) {
        return tekmovanje.getSeznamGlasov().stream().filter(g -> g.getZaDrzavo().equalsIgnoreCase(drzava)).mapToInt(Glas::getStTock).sum();
    }
}
