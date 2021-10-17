package main;

import java.util.Locale;

abstract public class KochAssistentObject {
    static int object_count = 0;
    protected String name;
    protected String id;

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
}
