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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
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
    private static User thisUser;
    private static LoginDetails thisLoginDetails;
    private static AnchorPane mainCopy;
    private static AnchorPane headerCopy;
    private static NotificationPopup popup;
    private List<JFXButton> leaderboards = new ArrayList<>();


    @FXML
    private JFXHamburger menu;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane headerPane;
    @FXML
    private Label lblFirstName;
    @FXML
    private Label lblLastName;
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
    private Label lblWelcome;
    @FXML
    private Label lblRank;
    @FXML
    private Label lblProgress;
    @FXML
    private JFXButton btnRefresh;
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
    @FXML
    private BarChart weekChart;
    @FXML
    private Circle circleProfile;

    /**.
     * Update the first name for the label containing the full name of the user
     * @param newFirstName - new first name value
     */
    public void updateFirstName(String newFirstName) {
        lblFirstName.setText(newFirstName);
    }

    /**.
     * Update the last name for the label containing the full name of the user
     * @param newLastName - new last name value
     */
    public void updateLastName(String newLastName) {
        lblLastName.setText(newLastName);
    }

    /**.
     * Update the user information on the homepage
     * @param loginDetails - current login details (assigned to user)
     */
    public void updateUser(LoginDetails loginDetails) {
        //update the user and his login details
        thisUser = Requests.instance.loginRequest(loginDetails);

        //update the field values on the homepage dashboard
        circleProfile.setFill(new ImagePattern(
                new Image("avatars/" + thisUser.getAvatar() + ".jpg")));
        lblFirstName.setText(thisUser.getFirstName().toUpperCase());
        lblLastName.setText(thisUser.getLastName().toUpperCase());
        lblEmail.setText(thisUser.getEmail());
        lblLevel.setText(Integer.toString(thisUser.getProgress().getLevel()));
        lblRank.setText(Integer.toString(Requests.instance.getUserRanking(thisLoginDetails)));
        lblProgress.setText(thisUser.getProgress().pointsNeeded()
                + " Points left");
        lblActivities.setText(Integer.toString(thisUser.getActivities().size()));
        lblFriends.setText(Integer.toString(thisUser.getFriends().size()));
        lblYourCarbon.setText("You have saved " + thisUser.getTotalCarbonSaved()
                + " kg of CO2 so far");
        lblAverageCarbon.setText("Average person saved "
                + ((int)(Requests.instance.getAverageCO2Saved() * 1000)) / 1000.0
                + " kg of CO2 so far");

        //update user information for the homepage charts
        fillPieChart(thisUser, chartMyActivities);
        fillBarChart("Your CO2 Savings", barChart);
        fillWeekChart(thisUser, weekChart);
    }

    private void updateLeaderboards() {
        fillLeaderboards(5, tableTop5);
        fillLeaderboards(10, tableTop10);
        fillLeaderboards(25, tableTop25);
        fillLeaderboards(50, tableTop50);
        fillLeaderboards(100, tableTop100);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Events.homepageController = this;
        EditProfilePopUpController.homepageController = this;

        popup = new NotificationPopup();
        //add buttons to leader boards list
        leaderboards.add(btnTop5);
        leaderboards.add(btnTop10);
        leaderboards.add(btnTop25);
        leaderboards.add(btnTop50);
        leaderboards.add(btnTop100);

        //update leaderboards upon clicking refresh button
        btnRefresh.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> updateLeaderboards());

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

        //update profile information and leaderboards upon initialising page
        updateUser(thisLoginDetails);
        updateLeaderboards();

        Events.addLeaderboards(leaderboards);
        Events.addJfxButtonHover(btnProfile);
        Events.addJfxButtonHover(btnRefresh);

        btnProfile.setOnAction(event -> StageSwitcher.sceneSwitch(Main.getPrimaryStage(),
                Main.getProfilePage()));

        try {
            goGreen.setFont(Main.getReenieBeanie(100));
            lblWelcome.setFont(Main.getReenieBeanie(40));
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

        chart.getData().clear();
        chart.setData(FXCollections.observableArrayList(
                new PieChart.Data("FOOD",
                        queries.filterActivities("Food").size()),
                new PieChart.Data("TRANSPORTATION",
                        queries.filterActivities("Transportation").size()),
                new PieChart.Data("HOUSEHOLD",
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
        List<User> users = Requests.instance.getTopUsers(thisLoginDetails, top);

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
        ActivityQueries thisQuery = new ActivityQueries(thisUser.getActivities());
        series.getData().add(new XYChart.Data("TODAY",
                thisQuery.getTotalCO2Saved(DateUnit.TODAY)));
        series.getData().add(new XYChart.Data("LAST WEEK",
                thisQuery.getTotalCO2Saved(DateUnit.WEEK)));
        series.getData().add(new XYChart.Data("LAST MONTH",
                thisQuery.getTotalCO2Saved(DateUnit.MONTH)));
    }

    private void fillBarChart(String title, BarChart chart) {
        XYChart.Series series = new XYChart.Series();
        series.setName(title);
        populateBarChart(series);
        chart.getData().clear();
        chart.getData().addAll(series);
        chart.setLegendVisible(false);
    }

    private static void fillWeekChart(User user, BarChart barChart) {
        ActivityQueries queries = new ActivityQueries(user.getActivities());
        XYChart.Series series = new XYChart.Series();
        series.getData().addAll(queries.getWeeklyCO2Savings());
        barChart.getData().clear();
        barChart.getData().addAll(series);
        barChart.setLegendVisible(false);
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
    public static void setUser(User  passedUser) {
        thisUser = passedUser;

    }

    /**
     * Sets the login details and starts the notification thread.
     * @param passedLoginDetails - login details from sign in form
     */
    public static void setLoginDetails(LoginDetails passedLoginDetails) {
        thisLoginDetails = passedLoginDetails;
        SyncUserTask syncUserTask = new SyncUserTask(Requests.instance, thisLoginDetails, thisUser);
        NotificationThread notificationThread = new NotificationThread(syncUserTask);
        notificationThread.start();
    }
}
