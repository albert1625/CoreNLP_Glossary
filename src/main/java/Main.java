import glossary.Glossary;
import glossary.GlossaryItem;
import org.apache.commons.lang3.time.StopWatch;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        //taking execution time
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();

        Utils utils = new Utils();

        Glossary glossary = utils.createFilledGlossary("ISTQB_glossary.txt");

        glossary.addPotentialLinks();
        glossary.addLinksTo();
        glossary.addLinksFrom();




        File outputFile = new File("output.txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(outputFile));
        for (Map.Entry<String, GlossaryItem> entry : glossary.getTerms().entrySet()) {
            output.write("@" + entry.getKey() + "\n");
            output.write("#Links To\n");
            for (GlossaryItem lt : entry.getValue().getLinksTo())
                output.write(lt.getTerm() + "\n");
            output.write("#Links From\n");
            for (GlossaryItem lf : entry.getValue().getLinksFrom())
                output.write(lf.getTerm() + "\n");
            output.write("#Potential Links To\n");
            for (String pl : entry.getValue().getPotentialLinksTo())
                output.write(pl + "\n");
            output.write("\n");
        }
        output.close();


        stopwatch.stop();
        long timeTaken = stopwatch.getTime();
        System.out.println("\nEnd!\n");
        System.out.println("Time taken: " + timeTaken);
    }

}
