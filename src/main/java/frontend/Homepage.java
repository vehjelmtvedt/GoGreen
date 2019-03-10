package frontend;

import backend.data.EatVegetarianMeal;
import backend.data.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.springframework.web.bind.annotation.RequestBody;

public class Homepage {

    /**.
     * Creates scene for Homepage
     * @return scene for Homepage
     */
    public static Scene createScene(User user) {
        HBox box = new HBox();
        box.setStyle("-fx-background-color: #009933;");

        HBox left = addHbox(1);
        HBox right = addHbox(2);
        left.setAlignment(Pos.CENTER_LEFT);
        right.setAlignment(Pos.CENTER_RIGHT);
        box.getChildren().addAll(left, right);
        HBox.setHgrow(right, Priority.ALWAYS);

        BorderPane border = new BorderPane();

        //Button testing
        Button addMealTest = new Button("Eat vegetarian meal");
        addMealTest.setOnAction(e -> {
            EatVegetarianMeal vegetarianMeal = new EatVegetarianMeal();
            vegetarianMeal.performActivity(user);
            user.addActivity(vegetarianMeal);
            String message = "carbon saved: " + user.getTotalCarbonSaved();
            message += "\n" + "times repeated today: "
                    + vegetarianMeal.timesPerformedInTheSameDay(user);
            General.showAlert(Alert.AlertType.CONFIRMATION,
                    border.getScene().getWindow(), "Activity results",
                    message);
        });
        //testing

        border.setTop(box);
        border.setCenter(addMealTest);
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
