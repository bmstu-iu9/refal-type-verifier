package VerificatorInterpritator.Tokens;

import java.util.ArrayList;
import java.util.List;

public class FixedType extends SimpleType {
    List<TermType> terms = new ArrayList<>();

    public List<TermType> getTerms() {
        return terms;
    }

    public FixedType setTerms(List<TermType> terms) {
        this.terms = terms;
        return this;
    }

    @Override
    public String toString() {
        return "" + terms;
    }
}
