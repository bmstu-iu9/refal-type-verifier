package RefalInterpritator.Tokens;

import java.util.ArrayList;
import java.util.List;

public class Definition {

    private boolean isEntry;
    private List<Sentence> sentences = new ArrayList<>();
    private String name;

    private VerificatorInterpritator.Tokens.Function func_type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEntry() {
        return isEntry;
    }

    public void setEntry(boolean entry) {
        isEntry = entry;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public Sentence getLastSentence() {
        return sentences.get(sentences.size() - 1);
    }

    public String toString() {
        final String[] ans = {""};
        if (isEntry) {
            ans[0] += "$ENTRY ";
        }
        if(name == null) {
            return ans[0];
        }
        ans[0] += name;
        ans[0] += "{\n";
        sentences.forEach(sentence -> ans[0] = ans[0] + sentence.toString() );
        return ans[0]+"\n}\n";
    }

    public void setType(VerificatorInterpritator.Tokens.Function type) {
        this.func_type = type;
    }

    public VerificatorInterpritator.Tokens.Function getType() {
        return this.func_type;
    }
}
