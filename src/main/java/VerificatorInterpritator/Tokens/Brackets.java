package VerificatorInterpritator.Tokens;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class Brackets extends TermType {
    private SimpleType content;

    public SimpleType getContent() {
        return content;
    }

    public Brackets setContent(SimpleType content) {
        this.content = content;
        return this;
    }

    public String toString() {
        final String[] ans = {"("};
        System.out.println(content.toString());
        ans[0] += content.toString();
        ans[0] += ")";
        return ans[0];
    }
}
