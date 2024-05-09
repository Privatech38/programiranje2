package dn09;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Tekmovanje {

    private final ArrayList<Tekmovalec> seznamTekmovalcev;
    private final ArrayList<Glas> seznamGlasov;

    private Kriterij kriterij;

    public Tekmovanje(ArrayList<Tekmovalec> seznamTekmovalcev, ArrayList<Glas> seznamGlasov) {
        this.seznamTekmovalcev = seznamTekmovalcev;
        this.seznamGlasov = seznamGlasov;
        this.kriterij = new OsnovniKriterij();
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

    public static Tekmovanje izDatotek(String datotekaTekmovalci, String datotekaGlasovi) {
        try (BufferedReader readerTekmovalci = new BufferedReader(new FileReader(datotekaTekmovalci));
            BufferedReader readerGlasovi = new BufferedReader(new FileReader(datotekaGlasovi))) {
            return new Tekmovanje(readerTekmovalci.lines().skip(1).filter(l -> !l.isBlank()).map(l -> new Tekmovalec(l.split(";")[1], l.split(";")[2], l.split(";")[3])).collect(Collectors.toCollection(ArrayList::new)),
                    readerGlasovi.lines().skip(1).filter(l -> !l.isBlank()).map(l -> new Glas(l.split(";")[2], l.split(";")[3], Integer.parseInt(l.split(";")[4]))).collect(Collectors.toCollection(ArrayList::new)));
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return new Tekmovanje(new ArrayList<>(), new ArrayList<>());
        }
    }


}
