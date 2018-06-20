package VerificatorInterpritator.Tokens;

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
        final String[] ans = {"!("};
        ans[0] += content.toString();
        ans[0] += ")!";
        return ans[0];
    }
}
