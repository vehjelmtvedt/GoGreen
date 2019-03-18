package frontend.controllers;

import data.LoginDetails;
import data.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import tools.Requests;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class FriendspageController implements Initializable {

    private static User thisUser;

    private static LoginDetails thisLoginDetails;

    @FXML
    private Label goGreen;

    @FXML
    private AnchorPane centerPane;

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
    private HBox dataPane;

    private List searchresults;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            NavPanelController.setup(drawer, menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fillFriendsTreeView();
        drawFriendRequestDrawer();
        drawFriendsBarChart("Today");
        drawFriendsBarChart("This week");
        drawFriendsBarChart("This year");
    }

    /**
     * Draws the bar graph to the Friends page.
     */
    public void drawFriendsBarChart(String title) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc =
                new BarChart<>(xAxis, yAxis);
        bc.setTitle("Carbon Saved");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(title);
        populateBarChart(series1);

        bc.getData().addAll(series1);
        dataPane.getChildren().addAll(bc);
    }

    /**
     * Adds the data to the first bar chart.
     *
     * @param series1 - the series to add data to
     */
    public void populateBarChart(XYChart.Series series1) {
        series1.getData().add(new XYChart.Data(thisUser.getUsername(),
                thisUser.getTotalCarbonSaved()));
        List<User> friendsList = Requests.getFriends(thisLoginDetails);
        for (User friend : friendsList) {
            series1.getData().add(new XYChart.Data(friend.getUsername(),
                    friend.getTotalCarbonSaved()));
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
