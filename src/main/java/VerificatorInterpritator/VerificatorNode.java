package VerificatorInterpritator;

import VerificatorInterpritator.Tokens.LexerToken;

import java.util.ArrayList;
import java.util.List;

public class VerificatorNode {
    private List<VerificatorNode> childs;
    private LexerToken name;
    private VerificatorNode paren;

    public String getName() {
        return name.name;
    }

    public VerificatorNode(LexerToken token) {
        name = token;
        childs = new ArrayList<VerificatorNode>();
        paren = null;
    }

    public void addChild(VerificatorNode child) {
        childs.add(child);
    }

    public VerificatorNode getParen() {
        return paren;
    }

    public VerificatorNode setParen(VerificatorNode paren) {
        this.paren = paren;
        return this;
    }

    public String toString() {
        final String[] ans = {name.name};
        if(childs.size() != 0) {
                ans[0] += "[\n";
                        childs.forEach(node -> {
                       ans[0] +=" " + node.toString();
                       });
        ans[0] += "]";
        }
        return ans[0] + " ";
    }

    public VerificatorNode getLastChild() {
        return childs.get(childs.size() - 1);
    }

    public List<VerificatorNode> getChilds() {
        return childs;
    }

    public VerificatorNode getArguments() {
        return childs.get(0);
    }

    public VerificatorNode getResult() {
        return  childs.get(1);
    }

    public boolean equals(VerificatorNode verificatorNode) {
        return this.getName().equals(verificatorNode.getName());
    }
}