package frontend.gui;

import frontend.controllers.DialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class Dialog {


    /**
     * Shows a JFXDialog.
     * @param headerText - text of header in dialog
     * @param bodyText - text of body in dialog
     * @param buttonText - text on button in dialog
     * @param icon - what icon to be displayed (sucess/error)
     * @throws IOException - if fails to load dialog
     */

    public static void show(String headerText, String bodyText,
                            String buttonText, String icon, boolean blocking) throws IOException {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        if (blocking) {
            stage.initModality(Modality.APPLICATION_MODAL);
        }
        FXMLLoader dialogloader = new FXMLLoader(
                Main.class.getResource("/frontend/fxmlPages/Dialog.fxml"));
        Parent dialog = dialogloader.load();

        DialogController controller = dialogloader.getController();

        controller.setHeading(headerText);
        controller.setBody(bodyText);
        controller.setButtonText(buttonText);
        controller.setIcon(icon);

        Scene scene = new Scene(dialog,
                General.getBounds()[0] / 2, General.getBounds()[1] / 2);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
        stage.setAlwaysOnTop(true);
    }
}
