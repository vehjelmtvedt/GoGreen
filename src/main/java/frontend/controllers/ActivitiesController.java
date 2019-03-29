package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import data.Activity;
import data.User;
import frontend.gui.Events;
import frontend.gui.General;
import frontend.gui.Main;
import frontend.gui.NavPanel;
import frontend.gui.StageSwitcher;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ActivitiesController implements Initializable {
    private static User loggedUser;
    private static List<JFXCheckBox> checkList = new ArrayList<>();
    private static List<JFXRadioButton> radioList = new ArrayList<>();

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
    @FXML
    private AnchorPane paneBike;
    @FXML
    private AnchorPane paneBus;
    @FXML
    private AnchorPane paneTrain;
    @FXML
    private JFXTextField inputDistance;
    @FXML
    private Label lblDistanceValidate;
    @FXML
    private JFXButton btnBike;
    @FXML
    private JFXButton btnBus;
    @FXML
    private JFXButton btnTrain;
    @FXML
    private JFXCheckBox checkFood;
    @FXML
    private JFXCheckBox checkTransportation;
    @FXML
    private JFXCheckBox checkHousehold;
    @FXML
    private Label lblClearFilters;
    @FXML
    private Label lblApply;
    @FXML
    private Label goGreen;
    @FXML
    private JFXRadioButton radioToday;
    @FXML
    private JFXRadioButton radioWeek;
    @FXML
    private JFXRadioButton radioMonth;
    @FXML
    private JFXTextField minCarbon;
    @FXML
    private JFXTextField maxCarbon;

    /**
     * .
     * Setup page before loading .fxml files
     *
     * @param location  Standard parameters
     * @param resources Standard parameters
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //addFonts
        try {
            goGreen.setFont(Main.getReenieBeanie(100));
        } catch (IOException e) {
            System.out.println("Fonts not found");
        }
        //add Activity Event on clicking ( plus add in history table )
        Events.addFoodActivity(paneVegetarianMeal, 1, loggedUser, activityTable);
        Events.addFoodActivity(paneOrganicFood, 2, loggedUser, activityTable);
        Events.addFoodActivity(paneLocalFood, 3, loggedUser, activityTable);
        Events.addFoodActivity(paneNonProFood, 4, loggedUser, activityTable);
        Events.addTransportActivity(paneBike, inputDistance,
                lblDistanceValidate, 1, loggedUser, activityTable);
        Events.addTransportActivity(paneBus, inputDistance,
                lblDistanceValidate, 2, loggedUser, activityTable);
        Events.addTransportActivity(paneTrain, inputDistance,
                lblDistanceValidate, 3, loggedUser, activityTable);

        //add hover events for button activities
        Events.addActivityHover(paneVegetarianMeal, btnVegetarianMeal);
        Events.addActivityHover(paneOrganicFood, btnOrganicFood);
        Events.addActivityHover(paneLocalFood, btnLocalFood);
        Events.addActivityHover(paneNonProFood, btnNonProFood);
        Events.addActivityHover(paneBike, btnBike);
        Events.addActivityHover(paneBus, btnBus);
        Events.addActivityHover(paneTrain, btnTrain);

        //setup notification and navigation panels
        try {
            StageSwitcher.activityDrawer = NavPanel.addNavPanel(mainPane, headerPane, menu);
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

        //create check list
        checkList.add(checkFood);
        checkList.add(checkTransportation);
        checkList.add(checkHousehold);

        //create radio list
        radioList.add(radioToday);
        radioList.add(radioWeek);
        radioList.add(radioMonth);

        //Add events for the filter tab in activity history
        General.addTextListener(minCarbon);
        General.addTextListener(maxCarbon);
        Events.addRadioToggle(radioList);
        Events.addHoverOnFilter(lblClearFilters);
        Events.addHoverOnFilter(lblApply);
        Events.clearFilters(lblClearFilters, checkList, radioList,
                minCarbon, maxCarbon, loggedUser, activityTable);
        Events.applyFilters(lblApply,checkList, radioList,
                minCarbon, maxCarbon, loggedUser, activityTable);

    }

    //GENERAL METHODS
    @FXML
    private void handleCategory(ActionEvent event) {
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
