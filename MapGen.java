/**
 *  Generate and modify a matrix of bricks for the gameplay loop.
 */
public class MapGen {
    public boolean[][] brickMap;
    public int bricksWidth;
    public int bricksHeight;

    /**
     *  generate the structure of the playing field.
     * @param row number of rows of bricks
     * @param col number of columns of bricks
     */
    public MapGen(int row, int col) {
        brickMap = new boolean[col][row]; // Initialize the brick array
        for (int i = 0; i < brickMap.length; i++) {
            for (int j = 0; j < brickMap[i].length; j++) {
                brickMap[i][j] = true; // All bricks are initially present
            }
        }
    }

    /** 
     * Control the visibility of blocks, make them disappear when hit by the ball.
     * @param value true or false: true being the block should become visible.
     * @param row row index of the specific block in the brickMap matrix.
     * @param col column index of the specific block in the brickMap matrix.
     */
    public void setBricksValue(boolean value, int row, int col) {
        brickMap[row][col] = value;

    }
}
