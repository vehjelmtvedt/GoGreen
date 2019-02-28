package frontend;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Homepage {

    /**.
     * returns the scene of the Homepage to Input Validation, once user is logged in
     * @return Scene of Homepage
     */
    public static Scene setHomepage() {
        BorderPane border = new BorderPane();
        HBox hbox = Homepage.addHBox();
        border.setTop(hbox);

        return new Scene(border, SetupStructure.getBounds()[0], SetupStructure.getBounds()[1]);
    }

    private static HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #1BBC2E;");

        Button buttonCurrent = new Button("Current");
        buttonCurrent.setPrefSize(100, 20);
        StageSwitcher.buttonSwitch(buttonCurrent, Main.getPrimaryStage(), Main.getSignIn());

        Button buttonProjected = new Button("Projected");
        buttonProjected.setPrefSize(100, 20);

        hbox.getChildren().addAll(buttonCurrent, buttonProjected);

        return hbox;
    }
}
