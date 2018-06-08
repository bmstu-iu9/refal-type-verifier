package VerificatorInterpritator.Tokens;

public class Character extends Symbol{
    private char value;

    public Character(char value) {
        this.value = value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
