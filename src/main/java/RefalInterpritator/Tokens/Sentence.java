package RefalInterpritator.Tokens;

import java.util.List;

public class Sentence {
    private Expression pattern;
    private List<Condition> conditions;
    private Expression result;
    private List<Sentence> block;

    public Expression getPattern() {
        return pattern;
    }

    public void setPattern(List<Term> pattern) {
        this.pattern = new Expression().setTerms(pattern);
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public Expression getResult() {
        return result;
    }

    public void setResult(List<Term> result) {
        this.result = new Expression().setTerms(result);
    }

    public List<Sentence> getBlock() {
        return block;
    }

    public void setBlock(List<Sentence> block) {
        this.block = block;
    }
}
