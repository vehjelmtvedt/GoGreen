package frontend;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ActivitiesController {

    @FXML
    public ImageView backIcon;
    @FXML
    private JFXButton btnFood;
    @FXML
    private JFXButton btnTransport;
    @FXML
    private JFXButton btnSolarPanels;
    @FXML
    private JFXButton btnHouse;
    @FXML
    private JFXButton btnHistory;
    @FXML
    private Pane paneFood;
    @FXML
    private Pane paneTransport;
    @FXML
    private Pane paneSolarPanels;
    @FXML
    private Pane paneHouse;
    @FXML
    private Pane paneHistory;

    @FXML
    private void initialize() {
        backIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->
                StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getHomepage()));
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnFood) {
            paneFood.toFront();
        } else if (event.getSource() == btnTransport) {
            paneTransport.toFront();
        } else if (event.getSource() == btnSolarPanels) {
            paneSolarPanels.toFront();
        } else if (event.getSource() == btnHistory) {
            paneHistory.toFront();
        } else {
            if (event.getSource() == btnHouse) {
                paneHouse.toFront();
            }
        }
    }
}
