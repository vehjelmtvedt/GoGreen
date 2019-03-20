package frontend.gui;

import com.jfoenix.controls.JFXDialog;
import frontend.controllers.DialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;


import java.io.IOException;


public class Dialog {


    public static void show(AnchorPane mainPane, String headerText, String bodyText, String buttonText, String icon) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Dialog.class.getResource("/frontend/fxmlPages/Dialog.fxml"));
        JFXDialog dialog = loader.load();


        DialogController controller = loader.getController();
        controller.setHeading(headerText);
        controller.setBody(bodyText);
        controller.setButtonText(buttonText);
        controller.setIcon(icon);
        StackPane pane = new StackPane();
        pane.autosize();
        controller.setDialogParent(pane);

        AnchorPane.setRightAnchor(pane, mainPane.getWidth());
        AnchorPane.setTopAnchor(pane, (mainPane.getHeight()));
        AnchorPane.setBottomAnchor(pane, (mainPane.getHeight()));
        AnchorPane.setLeftAnchor(pane, (mainPane.getWidth()));
        mainPane.getChildren().addAll(pane);
        dialog.show();
    }
}
