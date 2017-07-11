package cop2513.breakout;

import javafx.scene.layout.Pane;
import static cop2513.breakout.Constants.*;

/**
 * Created by Philip on 4/26/17.
 */
public class Block extends ImageViewGameObj {

    public Block(Pane root, String url, int width, int height) { // Constructor for Block class.
        super(root, url, BLOCK_WIDTH, BLOCK_HEIGHT);
    }

    @Override
    public void setX(double cx) { // Sets the X position of the block.
        super.setX(cx);
    }

    @Override
    public void setY(double cy) {
        super.setY(cy - this.getHalfHeight());
    } // Sets the Y position of the block.

    @Override
    public String toString() {
        return super.toString();
    }
}
