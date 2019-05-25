package glossary;

import java.util.*;

public class Glossary {

    private Map<String, GlossaryItem> terms = new HashMap<>();

    public Glossary() {
    }

    public Map<String, GlossaryItem> getTerms() {
        return terms;
    }

    public void setTerms(Map<String, GlossaryItem> terms) {
        this.terms = terms;
    }
}
