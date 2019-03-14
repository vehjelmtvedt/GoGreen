package frontend.controllers;

import backend.data.Activity;
import backend.data.EatVegetarianMeal;
import backend.data.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import frontend.Main;
import frontend.StageSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ActivitiesController implements Initializable {
    private static User loggedUser;

    @FXML
    public ImageView backIcon;
    @FXML
    private JFXButton btnFood;
    @FXML
    private JFXButton btnTransportation;
    @FXML
    private JFXButton btnHousehold;
    @FXML
    private JFXButton btnHistory;
    @FXML
    private JFXButton btnVegetarianMeal;
    @FXML
    private JFXButton btnLocalFood;
    @FXML
    private JFXButton btnOrganicFood;
    @FXML
    private JFXButton btnNonProFood;
    @FXML
    private Pane paneFood;
    @FXML
    private Pane paneTransportation;
    @FXML
    private Pane paneHousehold;
    @FXML
    private Pane paneHistory;
    @FXML
    private TableView<Activity> activityTable = new TableView<>();
    @FXML
    private TableColumn<Activity, String> categoryColumn;
    @FXML
    private TableColumn<Activity, String> nameColumn;
    @FXML
    private TableColumn<Activity, Date> dateColumn;
    @FXML
    private TableColumn<Activity, Double> carbonColumn;
    @FXML
    private JFXHamburger menu;
    @FXML
    private JFXDrawer drawer;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnFood) {
            resetButtonColors(btnFood, btnTransportation, btnHousehold, btnHistory);
            btnFood.setStyle("-fx-background-color: #c6c6c6;");
            paneFood.toFront();
        } else if (event.getSource() == btnTransportation) {
            resetButtonColors(btnFood, btnTransportation, btnHousehold, btnHistory);
            btnTransportation.setStyle("-fx-background-color: #c6c6c6;");
            paneTransportation.toFront();
        } else if (event.getSource() == btnHistory) {
            resetButtonColors(btnFood, btnTransportation, btnHousehold, btnHistory);
            btnHistory.setStyle("-fx-background-color: #c6c6c6;");
            paneHistory.toFront();
        } else {
            if (event.getSource() == btnHousehold) {
                resetButtonColors(btnFood, btnTransportation, btnHousehold, btnHistory);
                btnHousehold.setStyle("-fx-background-color: #c6c6c6;");
                paneHousehold.toFront();
            }
        }
    }

    @FXML
    private void addFoodActivity(ActionEvent event) {
        if (event.getSource() == btnVegetarianMeal) {
            EatVegetarianMeal meal = new EatVegetarianMeal();
            meal.performActivity(loggedUser);
            loggedUser.addActivity(meal);
            ObservableList<Activity> activities = getActivities(loggedUser);

            activityTable.setItems(activities);
        } else if (event.getSource() == btnLocalFood) {
            //todo
        } else if (event.getSource() == btnOrganicFood) {
            //todo
        } else {
            if (event.getSource() == btnNonProFood) {
                //todo
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            NavPanelController.setup(drawer, menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
        resetButtonColors(btnFood, btnTransportation, btnHousehold, btnHistory);
        backIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->
                StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getHomepage()));

        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("Category"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        carbonColumn.setCellValueFactory(new PropertyValueFactory<>("CarbonSaved"));

        activityTable.setItems(getActivities(loggedUser));
        if (loggedUser.getActivities().isEmpty()) {
            activityTable.setPlaceholder(new Label("No previous activities"));
        }
    }

    private ObservableList<Activity> getActivities(User user) {
        return FXCollections.observableArrayList(user.getActivities());
    }


    private void resetButtonColors(JFXButton btnFood, JFXButton btnTransportation,
                                   JFXButton btnHousehold, JFXButton btnHistory) {
        btnFood.setStyle("-fx-background-color: transparent;");
        btnTransportation.setStyle("-fx-background-color: transparent;");
        btnHousehold.setStyle("-fx-background-color: transparent;");
        btnHistory.setStyle("-fx-background-color: transparent;");
    }

    public static void setUser(User passedUser) {
        loggedUser = passedUser;
    }
}
