package VerificatorInterpritator.Tokens;

public class Number extends Symbol {
    private long value;

    public Number(String value) {
        this.value = Integer.valueOf(value);
    }
}
