
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class WordcountOpti {

    /**
     * public static void main(String[] args) {

        String text = "Hello World, Bye World! Hello hadoop, Goodbye to hadoop";

        countAndDisplayWords(text);

    }
     * @param text
     */

    private static void countAndDisplayWords(String text) {

        Map<String, Integer> counter = new TreeMap<String, Integer>();

        // Compter chaque mot
        StringTokenizer stringTokenizer = new StringTokenizer(text, " ");
        while (stringTokenizer.hasMoreElements()) {
            String word = stringTokenizer.nextToken();

            Integer currentCount = counter.get(word);
            if (currentCount == null) {
                currentCount = 0;
            }

            ++currentCount;
            counter.put(word, currentCount);
        }

        // Obtenir la list en ordre
        for (Entry<String, Integer> entry : counter.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            /**
             *  one.set(entry.getValue().intValue());
				word.set(entry.getKey());
				sortie.write(word, one);
             */
        }
        

    }
}