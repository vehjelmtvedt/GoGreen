package frontend.controllers;

import com.jfoenix.controls.JFXButton;
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
    private ImageView background;

    @FXML
    private AnchorPane graphics;

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
    private JFXComboBox MeatAndDairyOptions;

    @FXML
    private JFXComboBox LocallyProducedFoodOptions;

    @FXML
    private JFXComboBox OrganicOptions;

    @FXML
    private JFXComboBox ProcessedOptions;

    @FXML
    private JFXButton SubmitButton;

    ObservableList<String> carsizes = FXCollections.observableArrayList("I don't own a car","Small", "Medium", "Large");

    ObservableList<String> meatanddairyoptions = FXCollections.observableArrayList("Above average", "Average", "Below average", "Vegan");

    ObservableList<String> locallyproducedfoodoptions = FXCollections.observableArrayList("Very little", "Average", "Above average", "Almost all");

    ObservableList<String> organicoptions = FXCollections.observableArrayList("None", "Some", "Most", "All");

    ObservableList<String> processedoptions = FXCollections.observableArrayList("Above average", "Average", "Below average", "Very little");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        background.fitWidthProperty().bind(graphics.widthProperty());
        background.fitHeightProperty().bind(graphics.heightProperty());

        CarSizes.setValue("I don't own a car");
        CarSizes.setItems(carsizes);

        MeatAndDairyOptions.setValue("Above average");
        MeatAndDairyOptions.setItems(meatanddairyoptions);

        LocallyProducedFoodOptions.setValue("Very little");
        LocallyProducedFoodOptions.setItems(locallyproducedfoodoptions);

        OrganicOptions.setValue("None");
        OrganicOptions.setItems(organicoptions);

        ProcessedOptions.setValue("Above average");
        ProcessedOptions.setItems(processedoptions);

    }

}
