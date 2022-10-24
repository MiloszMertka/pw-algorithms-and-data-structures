package pl.edu.pw.ee.performance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.ee.HashListChaining;
import pl.edu.pw.ee.services.HashTable;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class HashListChainingPerformanceTest {

    private static final String inputFile = "wordlist.txt";
    private static final String outputFile = "results.txt";

    private HashTable<String> hashTable;
    private final List<String> words = new ArrayList<>();
    private final int resultsCount = 30;
    private final long[] results = new long[resultsCount];
    private final int[] sizes = {4096, 8192, 16384, 32768, 65536, 131072, 262144, 4093, 8191, 16381, 32771, 65537, 131071, 262147};
    private final double[] finalResults = new double[sizes.length];

    @Before
    public void readInputFile() throws IOException {
        File file = new File(inputFile);

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNext()) {
                words.add(reader.next());
            }
        } catch (IOException exception) {
            throw new IOException("cannot read input file");
        }
    }

    @Test
    public void performanceTest() {
        for (int i = 0; i < sizes.length; i++) {
            hashTable = new HashListChaining<>(sizes[i]);
            addWordsToHashTable();
            runPerformanceTestForGivenSize();
            finalResults[i] = calculateAverageResult();
        }
    }

    @After
    public void writeOutputFile() throws IOException {
        File file = new File(outputFile);

        try (PrintWriter writer = new PrintWriter(file)) {
            for (int i = 0; i < sizes.length; i++) {
                writer.println(sizes[i] + " " + finalResults[i]);
            }
        } catch (IOException exception) {
            throw new IOException("cannot write output file");
        }
    }

    private void addWordsToHashTable() {
        for (String word : words) {
            hashTable.add(word);
        }
    }

    private void runPerformanceTestForGivenSize() {
        for (int i = 0; i < resultsCount; i++) {
            long startTime = System.currentTimeMillis();
            findWordsInHashTable();
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            results[i] = elapsedTime;
        }
    }

    private void findWordsInHashTable() {
        for (String word : words) {
            hashTable.get(word);
        }
    }

    private double calculateAverageResult() {
        Arrays.sort(results);

        double sum = 0;
        for (int i = 10; i < resultsCount - 10; i++) {
            sum += results[i];
        }

        return sum / 10;
    }

}
