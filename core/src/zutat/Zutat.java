package zutat;

import main.KochAssistentObject;
import quantitaet.Quantitaet;
import prolog.ParameterSet;

import java.util.ArrayList;

abstract public class Zutat extends KochAssistentObject {
    protected Quantitaet quant;
    protected ArrayList<String> eigenschaften;
    protected KochAssistentObject my_container;
    protected boolean is_in_hand;

    public Zutat() {
        eigenschaften = new ArrayList<String>();
        is_in_hand = false;
    }

    public void setInHand(boolean h) {
        is_in_hand = h;
    }

    public void setQuantity(Quantitaet q) {
        quant = q;
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
        allIngredientFacts = p.add(bezeichner() + "(" + id() + ")");
        if (is_in_hand) allIngredientFacts.add("in_hand(" + id() + ",s0)");
        if (quant != null) allIngredientFacts = allIngredientFacts.add("hatMenge(" + id() + "," + quant + ")");

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

    public void setContainer(KochAssistentObject o) {
        my_container = o;
    }

    public KochAssistentObject getContainer() {
        return my_container;
    }
}
