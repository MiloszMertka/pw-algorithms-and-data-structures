package pl.edu.pw.ee;

class LongestCommonSubsequence {

    private final String topStr;
    private final String leftStr;

    private Cell[][] matrix;

    public LongestCommonSubsequence(String topStr, String leftStr) {
        validateInputString(topStr);
        validateInputString(leftStr);

        this.topStr = topStr;
        this.leftStr = leftStr;
    }

    public String findLCS() {
        buildMatrixIfNull();

        return constructLCSFromMatrix();
    }

    public void display() {
        findLCS();
        printMatrix();
    }

    private void validateInputString(String input) {
        if (input == null || input.length() == 0) {
            throw new IllegalArgumentException("input string cannot be empty");
        }
    }

    private void buildMatrixIfNull() {
        if (matrix == null) {
            buildMatrix();
        }
    }

    private void buildMatrix() {
        matrix = initializeMatrix();

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                fillCellAtGivenPosition(i, j);
            }
        }
    }

    private Cell[][] initializeMatrix() {
        matrix = new Cell[leftStr.length() + 1][topStr.length() + 1];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = new Cell();
            }
        }

        return matrix;
    }

    private void fillCellAtGivenPosition(int row, int column) {
        Cell currentCell = matrix[row][column];
        Cell topCell = matrix[row - 1][column];
        Cell leftCell = matrix[row][column - 1];
        Cell topLeftCell = matrix[row - 1][column - 1];

        if (leftStr.charAt(row - 1) == topStr.charAt(column - 1)) {
            currentCell.setNum(topLeftCell.getNum() + 1);
            currentCell.setDir(Direction.TOP_LEFT);
            return;
        }

        if (topCell.getNum() >= leftCell.getNum()) {
            currentCell.setNum(topCell.getNum());
            currentCell.setDir(Direction.TOP);
        } else {
            currentCell.setNum(leftCell.getNum());
            currentCell.setDir(Direction.LEFT);
        }
    }

    private String constructLCSFromMatrix() {
        StringBuilder LCS = new StringBuilder();

        int i = matrix.length - 1;
        int j = matrix[i].length - 1;
        while (i > 0 && j > 0) {
            Cell cell = matrix[i][j];
            cell.setChecked(true);

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

    private void printMatrix() {
        String tempTopString = "  " + escapeString(topStr);
        String tempLeftString = " " + escapeString(leftStr);

        int specialCharactersInTopString = countSpecialCharactersInString(tempTopString);
        int columnsCount = tempTopString.length() - specialCharactersInTopString;

        printRows(columnsCount, tempTopString, tempLeftString);
    }

    private String escapeString(String str) {
        return str.replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("'", "\\'")
                .replace("\"", "\\\"");
    }

    private int countSpecialCharactersInString(String str) {
        int specialCharacters = 0;
        for (char character : str.toCharArray()) {
            if (character == '\\') {
                specialCharacters++;
            }
        }

        return specialCharacters;
    }

    private void printRows(int columnsCount, String topString, String leftString) {
        printTopStringRow(columnsCount, topString);

        for (int i = 0; i < matrix.length; i++) {
            printBorderRow(columnsCount);
            printTopCellPartRow(matrix[i]);

            for (int j = -1; j < matrix[i].length; j++) {
                if (j == -1) {
                    char character = leftString.charAt(i);

                    if (character == '\\') {
                        i++;
                        System.out.print("|  " + character + leftString.charAt(i) + "   ");
                    } else {
                        System.out.print("|   " + character + "   ");
                    }

                    continue;
                }

                Cell cell = matrix[i][j];

                if (cell.isChecked() && cell.getDir() == Direction.LEFT) {
                    System.out.print("| < " + cell.getNum() + "   ");
                } else {
                    System.out.print("|   " + cell.getNum() + "   ");
                }
            }

            System.out.println("|");

            printBottomCellPartRow(columnsCount);
        }

        printBorderRow(columnsCount);
    }

    private void printTopStringRow(int columnsCount, String topString) {
        printBorderRow(columnsCount);
        printTopCellPartRow(columnsCount);
        printMiddleCellPartRow(topString);
        printBottomCellPartRow(columnsCount);
    }

    private void printBorderRow(int columnsCount) {
        for (int i = 0; i < columnsCount; i++) {
            System.out.print("+-------");
        }

        System.out.println("+");
    }

    private void printTopCellPartRow(int columnsCount) {
        for (int i = 0; i < columnsCount; i++) {
            System.out.print("|       ");
        }

        System.out.println("|");
    }

    private void printTopCellPartRow(Cell[] row) {
        for (int i = -1; i < row.length; i++) {
            if (i >= 0) {
                Cell cell = row[i];

                if (cell.isChecked()) {
                    if (cell.getDir() == Direction.TOP) {
                        System.out.print("|   ^   ");
                        continue;
                    } else if (cell.getDir() == Direction.TOP_LEFT) {
                        System.out.print("| \\     ");
                        continue;
                    }
                }
            }

            System.out.print("|       ");
        }

        System.out.println("|");
    }

    private void printMiddleCellPartRow(String str) {
        for (int i = 0; i < str.length(); i++) {
            char character = str.charAt(i);

            if (character == '\\') {
                i++;
                System.out.print("|  " + character + str.charAt(i) + "   ");
            } else {
                System.out.print("|   " + character + "   ");
            }
        }

        System.out.println("|");
    }

    private void printBottomCellPartRow(int columnsCount) {
        for (int i = 0; i < columnsCount; i++) {
            System.out.print("|       ");
        }

        System.out.println("|");
    }

    private enum Direction {
        TOP,
        LEFT,
        TOP_LEFT
    }

    private static class Cell {

        private int num = 0;
        private Direction dir = null;
        private boolean checked = false;

        public int getNum() {
            return this.num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public Direction getDir() {
            return this.dir;
        }

        public void setDir(Direction dir) {
            this.dir = dir;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }

}
