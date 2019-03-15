package frontend.controllers;

import backend.data.LoginDetails;
import backend.data.User;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import frontend.Requests;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class FriendspageController implements Initializable {

    private static User thisUser;

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
    }

    public void drawFriendRequestDrawer() {

        searchField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (searchField.getText().trim().isEmpty()) {
                results.getChildren().clear();
            } else {
                searchresults = getSearchResults(searchField.getText());

                results.getChildren().clear();
                for (int i = 0; i < searchresults.size(); i++) {
                    if (!searchresults.get(i).equals(thisUser.getUsername())) {
                        HBox hBox = new HBox();
                        VBox.setMargin(hBox, new Insets(0, 20, 0, 20));
                        hBox.setStyle("-fx-background-color: #4286f4;");
                        hBox.setPrefWidth(results.getPrefWidth());
                        hBox.setPrefHeight(50);
                        Label tmpLabel = new Label(searchresults.get(i).toString());
                        tmpLabel.setPrefWidth(hBox.getPrefWidth() / 2);
                        JFXButton addButton = new JFXButton("+");
                        addButton.setStyle("-fx-background-color: #5b8d5b;");
                        addButton.setPrefWidth(hBox.getPrefWidth() / 3);
                        tmpLabel.setStyle("-fx-text-fill: white;");
                        HBox.setMargin(addButton, new Insets(10, 10, 0, 90));
                        HBox.setMargin(tmpLabel, new Insets(15, 0, 0,30));
                        addButton.setMaxWidth(40);
                        addButton.setOnAction(e -> Requests.sendFriendRequest(thisUser.getUsername(), tmpLabel.getText()));
                        hBox.getChildren().addAll(tmpLabel, addButton);
                        results.getChildren().add(hBox);
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

    public List getSearchResults(String keyword) {

        return Requests.getMatchingUsersRequest(keyword, new LoginDetails("vehjelm", "Tiger1466"));
    }

    public void fillFriendsTreeView() {

        JFXTreeTableColumn<UserItem, String> usernameColumn = new JFXTreeTableColumn<>("Friends");
        usernameColumn.setCellValueFactory(param -> param.getValue().getValue().username);
        usernameColumn.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<UserItem, String> lastActivityColumn = new JFXTreeTableColumn<>("Recent Activity");
        lastActivityColumn.setCellValueFactory(param -> param.getValue().getValue().lastActivity);

        JFXTreeTableColumn<UserItem, String> totalCarbonSavedColumn = new JFXTreeTableColumn<>("Total carbon Saved");
        totalCarbonSavedColumn.setCellValueFactory(param -> param.getValue().getValue().carbonSaved);

        totalCarbonSavedColumn.setPrefWidth(150);
        usernameColumn.setPrefWidth(150);
        lastActivityColumn.setPrefWidth(300);

        ObservableList<UserItem> friendsList = getTableData();
        final TreeItem<UserItem> root = new RecursiveTreeItem<>(friendsList, RecursiveTreeObject::getChildren);
        friendsPane.getColumns().setAll(usernameColumn, lastActivityColumn, totalCarbonSavedColumn);
        friendsPane.setRoot(root);
        friendsPane.setShowRoot(false);

        styleTreeView(usernameColumn, lastActivityColumn, totalCarbonSavedColumn);
    }

    public void styleTreeView(JFXTreeTableColumn username, JFXTreeTableColumn lastActivity, JFXTreeTableColumn carbon) {
        username.setStyle("-fx-alignment: center;");
        lastActivity.setStyle("-fx-alignment: center;");
        carbon.setStyle("-fx-alignment: center;");
    }

    private ObservableList<UserItem> getTableData() {
        ObservableList<UserItem> friendsList = FXCollections.observableArrayList();
        for (String username : thisUser.getFriends()) {
            User tmpFriend = Requests.getUserRequest(username);
            System.out.println(tmpFriend.toString());
            String activity = "This user has no activities";
            if (tmpFriend.getActivities().size() != 0) {
                activity = tmpFriend.getActivities().get(tmpFriend.getActivities().size() - 1).getName();
            }
            String carbonSaved = Double.toString(tmpFriend.getTotalCarbonSaved());
            friendsList.add(new UserItem(username, activity, carbonSaved));
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
}
