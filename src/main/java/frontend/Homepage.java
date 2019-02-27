package frontend;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Homepage {
    public static Scene setHomepage() {
        GridPane gridPane = new GridPane();
        Label label = new Label("Welcome!");
        Scene scene = new Scene(label, SetupStructure.getBounds()[0],
                SetupStructure.getBounds()[1]);
        return scene;
    }
}
