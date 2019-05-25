package glossary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GlossaryItem {

    private String definition;
    private List<String> additions = new ArrayList<>();
    private Set<String> potentialLinks = new HashSet<>();

    public GlossaryItem() {
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

    public Set<String> getPotentialLinks() {
        return potentialLinks;
    }

    public void setPotentialLinks(Set<String> potentialLinks) {
        this.potentialLinks = potentialLinks;
    }
}
