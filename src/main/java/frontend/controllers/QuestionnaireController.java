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
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import tools.Requests;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Helps to Keep the image scaling in check

        background.fitWidthProperty().bind(graphics.widthProperty());
        background.fitHeightProperty().bind(graphics.heightProperty());

        //Numeric restrain to Text Feilds For any positive integer value

        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String input = change.getText();
            if (input.matches("^[0-9]{0,7}$")) {
                return change;
            }
            return null;
        };

        textHousehold.setTextFormatter(new TextFormatter<String>(integerFilter));
        textCarUsage.setTextFormatter(new TextFormatter<String>(integerFilter));
        textElectricity.setTextFormatter(new TextFormatter<String>(integerFilter));
        textOil.setTextFormatter(new TextFormatter<String>(integerFilter));


        //Initializing the Combo Boxes With the Specified Values

        ObservableList<String> carsizes = FXCollections.observableArrayList(
                "I don't own a car","small", "medium", "large"
        );

        carSizes.setItems(carsizes);

        ObservableList<String> meatanddairyoptions = FXCollections.observableArrayList(
                "above average", "average", "below average", "vegan"
        );

        meatAndDairyOptions.setItems(meatanddairyoptions);

        ObservableList<String> locallyproducedfoodoptions = FXCollections.observableArrayList(
                "very little", "average", "above average", "almost all"
        );

        locallyProducedFoodOptions.setItems(locallyproducedfoodoptions);

        ObservableList<String> organicoptions = FXCollections.observableArrayList(
                "none", "some", "most", "all"
        );

        organicOptions.setItems(organicoptions);

        ObservableList<String> processedoptions = FXCollections.observableArrayList(
                "above average", "average", "below average", "very little"
        );

        processedOptions.setItems(processedoptions);

        // Submit Button Logic to send information to the user object

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

           // For Debugging Purposes
           // System.out.println("household members: " + householdMembers);
           // System.out.println("daily electricity consumption: " + dailyElectricityConsumption);
           // System.out.println("daily oil consumption: " + dailyHeatingOilConsumption);
           // System.out.printf("You have a %s car\n", carType);
           // System.out.println("Kilometres per day: " + dailyCarKilometres);
           // System.out.println("Meat and Dairy consumption: " + meatAndDairyConsumption);
           // System.out.println("Locally produced food consumption: " + locallyProducedFoodConsumption);
           // System.out.println("Organic Food Consumption: " + organicFoodConsumption);
           // System.out.println("Processed Food Consmption: " + processedFoodConsumption);
           // System.out.println("");

            // Send the user Back to Login after Questionnaire is complete

            String response = Requests.signupRequest(thisUser);
            if (response != null) {
                if (response.equals("success")) {
                    StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getSignIn());
                }
            }
        });

    }

    // Updates the Existing user with the questionnaire information

    public static void setUser(User user) {
        thisUser = user;
    }

}
