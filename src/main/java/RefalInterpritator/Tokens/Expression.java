package RefalInterpritator.Tokens;

import java.util.ArrayList;
import java.util.List;

public class Expression {
    private List<Term> terms;
    private Expression parent;

    public Expression() {
        terms = new ArrayList<>();
    }

    public Expression getParent() {
        return parent;
    }

    public Expression setParent(Expression parent) {
        this.parent = parent;
        return this;
    }

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

    public String toString() {
        final String[] ans = {""};
        terms.forEach(term -> ans[0] = ans[0] + term.toString() + " ");
        return ans[0];
    }
}
