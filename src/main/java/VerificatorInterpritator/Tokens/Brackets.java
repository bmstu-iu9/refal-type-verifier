package VerificatorInterpritator.Tokens;

import java.util.ArrayList;
import java.util.List;

public class Brackets extends SimpleType {
    private List<SimpleType> content = new ArrayList<>();

    public List<SimpleType> getContent() {
        return content;
    }

    public void setContent(List<SimpleType> content) {
        this.content = content;
    }

    public String toString() {
        final String[] ans = {"("};
        content.forEach(term -> ans[0] = ans[0] + term.toString() + " ");
        ans[0] += ")";
        return ans[0];
    }
}
