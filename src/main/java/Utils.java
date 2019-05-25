import glossary.Glossary;
import glossary.GlossaryItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Utils {

    public Utils() {
    }

    public Glossary createFilledGlossary(String resourceName) throws FileNotFoundException {

        Glossary glossary = new Glossary();
        File file = getFileFromResource(resourceName);

        //reading file / filling glossary
        Scanner scanner = new Scanner(file);
        String term = null;
        GlossaryItem glossaryItem = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("@")) {
                term = line.substring(1);
                glossaryItem = new GlossaryItem();
            }
            else if (line.startsWith("Synonyms:") || line.startsWith("See Also:")){
                List<String> additions = new ArrayList<>(
                        Arrays.asList(line.substring(10).split(", "))
                );
                assert glossaryItem != null;
                glossaryItem.getAdditions().addAll(additions);
            }
            else if (line.equals("")){
                glossary.getTerms().put(term, glossaryItem);
            }
            else if (!line.startsWith("Ref:")){
                assert glossaryItem != null;
                glossaryItem.setDefinition(line);
            }

        }

        return glossary;
    }

    public File getFileFromResource(String resourceName){
        URL resource = getClass().getClassLoader().getResource(resourceName);
        File file;
        if (resource != null)
            file = new File(resource.getFile());
        else
            throw new IllegalArgumentException("Resource not found");

        return file;
    }

}
