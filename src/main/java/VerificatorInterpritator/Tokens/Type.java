package VerificatorInterpritator.Tokens;

import java.util.List;

public class Type {
    private Mode mode;
    private String name;
    private List<SimpleType> constructors;

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
        return "Type{" +
                "mode=" + mode +
                ", name='" + name + '\'' +
                ", constructors=" + constructors +
                '}';
    }
}
