package RefalInterpritator.Tokens;

public class CompoundSymbol extends Term {
    private String value;

    public CompoundSymbol(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
