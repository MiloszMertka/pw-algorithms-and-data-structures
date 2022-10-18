package pl.edu.pw.ee.performance;

import pl.edu.pw.ee.SelectionSort;

public class SelectionSortPerformanceTest extends CommonPerformanceTest {

    private static final int MAX_DATA_SIZE = 100000;

    private static final int MIN_DATA_SIZE = 5000;

    private static final int STEP = 5000;

    public SelectionSortPerformanceTest() {
        super(new SelectionSort(), "selectionSort.csv");
        setDataSetsSizes(MIN_DATA_SIZE, MAX_DATA_SIZE, STEP);
    }

    @Override
    protected void testSortPerformanceForPessimisticData() {
        for (int i = MIN_DATA_SIZE; i <= MAX_DATA_SIZE; i += STEP) {
            double[] nums = generateRandomData(i);
            double result = testSortPerformance(nums);
            pessimisticDataResults.add(result);
        }
    }

    @Override
    protected void testSortPerformanceForOptimisticData() {
        for (int i = MIN_DATA_SIZE; i <= MAX_DATA_SIZE; i += STEP) {
            double[] nums = generateRandomData(i);
            double result = testSortPerformance(nums);
            optimisticDataResults.add(result);
        }
    }

    @Override
    protected void testSortPerformanceForRandomData() {
        for (int i = MIN_DATA_SIZE; i <= MAX_DATA_SIZE; i += STEP) {
            double[] nums = generateRandomData(i);
            double result = testSortPerformance(nums);
            randomDataResults.add(result);
        }
    }

}
