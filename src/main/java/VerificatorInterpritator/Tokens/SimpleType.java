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
        if (this instanceof StretchType) {
            return ((StretchType) this).getStretch().equals(((FixedType)simpleType).getTerms().get(0));
        }
        return ((FixedType)simpleType).getTerms().get(0).equals(((FixedType)this).getTerms().get(0));
    }
}
