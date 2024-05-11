package dn09;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Tekmovanje {

    private final ArrayList<Tekmovalec> seznamTekmovalcev;
    private final ArrayList<Glas> seznamGlasov;
    private Kriterij kriterij;
    private boolean urejeno;

    public Tekmovanje(ArrayList<Tekmovalec> seznamTekmovalcev, ArrayList<Glas> seznamGlasov) {
        this.seznamTekmovalcev = seznamTekmovalcev;
        this.seznamGlasov = seznamGlasov;
        this.kriterij = new OsnovniKriterij();
        this.urejeno = false;
    }

    public ArrayList<Tekmovalec> getSeznamTekmovalcev() {
        return seznamTekmovalcev;
    }

    public ArrayList<Glas> getSeznamGlasov() {
        return seznamGlasov;
    }

    public Kriterij getKriterij() {
        return kriterij;
    }

    public void setKriterij(Kriterij kriterij) {
        this.kriterij = kriterij;
        this.urejeno = false;
    }

    public void izpisiTekmovalce() {
        if (this.seznamTekmovalcev.isEmpty()) {
            System.out.println("Seznam tekmovalcev je prazen.");
            return;
        }
        System.out.println("Seznam tekmovalcev:");
        this.seznamTekmovalcev.forEach(tekmovalec -> System.out.println(tekmovalec.toString()));
    }

    public void izpisiGlasove() {
        if (this.seznamGlasov.isEmpty()) {
            System.out.println("Seznam glasov je prazen.");
            return;
        }
        System.out.println("Seznam glasov:");
        this.seznamGlasov.forEach(glas -> System.out.println(glas.toString()));
    }

    public int steviloTock(String drzava) {
        return this.kriterij.steviloTock(this, drzava);
    }

    public void izpisiTocke() {
        if (this.seznamTekmovalcev.isEmpty()) {
            System.out.println("Seznam tekmovalcev je prazen.");
            return;
        }
        System.out.println("Seznam tekmovalcev in njihovih tock:");
        this.seznamTekmovalcev.forEach(t -> System.out.println(t.toString() + ": " + this.steviloTock(t.getDrzava()) + "t"));
    }

    public Tekmovalec getZmagovalec() {
        this.urediPoTockah();
        return seznamTekmovalcev.stream().max((t1, t2) -> Integer.compare(this.steviloTock(t1.getDrzava()), this.steviloTock(t2.getDrzava()))).get();
    }

    public void urediPoTockah() {
        seznamTekmovalcev.sort((t1, t2) -> Integer.compare(this.steviloTock(t2.getDrzava()), this.steviloTock(t1.getDrzava())));
        this.urejeno = true;
    }

    public int getMesto(String drzava) {
        this.urediPoTockah();
        for (Tekmovalec tekmovalec : this.seznamTekmovalcev) {
            if (tekmovalec.getDrzava().equalsIgnoreCase(drzava)) {
                return this.seznamTekmovalcev.indexOf(tekmovalec) + 1;
            }
        }
        return -1;
    }

    public void izpisiRezultateUrejeno(int topK) {
        this.urediPoTockah();
        System.out.println("Najboljse uvrsceni tekmovalci:");
        for (Tekmovalec tekmovalec : (topK > this.seznamTekmovalcev.size() ? this.seznamTekmovalcev : this.seznamTekmovalcev.subList(0, topK))) {
            System.out.printf("%d. %s: %dt\n", this.seznamTekmovalcev.indexOf(tekmovalec) + 1, tekmovalec.toString(), this.steviloTock(tekmovalec.getDrzava()));
        }
    }

    public static Tekmovanje izDatotek(String datotekaTekmovalci, String datotekaGlasovi) {
        try (BufferedReader readerTekmovalci = new BufferedReader(new FileReader(datotekaTekmovalci));
            BufferedReader readerGlasovi = new BufferedReader(new FileReader(datotekaGlasovi))) {
            return new Tekmovanje(readerTekmovalci.lines().skip(1).filter(l -> !l.isBlank()).map(l -> new Tekmovalec(l.split(";")[1], l.split(";")[2], l.split(";")[3])).collect(Collectors.toCollection(ArrayList::new)),
                    readerGlasovi.lines().skip(1).filter(l -> !l.isBlank()).map(l -> {
                        String[] values = l.split(";");
                        if (values[2].equalsIgnoreCase("Svet")) {
                            return new LocenGlas(values[2], values[3], Integer.parseInt(values[4]), Integer.parseInt(values[5]), 0);
                        }
                        if (values[5].isEmpty() || values[6].isEmpty()) {
                            return new Glas(values[2], values[3], Integer.parseInt(values[4]));
                        }
                        return new LocenGlas(values[2], values[3], Integer.parseInt(values[4]), Integer.parseInt(values[5]), Integer.parseInt(values[6]));
                    }).collect(Collectors.toCollection(ArrayList::new)));
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return new Tekmovanje(new ArrayList<>(), new ArrayList<>());
        }
    }


}
