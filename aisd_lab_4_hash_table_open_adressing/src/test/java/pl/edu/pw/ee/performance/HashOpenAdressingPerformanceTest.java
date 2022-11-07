package pl.edu.pw.ee.performance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.ee.HashLinearProbing;
import pl.edu.pw.ee.services.HashTable;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class HashOpenAdressingPerformanceTest {

    private static final String INPUT_FILE = "wordlist.txt";
    private static final String OUTPUT_FILE = "results.txt";

    private final List<String> words = new ArrayList<>();
    private final int resultsCount = 30;
    private final long[] results = new long[resultsCount];
    private final int[] sizes = {512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144};
    private final double[] finalResults = new double[sizes.length];
    private HashTable<String> hashTable;

    @Before
    public void readInputFile() throws IOException {
        File file = new File(INPUT_FILE);

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
            runPerformanceTestForGivenSize(sizes[i]);
            finalResults[i] = calculateAverageResult();
        }
    }

    @After
    public void writeOutputFile() throws IOException {
        File file = new File(OUTPUT_FILE);

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
            hashTable.put(word);
        }
    }

    private void runPerformanceTestForGivenSize(int size) {
        for (int i = 0; i < resultsCount; i++) {
            hashTable = new HashLinearProbing<>(size);
            long startTime = System.currentTimeMillis();
            addWordsToHashTable();
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            results[i] = elapsedTime;
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
