package zubereitung;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import kuechengeraet.Kuechengeraet;
import rezept.Rezept;
import prolog.ParameterSet;
import status.StateDescription;
import prolog.Substitution;
import zutat.Zutat;

import java.lang.reflect.InvocationTargetException;

import static java.lang.Class.forName;

public class Puerieren extends Zubereitung {
    @TaskAttribute
    public String geraet;
    @TaskAttribute
    public String gefaess;
    @TaskAttribute
    public String ergebnis;
    @TaskAttribute
    public String typ;

    private Rezept recipe;
    private Kuechengeraet mixer;
    private Kuechengeraet behaelter;

    private void initLocalVars() {
        recipe = (Rezept) getObject();
        mixer = recipe.getTool(geraet);
        behaelter = recipe.getTool(gefaess);
    }

    public boolean preconditionsSatisfied(StateDescription current) {
        if (gefaess == null) {
            // System.out.println("Gefaess " + behaelter.bezeichner() + " ist nicht verfügbar.");
            return false;
        } else if (geraet == null) {
            // System.out.println("Gerät" + mixer.bezeichner() + " ist nicht verfügbar.");
            return false;
        } else {
            Substitution s;

            s = current.entails(new ParameterSet("hatInhalt(" + gefaess + ")"));
            return (s != null);
        }
    }

    public Status currentTaskState(StateDescription current) {
        if (current.entails(new ParameterSet().add("pueriert(" + ergebnis + ")")) != null)
            return Status.SUCCEEDED;
        else
            return Status.FAILED;
    }

    public StateDescription effects(StateDescription current) {
        ParameterSet p = current.getFacts();
        return new StateDescription(p.add(typ.toLowerCase() + "(" + ergebnis + ")").add("pueriert(" + ergebnis + ")"));
    }
    
    public Status execute() {
        initLocalVars();
        StateDescription current = recipe.statusProlog();

        if (preconditionsSatisfied(current)) {
            System.out.println(behaelter.bezeichner() + " hat Inhalt.");

            Class<?> product = null;
            try {
                product = forName("zutat.produkt." + typ);
                Zutat z = (Zutat) product.getDeclaredConstructor().newInstance();
                z.setName(ergebnis);
                recipe.addIngredient(z);

                System.out.println("Ich püriere den Inhalt von " + behaelter.bezeichner());

                return Status.SUCCEEDED;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return Status.FAILED;
            } catch (InstantiationException e) {
                e.printStackTrace();
                return Status.FAILED;
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                return Status.FAILED;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return Status.FAILED;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return Status.FAILED;
            }
        } else {
            System.out.println("Fehler: " + behaelter.bezeichner() + " ist leer!");
            return Status.FAILED;
        }
    }

    protected Task copyTo(Task task) {
        return null;
    }
}