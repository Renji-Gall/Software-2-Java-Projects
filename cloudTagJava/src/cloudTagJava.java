import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * This program takes an input file containing text and outputs a tag cloud in
 * an HTML file detailing every individual word in the file and the number of
 * times that word occurs in the file.
 *
 * @author Reniel Gall and Abel Tatek
 */
public final class cloudTagJava {

    private cloudTagJava() {
    }

    private static class mapEntry {

        private String key;
        private Integer value;

        mapEntry(String s, Integer n) {
            this.key = s;
            this.value = n;
        }

        public String getKey() {
            return this.key;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    private static final String SEPARATORS = " \"\t\n\r,-.!?[]'`*;:/()";

    private static final class IntegerLT implements Comparator<mapEntry> {
        @Override
        public int compare(mapEntry e1, mapEntry e2) {
            return Integer.compare(e2.getValue(), e1.getValue());
        }
    }

    private static final class StringLT implements Comparator<mapEntry> {
        @Override
        public int compare(mapEntry e1, mapEntry e2) {
            return e1.getKey().compareToIgnoreCase(e2.getKey());
        }
    }

    public static void outputHeaders(PrintWriter out, String inFile, int numWords) {
        assert out != null : "Violation of: out is not null";

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Top " + numWords + " words in " + inFile + "</title>");

        out.println(
                "<link href=\"https://cse22x1.engineering.osu.edu/2231/web-sw2/assignments/projects/tag-cloud-generator/data/tagcloud.css\" "
                        + "rel=\"stylesheet\" type=\"text/css\">");
        out.println("<link href=\"tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");

        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Top " + numWords + " words in " + inFile + "</h1>");
        out.println("<hr>");
    }

    /**
     * Processes input text, building the word frequency map.
     */
    public static void processInput(BufferedReader input, Map<String, Integer> wordCount,
            Set<Character> separators) throws IOException {
        assert input != null : "input is not null";
        assert wordCount != null : "wordCount is not null";

        String line;
        while ((line = input.readLine()) != null) {

            for (int i = 0; i < line.length();) {

                String token = nextWordOrSeparator(line, i, separators);

                if (!separators.contains(token.charAt(0))) {
                    String word = token.toLowerCase();
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }

                i += token.length();
            }
        }
    }

    /**
     * Outputs HTML tag cloud.
     */
    public static void output(PrintWriter output, Map<String, Integer> map) {
        assert output != null : "output is not null";
        assert map != null : "termsAndCounts is not null";

        int minC = Integer.MAX_VALUE;
        int maxC = Integer.MIN_VALUE;

        List<mapEntry> entrys = new ArrayList<>();

        for (Map.Entry<String, Integer> i : map.entrySet()) {

            entrys.add(new mapEntry(i.getKey(), i.getValue()));

            int c = i.getValue();

            minC = Math.min(minC, c);

            maxC = Math.max(maxC, c);

        }

        Collections.sort(entrys, new StringLT());

        output.println("<div class=\"cdiv\">");
        output.println("<p class=\"cbox\">");

        final int minF = 11;

        final int maxF = 48;

        final int range = maxF - minF;

        for (mapEntry entry : entrys) {

            String word = entry.getKey();
            int count = entry.getValue();

            int fontSize = minF;

            if (maxC > minC) {
                fontSize = minF + (count - minC) * range / (maxC - minC);
            }

            output.println("<span style=\"cursor:default\" class=\"f" + fontSize
                    + "\" title=\"count: " + count + "\">" + word + "</span>");
        }

        output.println("</p>");
        output.println("</div>");
        output.println("</body>");
        output.println("</html>");
    }

    /**
     * Fills the separator set.
     */
    private static void generateSeparatorSet(Set<Character> separators) {
        for (int i = 0; i < SEPARATORS.length(); i++) {
            char c = SEPARATORS.charAt(i);
            separators.add(c);
        }
    }

    /**
     * Returns next word or separator string.
     */
    private static String nextWordOrSeparator(String text, int pos,
            Set<Character> separator) {

        boolean isSep = separator.contains(text.charAt(pos));
        int i;

        for (i = pos; i < text.length(); i++) {
            char c = text.charAt(i);
            if (separator.contains(c) != isSep) {
                break;
            }
        }

        return text.substring(pos, i);
    }

    /**
     * Main method.
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        BufferedReader fileIn;
        PrintWriter out;

        System.out.print("Enter name of input file: ");
        String inputFile = input.nextLine();

        System.out.print("Enter name of output file: ");
        String outputFile = input.nextLine();

        try {
            fileIn = new BufferedReader(new FileReader(inputFile));
            out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
        } catch (IOException e) {
            System.err.println("Error: couldn't open file " + e);
            input.close();
            return;
        }

        int numWords = 0;
        while (numWords <= 0) {
            System.out.print("Enter number of words for tag cloud: ");
            try {
                numWords = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Invalid number.\n");
            }
        }

        try {
            outputHeaders(out, inputFile, numWords);

            Map<String, Integer> table = new HashMap<>();
            Set<Character> separators = new HashSet<>();
            generateSeparatorSet(separators);
            processInput(fileIn, table, separators);

            List<mapEntry> entries = new ArrayList<>();
            for (Map.Entry<String, Integer> e : table.entrySet()) {
                entries.add(new mapEntry(e.getKey(), e.getValue()));
            }

            Collections.sort(entries, new IntegerLT());

            Map<String, Integer> topWords = new HashMap<>();
            for (int i = 0; i < numWords && i < entries.size(); i++) {
                mapEntry e = entries.get(i);
                topWords.put(e.getKey(), e.getValue());
            }

            output(out, topWords);

        } catch (IOException e) {
            System.err.println("Error processing file.");
        }

        input.close();
        out.close();
        try {
            fileIn.close();
        } catch (IOException e) {
            System.err.println("Error closing input file.");
        }
    }
}
