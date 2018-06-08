package VerificatorInterpritator.Tokens;

public class Compound extends Symbol{
    private String value;

    public Compound(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
