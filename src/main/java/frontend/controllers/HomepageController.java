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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import tools.ActivityQueries;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //addFonts
        try {
            goGreen.setFont(Main.getReenieBeanie(100));
        } catch (IOException e) {
            System.out.println("Fonts not found");
        }

        lblWelcome.setText("Welcome, " + loggedUser.getFirstName() + " "
                + loggedUser.getLastName() + "! Here is your dashboard!");
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
        ActivityQueries queries = new ActivityQueries(user.getActivities());
        //        ArrayList<int[]> countCat = queries.getNrOfActivitiesByCat();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                                    new PieChart.Data("Food", 1),
                                    new PieChart.Data("Transportation", 2),
                                    new PieChart.Data("Household", 3)
        );

        //        if (type == 1) {
        //            pieChartData = FXCollections.observableArrayList(
        //                    new PieChart.Data("Food", countCat.get(0)[0]),
        //                    new PieChart.Data("Transportation", countCat.get(0)[1]),
        //                    new PieChart.Data("Household", countCat.get(0)[2])
        //            );
        //        } else {
        //            pieChartData = FXCollections.observableArrayList(
        //                    new PieChart.Data("Eat Vegetarian Meal", countCat.get(1)[0]),
        //                    new PieChart.Data("Buy Organic Food", countCat.get(1)[0]),
        //                    new PieChart.Data("Buy Locally Produced Food", countCat.get(1)[0]),
        //                    new PieChart.Data("Buy Non-Processed Food", countCat.get(1)[0])
        //            );
        //        }
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
