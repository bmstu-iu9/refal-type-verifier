package RefalInterpritator.Tokens;

public class Variable extends Term {
    private char type;
    private String index;

    public Variable(String term) {
        type = term.charAt(0);
        index = term.substring(2);
    }

    public String toString() {
        return String.valueOf(type) + "." + index;
    }
}
