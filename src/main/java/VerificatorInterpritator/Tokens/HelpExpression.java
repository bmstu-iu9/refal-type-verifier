package VerificatorInterpritator.Tokens;

import java.util.ArrayList;
import java.util.List;

public class HelpExpression {
    private HelpExpression parrent;
    private List<SimpleType> currentSimples = new ArrayList<>();

    public HelpExpression getParrent() {
        return parrent;
    }

    public HelpExpression setParrent(HelpExpression parrent) {
        this.parrent = parrent;
        return this;
    }

    public List<SimpleType> getCurrentSimples() {
        return currentSimples;
    }

    public void setCurrentSimples(List<SimpleType> currentSimples) {
        this.currentSimples = currentSimples;
    }

    public SimpleType getLastSimpleType() {
        return currentSimples.get(currentSimples.size() - 1);
    }
}
