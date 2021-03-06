package VerificatorInterpritator.Tokens;

import java.util.List;

public class Type {
    private Mode mode;
    private String name;
    private List<SimpleType> constructors;

    public Type(String value) {
        setMode(value.charAt(0));
        this.name = value.substring(2);
    }

    public Type() {

    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(char mode) {
        switch (mode) {
            case 's':
            case 'S':
                this.mode = Mode.S;
                break;
            case 't':
            case 'T':
                this.mode = Mode.T;
                break;
            case 'e':
            case 'E':
                this.mode = Mode.E;
                break;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SimpleType> getConstructors() {
        return constructors;
    }

    public void setConstructors(List<SimpleType> constructors) {
        this.constructors = constructors;
    }

    @Override
    public String toString() {
        return "" + mode +
                "." + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Type type = (Type) o;

        if (mode != type.mode) return false;
        return name.equals(type.name);
    }

    @Override
    public int hashCode() {
        int result = mode.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
