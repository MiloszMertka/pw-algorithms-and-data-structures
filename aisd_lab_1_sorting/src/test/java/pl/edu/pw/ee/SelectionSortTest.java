package pl.edu.pw.ee;

import org.junit.Test;
import pl.edu.pw.ee.services.Sorting;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

public class SelectionSortTest {

    private static final double DELTA = 0;
    private final Sorting selectionSort;

    public SelectionSortTest() {
        selectionSort = new SelectionSort();
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionWhenNumsAreNull() {
        // given
        double[] nums = null;

        // when
        selectionSort.sort(nums);

        // then
        assert false;
    }

    @Test
    public void itShouldNotThrowExceptionWhenNumsAreEmpty() {
        // given
        double[] nums = new double[0];

        // when
        selectionSort.sort(nums);

        // then
        double[] expected = new double[0];
        assertArrayEquals(expected, nums, DELTA);
    }

    @Test
    public void itShouldHandleArrayWithOneElement() {
        // given
        double[] nums = {1};

        // when
        selectionSort.sort(nums);

        // then
        double[] expected = {1};
        assertArrayEquals(expected, nums, DELTA);
    }

    @Test
    public void itShouldNotChangeSortedArray() {
        // given
        double[] nums = {1, 2, 3, 4, 5};

        // when
        selectionSort.sort(nums);

        // then
        double[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, nums, DELTA);
    }

    @Test
    public void itShouldSortReversedArray() {
        // given
        double[] nums = {5, 4, 3, 2, 1};

        // when
        selectionSort.sort(nums);

        // then
        double[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, nums, DELTA);
    }

    @Test
    public void itShouldSortArrayWithExtremeValues() {
        // given
        double[] nums = {-8, 4, -3, 2.33, 0, 1, 54, 54, Double.MAX_VALUE, 14, -Double.MIN_VALUE, Double.MIN_VALUE, -Double.MAX_VALUE};

        // when
        selectionSort.sort(nums);

        // then
        double[] expected = {-Double.MAX_VALUE, -8, -3, -Double.MIN_VALUE, 0, Double.MIN_VALUE, 1, 2.33, 4, 14, 54, 54, Double.MAX_VALUE};
        assertArrayEquals(expected, nums, DELTA);
    }

    @Test
    public void itShouldSortArrayOfRandomNums() {
        // given
        long seed = 1234;
        Random random = new Random(seed);
        double[] nums = new double[100000];

        for (int i = 0; i < nums.length; i++) {
            nums[i] = random.nextDouble();
        }

        double[] expected = nums.clone();
        Arrays.sort(expected);

        // when
        selectionSort.sort(nums);

        // then
        assertArrayEquals(expected, nums, DELTA);
    }

}
