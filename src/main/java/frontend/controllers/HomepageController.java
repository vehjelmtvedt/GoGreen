package frontend.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import data.User;
import frontend.gui.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {
    private static User loggedUser;

    @FXML
    private JFXHamburger menu;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane headerPane;
    @FXML
    private PieChart chartCategory;
    @FXML
    private PieChart chartFood;
    @FXML
    private PieChart chartTransportation;
    @FXML
    private PieChart chartHousehold;
    @FXML
    private Label lblWelcome;
    @FXML
    private Label goGreen;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblLevel;
    @FXML
    private Label lblYourSavings;
    @FXML
    private Label lblAverageSavings;
    @FXML
    private Label lblLevelCompletion;
    @FXML
    private ProgressBar progressLevel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //addFonts
        try {
            goGreen.setFont(Main.getReenieBeanie(100));
        } catch (IOException e) {
            System.out.println("Fonts not found");
        }

        //setting up dashboard
        lblWelcome.setText("Welcome, " + loggedUser.getFirstName() + " "
                + loggedUser.getLastName() + "! Here is your dashboard!");

        //profile information
        int level = loggedUser.getProgress().getLevel();
        lblUsername.setText(loggedUser.getUsername());
        lblLevel.setText("Level : " + level);
        lblLevelCompletion.setText("X% completed out of level " + (Integer)(level + 1));
        lblYourSavings.setText(loggedUser.getTotalCarbonSaved() + " kg");

        //charts
        chartCategory.setData(fillPieChart(loggedUser));
        chartFood.setData(fillPieChart(loggedUser));
        chartTransportation.setData(fillPieChart(loggedUser));
        chartHousehold.setData(fillPieChart(loggedUser));
        try {
            NotificationPanelController.addNotificationPanel(headerPane, mainPane);
            NavPanelController.setup(drawer, menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ObservableList<PieChart.Data> fillPieChart(User user) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                                    new PieChart.Data("Food", 1),
                                    new PieChart.Data("Transportation", 2),
                                    new PieChart.Data("Household", 3)
        );
        return pieChartData;
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
