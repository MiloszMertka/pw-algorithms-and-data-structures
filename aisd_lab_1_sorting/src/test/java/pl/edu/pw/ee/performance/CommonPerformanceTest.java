package pl.edu.pw.ee.performance;

import org.junit.Test;
import pl.edu.pw.ee.services.Sorting;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class CommonPerformanceTest {

    private static final Long SEED = 1234L;

    private static final char DELIMITER = ';';

    private static final String OUTPUT_FILE_ROOT_PATH = "performanceTestsResults/";

    protected static int REPEAT_TEST_TIMES = 10;

    protected final Sorting sorter;
    protected final String outputFilePath;
    protected List<Double> pessimisticDataResults = new ArrayList<>();
    protected List<Double> optimisticDataResults = new ArrayList<>();
    protected List<Double> randomDataResults = new ArrayList<>();
    protected List<Integer> dataSetsSizes = new ArrayList<>();

    protected CommonPerformanceTest(Sorting sorter, String outputFilePath) {
        this.sorter = sorter;
        this.outputFilePath = OUTPUT_FILE_ROOT_PATH + outputFilePath;
    }

    protected abstract void testSortPerformanceForPessimisticData();

    protected abstract void testSortPerformanceForOptimisticData();

    protected abstract void testSortPerformanceForRandomData();

    @Test
    public void testSortPerformanceAndWriteResultsToCsvFile() throws IOException {
        System.out.println("Running tests for pessimistic data");
        testSortPerformanceForPessimisticData();

        System.out.println("Running tests for optimistic data");
        testSortPerformanceForOptimisticData();

        System.out.println("Running tests for random data");
        testSortPerformanceForRandomData();

        saveTestsResultsToCsvFile();
    }

    protected void setDataSetsSizes(int minSize, int maxSize, int step) {
        for (int i = minSize; i <= maxSize; i += step) {
            dataSetsSizes.add(i);
        }
    }

    protected double testSortPerformance(double[] nums) {
        List<Long> measuredTimes = new ArrayList<>(REPEAT_TEST_TIMES);

        for (int i = 0; i < REPEAT_TEST_TIMES; i++) {
            long startTime = System.currentTimeMillis();
            sorter.sort(nums);
            long finishTime = System.currentTimeMillis();
            long elapsedTime = finishTime - startTime;
            measuredTimes.add(elapsedTime);
        }

        return calculateAverageValue(measuredTimes);
    }

    protected double[] generateRandomData(int size) {
        checkIfSizeIsGreaterThanZero(size);

        Random random = new Random(SEED);

        double[] nums = new double[size];
        for (int i = 0; i < size; i++) {
            nums[i] = random.nextDouble();
        }

        return nums;
    }

    protected double[] generateAscendingData(int size) {
        checkIfSizeIsGreaterThanZero(size);

        double[] nums = new double[size];
        for (int i = 0; i < size; i++) {
            nums[i] = i;
        }

        return nums;
    }

    protected double[] generateDescendingData(int size) {
        checkIfSizeIsGreaterThanZero(size);

        double[] nums = new double[size];
        for (int i = 0; i < size; i++) {
            nums[i] = size - 1 - i;
        }

        return nums;
    }

    private void saveTestsResultsToCsvFile() throws IOException {
        String dataSetsSizesCsvString = convertListToCsvString(dataSetsSizes, DELIMITER);
        String pessimisticDataResultsCsvString = convertListToCsvString(pessimisticDataResults, DELIMITER);
        String optimisticDataResultsCsvString = convertListToCsvString(optimisticDataResults, DELIMITER);
        String randomDataResultsCsvString = convertListToCsvString(randomDataResults, DELIMITER);

        File outputFile = new File(outputFilePath);

        try (PrintWriter printWriter = new PrintWriter(outputFile)) {
            outputFile.createNewFile();
            printWriter.println(dataSetsSizesCsvString);
            printWriter.println(pessimisticDataResultsCsvString);
            printWriter.println(optimisticDataResultsCsvString);
            printWriter.println(randomDataResultsCsvString);
        } catch (IOException ioException) {
            throw new IOException("failed to write tests results" + "\n" + ioException.getMessage());
        }
    }

    private <T> String convertListToCsvString(List<T> list, char delimiter) {
        StringBuilder csvString = new StringBuilder();

        for (T item : list) {
            csvString.append(item).append(delimiter);
        }

        removeLastDelimiter(csvString, delimiter);

        return csvString.toString();
    }

    private void removeLastDelimiter(StringBuilder csvString, char delimiter) {
        if (csvString.length() > 0 && csvString.charAt(csvString.length() - 1) == delimiter) {
            csvString.deleteCharAt(csvString.length() - 1);
        }
    }

    private double calculateAverageValue(List<Long> values) {
        Long sum = 0L;
        for (Long value : values) {
            sum += value;
        }

        return (double) sum / values.size();
    }

    private void checkIfSizeIsGreaterThanZero(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size cannot be less or equal 0");
        }
    }

}
