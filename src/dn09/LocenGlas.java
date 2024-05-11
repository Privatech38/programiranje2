package dn09;

public class LocenGlas extends Glas {

    private final int stTockGlasovi;
    private final int stTockZirija;

    public LocenGlas(String odDrzave, String zaDrzavo, int stTock, int stTockGlasovi, int stTockZirije) {
        super(odDrzave, zaDrzavo, stTock);
        this.stTockGlasovi = stTockGlasovi;
        this.stTockZirija = stTockZirije;
    }

    public int getStTockGlasovi() {
        return stTockGlasovi;
    }

    public int getStTockZirija() {
        return stTockZirija;
    }

}
