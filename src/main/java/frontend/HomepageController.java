package frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.awt.*;

public class HomepageController {

    @FXML
    public void logoutButton(){
        System.out.println("logout test");
    }

    @FXML
    private TextField idFeild;
    @FXML
    public ComboBox activitiesMenu;
    @FXML
    ObservableList<String> activvites = FXCollections.observableArrayList("Vegitarian Meal", "Solar Panels", "Placeholder 1", "Placeholder 2");


    //Code For activites Menu
//    @FXML
//    public static void activitiesIntialize(){
//
//        public TextField idFeild;
//        public ComboBox activitiesMenu;
//        ObservableList<String> activites = FXCollections.observableArrayList("Vegitarian Meal", "Solar Panels", "Placeholder 1", "Placeholder 2");
//
//        activitiesMenu.setValue("Please Choose");
//        activitiesMenu.setItems(activites);
//
//    }

}
