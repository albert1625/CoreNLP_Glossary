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

        glossary.exportTermsToDOT("terms");


        stopwatch.stop();
        long timeTaken = stopwatch.getTime();
        System.out.println("\nEnd!\n");
        System.out.println("Time taken: " + timeTaken);
    }

}
