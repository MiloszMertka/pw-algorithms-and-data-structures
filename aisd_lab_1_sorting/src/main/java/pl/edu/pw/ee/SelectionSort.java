package pl.edu.pw.ee;

import pl.edu.pw.ee.services.Sorting;

public class SelectionSort implements Sorting {

    @Override
    public void sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("nums cannot be null");
        }

        selectionSort(nums);
    }

    private void selectionSort(double[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            int minValueIndex = findMinValueIndex(nums, i);
            swap(nums, minValueIndex, i);
        }
    }

    private int findMinValueIndex(double[] nums, int startIndex) {
        int minValueIndex = startIndex;
        for (int j = startIndex + 1; j < nums.length; j++) {
            if (nums[minValueIndex] > nums[j]) {
                minValueIndex = j;
            }
        }

        return minValueIndex;
    }

    private void swap(double[] nums, int firstIndex, int secondIndex) {
        if (firstIndex != secondIndex) {
            double tmp = nums[firstIndex];
            nums[firstIndex] = nums[secondIndex];
            nums[secondIndex] = tmp;
        }
    }

}
