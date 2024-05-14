package dn09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ZgodovinaTekmovanj {

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
        List<String> seznam = this.seznamTekmovanj.stream().flatMap(t -> t.getSeznamGlasov().stream()).collect(Collectors.groupingBy(g -> Stream.of(g.getOdDrzave(), g.getZaDrzavo()).sorted(Comparator.naturalOrder()).toList())).entrySet()
                .stream().map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue().stream().mapToInt(Glas::getStTock).sum()))
                .sorted(Comparator.<Map.Entry<List<String>, Integer>>comparingInt(Map.Entry::getValue).reversed().thenComparing((e1, e2) -> e1.getKey().get(0).compareToIgnoreCase(e2.getKey().get(0)))).limit(topN)
//                    .map(e -> new AbstractMap.SimpleEntry<>(e.getKey().stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList()), e.getValue()))
//                    .sorted(Comparator.<Map.Entry<List<String>, Integer>>comparingInt(Map.Entry::getValue).reversed().thenComparing((e1, e2) -> e1.getKey().get(0).toLowerCase().compareTo(e2.getKey().get(0))))
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
