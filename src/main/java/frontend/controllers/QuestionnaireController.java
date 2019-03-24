package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import data.User;
import frontend.gui.Dialog;
import frontend.gui.Main;
import frontend.gui.StageSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import tools.Requests;

import java.io.IOException;
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

    private AnchorPane form;

    private User user;

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

        SubmitButton.setOnAction(e -> {
            int householdMembers = Integer.parseInt(textHousehold.getText());
            int dailyElectricityConsumption =
                    Integer.parseInt(textElectricity.getText()) / 365 / householdMembers;
            double dailyHeatingOilConsumption =
                    Integer.parseInt(textOil.getText()) / 365.0 / householdMembers;
            String carType = CarSizes.getValue().toString();
            int dailyCarKilometres = Integer.parseInt(textCarUsage.getText()) / 365;
            String meatAndDairyConsumption = MeatAndDairyOptions.getValue().toString();
            String locallyProducedFoodConsumption = LocallyProducedFoodOptions.getValue().toString();
            String organicFoodConsumption = OrganicOptions.getValue().toString();
            String processedFoodConsumption = ProcessedOptions.getValue().toString();
            user.setElectricityDailyConsumption(dailyElectricityConsumption);
            user.setHeatingOilDailyConsumption(dailyHeatingOilConsumption);
            user.setCarType(carType);
            user.setDailyCarKilometres(dailyCarKilometres);
            user.setMeatAndDairyConsumption(meatAndDairyConsumption);
            user.setLocallyProducedFoodConsumption(locallyProducedFoodConsumption);
            user.setOrganicFoodConsumption(organicFoodConsumption);
            user.setProcessedFoodConsumption(processedFoodConsumption);

            String response = Requests.signupRequest(user);
            if (response != null) {
                if (response.equals("success")) {
                    try {
                        Dialog.show(form, "Registration Successful!", "Enter your new credentials!",
                                "ACCEPT", "sucess");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getSignIn());
                }
            }
        });

    }

}
