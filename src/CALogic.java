public class CALogic {
    protected int matrix[][];
    protected int rows;
    protected int cols;
    protected int binaryString;

    public CALogic(int rows, int cols, int binaryString){
        this.rows = rows;
        this.cols = cols;
        this.binaryString = binaryString;
        matrix = new int[this.rows][this.cols];
        matrix[0][cols/2] = 1;
    }

    public void setRule(int binaryString) {
        String stringified = Integer.toString(binaryString);
        int strIndex = 0;

        // Ensure that the loop doesn't go out of bounds
        for (int i = Math.max(0, cols / 2 - 3); i < Math.min(cols, cols / 2 - 3 + stringified.length()); i++) {
            matrix[1][i] = Character.getNumericValue(stringified.charAt(strIndex));
            strIndex++;
        }
    }

    public int calculateNextGen (int x, int y, int z){
        int nextGen = 0;
        if(x == 0 && y == 0 && z == 0){
            nextGen= 0;
        }
        if(x == 0 && y == 1 && z == 0){
            nextGen= 0;
        }
        if(x == 1 && y == 0 && z == 1){
            nextGen= 0;
        }
        if(x == 1 && y == 1 && z == 1){
            nextGen= 0;
        }
        if(x == 0 && y == 0 && z == 1){
            nextGen= 1;
        }
        if(x == 0 && y == 1 && z == 1){
            nextGen= 1;
        }
        if(x == 1 && y == 0 && z == 0){
            nextGen= 1;
        }
        if(x == 1 && y == 1 && z == 0){
            nextGen= 1;
        }
        return nextGen;
    }

    public static int getDecimal(int binary){
        int decimalNumber = Integer.parseInt(Integer.toString(binary), 2);
        return decimalNumber;
    }



    public int [][] run(){
        int x; int y; int z;

        for(int i = 0; i < this.rows - 1; i++){
            for(int j = 0; j < this.cols-2; j++){

                x = this.matrix[i][j];
                y = this.matrix[i][j+1];
                z = this.matrix[i][j+2];

                int nextGen = this.calculateNextGen(x, y, z);
                matrix[i+1][j+1] = nextGen;
            }
        }
        return this.matrix;
    }

    public static void main(String[] args) {
        CALogic caLogic = new CALogic(10, 10, 00001010);
        System.out.println(caLogic.getDecimal(11111010));
    }
}
