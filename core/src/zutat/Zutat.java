package zutat;

import main.KochAssistentObject;
import quantitaet.Quantitaet;
import prolog.ParameterSet;

import java.util.ArrayList;

abstract public class Zutat extends KochAssistentObject {
    protected Quantitaet quant;
    protected ArrayList<String> eigenschaften;

    public Zutat() {
        eigenschaften = new ArrayList<String>();
    }

    public void setQuantity(Quantitaet q) {
        quant = q;
    }

    public String id() {
        return name;
    }

    public void verbrauchen() {
        addProperty("verbraucht(" + this.id() + ")");
    }

    public void addProperty(String methode) {
        eigenschaften.add(methode);
    }

    public void removeProperty(String methode) {
        eigenschaften.remove(methode);
    }

    public ParameterSet factsToProlog(ParameterSet p) {
        // leeres Parameterset
        ParameterSet allIngredientFacts;

        // wir fügen res das bisherige ParameterSet hinzu. Außerdem fügen wir den Klassennamen der aktuellen Zutat in lower case,
        // den Namen der Zutat, die quantität (falls es eine Quantitätsangabe gibt)....
        allIngredientFacts = p.add(this.getClass().getSimpleName().toLowerCase() + "(" + name + ")");
        if (quant != null) allIngredientFacts = allIngredientFacts.add("hatMenge(" + name + "," + quant + ")");

        // zur Veranschaulichung nächste Zeile ausklammern:
        //System.out.println(res.toString());

        // ...und alle Eigenschaften der Zutat hinzu
        for (String e : eigenschaften) {
            System.out.println("Zutat to Prolog: " + e);
            allIngredientFacts = allIngredientFacts.add(e);
            // negierte Fakten gibt es nicht,
            // stattdessen negation as failure: was nicht dasteht ist falsch.
        }

        return allIngredientFacts;
    }

    public String toString() {
        return id();
    }
}
