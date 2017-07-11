package cop2513.breakout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static cop2513.breakout.Constants.*;
/**
 * Created by Philip on 4/26/17.
 */
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Breakout breakout = new Breakout(false);
        Scene scene = new Scene(breakout.getRoot(), SCENE_WIDTH, SCENE_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Final Exam: Breakout");
        stage.show();
    }

}