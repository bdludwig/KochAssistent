package quantitaet;

abstract public class Quantitaet {
    protected double menge;
    protected String name;

    static private int counter = 0;

    public Quantitaet() {
    }

    public Quantitaet(String n, double m) {
        name = n;
        menge = m;
    }

    /*public void facts(boolean nlg) {
        if (nlg) {
            System.out.println(name + " ist " + this.getClass().getSimpleName());
            System.out.println(name + " hat Einheiten " + menge);
        }
        else {
            System.out.println(this.getClass().getSimpleName() + "(" + name + ")" );
            System.out.println("hatEinheiten(" + name + "," + menge + ")");
        }
    }*/

    static public Quantitaet toQuantity(String s) {
        String [] tmp = s.split(" ");
        int len = tmp.length;

        if (tmp[len-1].equals("EL")) return new Essloeffel("q" + (counter++), Double.parseDouble(tmp[0]));
        else return null;
    }
}
