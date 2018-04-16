package RefalInterpritator;

import java.util.ArrayList;
import java.util.List;

public class RefalNode {
    private List<RefalNode> childs;
    private LexerToken name;
    private RefalNode paren;

    public String getName() {
        return name.name;
    }

    public RefalNode(LexerToken token) {
        name = token;
        childs = new ArrayList<RefalNode>();
        paren = null;
    }

    public void addChild(RefalNode child) {
        childs.add(child);
    }

    public RefalNode getParen() {
        return paren;
    }

    public RefalNode setParen(RefalNode paren) {
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

    public RefalNode getLastChild() {
        return childs.get(childs.size() - 1);
    }

    public List<RefalNode> getChilds() {
        return childs;
    }
}

