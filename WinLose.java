package cop2513.breakout;

import javafx.scene.layout.Pane;

/**
 * Created by Philip on 4/26/17.
 */
public class WinLose extends ImageViewGameObj {

    public WinLose(Pane root, String url, int width, int height) { // Constructor for WinLose class.

        super(root, url, width, height);
    }

    @Override
    public void setX(double cx) {
        super.setX(cx - this.getHalfWidth());
    } // Sets X position of the win/lose image.

    @Override
    public void setY(double cy) {
        super.setY(cy - this.getHalfHeight());
    } // Sets the Y position of the win/lose image.

    @Override
    public String toString() {
        return super.toString();
    }
}
