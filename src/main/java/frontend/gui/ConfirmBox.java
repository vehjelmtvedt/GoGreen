package frontend.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

    private static boolean answer;

    private static boolean display(String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Close request safety");
        window.setMinWidth(400);
        window.setMinHeight(200);
        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });

        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }

    /**.
     *
     * @param window stage to close on answer
     */
    public static void closeProgram(Stage window, String message) {
        boolean answer = ConfirmBox.display(message);
        if (answer) {
            window.close();
        }
    }

    /**.
     * Add logout option for confirm Box
     * @param window window to change scenes for
     * @param nextScene scene after logging out
     * @param image image to change
     * @param message message for logging out
     */
    public static void logout(Stage window, Scene nextScene, ImageView image, String message) {
        boolean answer = ConfirmBox.display(message);
        if (answer) {
            window.setScene(nextScene);
        }
        image.setImage(new Image("frontend/Pics/logoutIcon.png"));
    }
}
