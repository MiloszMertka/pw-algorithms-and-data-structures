package pl.edu.pw.ee.performance;

import pl.edu.pw.ee.HeapSort;

public class HeapSortPerformanceTest extends CommonPerformanceTest {

    private static final int MAX_DATA_SIZE = 100000;

    private static final int MIN_DATA_SIZE = 5000;

    private static final int STEP = 5000;

    public HeapSortPerformanceTest() {
        super(new HeapSort(), "heapSort.csv");
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
            double[] nums = generateAscendingData(i);
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
