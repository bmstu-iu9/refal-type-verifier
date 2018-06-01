package RefalInterpritator.Tokens;

import java.util.List;

public class Expression {
    public List<Term> terms;

    public List<Term> getTerms() {
        return terms;
    }

    public Expression setTerms(List<Term> terms) {
        this.terms = terms;
        return this;
    }

    public Term getLastTerm() {
        return terms.get(terms.size() - 1);
    }
}
