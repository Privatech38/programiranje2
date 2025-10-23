public class Kviz3 {

    public static int[] sestejPolinoma(int[] pa, int[] pb) {
        int[] pr = new int[Math.max(pa.length, pb.length)];
        int[] higherLevelP = (pa.length >= pb.length ? pa : pb);
        int[] lowerLevelP = (pa.length < pb.length ? pa : pb);
        if (pa.length != pb.length) {
            System.arraycopy(higherLevelP, lowerLevelP.length, pr, lowerLevelP.length, higherLevelP.length - lowerLevelP.length);
        }
        for (int i = 0; i < lowerLevelP.length; i++) {
            pr[i] = pa[i] + pb[i];
        }
        return pr;
    }

    public static int[] zmnoziPolinoma(int[] pa, int[] pb) {
        int[] pr = new int[pa.length + pb.length - 1];
        for (int i = 0; i < pa.length; i++) {
            for (int j = 0; j < pb.length; j++) {
                pr[i + j] += pa[i] * pb[j];
            }
        }
        return pr;
    }

    static class Tocka {

        private int x;
        private int y;

        public Tocka(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Tocka(String x, String y) {
            this.x = Integer.parseInt(x);
            this.y = Integer.parseInt(y);
        }

        @Override
        public String toString() {
            return String.format("(%d,%d)", this.x, this.y);
        }

        public static Tocka[] preberiTocke(String imeDatoteke) {
            try (java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(imeDatoteke))) {
                return bufferedReader.lines().filter(s -> !s.isBlank()).map(s -> new Tocka(s.split(" ")[0], s.split(" ")[1])).toArray(Tocka[]::new);
            } catch (java.io.IOException e) {
                e.printStackTrace();
                return new Tocka[0];
            }
        }

        public static String tabelaToString(Tocka[] tocke) {
            return java.util.Arrays.toString(tocke);
        }

        public static void najblizji(Tocka[] t1, Tocka[] t2) {
            if (t1.length == 0) {
                System.out.println("Prva tabela je prazna");
                return;
            }
            if (t2.length == 0) {
                System.out.println("Druga tabela je prazna");
                return;
            }
            Tocka m1 = null, m2 = null;
            double distance = Double.MAX_VALUE;
            for (Tocka tocka1 : t1) {
                for (Tocka tocka2 : t2) {
                    double currentDistance = Math.sqrt(Math.pow(tocka1.x - tocka2.x, 2) + Math.pow(tocka1.y - tocka2.y, 2));
                    if (currentDistance < distance) {
                        m1 = tocka1;
                        m2 = tocka2;
                        distance = currentDistance;
                    }
                }
            }
            System.out.printf("Najbližji točki sta %s in %s, razdalja med njima je  %.2f", m1.toString(), m2.toString(), distance);
        }


    }

    static class Matrika {

        private int[][] elementi;

    }

    public static void preberiInIzpisi(String oznaka) {
        java.util.HashMap<String, String> imena = new java.util.HashMap<>();
        // Imena
        try (java.io.BufferedReader prijave = new java.io.BufferedReader(new java.io.FileReader(oznaka + "-prijave.txt"))) {
            prijave.lines().filter(s -> !s.isBlank()).forEach(l -> imena.put(l.split(":")[0], l.split(":")[1]));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        // All results
        java.util.HashMap<String, Integer> tocke = new java.util.HashMap<>();
        for (int i = 1; i <= 4; i++) {
            try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(oznaka + "-n" + i + ".txt"))) {
                reader.lines().forEach(l -> {
                    String key = l.split(":")[0];
                    if (!tocke.containsKey(key)) {
                        tocke.put(key, Integer.parseInt(l.split(":")[1]));
                        return;
                    }
                    tocke.put(key, tocke.get(key) + Integer.parseInt(l.split(":")[1]));
                });
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        // Print
        imena.entrySet().stream().sorted(java.util.Map.Entry.comparingByValue()).forEach(e -> {
            String strTocke = (tocke.containsKey(e.getKey()) ? "" + tocke.get(e.getKey()) : "VP");
            System.out.println(String.join(":", e.getKey(), e.getValue(), strTocke));
        });
    }

    class Elipsa {
        public Elipsa(double x, double y, double malaPolos, double velikaPolos) {
            this.x = x;
            this.y = y;
            this.malaPolos = malaPolos;
            this.velikaPolos = velikaPolos;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getMalaPolos() {
            return malaPolos;
        }

        public void setMalaPolos(double malaPolos) {
            this.malaPolos = malaPolos;
        }

        public double getVelikaPolos() {
            return velikaPolos;
        }

        public void setVelikaPolos(double velikaPolos) {
            this.velikaPolos = velikaPolos;
        }

        private double x;
        private double y;
        private double malaPolos;
        private double velikaPolos;

        @Override
        public String toString() {
            return String.format("Ellipse [x=%.1f, y=%.1f, malaPolos=%.1f, velikaPolos=%.1f]", x, y, malaPolos, velikaPolos);
        }

        public double getPovrsina() {
            return malaPolos * velikaPolos * Math.PI;
        }

        public double getObseg() {
            return Math.PI * (malaPolos + velikaPolos);
        }
    }

}
