package VerificatorInterpritator.Tokens;

public class Compound extends Symbol{
    private String value;

    public Compound(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;

        Compound compound = (Compound) o;
        return value.equals(compound.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
