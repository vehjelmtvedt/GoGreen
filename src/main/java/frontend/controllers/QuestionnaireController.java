package frontend.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class QuestionnaireController implements Initializable {

    @FXML
    private JFXTextField textHousehold;

    @FXML
    private JFXTextField textElectricity;

    @FXML
    private JFXTextField textOil;

    @FXML
    private JFXTextField textCarUsage;

    @FXML
    private JFXComboBox CarSizes;

    @FXML
    private JFXComboBox MeatAndDairiyOptions;

    @FXML
    private JFXComboBox LocallyProducedFoodOptions;

    @FXML
    private JFXComboBox OrganicOptions;

    @FXML
    private JFXComboBox ProcessedOptions;

    ObservableList<String> carsizes = FXCollections.observableArrayList("I don't own a car","Small", "Medium", "Large");

    ObservableList<String> meatanddairiyoptions = FXCollections.observableArrayList("Above average", "Average", "Below average", "Vegan");

    ObservableList<String> locallyproducedfoodoptions = FXCollections.observableArrayList("Very little", "Average", "Above average", "Almost all");

    ObservableList<String> organicoptions = FXCollections.observableArrayList("None", "Some", "Most", "All");

    ObservableList<String> processedoptions = FXCollections.observableArrayList("Above average", "Average", "Below average", "Very little");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CarSizes.setValue("I don't own a car");
        CarSizes.setItems(carsizes);

        MeatAndDairiyOptions.setValue("Above average");
        MeatAndDairiyOptions.setItems(meatanddairiyoptions);

        LocallyProducedFoodOptions.setValue("Very little");
        LocallyProducedFoodOptions.setItems(locallyproducedfoodoptions);

        OrganicOptions.setValue("None");
        OrganicOptions.setItems(organicoptions);

        ProcessedOptions.setValue("Above average");
        ProcessedOptions.setItems(processedoptions);

    }

}
