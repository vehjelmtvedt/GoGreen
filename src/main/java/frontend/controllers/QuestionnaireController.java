package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import data.User;
import frontend.gui.Main;
import frontend.gui.StageSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import tools.Requests;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionnaireController implements Initializable {

    private static User thisUser;

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
    private JFXComboBox carSizes;

    @FXML
    private JFXComboBox meatAndDairyOptions;

    @FXML
    private JFXComboBox locallyProducedFoodOptions;

    @FXML
    private JFXComboBox organicOptions;

    @FXML
    private JFXComboBox processedOptions;

    @FXML
    private JFXButton submitButton;

    private AnchorPane form;

    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        background.fitWidthProperty().bind(graphics.widthProperty());
        background.fitHeightProperty().bind(graphics.heightProperty());

        ObservableList<String> carsizes = FXCollections.observableArrayList(
                "I don't own a car","Small", "Medium", "Large"
        );

        carSizes.setValue("I don't own a car");
        carSizes.setItems(carsizes);

        ObservableList<String> meatanddairyoptions = FXCollections.observableArrayList(
                "Above average", "Average", "Below average", "Vegan"
        );

        meatAndDairyOptions.setValue("Above average");
        meatAndDairyOptions.setItems(meatanddairyoptions);

        ObservableList<String> locallyproducedfoodoptions = FXCollections.observableArrayList(
                "Very little", "Average", "Above average", "Almost all"
        );

        locallyProducedFoodOptions.setValue("Very little");
        locallyProducedFoodOptions.setItems(locallyproducedfoodoptions);

        ObservableList<String> organicoptions = FXCollections.observableArrayList(
                "None", "Some", "Most", "All"
        );

        organicOptions.setValue("None");
        organicOptions.setItems(organicoptions);

        ObservableList<String> processedoptions = FXCollections.observableArrayList(
                "Above average", "Average", "Below average", "Very little"
        );

        processedOptions.setValue("Above average");
        processedOptions.setItems(processedoptions);

        submitButton.setOnAction(e -> {
            int householdMembers = Integer.parseInt(textHousehold.getText());
            int dailyElectricityConsumption =
                    Integer.parseInt(textElectricity.getText()) / 365 / householdMembers;
            double dailyHeatingOilConsumption =
                    Integer.parseInt(textOil.getText()) / 365.0 / householdMembers;
            String carType = carSizes.getValue().toString();
            int dailyCarKilometres = Integer.parseInt(textCarUsage.getText()) / 365;
            String meatAndDairyConsumption = meatAndDairyOptions.getValue().toString();
            String locallyProducedFoodConsumption =
                    locallyProducedFoodOptions.getValue().toString();
            String organicFoodConsumption = organicOptions.getValue().toString();
            String processedFoodConsumption = processedOptions.getValue().toString();
            thisUser.setElectricityDailyConsumption(dailyElectricityConsumption);
            thisUser.setHeatingOilDailyConsumption(dailyHeatingOilConsumption);
            thisUser.setCarType(carType);
            thisUser.setDailyCarKilometres(dailyCarKilometres);
            thisUser.setMeatAndDairyConsumption(meatAndDairyConsumption);
            thisUser.setLocallyProducedFoodConsumption(locallyProducedFoodConsumption);
            thisUser.setOrganicFoodConsumption(organicFoodConsumption);
            thisUser.setProcessedFoodConsumption(processedFoodConsumption);

            String response = Requests.signupRequest(thisUser);
            if (response != null) {
                if (response.equals("success")) {
                    StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getSignIn());
                }
            }
        });

    }

    public static void setUser(User user) {
        thisUser = user;
    }

}
