import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class Main {

    private static final List<String> NOUN_TAGS = Arrays.asList("NN", "NNS", "NNP", "NNPS");

    public static String text = "boundary value analysis is\n" +
            "A black-box test technique in which test cases are designed based on boundary values.";

    public static void main(String[] args) {

        Properties props = new Properties();

        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, depparse, natlog, openie");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Annotation document = new Annotation(text);

        pipeline.annotate(document);



        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        for(CoreMap sentence: sentences) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            System.out.println("Words:");
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // only nouns
                if (NOUN_TAGS.contains(token.get(CoreAnnotations.PartOfSpeechAnnotation.class))) {
                    // this is the text of the token
                    String word = token.get(CoreAnnotations.TextAnnotation.class);
                    System.out.print(word);
                    // this is the POS tag of the token
                    String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                    System.out.println(", pos: " + pos);
                }
            }

            // this is the Stanford dependency graph of the current sentence
            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
            System.out.println("\nCompounds:");
//            System.out.println(dependencies.toPOSList());
            dependencies.typedDependencies();
            for (TypedDependency td : dependencies.typedDependencies()){
                if (td.reln().getShortName().equals("compound"))
                    System.out.println(td.dep().lemma() + " " + td.gov().lemma());
            }


            // Get the OpenIE triples for the sentence
            Collection<RelationTriple> triples =
                    sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
            // Print the triples
            System.out.println("\nTriples:");
            for (RelationTriple triple : triples) {
                System.out.println(triple.subjectLemmaGloss());
                System.out.println(triple.objectLemmaGloss());
            }
        }

        System.out.println("\nEnd!");
    }

}