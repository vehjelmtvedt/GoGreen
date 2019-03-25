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
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import tools.Requests;

import java.io.IOException;
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
    private AnchorPane mainPane;

    @FXML
    private JFXComboBox houseHoldNo;

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

    public void comboBoxSetup(JFXComboBox comboBox, ObservableList itemList) {
        comboBox.setItems(itemList);
        comboBox.setValue(itemList.get(0));
    }

    public void changeListener(JFXTextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[0-9]{0,7}$")) {
                textField.setText(oldValue);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Helps to Keep the image scaling in check

        background.fitWidthProperty().bind(graphics.widthProperty());
        background.fitHeightProperty().bind(graphics.heightProperty());

        //Numeric restrain to Text Fields For any positive integer value

        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String input = change.getText();
            if (input.matches("^[0-9]{0,7}$")) {
                return change;
            }
            return null;
        };

        //Numeric restrain to Text Fields For Values 1-9

        UnaryOperator<TextFormatter.Change> integerFilteralt = change -> {
            String input = change.getText();
            if (input.matches("")) {
                return change;
            }
            return null;
        };

        changeListener(textCarUsage);
        changeListener(textElectricity);
        changeListener(textOil);


        //Initializing the Combo Boxes With the Specified Values

        ObservableList<Integer> householdmembers = FXCollections.observableArrayList(
                1,2,3,4,5,6,7,8,9
        );

        comboBoxSetup(houseHoldNo, householdmembers);

        ObservableList<String> carsizes = FXCollections.observableArrayList(
                "I don't own a car","small", "medium", "large"
        );

        comboBoxSetup(carSizes, carsizes);

        ObservableList<String> meatanddairyoptions = FXCollections.observableArrayList(
                "above average", "average", "below average", "vegan"
        );

        comboBoxSetup(meatAndDairyOptions, meatanddairyoptions);

        ObservableList<String> locallyproducedfoodoptions = FXCollections.observableArrayList(
                "very little", "average", "above average", "almost all"
        );

        comboBoxSetup(locallyProducedFoodOptions, locallyproducedfoodoptions);

        ObservableList<String> organicoptions = FXCollections.observableArrayList(
                "none", "some", "most", "all"
        );

        comboBoxSetup(organicOptions, organicoptions);

        ObservableList<String> processedoptions = FXCollections.observableArrayList(
                "above average", "average", "below average", "very little"
        );

        comboBoxSetup(processedOptions,processedoptions);

        textCarUsage.setText("0");
        textCarUsage.setEditable(false);

        carSizes.setOnAction(e -> {
            if (carSizes.getValue().toString().equals("I don't own a car")) {
                textCarUsage.setText("0");
                textCarUsage.setEditable(false);
            } else {
                textCarUsage.setEditable(true);
            }
        });

        // Submit Button Logic to send information to the user object

        submitButton.setOnAction(e -> {
            if (!(textElectricity.getText().isEmpty() && textOil.getText().isEmpty())) {

                int householdMembers = Integer.parseInt(houseHoldNo.getValue().toString());
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

                // Send the user Back to Login after Questionnaire is complete

                String response = Requests.signupRequest(thisUser);
                if (response != null) {
                    if (response.equals("success")) {
                        StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getSignIn());
                    }
                }
            } else {
                System.out.println("Form not complete");
                try {
                    Dialog.show(
                            mainPane, "Questionnaire Incomplete",
                            "Please Complete the Questionnaire", "DISMISS", "error"
                    );
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

    // Updates the Existing user with the questionnaire information

    public static void setUser(User user) {
        thisUser = user;
    }

}
