/**
 * Cellular Automaton Logic class for generating and updating a 1D cellular automaton.
 */
public class CALogic {

    /**
     * The 2D matrix representing the cellular automaton.
     */
    protected int matrix[][];

    /**
     * The number of rows in the cellular automaton matrix.
     */
    protected int rows;

    /**
     * The number of columns in the cellular automaton matrix.
     */
    protected int cols;

    /**
     * The binary rule used for updating the cellular automaton.
     */
    protected int binaryString;

    /**
     * Constructs a Cellular Automaton Logic instance.
     *
     * @param rows         The number of rows in the cellular automaton matrix.
     * @param cols         The number of columns in the cellular automaton matrix.
     * @param binaryString The binary rule used for updating the cellular automaton.
     */
    public CALogic(int rows, int cols, int binaryString) {
        this.rows = rows;
        this.cols = cols;
        this.binaryString = binaryString;
        matrix = new int[this.rows][this.cols];
        matrix[0][cols / 2] = 1;
    }

    /**
     * Sets the binary rule for updating the cellular automaton.
     *
     * @param binaryString The binary rule to set.
     */
    public void setRule(int binaryString) {
        String stringified = Integer.toString(binaryString);
        int strIndex = 0;

        for (int i = Math.max(0, cols / 2 - 3); i < Math.min(cols, cols / 2 - 3 + stringified.length()); i++) {
            matrix[1][i] = Character.getNumericValue(stringified.charAt(strIndex));
            strIndex++;
        }


    }

    /**
     * Calculates the next generation value based on the given neighborhood.
     *
     * @param x The left cell value.
     * @param y The center cell value.
     * @param z The right cell value.
     * @return The next generation value.
     */
    public int calculateNextGen(int x, int y, int z) {
        int nextGen = 0;

        // Cell state transition rules
        if (x == 0 && y == 0 && z == 0) {
            nextGen = 0;
        } else if (x == 0 && y == 1 && z == 0) {
            nextGen = 0;
        } else if (x == 1 && y == 0 && z == 1) {
            nextGen = 0;
        } else if (x == 1 && y == 1 && z == 1) {
            nextGen = 0;
        } else if (x == 0 && y == 0 && z == 1) {
            nextGen = 1;
        } else if (x == 0 && y == 1 && z == 1) {
            nextGen = 1;
        } else if (x == 1 && y == 0 && z == 0) {
            nextGen = 1;
        } else if (x == 1 && y == 1 && z == 0) {
            nextGen = 1;
        }

        return nextGen;
    }

    /**
     * Converts a binary number to its decimal equivalent.
     *
     * @param binary The binary number to convert.
     * @return The decimal equivalent of the binary number.
     */
    public static int getDecimal(int binary) {
        int decimalNumber = Integer.parseInt(Integer.toString(binary), 2);
        return decimalNumber;
    }

    /**
     * Runs the cellular automaton simulation for the specified number of rows.
     *
     * @return The final state of the cellular automaton matrix.
     */
    public int[][] run() {
        int x, y, z;

        for (int i = 0; i < this.rows - 1; i++) {
            for (int j = 0; j < this.cols - 2; j++) {
                x = this.matrix[i][j];
                y = this.matrix[i][j + 1];
                z = this.matrix[i][j + 2];

                int nextGen = this.calculateNextGen(x, y, z);
                matrix[i + 1][j + 1] = nextGen;
            }
        }
        return this.matrix;
    }
}
