package frontend.gui;

import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class General {

    /**
     * .
     * Gets the sizes for width & height of primary screen
     *
     * @return array of double values for width & height
     */
    public static Double[] getBounds() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        Double[] sizes = new Double[2];
        sizes[0] = bounds.getWidth();
        sizes[1] = bounds.getHeight();
        return sizes;
    }

    /**.
     * Set up icon and title for stage + confirm box
     * @param stage Stage to set up
     * @param title title to add to stage
     */
    public static void setPrimaryStage(Stage stage, String title) {
        stage.setTitle(title);
        stage.getIcons().addAll(new Image("file:C:"
                + "\\Users\\Alexandru\\Documents\\template\\src\\main\\"
                + "Resources\\frontend\\Pics\\GoGreenIcon.png"));
        stage.setOnCloseRequest(e -> {
            e.consume();
            String message = "Are you sure you want to close the application?";
            ConfirmBox.closeProgram(stage, message);
        });
    }

    /**.
     * Set up final changes to stage + displaying it
     * @param stage stage to set up
     * @param scene scene to start stage with
     */
    public static void finaliseStage(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    /**.
     * Add text listener to the given field to not accept anything other than numbers
     * @param field - field to add listener to
     */
    public static void addTextListener(JFXTextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[0-9]{0,7}$")) {
                field.setText(oldValue);
            }
        });
    }
}
