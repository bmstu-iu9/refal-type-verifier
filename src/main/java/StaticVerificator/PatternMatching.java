package StaticVerificator;

import RefalInterpritator.Tokens.*;
import VerificatorInterpritator.Tokens.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternMatching {

    private List<Function> functions;
    private List<Type> types;
    private Map<Type, Boolean> isUsed;
    private Map<Term, PseudoType> connection;
    private boolean isRight = false;
    private int indexForOpen = 0;
    private int refIndex = 0;

    public PatternMatching(List<Function> functions, List<Type> types) {
        this.functions = functions;
        this.types = types;
        isUsed = new HashMap<>();
        connection = new HashMap<>();
    }

    public boolean match(Definition definition, Function function) {
        connection.clear();
        isUsed.clear();
        indexForOpen = 0;
        refIndex = 0;
        isRight = false;
        for (int i = 0; i < definition.getSentences().size(); i++) {
            isRight = false;
            if (!matchLeftPart(definition.getSentences().get(i).getPattern(), function.getArgument())) {
                return false;
            }
            isRight = true;
            System.out.println("!!!!");
            if (!matchRightPart(definition.getSentences().get(i).getResult(), function.getResult())) {
                return false;
            }
            connection.clear();
        }
        return true;
    }

    public boolean matchRightPart(Expression expression, List<SimpleType> results) {
        for (int i = 0; i < results.size(); i++) {
            if (matchExpressionAndSimpleType(expression.getTerms(), results.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean matchLeftPart(Expression expression, SimpleType argument) {
//        if (argument instanceof FixedType && expression.getTerms().size() != ((FixedType)argument).getTerms().size())
//        {
//            System.out.println("Invalid number of arguments");
//            return false;
//        }
        return matchExpressionAndSimpleType(expression.getTerms(), argument);
    }

    public boolean matchExpressionAndSimpleType(List<Term> expression, SimpleType simpleType) {
        int i = 0;
        int j = 0;
        if (simpleType instanceof FixedType) {
            FixedType variable;
            if (((FixedType)simpleType).getTerms().get(0) instanceof VarTermType &&  ((VarTermType)((FixedType)simpleType).getTerms().get(0)).getRef().equals(new Type("e.ANY"))) {
                return true;
            }
            for (i = 0; i < expression.size(); i++) {
                types.forEach(type -> isUsed.put(type, false));
                j = i;
                variable = new FixedType();
                PseudoType save = null;
                variable.getTerms().add(((FixedType)simpleType).getTerms().get(j - indexForOpen));
                if (expression.get(i) instanceof Variable && ((Variable)expression.get(i)).getType().equals(Mode.E)) {
                    if (isRight) {
                        save = new PseudoType().setTypes(connection.get((Variable) expression.get(i)).getTypes());
                    }
                    while (matchTermAndTermType(expression.get(i), variable)){
                        j++;
                        if (isRight) {
                            connection.get((Variable) expression.get(i)).getTypes().remove(0);
                        }
                        if (j == ((FixedType)simpleType).getTerms().size()) {
                            if (isRight) {
                                if (connection.get((Variable) expression.get(i)).getTypes().size()!= 0) {
                                    System.out.println("Invalid/ number of variables " + simpleType + " and " + save);
                                    return false;
                                }
                                connection.put((Variable) expression.get(i), save);
                            }
                            return true;
                        }
                        variable = new FixedType();
                        variable.getTerms().add(((FixedType)simpleType).getTerms().get(j));
                    }
                    System.out.println("Cant match " + expression + " and " + simpleType);
                    return false;
                } else if (!matchTermAndTermType(expression.get(i), variable)) {
                    System.out.println("Can't match term with simpleType: " + expression.get(i) + " expected " + simpleType + " but found " + connection.getOrDefault(expression.get(i), null));
                    return false;
                }
            }
        }
//        System.out.println(((FixedType)simpleType).getTerms().get());
        if ((indexForOpen == 0 && i != ((FixedType)simpleType).getTerms().size())
                || (indexForOpen != 0 && indexForOpen != ((FixedType)((VarTermType)((FixedType)simpleType).getTerms().get(0)).getRef().getConstructors().get(refIndex)).getTerms().size())) {
            System.out.println("Invalid// number of variables " + simpleType + " and " + expression);
            return false;
        }
        indexForOpen = 0;
        refIndex = 0;
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
            if (term instanceof Macrodigit) {
                return termTypes.get(i) instanceof VarTermType && ((VarTermType) termTypes.get(i)).getName().equals("NUMBER");
            }
            if (term instanceof CompoundSymbol) {
                if (termTypes.get(i) instanceof Compound) {
                    if (!isRight) {
                        connection.put(term, new PseudoType());
                        connection.get(term).getTypes().add(termTypes.get(i));
                        return true;
                    } else {
                        if (!connection.containsKey(term)) {
                            return false;
                        }
                        return connection.get(term).getTypes().get(0).equals(termTypes.get(i));
                    }
                } else if (termTypes.get(i) instanceof VarTermType) {
                    if (matchTermAndVarTerm(term, (VarTermType) termTypes.get(i))) {
                        return true;
                    }
                }
                continue;
            }
            if (term instanceof Variable) {
                if (termTypes.get(i) instanceof VarTermType) {
                    if (isRight) {
                        return connection.getOrDefault(term, null).getTypes().get(0).equals(termTypes.get(i));
                    } else {
                        if (((Variable) term).equals((VarTermType) termTypes.get(i))) {
                            if (!connection.containsKey((Variable) term)) {
                                connection.put((Variable) term, new PseudoType());
                            }
                            connection.get((Variable) term).getTypes().add(termTypes.get(i));
                        }
                        return ((Variable) term).equals((VarTermType) termTypes.get(i));
                    }
                }
                if (termTypes.get(i) instanceof Compound) {
                    if (isRight) {
                        return connection.getOrDefault((Variable) term, null).getTypes().size() != 0 && connection.getOrDefault((Variable) term, null).getTypes().get(0).equals(termTypes.get(i));
                    } else {
                        if (!connection.containsKey((Variable) term)) {
                            connection.put((Variable) term, new PseudoType());
                        }
                        connection.get((Variable) term).getTypes().add(termTypes.get(i));
                        return true;
                    }
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
                return j != functions.size()
                        && matchLeftPart(new Expression().setTerms(((CallBrackets) term).getContent()), functions.get(j).getArgument())
                        && checkFunctionResult(functions.get(j).getResult(), simpleType);
            }
        }
        return false;
    }

    private boolean matchTermAndVarTerm(Term symbol, VarTermType varTermType) {
        if(varTermType.getRef().getConstructors() == null) {
            System.out.println("Can't match term with termType: " + symbol);
            return false;
        }
        if (!isUsed.get(varTermType.getRef())) {
            isUsed.put(varTermType.getRef(), true);
        } else {
            System.out.println("Can't match term with termType: " + symbol);
            return false;
        }
        List<Term> terms = new ArrayList<>();
        terms.add(symbol);
        FixedType variable;
        for (int i = refIndex; i < varTermType.getRef().getConstructors().size(); i++) {
            variable = new FixedType();
            if (((FixedType)varTermType.getRef().getConstructors().get(i)).getTerms().size() > indexForOpen) {
                variable.getTerms().add(((FixedType) varTermType.getRef().getConstructors().get(i)).getTerms().get(indexForOpen));
                if (matchTermAndTermType(symbol, variable)) {
                    indexForOpen++;
                    return true;
                }
            }
            indexForOpen = 0;
            refIndex++;
        }
        System.out.println("Can't match term with termType: " + symbol);
        return false;
    }

    private boolean checkFunctionResult(List<SimpleType> result, SimpleType pattern) {
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).equal(pattern)) {
                return true;
            }
        }
        System.out.println("Wrong function result: found " + result + " but expected " + pattern);
        return false;
    }
}
