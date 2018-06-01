package RefalInterpritator.Tokens;

import java.util.ArrayList;
import java.util.List;

public class Defenition {

    private boolean isEntry;
    private List<Sentence> sentences = new ArrayList<>();
    private String name;

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
}
