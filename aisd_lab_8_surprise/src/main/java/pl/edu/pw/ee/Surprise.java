package pl.edu.pw.ee;

public class Surprise {
    
    private static final int NUM_OF_DICE_WALLS = 6;
    private static final int MAX_FIELDS_ARRAY_SIZE = 99999;
    
    public int countMaxSum(int[] fields) {
        validateFieldsArrayNotNull(fields);
        validateFieldsArraySize(fields);
        
        int sum = fields[0];
        int jump = 1;
        
        for (int i = 1; i < fields.length; i++) {
            if (fields[i] < 0) {
                if (jump == NUM_OF_DICE_WALLS - 1) {
                    int from = i - jump + 1;
                    int to = i;
                    int maxValueIndex = findMaxValueIndexInRange(fields, from, to);
                    
                    jump = 1;
                    
                    sum += fields[maxValueIndex];   
                }
                
                jump++;
                continue;
            }
            
            jump = 1;
            sum += fields[i];
        }
        
        if (jump != 1) {
            sum += fields[fields.length - 1];
        }
        
        return sum;
    }
    
    private int findMaxValueIndexInRange(int[] fields, int from, int to) {
        int maxIndex = from;
        for (int i = from + 1; i <= to && i < fields.length; i++) {
            if (fields[i] >= fields[maxIndex]) {
                maxIndex = i;
            }
        }
        
        return maxIndex;
    }
    
    private void validateFieldsArrayNotNull(int fields[]) {
        if (fields == null) {
            throw new IllegalArgumentException("fields cannot be null!");
        }
    }
    
    private void validateFieldsArraySize(int fields[]) {
        if (fields.length > MAX_FIELDS_ARRAY_SIZE) {
            throw new IllegalArgumentException("fields size cannot be greater than " + MAX_FIELDS_ARRAY_SIZE);
        }
    }
    
}
