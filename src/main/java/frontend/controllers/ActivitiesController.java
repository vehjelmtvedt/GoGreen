package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import data.Activity;
import data.User;
import frontend.gui.Events;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ActivitiesController implements Initializable {
    private static User loggedUser;

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
    private AnchorPane mainPane;
    @FXML
    private AnchorPane headerPane;
    @FXML
    private AnchorPane paneVegetarianMeal;
    @FXML
    private AnchorPane paneOrganicFood;
    @FXML
    private AnchorPane paneLocalFood;
    @FXML
    private AnchorPane paneNonProFood;

    /**
     * .
     * Setup page before loading .fxml files
     *
     * @param location  Standard parameters
     * @param resources Standard parameters
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //add hover for burger menu
        Events.addBurgerCursor(menu);

        //add hover events for Activity Category buttons
        Events.addButtonCursor(btnFood);
        Events.addButtonCursor(btnTransportation);
        Events.addButtonCursor(btnHousehold);
        Events.addButtonCursor(btnHistory);

        //add Activity Event on clicking ( plus add in history table )
        Events.addActivityClick(paneVegetarianMeal, 1, loggedUser, activityTable);
        Events.addActivityClick(paneOrganicFood, 2, loggedUser, activityTable);
        Events.addActivityClick(paneLocalFood, 3, loggedUser, activityTable);
        Events.addActivityClick(paneNonProFood, 4, loggedUser, activityTable);

        //add hover events for Food buttons
        Events.addActivityHover(paneVegetarianMeal, btnVegetarianMeal);
        Events.addActivityHover(paneOrganicFood, btnOrganicFood);
        Events.addActivityHover(paneLocalFood, btnLocalFood);
        Events.addActivityHover(paneNonProFood, btnNonProFood);

        //setup notification panel and navigation panel
        try {
            NavPanelController.setup(drawer, menu);
            NotificationPanelController.addNotificationPanel(headerPane, mainPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        resetButtonColors(btnFood, btnTransportation, btnHousehold, btnHistory);

        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("Category"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        carbonColumn.setCellValueFactory(new PropertyValueFactory<>("CarbonSaved"));

        activityTable.setItems(getActivities(loggedUser));
        if (loggedUser.getActivities().isEmpty()) {
            activityTable.setPlaceholder(new Label("No previous activities"));
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnFood) {
            resetButtonColors(btnFood, btnTransportation, btnHousehold, btnHistory);
            btnFood.setStyle("-fx-background-color: #00db00;");
            paneFood.toFront();
        } else if (event.getSource() == btnTransportation) {
            resetButtonColors(btnFood, btnTransportation, btnHousehold, btnHistory);
            btnTransportation.setStyle("-fx-background-color: #00db00;");
            paneTransportation.toFront();
        } else if (event.getSource() == btnHistory) {
            resetButtonColors(btnFood, btnTransportation, btnHousehold, btnHistory);
            btnHistory.setStyle("-fx-background-color: #00db00;");
            paneHistory.toFront();
        } else {
            if (event.getSource() == btnHousehold) {
                resetButtonColors(btnFood, btnTransportation, btnHousehold, btnHistory);
                btnHousehold.setStyle("-fx-background-color: #00db00;");
                paneHousehold.toFront();
            }
        }
    }

    /**
     * .
     * Gets the User's activities in an ObservableList
     *
     * @param user Takes the user as a parameter
     * @return returns the Observable list
     */
    public static ObservableList<Activity> getActivities(User user) {
        return FXCollections.observableArrayList(user.getActivities());
    }

    /**
     * .
     * Resets the button "selected" color upon de-selecting
     *
     * @param btnFood           Button for Food category
     * @param btnTransportation Button for Transportation category
     * @param btnHousehold      Button for Household category
     * @param btnHistory        Button for Activity History
     */
    private void resetButtonColors(JFXButton btnFood, JFXButton btnTransportation,
                                   JFXButton btnHousehold, JFXButton btnHistory) {
        btnFood.setStyle("-fx-background-color: transparent;");
        btnTransportation.setStyle("-fx-background-color: transparent;");
        btnHousehold.setStyle("-fx-background-color: transparent;");
        btnHistory.setStyle("-fx-background-color: transparent;");
    }

    /**
     * .
     * Sets the current logged in User to the one that was passed
     *
     * @param passedUser Logged in current user
     */
    public static void setUser(User passedUser) {
        loggedUser = passedUser;
    }

    /**
     * .
     * Get the logged in User
     *
     * @return logged User
     */
    public static User getUser() {
        return loggedUser;
    }
}
