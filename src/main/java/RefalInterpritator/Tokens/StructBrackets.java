package RefalInterpritator.Tokens;

import java.util.List;

public class StructBrackets extends Term {
    private List<Term> content;

    public List<Term> getContent() {
        return content;
    }

    public void setContent(List<Term> content) {
        this.content = content;
    }

    public String toString() {
        final String[] ans = {"("};
        content.forEach(term -> ans[0] = ans[0] + term.toString() + " ");
        ans[0] += ")";
        return ans[0];
    }
}
