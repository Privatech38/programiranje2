package dn09;

public class Glas {

    private final String odDrzave;
    private final String zaDrzavo;
    private final int stTock;

    public Glas(String odDrzave, String zaDrzavo, int stTock) {
        this.odDrzave = odDrzave;
        this.zaDrzavo = zaDrzavo;
        this.stTock = stTock;
    }

    public String getOdDrzave() {
        return odDrzave;
    }

    public String getZaDrzavo() {
        return zaDrzavo;
    }

    public int getStTock() {
        return stTock;
    }

    @Override
    public String toString() {
        return String.format("%s --%st-> %s", this.odDrzave, this.stTock, this.zaDrzavo);
    }
}
