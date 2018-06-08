package VerificatorInterpritator.Tokens;

import java.util.ArrayList;
import java.util.List;

public class StretchType extends SimpleType {
    List<TermType> left = new ArrayList<>();
    TermType stretch;
    List<TermType> right = new ArrayList<>();

    public List<TermType> getLeft() {
        return left;
    }

    public void setLeft(List<TermType> left) {
        this.left = left;
    }

    public TermType getStretch() {
        return stretch;
    }

    public void setStretch(TermType stretch) {
        this.stretch = stretch;
    }

    public List<TermType> getRight() {
        return right;
    }

    public void setRight(List<TermType> right) {
        this.right = right;
    }

    public String toString() {
        return "" + left + "* " + stretch + right;
    }

    public void lastIsStretch() {
        setStretch(left.get(left.size() - 1));
        left.remove(left.size() - 1);
    }
}
