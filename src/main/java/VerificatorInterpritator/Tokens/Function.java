package VerificatorInterpritator.Tokens;

import java.util.ArrayList;
import java.util.List;

public class Function {
    private String name;
    private SimpleType argument;
    private List<SimpleType> result = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimpleType getArgument() {
        return argument;
    }

    public void setArgument(SimpleType argument) {
        this.argument = argument;
    }

    public List<SimpleType> getResult() {
        return result;
    }

    public void setResult(List<SimpleType> result) {
        this.result = result;
    }

    public String toString() {
        final String[] ans = {""};
        ans[0] = "<" + name + " " + argument.toString() + "> == ";
        result.stream().forEach(simpleType -> ans[0] = ans[0] + " " + simpleType.toString());
        return ans[0];
    }
}
