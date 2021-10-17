package zubereitung;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import kuechengeraet.Kuechengeraet;
import prolog.ParameterSet;
import prolog.Substitution;
import quantitaet.Quantitaet;
import rezept.Rezept;
import status.StateDescription;
import zutat.Zutat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static java.lang.Class.forName;

public class Bereitstellen extends Zubereitung {
    @TaskAttribute public String zutat;
    @TaskAttribute public String geraet;
    @TaskAttribute public String name;
    @TaskAttribute public String menge;


    public StateDescription effects(StateDescription current) {
        ParameterSet p = current.getFacts();

        if (zutat != null) {
            p.add(zutat.toLowerCase() + "(" + name + ")");
            p.add("bereit(" + name + ")");

            if (menge != null) {
                Quantitaet q = Quantitaet.toQuantity(menge);
                p.add("hatMenge(" + name + "," + q.toString() + ")");
            }
        }
        else if (geraet != null) {
            p.add(geraet.toLowerCase() + "(" + name + ")");
            p.add("bereit(" + name + ")");
        }

        return new StateDescription(p);
    }

    public Status execute() {
        Rezept recipe = (Rezept)getObject();

            try {
                if (zutat != null) {
                    Class<?> clazz = forName("zutat." + zutat);
                    Zutat z = (Zutat)clazz.getDeclaredConstructor().newInstance();
                    z.setName(name);
                    recipe.addIngredient(z);

                    if (menge != null) {
                        z.setQuantity(Quantitaet.toQuantity(menge));
                    }

                    return Status.SUCCEEDED;
                }
                else if (geraet != null) {
                    Class<?> clazz = forName("kuechengeraet." + geraet);
                    Kuechengeraet k = (Kuechengeraet) clazz.getDeclaredConstructor().newInstance();
                    k.setName(name);
                    recipe.addTool(k);

                    return Status.SUCCEEDED;
                }
                else return Status.FAILED;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
        }

        return Status.FAILED;
    }

    protected Task copyTo(Task task) {
        return null;
    }
}
