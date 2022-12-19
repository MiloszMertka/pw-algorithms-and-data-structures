package pl.edu.pw.ee;

class LongestCommonSubsequence {

    private final String topStr;
    private final String leftStr;
    
    private Cell[][] matrix;
    
    public LongestCommonSubsequence(String topStr, String leftStr){
	validateInputString(topStr);
        validateInputString(leftStr);
        
        this.topStr = topStr;
        this.leftStr = leftStr;
    }

    public String findLCS(){
        buildMatrix();
        
        StringBuilder LCS = new StringBuilder("");
        
        int i = matrix.length - 1;
        int j = matrix.length - 1;
        while (i > 0 && j > 0) {
            Cell cell = matrix[i][j];
            
            if (cell.getDir() == Direction.TOP_LEFT) {
                LCS.append(topStr.charAt(j - 1));
            }
            
            switch (cell.getDir()) {
                case LEFT:
                    j--;
                    break;
                case TOP:
                    i--;
                    break;
                case TOP_LEFT:
                    j--;
                    i--;
                    break;
            }
        }
        
	return LCS.reverse().toString();
    }

    public void display(){
	// TODO

    }
    
    private void validateInputString(String input) {
        if (input == null || input.length() == 0) {
            throw new IllegalArgumentException("input string cannot be empty");
        }
    }
    
    private void buildMatrix() {
        matrix = initializeMatrix();
        
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                Cell currentCell = matrix[i][j];
                Cell topCell = matrix[i - 1][j];
                Cell leftCell = matrix[i][j - 1];
                Cell topLeftCell = matrix[i - 1][j - 1];
                printMatrix();
                
                if (leftStr.charAt(i - 1) == topStr.charAt(j - 1)) {
                    currentCell.setNum(topLeftCell.getNum() + 1);
                    currentCell.setDir(Direction.TOP_LEFT);
                    continue;
                }
                
                if (topCell.getNum() >= leftCell.getNum()) {
                    currentCell.setNum(topCell.getNum());
                    currentCell.setDir(Direction.TOP);
                } else {
                    currentCell.setNum(leftCell.getNum());
                    currentCell.setDir(Direction.LEFT);
                }
            }
        }
    }
    
    private Cell[][] initializeMatrix() {
        matrix = new Cell[leftStr.length() + 1][topStr.length() + 1];
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = new Cell();
            }
        }
        
        return matrix;
    }
    
    private void printMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                Cell cell = matrix[i][j];
                
                System.out.print(cell.getNum() + " ");
            }
            
            System.out.println();
        }
        
        System.out.println();
    }
    
    private static class Cell {
        private int num = 0;
        private Direction dir = null;
        
        public int getNum() {
            return this.num;
        }
        
        public Direction getDir() {
            return this.dir;
        }
        
        public void setNum(int num) {
            this.num = num;
        }
        
        public void setDir(Direction dir) {
            this.dir = dir;
        }
         
    }
    
    private static enum Direction {
        TOP,
        LEFT,
        TOP_LEFT
    }

}
