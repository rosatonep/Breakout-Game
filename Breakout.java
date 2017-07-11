package cop2513.breakout;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.jvm.hotspot.runtime.BasicLock;

import java.util.ArrayList;
import java.util.Random;

import static cop2513.breakout.Constants.*;

/**
 * Class for the Breakout game.
 */
public class Breakout {

    /**
     * The root of the scene graph that holds the board of the game.
     */
    private static Pane _root;

    /**
     * The ball.
     */
    private Ball _ball;

    /**
     * Whether to draw the grid lines.
     */
    private boolean _drawGridLines;

    /**
     * Create a new Breakout game.
     *
     * @param drawGridLines if true then draw the grid lines,
     * otherwise do not.
     */
    private ArrayList<Block> _blocks; // Creates an Array list.

    private Timeline _timeline; // Creates a timeline.

    private double _delayMs = 10; // Delay for the timeline.

    private boolean _bounceBottom = false; // If true, the ball will bounce off the bottom and the game won't end.

    private boolean _testTop = false; // If true, the ball's starting position will be above the blocks.

    private double mx; // Variable to store the mouse's X position.

    public Breakout(boolean drawGridLines) {
        // TODO: your implementation here
        _root = new Pane(); // Creates the root that will be used for the entire scene.

        Paddle paddle = new Paddle(_root); // Creates a paddle.

        paddle.setX((SCENE_WIDTH / 2) - paddle.getHalfWidth()); // Sets X position of the paddle.
        paddle.setY(SCENE_HEIGHT - paddle.getHeight()); // Sets Y position of the paddle.

       _blocks = new ArrayList<>(); // Makes an array list.

        for (int i = BLOCK_START; i < BLOCK_LIMIT; i++) { // For loop to create all 40 blocks and position them as desired.
            Block block = new Block (_root, blockString(), BLOCK_WIDTH, BLOCK_HEIGHT); // Creates the block.
            _blocks.add(block); // Adds the block to the array list.
            block.setX(BLOCK_WIDTH * i + BLOCK_BORDER); // // Sets X position of the block.
            block.setY(BLOCK_WIDTH); // Sets Y position of the block.
            if (i >= SECOND_ROW && i <= END_OF_SECOND) { // Creates the second row of blocks.
                block.setX(BLOCK_WIDTH * (i - SECOND_HELPER) + BLOCK_BORDER);
                block.setY(BLOCK_HEIGHT * ROW_SETTER2);
            }
            else if (i >= THIRD_ROW && i <= END_OF_THIRD) { // Creates the third row of blocks.
                block.setX(BLOCK_WIDTH * (i - THIRD_HELPER) + BLOCK_BORDER);
                block.setY(BLOCK_HEIGHT * ROW_SETTER3);
            }
            else if (i >= FOURTH_ROW && i <= END_OF_FOURTH) { // Creates the fourth row of blocks.
                block.setX(BLOCK_WIDTH * (i - FOURTH_HELPER) + BLOCK_BORDER);
                block.setY(BLOCK_HEIGHT * ROW_SETTER4);
            }
            else if (i >= FIFTH_ROW && i <= END_OF_FIFTH) { // Creates the fifth row of blocks.
                block.setX(BLOCK_WIDTH * (i - FIFTH_HELPER) + BLOCK_BORDER);
                block.setY(BLOCK_HEIGHT * ROW_SETTER5);
            }
        }
        System.out.println(_blocks); // Prints the array list.

        _ball = new Ball(_root); // Creates the ball.

        _ball.setX(SCENE_WIDTH / 2 - _ball.getHalfWidth()); // Sets X position of the ball.
        _ball.setY(SCENE_HEIGHT / 2 - _ball.getHalfHeight()); // Sets Y position of the ball.

        if (_testTop == true) { // Repositions the ball above the blocks if _testTop is true.
            _ball.setX(SCENE_WIDTH  / 2); // Sets X position of the ball.
            _ball.setY(BLOCK_HEIGHT / 2); // Sets Y position of the ball.
        }

        _timeline = new Timeline(new KeyFrame( // Creates the timeline.
                Duration.millis(_delayMs), // Implements the delay.
                (evt) -> {
                    _root.setOnMouseMoved((event) -> { // Tracks the mouse's movements.
                        mx = event.getX() - paddle.getHalfWidth(); // Gets the mouse's X position.
                        paddle.setX(mx); // Sets the center of the paddle to the mouse's X position.
                    });

                    updateBall(_ball); // Calls the updateBall method.
                    int dx, dy; // Initializes dx and dy that will be used for changing the velocity of the ball.
                    dx = _ball.getDx(); // Sets dx to the ball's X velocity.
                    dy = _ball.getDy(); // Sets the ball's Y velocity to dy.

                    if (_ball.getBounds().intersects(paddle.getBounds())) { // Determines if the ball and paddle collide.
                        _ball.setDy(-dy); // Changes the ball's Y velocity to the opposite direction.
                        // Changes X velocity of the ball if it hits the left half of the paddle.
                        if (_ball.getX() < (paddle.getX() + paddle.getHalfWidth())) {
                            double paddleDx = (paddle.getX() - _ball.getX()) / VELOCITY_SCALER;
                            _ball.setDx((int) paddleDx);
                        }
                        // Changes X velocity of the ball if it hits the right half of the paddle.
                        else if (_ball.getX() > (paddle.getX() + paddle.getHalfWidth())) {
                            if (_ball.getDx() < 0) {
                                _ball.setDx(-dx);
                            }
                            _ball.setDx(dx);
                        }
                    }

                    boolean hit = false; // Initializes a boolean to use for ball and block collisions.
                    for (Block block : _blocks) { // For loop to constantly check for collisions b/t the ball and the blocks.

                        // Removes the block from scene that the ball strikes.
                        if (_ball.getBounds().intersects(block.getBounds())) {
                                _root.getChildren().remove(block.getImageView());
                                hit = true;
                        }
                        if (_ball.getBounds().intersects(block.getBounds())) {
                            whichPartHit(block); // Calls the whichPartHit method.

                            // Checks if the ball struck the top of the bottom of the block.
                            if (whichPartHit(block) == BlockSide.BLOCK_TOP_BOT) {
                                dy = _ball.getDy();
                                _ball.setDy(-dy); // Changes the Y velocity of the ball.
                            }
                            // Checks if the ball struck the left or right side of the block.
                            else if (whichPartHit(block) == BlockSide.BLOCK_LT_RT) {
                                _ball.setDx(-dx); // Changes the X velocity of the ball.
                            }
                            // Checks if the ball struck one of the corners of the blocks.
                            else if (whichPartHit(block) == BlockSide.BLOCK_CORNER) {
                                dy = _ball.getDy();
                                _ball.setDx(-dx); // Changes the X velocity of the ball.
                                _ball.setDy(-dy); // Changes the Y velocity of the ball.
                            }
                        }
                    }
                    if (hit) { // Removes the block from the array list that the ball strikes.
                        _blocks.removeIf((b) -> {
                            return _ball.getBounds().intersects(b.getBounds());


                        });
                    }
                    if (_blocks.isEmpty()) { // Was supposed to end the game if all the blocks are removed from the array list.
                        _timeline.stop(); // Stops the timer.
                        WinLose win = new WinLose(_root, "/cop2513/breakout/images/you-win.png", WIN_LOSE_WIDTH, WIN_HEIGHT);
                        win.setX(SCENE_WIDTH / 2); // Sets the X position of the image.
                        win.setY(SCENE_HEIGHT / 2); // Sets the Y position of the image.
                    }
                    // Stops the game if the ball goes below the bottom.
                    if (_ball.getY() - _ball.getHalfHeight() > SCENE_HEIGHT) {
                        _timeline.stop(); // Stops the timeline.
                        WinLose lose = new WinLose(_root, "/cop2513/breakout/images/you-lose.png", WIN_LOSE_WIDTH, LOSE_HEIGHT);
                        lose.setX(SCENE_WIDTH / 2); // Sets the X position of the image.
                        lose.setY(SCENE_HEIGHT / LOSE_ADJUSTER); // Sets the Y position of the image.
                    }
                }
        ));
        _timeline.setCycleCount(Animation.INDEFINITE); // Makes the cycle count indefinite.
        _timeline.play(); // Starts the timeline.
    }

    // TODO: Your additional methods here
    public Pane getRoot() {return _root;} // Method in order to return the root "_root".

    public Ball updateBall(Ball ball) { // Method responsible for making the ball bounce off of the walls.
        _ball = ball;
        _ball.move(); // Calls the Ball class's move method to get the ball moving.
        int dx = _ball.getDx(); // Initializes dx and sets it equal to the X velocity of the ball.
        int dy = _ball.getDy(); // Initializes dy and sets it equal to the Y velocity of the ball.

        // Makes the ball bounce off of the right boundary of the window.
        if(_ball.getX() > SCENE_WIDTH - _ball.getHalfWidth()){
            dx = -dx;
            _ball.setDx(dx); // Changes the X velocity of the ball.
        }
        // Makes the ball bounce off of the Left boundary of the window.
        if(_ball.getX() < 0 + _ball.getHalfWidth()) {
            dx = -dx;
            _ball.setDx(dx); // Changes the X velocity of the ball.
        }
        // Makes the ball bounce off of the bottom boundary of the window if _bounceBottom is true.
        if (_bounceBottom == true) {
            if (_ball.getY() > SCENE_HEIGHT - _ball.getHalfWidth()) {
                dy = -dy;
                _ball.setDy(dy); // Changes the Y velocity of the ball.
            }
        }
        // Makes the ball bounce off of the top boundary of the window if _bounceBottom is true.
        if (_ball.getY() < 0 + _ball.getHalfHeight()) {
            dy = -dy;
            _ball.setDy(dy); // Changes the X velocity of the ball.
        }
        return _ball; // Returns the ball.
    }

    // Purpose is to randomly return one of the five block images.
    private String blockString() {
        // Initializes an string array with the block urls.
        String[] images = new String[] {"/cop2513/breakout/images/blue-block.png", "/cop2513/breakout/images/green-block.png",
                "/cop2513/breakout/images/orange-block.png", "/cop2513/breakout/images/purple-block.png",
                "/cop2513/breakout/images/red-block.png"};

        Random random = new Random(); // Initializes the randomizer.
        int index = random.nextInt(images.length); // Connects the string to a random integer "index".

        return images[index]; // Returns the random string.
    }

    /**
     * Helper method to show angle of where the ball hit.
     * IMPORTANT NOTE: Your ball class must have a
     *                 getX and getY methods that return the coordinates of the
     *                 center point of the ball.
     *
     * Based on code taken from,
     * http://gamedev.stackexchange.com/a/22614,
     * last access 4/25/2017
     *
     * @param block the block to test against.
     * @return one of BlockSide.BLOCK_TOP_BOT when the ball hit the top or bottom,
     *                BlockSide.BLOCK_LT_RT when the ball hit either the left or right side,
     *                BlockSide.BLOCK_CORNER when the ball hit a corner
     */
    private BlockSide whichPartHit(Block block) {

        final double upcorner = 63.43;
        final double botcorner = 116.56;

        final double epsilon = 0.02;

        Point2D brickFacing = new Point2D(0, -1);
        brickFacing = brickFacing.normalize();

        Point2D ballToBrick = new
                Point2D(
                        _ball.getX() - (block.getX() + block.getHalfWidth()),
                        _ball.getY() - (block.getY() + block.getHalfHeight()));
        ballToBrick = ballToBrick.normalize();
        double angle = Math.toDegrees(Math.acos(ballToBrick.dotProduct(brickFacing)));

        if (upcorner - epsilon <= angle && angle <= upcorner + epsilon) {
            // if hit close to upper corners
            return BlockSide.BLOCK_CORNER;
        }
        else if (botcorner - epsilon <= angle && angle <= botcorner + epsilon) {
            // if hit close to lower corners
            return BlockSide.BLOCK_CORNER;
        }
        else if (0 <= angle && angle < upcorner + epsilon) {
            // check top
            return BlockSide.BLOCK_TOP_BOT;
        }
        else if (botcorner + epsilon < angle) {
            // check bottom
            return BlockSide.BLOCK_TOP_BOT;
        }
        else if (upcorner + epsilon < angle && angle < botcorner - epsilon) {
            // only need 1 for this since angle is the same regardless of which
            // side we're on
            return BlockSide.BLOCK_LT_RT;
        }
        else {
            // should never occur
            throw new IllegalStateException("whichPartHit failed!");
        }
    }

    /**
     * Helper method to draw grid lines onto the board.
     */
    private void addGridLines() {
        // draw vertical lines
        for (int x = COL_WIDTH; x < SCENE_WIDTH; x += COL_WIDTH) {
            Line line = new Line(x, 0, x, SCENE_HEIGHT);
            line.setStroke(Color.LIGHTGREY);
            _root.getChildren().add(line);
        }

        // draw horizontal lines
        for (int y = ROW_HEIGHT; y < SCENE_HEIGHT; y += ROW_HEIGHT) {
            Line line = new Line(0, y, SCENE_WIDTH, y);
            line.setStroke(Color.LIGHTGREY);
            _root.getChildren().add(line);
        }
    }

    /**
     * Returns a random number in the given range from min (inclusive)
     * to max (exclusive).
     * Code taken from
     * https://www.mkyong.com/java/java-generate-random-integers-in-a-range/,
     * last access 4/22/2017
     * 
     * @param min the min (inclusive) of the range.
     * @param max the max (exclusive) of the range.
     * @return a random number in the given range from min to max.
     */
    public static int randInRange(int min, int max) {
        Random r = new Random();
        return r.ints(min, (max + 1)).findFirst().getAsInt();
    }

}
