package cop2513.breakout;

/**
 * Various useful constants for Breakout.
 * @author Phil Ventura, Ph.D. (pventura@usf.edu)
 */
public interface Constants {
    /**
     * Number of pixels around the block that is empty.
     */
    public static final int BLOCK_BORDER = 1;

    /**
     * Width of all blocks.
     */
    public static final int BLOCK_WIDTH = 76;

    /**
     * Height of all blocks.
     */
    public static final int BLOCK_HEIGHT = 38;

    /**
     * The number of rows of blocks.
     */
    public static final int NUM_ROWS = 5;

    /**
     * The number of columns on the screen.
     */
    public static final int NUM_COLS = 10;

    /**
     * Width of the scene.
     */
    public static final int SCENE_WIDTH = (BLOCK_WIDTH + BLOCK_BORDER * 2) * NUM_COLS;

    /**
     * Height of the scene.
     */
    public static final int SCENE_HEIGHT = 600;

    /**
     * The height of a row.
     */
    public static final int ROW_HEIGHT = BLOCK_HEIGHT + BLOCK_BORDER * 2;

    /**
     * The width of a column.
     */
    public static final int COL_WIDTH = BLOCK_WIDTH + BLOCK_BORDER * 2;

    /**
     * Number of blank rows above the blocks.
     */
    public static final int TOP_BLANK_ROWS = 2;

    /**
     * Number of blank cols on the left and right between the sides of screen and the blocks.
     */
    public static final int SIDE_BLANK_COLS = 1;

    /**
     * Number of pixels taken up by the top blank rows.
     */
    public static final int VERTICAL_GUTTER = TOP_BLANK_ROWS * ROW_HEIGHT;

    /**
     * Number of pixels used by one of the side blank columns.
     */
    public static final int HORIZONTAL_GUTTER = SIDE_BLANK_COLS * COL_WIDTH;

    public static final int WIN_LOSE_WIDTH = SCENE_WIDTH / 2; // Win/Lose image width.

    public static final int WIN_HEIGHT = SCENE_HEIGHT / 2; // Win image height.

    public static final int LOSE_HEIGHT = SCENE_HEIGHT / 3; // Lose image height.

    public static final double LOSE_ADJUSTER = 1.5; // Helps put the image below the blocks.

    public static final int BLOCK_LIMIT = 42; // Limiter for the for loop.

    public static final int BLOCK_START = 1; // Initializer for the for loop.

    public static final int SECOND_ROW = 9; // This block starts the second row.

    public static final int SECOND_HELPER = 8; // Resets the multiplier for the 2nd row.

    public static final int ROW_SETTER2 = 3; // Sets the 2nd row Y positioning 3 times the block height.

    public static final int END_OF_SECOND = 16; // Last block of the second row.

    public static final int THIRD_ROW = 18; // This block starts the third row.

    public static final int THIRD_HELPER = 17; // Resets the multiplier for the 3rd row.

    public static final int ROW_SETTER3 = 4; // Sets the 3rd row Y positioning 4 times the block height.

    public static final int END_OF_THIRD = 25; // Last block of the 3rd row.

    public static final int FOURTH_ROW = 26; // First block of the 4th row.

    public static final int FOURTH_HELPER = 25; // Resets the multiplier for the 4th row.

    public static final int ROW_SETTER4 = 5; // Sets the 4th row Y positioning 5 times the block height.

    public static final int END_OF_FOURTH = 33; // Last block in the 4th row.

    public static final int FIFTH_ROW = 34; // First block in the 5th row.

    public static final int FIFTH_HELPER = 33; // Resets the multiplier for the 5th row.

    public static final int ROW_SETTER5 = 6; // Sets the 5th row Y positioning 6 times the block height.

    public static final int END_OF_FIFTH = 41; // Last block in the 5th row.

    public static final int VELOCITY_SCALER = 5; // Rescales the velocity of the ball so it does not go too fast.

} // end Constants
