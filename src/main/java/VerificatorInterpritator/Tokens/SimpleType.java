package VerificatorInterpritator.Tokens;

public class SimpleType {
    private SimpleType parrent;

    public SimpleType getParrent() {
        return parrent;
    }

    public SimpleType setParrent(SimpleType parrent) {
        this.parrent = parrent;
        return this;
    }

    public boolean equal(SimpleType simpleType) {
        return ((FixedType)simpleType).getTerms().get(0).equals(((FixedType)this).getTerms().get(0));
    }
}
