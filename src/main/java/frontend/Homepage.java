package frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class Homepage {

    /**.
     * Creates scene for Homepage
     * @return scene for Homepage
     */
    public static Scene createScene() {
        HBox box = new HBox();
        box.setStyle("-fx-background-color: #009933;");

        HBox left = addHbox(1);
        HBox right = addHbox(2);
        left.setAlignment(Pos.CENTER_LEFT);
        right.setAlignment(Pos.CENTER_RIGHT);
        box.getChildren().addAll(left, right);
        HBox.setHgrow(right, Priority.ALWAYS);

        BorderPane border = new BorderPane();
        border.setTop(box);

        return new Scene(border, General.getBounds()[0], General.getBounds()[1]);
    }

    private static HBox addHbox(int type) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        if (type == 1) {
            Button buttonFriends = new Button("Friends");
            buttonFriends.setPrefSize(100, 20);


            Button buttonProfile = new Button("My Profile");
            buttonProfile.setPrefSize(100, 20);
            hbox.getChildren().addAll(buttonFriends, buttonProfile);
        } else {
            Button buttonLogout = new Button("Logout");
            StageSwitcher.buttonSwitch(buttonLogout, Main.getPrimaryStage(), Main.getSignIn());
            buttonLogout.setPrefSize(100, 20);

            Button buttonCloseApp = new Button("Exit");
            buttonCloseApp.setPrefSize(100, 20);
            StageSwitcher.closeApp(buttonCloseApp, Main.getPrimaryStage());
            hbox.getChildren().addAll(buttonLogout, buttonCloseApp);
        }
        return hbox;
    }
}
