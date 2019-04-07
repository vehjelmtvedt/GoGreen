package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import data.LoginDetails;
import data.User;
import frontend.gui.Events;
import frontend.gui.Main;
import frontend.gui.NavPanel;
import frontend.gui.NotificationPopup;
import frontend.gui.StageSwitcher;
import frontend.threading.NotificationThread;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tools.ActivityQueries;
import tools.DateUnit;
import tools.Requests;
import tools.SyncUserTask;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {
    private static User loggedUser;
    private static AnchorPane mainCopy;
    private static AnchorPane headerCopy;
    private static NotificationPopup popup;
    private static LoginDetails loginDetails;
    private List<JFXButton> leaderboards = new ArrayList<>();


    @FXML
    private JFXHamburger menu;
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
    private Label lblAverageCarbon;
    @FXML
    private JFXButton btnProfile;
    @FXML
    private JFXButton btnTop5;
    @FXML
    private JFXButton btnTop10;
    @FXML
    private JFXButton btnTop25;
    @FXML
    private JFXButton btnTop50;
    @FXML
    private JFXButton btnTop100;
    @FXML
    private JFXTreeTableView tableTop5;
    @FXML
    private JFXTreeTableView tableTop10;
    @FXML
    private JFXTreeTableView tableTop25;
    @FXML
    private JFXTreeTableView tableTop50;
    @FXML
    private JFXTreeTableView tableTop100;
    @FXML
    private PieChart chartMyActivities;
    @FXML
    private BarChart barChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        popup = new NotificationPopup();
        //add buttons to leader boards list
        leaderboards.add(btnTop5);
        leaderboards.add(btnTop10);
        leaderboards.add(btnTop25);
        leaderboards.add(btnTop50);
        leaderboards.add(btnTop100);

        Events.addLeaderboards(leaderboards);

        //Populate leaderboards with entries
        fillLeaderboards(5, tableTop5);
        fillLeaderboards(10, tableTop10);
        fillLeaderboards(25, tableTop25);
        fillLeaderboards(50, tableTop50);
        fillLeaderboards(100, tableTop100);

        //switch leaderboards upon clicking
        btnTop5.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            hideLeaderboards(tableTop5, tableTop10, tableTop25, tableTop50, tableTop100);
        });
        btnTop10.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            hideLeaderboards(tableTop10, tableTop5, tableTop25, tableTop50, tableTop100);
        });
        btnTop25.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            hideLeaderboards(tableTop25, tableTop5, tableTop10, tableTop50, tableTop100);
        });
        btnTop25.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            hideLeaderboards(tableTop50, tableTop5, tableTop10, tableTop50, tableTop100);
        });
        btnTop100.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            hideLeaderboards(tableTop100, tableTop5, tableTop10, tableTop25, tableTop50);
        });

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
        lblAverageCarbon.setText("Average person saved "
                + ((int)(Requests.instance.getAverageCO2Saved() * 1000)) / 1000.0
                + " kg of CO2 so far");
        btnProfile.setOnAction(event -> StageSwitcher.sceneSwitch(Main.getPrimaryStage(),
                Main.getProfilePage()));

        //charts on the right
        fillPieChart(loggedUser, chartMyActivities);
        fillBarChart("Your CO2 Savings", barChart);

        Events.addJfxButtonHover(btnProfile);

        try {
            NotificationPanelController.addNotificationPanel(headerPane, mainPane);
            StageSwitcher.homeDrawer = NavPanel.addNavPanel(mainPane, headerPane, menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainCopy = mainPane;
        headerCopy = headerPane;
    }

    public static void popup(String heading, String body, String icon,
                             int drawerNumber) throws IOException {
        String[] text = {heading, body, icon};
        popup.newNotification(mainCopy, headerCopy, text, drawerNumber);
    }

    private static void fillPieChart(User user, PieChart chart) {
        ActivityQueries queries = new ActivityQueries(user.getActivities());

        chart.setData(FXCollections.observableArrayList(
                new PieChart.Data("Food",
                        queries.filterActivities("Food").size()),
                new PieChart.Data("Transportation",
                        queries.filterActivities("Transportation").size()),
                new PieChart.Data("Household",
                        queries.filterActivities("Household").size())
        ));
    }


    /**
     * Gives style to the tree view.
     *
     * @param user       - first column of the table
     * @param level      - second column
     * @param activities - third column
     * @param friends    - fourth column
     * @param carbon     - fifth column
     */
    private void styleTreeView(JFXTreeTableColumn user,
                               JFXTreeTableColumn level, JFXTreeTableColumn activities,
                               JFXTreeTableColumn friends, JFXTreeTableColumn carbon) {
        user.setStyle("-fx-alignment: center;");
        level.setStyle("-fx-alignment: center;");
        activities.setStyle("-fx-alignment: center;");
        friends.setStyle("-fx-alignment: center;");
        carbon.setStyle("-fx-alignment: center;");
    }

    //Used for constructing TreeView
    private class UserItem extends RecursiveTreeObject<UserItem> {
        StringProperty username;
        StringProperty level;
        StringProperty totalActivities;
        StringProperty totalFriends;
        StringProperty totalCarbonSaved;

        public UserItem(String username, String level, String totalActivities,
                        String totalFriends, String totalCarbonSaved) {
            this.username = new SimpleStringProperty(username);
            this.level = new SimpleStringProperty(level);
            this.totalActivities = new SimpleStringProperty(totalActivities);
            this.totalFriends = new SimpleStringProperty(totalFriends);
            this.totalCarbonSaved = new SimpleStringProperty(totalCarbonSaved);
        }
    }

    private ObservableList<UserItem> getTableData(int top) {
        ObservableList<UserItem> friendsList = FXCollections.observableArrayList();
        List<User> users = Requests.instance.getTopUsers(loginDetails, top);

        for (Object user : users) {
            User thisUser = (User) user;
            String totalActivities = "No activities";
            String totalFriends = "No friends";
            if (thisUser.getActivities().size() != 0) {
                totalActivities = Integer.toString(thisUser.getActivities().size());
            }
            if (thisUser.getFriends().size() != 0) {
                totalFriends = Integer.toString(thisUser.getFriends().size());
            }
            String level = Integer.toString(thisUser.getProgress().getLevel());
            String carbonSaved = Double.toString(thisUser.getTotalCarbonSaved());
            friendsList.add(new UserItem(thisUser.getUsername(), level, totalActivities,
                    totalFriends, carbonSaved));
        }
        return friendsList;
    }

    /**
     * .
     * Fill the table tree view with the leaderboards
     *
     * @param top - the first x people to show on the leaderboards
     * @param table - the first x people to show on the leaderboards
     */
    public void fillLeaderboards(int top, JFXTreeTableView table) {
        JFXTreeTableColumn<UserItem, String>
                usernameColumn = new JFXTreeTableColumn<>("User");
        usernameColumn.setCellValueFactory(param -> param.getValue().getValue().username);
        usernameColumn.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<UserItem, String>
                levelColumn = new JFXTreeTableColumn<>("Level");
        levelColumn.setCellValueFactory(param -> param.getValue().getValue().level);

        JFXTreeTableColumn<UserItem, String>
                totalActivitiesColumn = new JFXTreeTableColumn<>("Activities");
        totalActivitiesColumn.setCellValueFactory(param ->
                param.getValue().getValue().totalActivities);

        JFXTreeTableColumn<UserItem, String>
                totalFriendsColumn = new JFXTreeTableColumn<>("Friends");
        totalFriendsColumn.setCellValueFactory(param ->
                param.getValue().getValue().totalFriends);

        JFXTreeTableColumn<UserItem, String>
                totalCarbonSavedColumn = new JFXTreeTableColumn<>("Total carbon saved");
        totalCarbonSavedColumn.setCellValueFactory(param ->
                param.getValue().getValue().totalCarbonSaved);

        usernameColumn.setPrefWidth(170);
        levelColumn.setPrefWidth(110);
        totalActivitiesColumn.setPrefWidth(170);
        totalFriendsColumn.setPrefWidth(170);
        totalCarbonSavedColumn.setPrefWidth(200);

        ObservableList<UserItem> userList = getTableData(top);
        final TreeItem<UserItem> root = new RecursiveTreeItem<>(
                userList, RecursiveTreeObject::getChildren);

        styleTreeView(usernameColumn, levelColumn, totalActivitiesColumn,
                totalFriendsColumn, totalCarbonSavedColumn);

        table.getColumns().setAll(usernameColumn, levelColumn, totalActivitiesColumn,
                totalFriendsColumn, totalCarbonSavedColumn);
        table.setRoot(root);
        table.setShowRoot(false);
    }

    /**
     * Adds the data to the first bar chart.
     *
     * @param series - the series to add data to
     */
    public void populateBarChart(XYChart.Series series) {
        ActivityQueries thisQuery = new ActivityQueries(loggedUser.getActivities());
        series.getData().add(new XYChart.Data("Today",
                thisQuery.getTotalCO2Saved(DateUnit.TODAY)));
        series.getData().add(new XYChart.Data("Last Week",
                thisQuery.getTotalCO2Saved(DateUnit.WEEK)));
        series.getData().add(new XYChart.Data("Last Month",
                thisQuery.getTotalCO2Saved(DateUnit.MONTH)));
    }

    private void fillBarChart(String title, BarChart chart) {
        XYChart.Series series = new XYChart.Series();
        series.setName(title);
        populateBarChart(series);
        chart.getData().addAll(series);
        chart.setLegendVisible(false);
    }

    private static void hideLeaderboards(JFXTreeTableView shown,
                                         JFXTreeTableView first, JFXTreeTableView second,
                                         JFXTreeTableView third, JFXTreeTableView fourth) {
        shown.setVisible(true);
        first.setVisible(false);
        second.setVisible(false);
        third.setVisible(false);
        fourth.setVisible(false);
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
     * Sets the login details and starts the notification thread.
     * @param passedloginDetails - login details from sign in form
     */
    public static void setLoginDetails(LoginDetails passedloginDetails) {
        loginDetails = passedloginDetails;
        SyncUserTask syncUserTask = new SyncUserTask(Requests.instance, loginDetails, loggedUser);
        NotificationThread notificationThread = new NotificationThread(syncUserTask);
        notificationThread.start();
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
