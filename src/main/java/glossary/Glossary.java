package glossary;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;


import java.util.*;

public class Glossary {

    public static final List<String> NOUN_TAGS = Arrays.asList("NN", "NNS", "NNP", "NNPS");

    private Map<String, GlossaryItem> terms = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public Glossary() {
    }

    public Map<String, GlossaryItem> getTerms() {
        return terms;
    }

    public void setTerms(Map<String, GlossaryItem> terms) {
        this.terms = terms;
    }

    public void addPotentialLinks(){
        Properties props = new Properties();

        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, depparse, natlog, openie");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        //iterate glossary and add potential links to terms
        for (Map.Entry<String, GlossaryItem> entry : this.getTerms().entrySet()) {

            String text = entry.getValue().getDefinition()+ " is " + entry.getValue().getDefinition();
            Set<String> potentialLinks = new HashSet<>();

            Annotation document = new Annotation(text);
            pipeline.annotate(document);

            List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
            for(CoreMap sentence: sentences) {
                // traversing the words in the current sentence
                // a CoreLabel is a CoreMap with additional token-specific methods
                for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                    // only nouns
                    if (NOUN_TAGS.contains(token.get(CoreAnnotations.PartOfSpeechAnnotation.class))) {
                        // this is the text of the token
                        potentialLinks.add(token.lemma());
                    }
                }

                // this is the Stanford dependency graph of the current sentence
                SemanticGraph dependencies = sentence
                        .get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
                dependencies.typedDependencies();
                for (TypedDependency td : dependencies.typedDependencies()){
                    if (td.reln().getShortName().equals("compound") || td.reln().getShortName().equals("amod")) {
                        potentialLinks.add(td.dep().lemma() + " " + td.gov().lemma());
                    }
                }


                // Get the OpenIE triples for the sentence
                Collection<RelationTriple> triples =
                        sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
                // Print the triples
                for (RelationTriple triple : triples) {
                    potentialLinks.add(triple.subjectLemmaGloss());
                    potentialLinks.add(triple.objectLemmaGloss());
                }
            }
            entry.getValue().setPotentialLinksTo(potentialLinks);
            System.out.println("done: " + entry.getKey());
        }
    }

    public void addLinksTo(){

        for (Map.Entry<String, GlossaryItem> entry : this.getTerms().entrySet()) {
            for (String pl : entry.getValue().getPotentialLinksTo()){
                if (this.getTerms().containsKey(pl))
                    entry.getValue().getLinksTo().add(this.getTerms().get(pl));
            }
        }
    }

    public void addLinksFrom(){

        for (Map.Entry<String, GlossaryItem> entry : this.getTerms().entrySet()) {
            for (GlossaryItem lt : entry.getValue().getLinksTo()){
                lt.getLinksFrom().add(entry.getValue());
            }
        }
    }
}
