package RefalInterpritator.Tokens;

import VerificatorInterpritator.Tokens.Compound;

public class CompoundSymbol extends Term {
    private String value;

    public CompoundSymbol(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Compound) {
            return ((Compound) o).getValue().equals(value);
        }
        if (o == null || getClass() != o.getClass()) return false;

        CompoundSymbol that = (CompoundSymbol) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
