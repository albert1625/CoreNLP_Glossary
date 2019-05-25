package glossary;

import java.util.*;

public class GlossaryItem {

    private String term;
    private String definition;
    private List<String> additions = new ArrayList<>();
    private Set<String> potentialLinksTo = new HashSet<>();
    private Set<GlossaryItem> linksTo = new HashSet<>();
    private Set<GlossaryItem> linksFrom = new HashSet<>();

    public GlossaryItem() {
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public List<String> getAdditions() {
        return additions;
    }

    public void setAdditions(List<String> additions) {
        this.additions = additions;
    }

    public Set<String> getPotentialLinksTo() {
        return potentialLinksTo;
    }

    public void setPotentialLinksTo(Set<String> potentialLinksTo) {
        this.potentialLinksTo = potentialLinksTo;
    }

    public Set<GlossaryItem> getLinksTo() {
        return linksTo;
    }

    public void setLinksTo(Set<GlossaryItem> linksTo) {
        this.linksTo = linksTo;
    }

    public Set<GlossaryItem> getLinksFrom() {
        return linksFrom;
    }

    public void setLinksFrom(Set<GlossaryItem> linksFrom) {
        this.linksFrom = linksFrom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GlossaryItem that = (GlossaryItem) o;
        return Objects.equals(term, that.term) &&
                Objects.equals(definition, that.definition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(term, definition);
    }
}
