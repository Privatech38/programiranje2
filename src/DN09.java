import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            case "pobratene" -> ZgodovinaTekmovanj.izDatotek(args[1], args[2]).izpisiPobrateneDrzave(Integer.parseInt(args[3]));
        }
    }

    static class Tekmovalec {

        private final String drzava;
        private final String ime;
        private final String naslovPesmi;

        public Tekmovalec(String drzava, String ime, String naslovPesmi) {
            this.drzava = drzava;
            this.ime = ime;
            this.naslovPesmi = naslovPesmi;
        }

        public String getDrzava() {
            return drzava;
        }

        public String getIzvajalec() {
            return ime;
        }

        public String getNaslovPesmi() {
            return naslovPesmi;
        }

        @Override
        public String toString() {
            return String.format("(%s) %s - %s", this.drzava, this.ime, this.naslovPesmi);
        }
    }

    static class Glas {

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

   static class LocenGlas extends Glas {

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

    static interface Kriterij {

        int steviloTock(Tekmovanje tekmovanje, String drzava);

    }

    static class OsnovniKriterij implements Kriterij {
        @Override
        public int steviloTock(Tekmovanje tekmovanje, String drzava) {
            return tekmovanje.getSeznamGlasov().stream().filter(g -> g.getZaDrzavo().equalsIgnoreCase(drzava)).mapToInt(Glas::getStTock).sum();
        }
    }

    static class UtezeniKriterij implements Kriterij {

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

    static class Tekmovanje {

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

    static class ZgodovinaTekmovanj {

        public ArrayList<Tekmovanje> seznamTekmovanj;

        public ZgodovinaTekmovanj(ArrayList<Tekmovanje> tekmovanja) {
            this.seznamTekmovanj = tekmovanja;
        }

        public int getSkupnoSteviloTock(String drzava) {
            return this.seznamTekmovanj.stream().mapToInt(t -> t.steviloTock(drzava)).sum();
        }

        public float povprecnaUvrstitev(String drzava) {
            return (float) this.seznamTekmovanj.stream().mapToInt(t -> t.getMesto(drzava)).filter(i -> i > 0).average().getAsDouble();
        }

        public int najboljsaUvrstitev(String drzava) {
            return this.seznamTekmovanj.stream().mapToInt(t -> t.getMesto(drzava)).filter(i -> i > 0).min().getAsInt();
        }

        public void izpisiPobrateneDrzave(int topN) {
            System.out.println("Drzave z najvec medsebojnih glasov:");
            List<String> seznam = this.seznamTekmovanj.stream().flatMap(t -> t.getSeznamGlasov().stream())
                    .collect(Collectors.groupingBy(g -> Stream.of(g.getOdDrzave(), g.getZaDrzavo()).sorted(Comparator.naturalOrder()).toList())).entrySet()
                    .stream().map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue().stream().mapToInt(Glas::getStTock).sum()))
                    .sorted(Comparator.<Map.Entry<List<String>, Integer>>comparingInt(Map.Entry::getValue).reversed().thenComparing((e1, e2) -> e2.getKey().get(0).compareTo(e1.getKey().get(0)))).limit(topN)
                    .map(e -> String.format("%s <-(%dt)-> %s\n", e.getKey().get(0), e.getValue(), e.getKey().get(1))).toList();
            for (int i = 0; i < seznam.size(); i++) {
                System.out.print(i + 1 + ". " + seznam.get(i)); // God damn it Oracle, zakaj ni for each with index
            }
        }

        public static ZgodovinaTekmovanj izDatotek(String datotekaTekmovalci, String datotekaGlasovi) {
            try (BufferedReader readerTekmovalci = new BufferedReader(new FileReader(datotekaTekmovalci));
                 BufferedReader readerGlasovi = new BufferedReader(new FileReader(datotekaGlasovi))) {
                Map<String, List<String>> tekmovalci = readerTekmovalci.lines().skip(1).filter(l -> !l.isBlank()).collect(Collectors.groupingBy(l -> l.split(";")[0]));
                Map<String, List<String>> glasovi = readerGlasovi.lines().skip(1).filter(l -> !l.isBlank()).collect(Collectors.groupingBy(l -> l.split(";")[0] + ";" + l.split(";")[1]));
                return new ZgodovinaTekmovanj(glasovi.entrySet().stream().sorted(Comparator.comparingInt(e -> Integer.parseInt(e.getKey().split(";")[0])))
                        .map(e -> new Tekmovanje(tekmovalci.get(e.getKey().split(";")[0]).stream().map(l -> new Tekmovalec(l.split(";")[1], l.split(";")[2], l.split(";")[3])).collect(Collectors.toCollection(ArrayList::new)),
                                e.getValue().stream().map(l -> {
                                    String[] values = l.split(";");
                                    if (values[2].equalsIgnoreCase("Svet")) {
                                        return new LocenGlas(values[2], values[3], Integer.parseInt(values[4]), Integer.parseInt(values[5]), 0);
                                    }
                                    if (values.length <= 5 || values[5].isEmpty() || values[6].isEmpty()) {
                                        return new Glas(values[2], values[3], Integer.parseInt(values[4]));
                                    }
                                    return new LocenGlas(values[2], values[3], Integer.parseInt(values[4]), Integer.parseInt(values[5]), Integer.parseInt(values[6]));
                                }).collect(Collectors.toCollection(ArrayList::new))))
                        .collect(Collectors.toCollection(ArrayList::new)));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

    }


}
