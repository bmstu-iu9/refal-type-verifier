package StaticVerificator;

import RefalInterpritator.RefalNode;
import VerificatorInterpritator.VerificatorNode;
import VerificatorInterpritator.VerificatorTreeBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Comparator {

    private static Map<String, String> compares = new HashMap<>();

    private VerificatorTreeBuilder verificatorTree;

    public Comparator(VerificatorTreeBuilder verificatorTree) {
        this.verificatorTree = verificatorTree;
    }

    private boolean compareSimples(String a, String b) {
        if (a.length() < 2 || b.length() < 2) {
            return false;
        }

        if (a.charAt(1) == '.' && b.charAt(1) == '.')
        {
            return a.charAt(0) == b.charAt(0);
        }
        return false;
    }

    private boolean compareSimpleTipes(RefalNode refalNode, VerificatorNode verificatorNode) {
        for (int i = 0; i < verificatorNode.getChilds().size(); i++) {
            if (refalNode.getName().equals("PARENS") && verificatorNode.getChilds().get(i).getName().equals("PARENS")) {
                return compareNodes(refalNode.getChilds(), verificatorNode.getChilds().get(i));
            } else if (refalNode.equals(verificatorNode.getChilds().get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean compareTypes(RefalNode refalNode, VerificatorNode verificatorNode) {
        for (int i = 0; i < verificatorNode.getChilds().size(); i++) {
            if (refalNode.getName().equals("PARENS") && verificatorNode.getChilds().get(i).getName().equals("PARENS")) {
                return compareNodes(refalNode.getChilds(), verificatorNode.getChilds().get(i));
            } else if (refalNode.equals(verificatorNode.getChilds().get(i))) {
                return true;
            }
        }
        int i = 0;
        for (; i < verificatorTree.getTypeDef().size(); i++) {
            if (verificatorTree.getTypeDef().get(i).equals(verificatorNode)) {
                break;
            }
        }
        if (i == verificatorTree.getTypeDef().size()) {
            return false;
        }
        return compareSimpleTipes(refalNode, verificatorTree.getTypeDef().get(i));
    }

    public boolean compareNodes(List<RefalNode> refalNode, VerificatorNode verificatorNode) {
        if (refalNode.size() != verificatorNode.getChilds().size()) {
            return false;
        }
        for (int i = 0; i < refalNode.size(); i++) {
            if (!compareSimples(refalNode.get(i).getName(), verificatorNode.getChilds().get(i).getName())) {
                int j = 0;
                if (refalNode.get(i).getName().length() > 2 && refalNode.get(i).getName().charAt(1) == '.' || refalNode.get(i).getName().equals("PARENS")) {
                    while (j < verificatorTree.getTypeDef().size()
                            && !verificatorTree.getTypeDef().get(j).equals(verificatorNode.getChilds().get(i))) {
                        j++;
                    }
                    if (j == verificatorTree.getTypeDef().size()) {
                        return false;
                    }
                    if (!compareTypes(refalNode.get(i), verificatorTree.getTypeDef().get(j))) {
                        return false;
                    }
                }
            }
            compares.put(refalNode.get(i).getName(), verificatorNode.getChilds().get(i).getName());
        }
        return true;
    }

    private boolean compareResultNodes(List<RefalNode> refalNode, VerificatorNode verificatorNode) {
        // Проверка на совпадение количества аргументов
        if (refalNode.size() != verificatorNode.getChilds().size()) {
            return false;
        }
        // Проверка каждого аргумента
        for (int i = 0; i < refalNode.size(); i++) {
            if (refalNode.get(i).equals(verificatorNode.getChilds().get(i))) {
                continue;
            }
            if (refalNode.get(i).getName().equals("FUNC")) {
                if(!checkFunc(refalNode.get(i))) {
                    return false;
                }
                VerificatorNode function = getFunction(refalNode.get(i));
                // Проверка совпадения результата функции и требуемого аргумента
                if (!function.getResult().equals(verificatorNode.getChilds().get(i))) {
                    return false;
                }
            } else if (!compares.containsKey(refalNode.get(i).getName()) ||
                    !compares.get(refalNode.get(i).getName()).
                            equals(verificatorNode.getChilds().get(i).getName())) {
                if(!compares.containsKey(refalNode.get(i).getName())) {
                    return false;
                }
                VerificatorNode typeDef = getTypeDef(refalNode.get(i));
                if(typeDef == null) {
                    int j = 0;
                    // TODO: Придумать, что делать с сравнением
                    for (; j < verificatorTree.getTypeDef().size()
                            && !verificatorTree.getTypeDef().get(j).getName().equals(compares.get(refalNode.get(i).getName())); j++) {
                    }
                    if(j == verificatorTree.getTypeDef().size()) {
                        return false;
                    }
                    if(!verificatorTree.getTypeDef().get(j).getChilds().get(0).getName().equals(verificatorNode.getChilds().get(i).getName())) {
                        return false;
                    }
                    continue;
                    // !!!!!!!!!
                }
                if(!compareResultTypes(refalNode.get(i), typeDef)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean compareResultTypes(RefalNode refalNode, VerificatorNode verificatorNode) {
        for (int i = 0; i < verificatorNode.getChilds().size(); i++) {
            if (refalNode.getName().equals("PARENS") && verificatorNode.getChilds().get(i).getName().equals("PARENS")) {
                if(compareResultNodes(refalNode.getChilds(), verificatorNode.getChilds().get(i))) {
                    return true;
                }
            } else if (refalNode.equals(verificatorNode.getChilds().get(i))) {
                return true;
            }
        }
        int i = 0;
        for (; i < verificatorTree.getTypeDef().size(); i++) {
            if (verificatorTree.getTypeDef().get(i).getName().equals(verificatorNode.getName())) {
                break;
            }
        }
        if (i == verificatorTree.getTypeDef().size()) {
            return false;
        }
        return compareResultTypes(refalNode, verificatorTree.getTypeDef().get(i));
    }

    public boolean checkResult(List<RefalNode> refalNode, VerificatorNode verificatorNode) {
        if(verificatorNode.getName().charAt(0) == 'e') {
            return true;
        }
        if (refalNode.size() != 1) {
            return verificatorNode.getChilds().size() > 0
                    && verificatorNode.getLastChild().getName().charAt(0) == 'e';
        }
        for (int i = 0; i < refalNode.size(); i++) {
            if (refalNode.get(i).getName().equals("PARENS")) {
                int j = 0;
                for (; j < verificatorTree.getTypeDef().size() && !verificatorTree.getTypeDef().get(j).getName().equals(verificatorNode.getName()); j++) {
                }
                if(j == verificatorTree.getTypeDef().size()) {
                    return verificatorNode.getChilds().get(0).getName().charAt(0) == 'e';
                }
                if(!compareResultTypes(refalNode.get(i), verificatorTree.getTypeDef().get(j))) {
                    return false;
                }
            } else if (refalNode.get(i).getName().equals("FUNC")) {
                if (!checkFunc(refalNode.get(i))) {
                    return false;
                }
                VerificatorNode function = getFunction(refalNode.get(i));
                if (function == null || !function.getResult().getName().equals(verificatorNode.getName())) {
                    return false;
                }
            } else if (!compares.containsKey(refalNode.get(i).getName()) || !compares.get(refalNode.get(i).getName()).equals(verificatorNode.getName())) {
                return false;
            }
//      if (compares.containsKey(refalNode.get(i).getName()) && compares.get(refalNode.get(i).getName()).equals(verificatorTree.getTypeDef().get(j))) {
//      }
        }
        return true;
    }

    private boolean checkFunc(RefalNode refalNode) {
        VerificatorNode function = getFunction(refalNode);
        if(function == null || !compareResultNodes(refalNode.getChilds().subList(1, refalNode.getChilds().size()), function.getArguments())) {
            System.out.println("Err 2");
            return false;
        }
        return true;
    }

    private VerificatorNode getFunction(RefalNode refalNode) {
        for (int j = 0; j < verificatorTree.getFunctions().size(); j++) {
            if (refalNode.getChilds().get(0).equals(verificatorTree.getFunctions().get(j))) {
                return verificatorTree.getFunctions().get(j);
            }
        }
        return null;
    }

    private VerificatorNode getTypeDef(RefalNode refalNode) {
        for (int j = 0; j < verificatorTree.getTypeDef().size(); j++) {
            if(verificatorTree.getTypeDef().get(j).equals(refalNode)) {
                return verificatorTree.getTypeDef().get(j);
            }
        }
        return null;
    }
}
