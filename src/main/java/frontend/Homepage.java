package frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Homepage {
    private BorderPane border;

    public Homepage() {
        this.border = new BorderPane();
    }

    public BorderPane getBorder() {
        return this.border;
    }

    public static void main(Homepage homepage) {
        HBox box = new HBox();
        box.setStyle("-fx-background-color: #0f0;");

        HBox left = homepage.addHbox(1);
        HBox right = homepage.addHbox(2);
        left.setAlignment(Pos.CENTER_LEFT);
        right.setAlignment(Pos.CENTER_RIGHT);
        box.getChildren().addAll(left, right);
        HBox.setHgrow(right, Priority.ALWAYS);
        homepage.getBorder().setTop(box);
    }

    private HBox addHbox(int type) {
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
