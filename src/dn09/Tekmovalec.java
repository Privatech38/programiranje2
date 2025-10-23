package dn09;

import java.util.HashSet;

public class Tekmovalec {

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
