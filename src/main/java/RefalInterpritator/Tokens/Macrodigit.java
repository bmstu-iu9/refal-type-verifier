package RefalInterpritator.Tokens;

public class Macrodigit extends Term {
    private long value;

    public Macrodigit(String value) {
        this.value = Integer.valueOf(value);
    }

    public String toString() {
        return String.valueOf(value);
    }
}
