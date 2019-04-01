package frontend.gui;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.ArrayList;

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

    /**
     * .
     * Reset input fields
     *
     * @param fields ArrayList containing all input fields of form
     */
    public static void resetFields(ArrayList<JFXTextField> fields) {
        for (TextField field : fields) {
            if (field != null) {
                field.setText(null);
            }
        }
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
     * Show alert on submit
     * @param alertType type of alert
     * @param title title of the alert window
     * @param message message on the alert window
     */
    public static void showAlert(Alert.AlertType alertType,
                                  Window window, String title, String message) {
        Alert alert = new Alert(alertType);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Main.getCssIntro());
        dialogPane.setId("alertDialog");
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(window);
        alert.show();
    }

    /**.
     * Add text listener to the given field to not accept anything other than numbers
     * @param field - field to add listener to
     */
    public static void addTextListener(JFXTextField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("^[0-9]{0,7}$")) {
                    field.setText(oldValue);
                }
            }
        });
    }
}
