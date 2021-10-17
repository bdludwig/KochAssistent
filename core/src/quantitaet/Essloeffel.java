package quantitaet;

public class Essloeffel extends Quantitaet {
    public Essloeffel() {
        super("EL", 0);
    }

    public Essloeffel(double amount) {
        super("EL", amount);
    }

    public Essloeffel(String name, double amount) {
        super(name, amount);
    }

    public String toString() {
        return name;
    }
}
