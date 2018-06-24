package StaticVerificator;

import VerificatorInterpritator.Tokens.TermType;

import java.util.ArrayList;
import java.util.List;

public class PseudoType {
    private List<TermType> types = new ArrayList<>();

    public List<TermType> getTypes() {
        return types;
    }

    public PseudoType setTypes(List<TermType> types) {
        this.types.addAll(types);
        return this;
    }

    @Override
    public String toString() {
        return "PseudoType{" +
                "types=" + types +
                '}';
    }
}
