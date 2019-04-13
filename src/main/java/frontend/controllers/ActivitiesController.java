package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import data.Activity;
import data.InstallSolarPanels;
import data.LoginDetails;
import data.LowerHomeTemperature;
import data.RecyclePaper;
import data.RecyclePlastic;
import data.User;
import frontend.gui.Events;
import frontend.gui.General;
import frontend.gui.Main;
import frontend.gui.NavPanel;
import frontend.gui.NotificationPopup;
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
    private static User thisUser;
    private static LoginDetails thisLoginDetails;
    private static List<JFXCheckBox> checkList = new ArrayList<>();
    private static List<JFXRadioButton> radioList = new ArrayList<>();
    private static AnchorPane mainCopy;
    private static AnchorPane headerCopy;
    private static NotificationPopup popup;

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
    private JFXButton btnPlastic;
    @FXML
    private JFXButton btnPaper;
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
    private AnchorPane paneSolarPanels;
    @FXML
    private AnchorPane paneEnergy;
    @FXML
    private AnchorPane panePlastic;
    @FXML
    private AnchorPane panePaper;
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
    private JFXButton btnSolarPanels;
    @FXML
    private JFXButton btnEnergy;
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
    private Label lblPanelsInstalled;
    @FXML
    private Label lblLoweredTemp;
    @FXML
    private Label lblPlastic;
    @FXML
    private Label lblPaper;
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
        popup = new NotificationPopup();
        mainCopy = mainPane;
        headerCopy = headerPane;

        //add Activity Event on clicking ( plus add in history table )
        Events.addFoodActivity(paneVegetarianMeal, 1, thisLoginDetails, thisUser, activityTable);
        Events.addFoodActivity(paneOrganicFood, 2, thisLoginDetails, thisUser, activityTable);
        Events.addFoodActivity(paneLocalFood, 3, thisLoginDetails, thisUser, activityTable);
        Events.addFoodActivity(paneNonProFood, 4, thisLoginDetails, thisUser, activityTable);
        Events.addTransportActivity(paneBike, inputDistance,
                lblDistanceValidate, 1, thisLoginDetails, thisUser, activityTable);
        Events.addTransportActivity(paneBus, inputDistance,
                lblDistanceValidate, 2, thisLoginDetails, thisUser, activityTable);
        Events.addTransportActivity(paneTrain, inputDistance,
                lblDistanceValidate, 3, thisLoginDetails, thisUser, activityTable);
        Events.addHouseholdActivity(paneSolarPanels, lblPanelsInstalled,
                lblLoweredTemp,1, thisLoginDetails, thisUser, activityTable);
        Events.addHouseholdActivity(paneEnergy, lblPanelsInstalled,
                lblLoweredTemp,2, thisLoginDetails, thisUser, activityTable);
        Events.addRecyclingActivity(panePlastic, lblPlastic, lblPaper,1,
                thisLoginDetails, thisUser, activityTable);
        Events.addRecyclingActivity(panePaper, lblPlastic, lblPaper,2,
                thisLoginDetails, thisUser, activityTable);

        //add hover events for button activities
        Events.addActivityHover(paneVegetarianMeal, btnVegetarianMeal);
        Events.addActivityHover(paneOrganicFood, btnOrganicFood);
        Events.addActivityHover(paneLocalFood, btnLocalFood);
        Events.addActivityHover(paneNonProFood, btnNonProFood);
        Events.addActivityHover(paneBike, btnBike);
        Events.addActivityHover(paneBus, btnBus);
        Events.addActivityHover(paneTrain, btnTrain);
        Events.addActivityHover(paneSolarPanels, btnSolarPanels);
        Events.addActivityHover(paneEnergy, btnEnergy);
        Events.addActivityHover(panePlastic,btnPlastic);
        Events.addActivityHover(panePaper, btnPaper);

        //setup notification and navigation panels
        try {
            goGreen.setFont(Main.getReenieBeanie(100));
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

        activityTable.setItems(getActivities(thisUser));
        if (thisUser.getActivities().isEmpty()) {
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
                minCarbon, maxCarbon, thisUser, activityTable);
        Events.applyFilters(lblApply, checkList, radioList,
                minCarbon, maxCarbon, thisUser, activityTable);

        //setup additional labels
        InstallSolarPanels panels = new InstallSolarPanels();
        LowerHomeTemperature temp = new LowerHomeTemperature();
        lblPanelsInstalled.setVisible(thisUser.getSimilarActivities(panels).size() > 0);
        lblLoweredTemp.setVisible(temp.timesPerformedInTheSameDay(thisUser) > 0);
        RecyclePlastic plastic = new RecyclePlastic();
        lblPlastic.setVisible(plastic.timesPerformedInTheSameDay(thisUser) > 0);
        RecyclePaper paper = new RecyclePaper();
        lblPaper.setVisible(paper.timesPerformedInTheSameDay(thisUser) > 0);
    }

    public static void popup(String heading, String body, String icon,
                             int drawerNumber) throws IOException {
        String[] text = {heading, body, icon};
        popup.newNotification(mainCopy, headerCopy, text, drawerNumber);
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
        thisUser = passedUser;
    }

    public static void setLoginDetails(LoginDetails passedLoginDetails) {
        thisLoginDetails = passedLoginDetails;
    }
}
