package RefalInterpritator.Tokens;

import java.util.ArrayList;
import java.util.List;

public class CallBrackets extends Term {
    private String name;
    private List<Term> content;

    public CallBrackets() {
        content = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Term> getContent() {
        return content;
    }

    public void setContent(List<Term> content) {
        this.content = content;
    }

    public String toString() {
        final String[] ans = {"<"};
        ans[0] = ans[0] + name + " ";
        content.forEach(term -> ans[0] = ans[0] + term.toString() + " ");
        ans[0] +=">";
        return ans[0];
    }
}
