package zbirke;

import izpis.Znaki;

public class Seznam {

    private static String[] strings;

    private static int dolzina;

    /**
     * Ustvari seznam
     * @param n max length of the list
     * @return true if succesfully created, false if already exists or n negative
     */
    public static boolean narediSeznam(int n) {
        if (strings != null) {
            return false;
        }
        if (n < 0) {
            return false;
        }
        strings = new String[n];
        dolzina = 0;
        return true;
    }

    /**
     * Adds an element to the end of the list
     * @param element element to add
     * @return true if successful, false if list doesn't exist or
     */
    public static boolean dodajNaKonecSeznama(String element) {
        if (strings == null) {
            return false;
        }
        if (dolzina == strings.length) {
            String[] kopija = strings.clone();
            strings = new String[kopija.length * 2];
            System.arraycopy(kopija, 0, strings, 0, kopija.length);
        }
        strings[dolzina] = element;
        dolzina++;
        return true;
    }

    /**
     * Lists the elements and their respective indexes (starting at 1) to the system's default logger
     */
    public static void izpisiSeznam() {
        if (strings == null) {
            System.out.println("NAPAKA: Seznam ne obstaja.");
        }
        if (dolzina == 0) {
            System.out.println("Seznam je prazen (nima elementov).");
        }
        for (int i = 0; i < dolzina; i++) {
            System.out.println(i + 1 + ": " + strings[i]);
        }
    }

    /**
     * Removes an element and justifies the elements
     * @param mesto Index of the element to remove
     * @return null if index is out of bounds or list doesn't exist otherwise the removed element
     */
    public static String odstraniIzSeznama(int mesto) {
        if (strings == null) {
            return null;
        }
        if (mesto < 1 || mesto > dolzina) {
            return null;
        }
        String[] kopija = strings.clone();
        System.arraycopy(kopija, 0, strings, 0, mesto - 1);
        if (dolzina > mesto + 1) {
            if (dolzina - (mesto + 1) >= 0)
                System.arraycopy(kopija, mesto, strings, mesto - 1, dolzina - mesto);
        }
        dolzina--;
        return kopija[mesto - 1];
    }

    /**
     * Add an element to the specified index and move other elements in the list accordingly
     * @param element Element to add
     * @param mesto Index to set it to, can be more than the length of the list then works the same as {@link #dodajNaKonecSeznama(String) dodajNaKonecSeznama}
     * @return true if successful, false if index is less than 1 or list doesn't exist or list is full
     */
    public static boolean dodajVSeznam(String element, int mesto) {
        if (mesto < 1) {
            return false;
        }
        if (strings == null) {
            return false;
        }
        if (dolzina == strings.length) {
            return false;
        }
        // Zadnje mesto
        if (mesto > dolzina) {
            strings[dolzina] = element;
            dolzina++;
            return true;
        }
        // Vrini
        String[] kopija = strings.clone();
        System.arraycopy(kopija, 0, strings, 0, mesto - 1);
        strings[mesto - 1] = element;
        System.arraycopy(kopija, mesto - 1, strings, mesto, dolzina - (mesto - 1));
        dolzina++;
        return true;
    }

    /**
     *
     * @return Returns the length of the list
     */
    public static int dolzinaSeznama() {
        if (strings == null) {
            return -1;
        }
        return dolzina;
    }

    /**
     * Removes the list
     * @return true if successful, false if list doen't exist
     */
    public static boolean uniciSeznam() {
        if (strings == null) {
            return false;
        }
        strings = null;
        dolzina = 0;
        return true;
    }

    public static void izpisiSeznam64Bit() {
        if (strings == null) {
            System.out.println("NAPAKA: Seznam ne obstaja.");
        }
        if (dolzina == 0) {
            System.out.println("Seznam je prazen (nima elementov).");
        }
        for (int i = 0; i < dolzina; i++) {
            Znaki.izpisi64bit(i + 1 + ":" + strings[i]);
        }
    }




}
