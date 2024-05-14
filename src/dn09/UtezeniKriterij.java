package dn09;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UtezeniKriterij implements Kriterij {

    private final float utezGlasovanja;
    private final float utezZirije;

    public UtezeniKriterij(float utezGlasovanja, float utezZirije) {
        this.utezGlasovanja = utezGlasovanja;
        this.utezZirije = utezZirije;
    }
    @Override
    public int steviloTock(Tekmovanje tekmovanje, String drzava) {
        Set<Glas> glasovi = tekmovanje.getSeznamGlasov().stream().filter(g -> g.getZaDrzavo().equalsIgnoreCase(drzava)).collect(Collectors.toSet());
        if (glasovi.stream().anyMatch(g -> g instanceof LocenGlas)) {
            return (int) Math.round(glasovi.stream()
                    .filter(g -> g instanceof LocenGlas).map(LocenGlas.class::cast)
                    .mapToDouble(lg -> lg.getStTockGlasovi() * utezGlasovanja + lg.getStTockZirija() * utezZirije).sum());
        }
        return (int) Math.round(glasovi.stream().mapToDouble(g -> g.getStTock() * utezZirije).sum());
    }
}
