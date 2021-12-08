package main;

import de.ur.ai.Renderer;
import prolog.ParameterSet;

import java.util.Locale;

abstract public class KochAssistentObject {
    static int object_count = 0;
    protected String name;
    protected String id;
    protected Renderer myRenderer;

    public KochAssistentObject() {
        id = new String("o_" + object_count++);
        name = this.getClass().getSimpleName().toLowerCase();
    }

    public String id() {
        return id;
    }

    public String bezeichner() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public String toString() {
        return id() + ": " + bezeichner();
    }

    public void setRenderer(Renderer r) {
        myRenderer = r;
    }

    public Renderer getRenderer() {
        return myRenderer;
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        else if (!(o instanceof KochAssistentObject)) return false;
        else return id.equals(((KochAssistentObject)o).id());
    }

    public abstract ParameterSet factsToProlog(ParameterSet p);
}
