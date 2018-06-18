package StaticVerificator;

import RefalInterpritator.Tokens.*;
import VerificatorInterpritator.Tokens.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternMatching {

    private List<Function> functions;
    private List<Type> types;
    private Map<Type, Boolean> isUsed;

    public PatternMatching(List<Function> functions, List<Type> types) {
        this.functions = functions;
        this.types = types;
        isUsed = new HashMap<>();
    }

    public boolean match(Definition definition, Function function) {
        for (int i = 0; i < definition.getSentences().size(); i++) {
            if (!matchLeftPart(definition.getSentences().get(i).getPattern(), function.getArgument())
                    || !matchRightPart(definition.getSentences().get(i).getResult(), function.getResult())) {
                return false;
            }
        }
        return true;
    }

    public boolean matchRightPart(Expression expression, List<SimpleType> results) {
        for (int i = 0; i < results.size(); i++) {
            if (matchExpressionAndSimpleType(expression.getTerms(), results.get(i))) {
                return true;
            }
        }
        System.out.println("1");
        return false;
    }

    public boolean matchLeftPart(Expression expression, SimpleType argument) {
        return matchExpressionAndSimpleType(expression.getTerms(), argument);
    }

    public boolean matchExpressionAndSimpleType(List<Term> expression, SimpleType simpleType) {
        if (simpleType instanceof FixedType) {
            for (int i = 0; i < expression.size(); i++) {
                types.forEach(type -> isUsed.put(type, false));
                if (!matchTermAndTermType(expression.get(i), simpleType)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean matchTermAndTermType(Term term, SimpleType simpleType) {
        List<TermType> termTypes = ((FixedType)simpleType).getTerms();
        for (int i = 0; i < termTypes.size(); i++) {
            if (term instanceof StructBrackets) {
                if (termTypes.get(i) instanceof Brackets) {
                    return matchExpressionAndSimpleType(((StructBrackets) term).getContent(), ((Brackets)termTypes.get(i)).getContent());
                } else if (termTypes.get(i) instanceof VarTermType) {
                    if (matchTermAndVarTerm(term, (VarTermType) termTypes.get(i))) {
                        return true;
                    }
                }
                continue;
            }
            if (term instanceof CompoundSymbol) {
                if (termTypes.get(i) instanceof Compound) {
                    return true;
                } else  if (termTypes.get(i) instanceof VarTermType) {
                    if (matchTermAndVarTerm((CompoundSymbol) term, (VarTermType) termTypes.get(i))) {
                        return true;
                    }
                }
                continue;
            }
            if (term instanceof Variable) {
                if (termTypes.get(i) instanceof VarTermType) {
                    return ((Variable) term).equals((VarTermType) termTypes.get(i));
                }
                continue;
            }
            if (term instanceof CallBrackets) {
                int j = 0;
                for (; j < functions.size(); j++) {
                    if (functions.get(j).getName().equals(((CallBrackets) term).getName())) {
                        break;
                    }
                }
                return j != functions.size() && matchExpressionAndSimpleType(((CallBrackets) term).getContent(), functions.get(j).getArgument());
            }
        }
        return false;
    }

    private boolean matchTermAndVarTerm(Term symbol, VarTermType varTermType) {
        if(varTermType.getRef().getConstructors() == null) {
            return false;
        }
        if (!isUsed.get(varTermType.getRef())) {
            isUsed.put(varTermType.getRef(), true);
        } else {
            return false;
        }
        for (int i = 0; i < varTermType.getRef().getConstructors().size(); i++) {
            if (matchTermAndTermType(symbol, varTermType.getRef().getConstructors().get(i))) {
                return true;
            }
        }
        return false;
    }
}
