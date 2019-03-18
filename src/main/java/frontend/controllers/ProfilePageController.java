package frontend.controllers;

import backend.data.Achievement;
import backend.data.User;
import frontend.ProfilePageLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class ProfilePageController implements Initializable {


    @FXML
    Label completed;

    @FXML
    private TableView<Achievement> all;

    @FXML
    private TableColumn<Achievement, String> achievementNameTableColumn;

    @FXML
    private TableColumn<Achievement, Integer> bonus;

    @FXML
    private Label level;

    @FXML
    private ImageView badge;

    //get user
    private User user;

    /**
     * how should the page be set up.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        level.setText("Level: " + ProfilePageLogic.getLevel(user));

        Image badgeimg = new Image(ProfilePageLogic.getBadge(user));

        //completed.setText(ProfilePageLogic.getAchievementsString(user));
        completed.setText("wouefg");


        bonus.setCellValueFactory(new PropertyValueFactory<Achievement, Integer>("Bonus"));
        achievementNameTableColumn.setCellValueFactory(
                new PropertyValueFactory<Achievement, String>("Name"));


        ObservableList<Achievement> allachievements =
                FXCollections.<Achievement>observableArrayList();

        all.setItems(allachievements);
    }



}

