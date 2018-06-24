package RefalInterpritator.Tokens;

import VerificatorInterpritator.Tokens.Mode;
import VerificatorInterpritator.Tokens.VarTermType;

public class Variable extends Term {
    private Mode type;
    private String index;

    public Variable(String term) {
        setMode(term.charAt(0));
        index = term.substring(2);
    }

    public String toString() {
        return String.valueOf(type) + "." + index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof VarTermType) {
            return ((VarTermType)o).getMode().equals(type);
        }
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        if (type != variable.type) return false;
        return index.equals(variable.index);
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = index.hashCode();
        return result;
    }

    public void setMode(char type) {
        switch (type) {
            case 's':
            case 'S':
                this.type = Mode.S;
                break;
            case 't':
            case 'T':
                this.type = Mode.T;
                break;
            case 'e':
            case 'E':
                this.type = Mode.E;
                break;
        }
    }

    public Mode getType() {
        return type;
    }

    public String getIndex() {
        return index;
    }
}
