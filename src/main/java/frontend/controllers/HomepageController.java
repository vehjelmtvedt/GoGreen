package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import com.jfoenix.controls.JFXTreeView;
import data.User;
import frontend.gui.Events;
import frontend.gui.Main;
import frontend.gui.StageSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import tools.ActivityQueries;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {
    private static User loggedUser;
    private List<JFXButton> leaderboards = new ArrayList<>();
    private List<JFXTreeView> listTables = new ArrayList<>();

    @FXML
    private JFXHamburger menu;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane headerPane;
    @FXML
    private Label lblName;
    @FXML
    private Label goGreen;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblLevel;
    @FXML
    private Label lblYourCarbon;
    @FXML
    private Label lblActivities;
    @FXML
    private Label lblFriends;
    @FXML
    private JFXButton btnProfile;
    @FXML
    private JFXButton btnMyStats;
    @FXML
    private JFXButton btnTop5;
    @FXML
    private JFXButton btnTop10;
    @FXML
    private JFXButton btnTop25;
    @FXML
    private JFXButton btnTop50;
    @FXML
    private JFXTreeView tableMyStats;
    @FXML
    private JFXTreeView tableTop5;
    @FXML
    private JFXTreeView tableTop10;
    @FXML
    private JFXTreeView tableTop25;
    @FXML
    private JFXTreeView tableTop50;
    @FXML
    private PieChart chartMyActivities;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //add buttons to leader boards list
        leaderboards.add(btnMyStats);
        leaderboards.add(btnTop5);
        leaderboards.add(btnTop10);
        leaderboards.add(btnTop25);
        leaderboards.add(btnTop50);

        //add tables to tables list
        listTables.add(tableMyStats);
        listTables.add(tableTop5);
        listTables.add(tableTop10);
        listTables.add(tableTop25);
        listTables.add(tableTop50);

        Events.addLeaderboards(leaderboards);

        //addFonts
        try {
            goGreen.setFont(Main.getReenieBeanie(100));
        } catch (IOException e) {
            System.out.println("Fonts not found");
        }

        //profile information
        lblName.setText(loggedUser.getFirstName().toUpperCase() + " "
                + loggedUser.getLastName().toUpperCase());
        lblEmail.setText(loggedUser.getEmail());
        lblLevel.setText(Integer.toString(loggedUser.getProgress().getLevel()));
        lblActivities.setText(Integer.toString(loggedUser.getActivities().size()));
        lblFriends.setText(Integer.toString(loggedUser.getFriends().size()));
        lblYourCarbon.setText("You have saved " + loggedUser.getTotalCarbonSaved()
                + " kg of CO2 so far");
        btnProfile.setOnAction(event -> StageSwitcher.sceneSwitch(Main.getPrimaryStage(),
                Main.getProfilePage()));

        //charts on the right
        chartMyActivities.setData(fillPieChart(loggedUser));

        Events.addJfxButtonHover(btnProfile);

        try {
            NotificationPanelController.addNotificationPanel(headerPane, mainPane);
            NavPanelController.setup(drawer, menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ObservableList<PieChart.Data> fillPieChart(User user) {
        ActivityQueries queries = new ActivityQueries(user.getActivities());
        List<String> categories = new ArrayList<>();
        categories.add("Food");
        categories.add("Transportation");
        categories.add("Household");

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                                    new PieChart.Data("Food",
                                            queries.filterActivitiesByCategories(
                                            categories.subList(0,1)).size()),
                                    new PieChart.Data("Transportation",
                                            queries.filterActivitiesByCategories(
                                                    categories.subList(1,2)).size()),
                                    new PieChart.Data("Household",
                                            queries.filterActivitiesByCategories(
                                                    categories.subList(2,3)).size())
        );
        return pieChartData;
    }

    //    @FXML
    //    private void handleLeaderboards(ActionEvent event) {
    //        JFXTreeView current = new JFXTreeView();
    //        if (event.getSource() == btnMyStats) {
    //            tableMyStats.setVisible(true);
    //            current = tableMyStats;
    //        } else if (event.getSource() == btnTop5) {
    //            current = tableTop5;
    //        } else if (event.getSource() == btnTop10) {
    //            current = tableTop10;
    //        } else if (event.getSource() == btnTop25) {
    //            current = tableTop25;
    //        } else {
    //            if (event.getSource() == btnTop50) {
    //                current = tableTop50;
    //            }
    //        }
    //        for (JFXTreeView table : this.listTables) {
    //            if (!table.equals(current)) {
    //                table.setVisible(false);
    //            }
    //        }
    //    }

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
