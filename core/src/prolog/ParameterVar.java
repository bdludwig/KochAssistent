package prolog;

public class ParameterVar {
    private String name;

    public ParameterVar(String name) {
        this.name = name;
    }

    public String name() {return name; }

    @Override
    public String toString() { return name; }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ParameterVar) return name.equals(((ParameterVar)o).name());
        else return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
