package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import data.LoginDetails;
import data.User;
import frontend.gui.NotificationPopup;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tools.ActivityQueries;
import tools.DateUnit;
import tools.Requests;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class FriendspageController implements Initializable {

    private static User thisUser;

    private static LoginDetails thisLoginDetails;

    @FXML
    private JFXTreeTableView friendsPane;

    @FXML
    private JFXHamburger menu;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXDrawer addFriendDrawer;

    @FXML
    private Button addFriendButton;

    @FXML
    private JFXTextField searchField;

    @FXML
    private VBox results;

    @FXML
    private BarChart todayChart;

    @FXML
    private BarChart weeklyChart;

    @FXML
    private BarChart monthlyChart;

    @FXML
    private StackPane todayPane;

    @FXML
    private StackPane weekPane;

    @FXML
    private StackPane monthPane;

    @FXML
    private HBox headingBox;

    @FXML
    private AnchorPane main;

    @FXML
    private AnchorPane headerPane;

    private List searchresults;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NotificationPopup popup = new NotificationPopup();
        try {
            NavPanelController.setup(drawer, menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fillFriendsTreeView();
        drawFriendRequestDrawer();
        fillChart("Today", "#6976ae", DateUnit.DAY, todayChart);
        fillChart("This Week", "#cd7b4c", DateUnit.WEEK, weeklyChart);
        fillChart("This Month", "#b74747", DateUnit.MONTH, monthlyChart);

        todayPane.prefWidthProperty().bind(headingBox.widthProperty());
        weekPane.prefWidthProperty().bind(headingBox.widthProperty());
        monthPane.prefWidthProperty().bind(headingBox.widthProperty());

        todayChart.setOnMouseClicked(e -> {
            try {
                popup.newNotification(main, headerPane, "Heading", "Text of the body",
                        "sucess");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    /**
     * Fills the chart on the page with data.
     * @param title - Title of the graph
     * @param color - color of the bars on the graph
     * @param unit - DateUnit enum, day, week or month
     * @param chart - the chart to edit
     */
    public void fillChart(String title, String color, DateUnit unit, BarChart chart) {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(title);
        populateBarChart(series1, unit);
        chart.getData().addAll(series1);
        colorBars(color, chart);
        chart.setLegendVisible(false);
    }

    private void colorBars(String color, BarChart chart) {
        Node node = chart.lookup(".data0.chart-bar");
        node.setStyle("-fx-bar-fill: #379B1E;");
        for (int i = 1; i != 6; i++) {
            node = chart.lookup(".data" + i + ".chart-bar");
            if (node == null) {
                return;
            }
            node.setStyle("-fx-bar-fill: " +  color + ";");
        }
    }

    /**
     * Adds the data to the first bar chart.
     *
     * @param series1 - the series to add data to
     */
    public void populateBarChart(XYChart.Series series1, DateUnit unit) {
        int counter = 0;
        ActivityQueries thisQuery = new ActivityQueries(thisUser.getActivities());
        series1.getData().add(new XYChart.Data("You",
                thisQuery.getTotalCO2Saved(unit)));
        List<User> friendsList = Requests.getFriends(thisLoginDetails);
        for (User friend : friendsList) {
            if (counter >= 5) {
                return;
            }
            ActivityQueries query = new ActivityQueries(friend.getActivities());
            series1.getData().addAll(new XYChart.Data(friend.getUsername(),
                    query.getTotalCO2Saved(unit)));
            counter++;

        }
    }

    /**
     * Adds the drawer to search for users and send them friend requests.
     */
    public void drawFriendRequestDrawer() {

        searchField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (searchField.getText().trim().isEmpty()) {
                results.getChildren().clear();
            } else {
                searchresults = getSearchResults(searchField.getText());

                results.getChildren().clear();
                for (int i = 0; i < searchresults.size(); i++) {
                    if (!searchresults.get(i).equals(thisUser.getUsername())) {
                        HBox hbox = new HBox();
                        VBox.setMargin(hbox, new Insets(0, 20, 0, 20));
                        hbox.setStyle("-fx-background-color: #4286f4;");
                        hbox.setPrefWidth(results.getPrefWidth());
                        hbox.setPrefHeight(50);
                        Label tmpLabel = new Label(searchresults.get(i).toString());
                        tmpLabel.setPrefWidth(hbox.getPrefWidth() / 2);
                        JFXButton addButton = new JFXButton("+");
                        addButton.setStyle("-fx-background-color: #5b8d5b;");
                        addButton.setPrefWidth(hbox.getPrefWidth() / 3);
                        tmpLabel.setStyle("-fx-text-fill: white;");
                        HBox.setMargin(addButton, new Insets(10, 10, 0, 90));
                        HBox.setMargin(tmpLabel, new Insets(15, 0, 0, 30));
                        addButton.setMaxWidth(40);
                        addButton.setOnAction(e -> Requests.sendFriendRequest(
                                thisUser.getUsername(), tmpLabel.getText()));
                        hbox.getChildren().addAll(tmpLabel, addButton);
                        results.getChildren().add(hbox);
                    }
                }
            }

        });

        addFriendDrawer.setVisible(false);

        addFriendButton.setOnAction(e -> {
            if (addFriendDrawer.isOpened()) {
                addFriendDrawer.close();
                addFriendDrawer.setVisible(false);
            } else {
                addFriendDrawer.open();
                addFriendDrawer.setVisible(true);
            }
        });
    }

    /**
     * Returns list of usernames based on keyword.
     *
     * @param keyword - keyword to search usernames
     * @return - list of users matching the keyword
     */
    public List getSearchResults(String keyword) {
        return Requests.getMatchingUsersRequest(keyword, thisLoginDetails);
    }

    /**
     * Adds the activity table to the GUI.
     */
    public void fillFriendsTreeView() {

        JFXTreeTableColumn<UserItem, String>
                usernameColumn = new JFXTreeTableColumn<>("Friends");
        usernameColumn.setCellValueFactory(param -> param.getValue().getValue().username);
        usernameColumn.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<UserItem, String>
                lastActivityColumn = new JFXTreeTableColumn<>("Recent Activity");
        lastActivityColumn.setCellValueFactory(param -> param.getValue().getValue().lastActivity);

        JFXTreeTableColumn<UserItem, String>
                totalCarbonSavedColumn = new JFXTreeTableColumn<>("Total carbon Saved");
        totalCarbonSavedColumn
                .setCellValueFactory(param -> param.getValue().getValue().carbonSaved);

        totalCarbonSavedColumn.setPrefWidth(150);
        usernameColumn.setPrefWidth(150);
        lastActivityColumn.setPrefWidth(300);

        ObservableList<UserItem> friendsList = getTableData();
        final TreeItem<UserItem> root = new RecursiveTreeItem<>(
                friendsList, RecursiveTreeObject::getChildren);
        friendsPane.getColumns().setAll(usernameColumn, lastActivityColumn, totalCarbonSavedColumn);
        friendsPane.setRoot(root);
        friendsPane.setShowRoot(false);

        styleTreeView(usernameColumn, lastActivityColumn, totalCarbonSavedColumn);
    }

    /**
     * Gives style to the tree view.
     *
     * @param username     - first column of the table
     * @param lastActivity - second column
     * @param carbon       - third column
     */
    public void styleTreeView(JFXTreeTableColumn username,
                              JFXTreeTableColumn lastActivity, JFXTreeTableColumn carbon) {
        username.setStyle("-fx-alignment: center;");
        lastActivity.setStyle("-fx-alignment: center;");
        carbon.setStyle("-fx-alignment: center;");
    }

    private ObservableList<UserItem> getTableData() {
        ObservableList<UserItem> friendsList = FXCollections.observableArrayList();
        List<User> friends = Requests.getFriends(thisLoginDetails);
        for (Object friend : friends) {
            User thisFriend = (User) friend;
            String activity = "This user has no activities";
            if (thisFriend.getActivities().size() != 0) {
                activity = thisFriend.getActivities().get(
                        thisFriend.getActivities().size() - 1).getName();
            }
            String carbonSaved = Double.toString(thisFriend.getTotalCarbonSaved());
            friendsList.add(new UserItem(thisFriend.getUsername(), activity, carbonSaved));
        }
        return friendsList;
    }


    //Used for constructing TreeView
    private class UserItem extends RecursiveTreeObject<UserItem> {
        StringProperty username;
        StringProperty lastActivity;
        StringProperty carbonSaved;

        public UserItem(String username, String lastActivity, String carbonSaved) {
            this.username = new SimpleStringProperty(username);
            this.lastActivity = new SimpleStringProperty(lastActivity);
            this.carbonSaved = new SimpleStringProperty(carbonSaved);

        }
    }

    public static void setUser(User user) {
        thisUser = user;
    }

    public static void setLoginDetails(LoginDetails loginDetails) {
        thisLoginDetails = loginDetails;
    }
}
