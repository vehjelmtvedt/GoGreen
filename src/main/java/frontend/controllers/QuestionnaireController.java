package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import data.User;
import frontend.gui.Dialog;
import frontend.gui.Events;
import frontend.gui.Main;
import frontend.gui.StageSwitcher;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import tools.Requests;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuestionnaireController implements Initializable {
    private static User thisUser;

    @FXML
    private Label lblLine1;
    @FXML
    private Label lblLine2;
    @FXML
    private Label lblLine3;
    @FXML
    private Label line4;
    @FXML
    private Label line5;
    @FXML
    private Label lblSaved;
    @FXML
    private Label lblTotalUsers;
    @FXML
    private Label goGreen;
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

    /**
     * Method used to setup the Combo Boxes with the appropriate item sets.
     */
    public static void comboBoxSetup(JFXComboBox comboBox, ObservableList itemList) {
        comboBox.setItems(itemList);
        comboBox.setValue(itemList.get(0));
    }

    /**
     * Method Used to restrict text field inputs to a desired numeric style.
     */
    public static void changeListener(JFXTextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[0-9]{0,7}$")) {
                textField.setText(oldValue);
            }
        });
    }

    /**
     * Method used to Check is a text field is filled in or not and accordingly highlight it.
     */
    public static void textFieldValidate(JFXTextField textField) {
        if (textField.getText().isEmpty()) {
            textField.setUnFocusColor(Color.RED);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblSaved.setText(Math.floor(Requests.instance.getTotalCO2Saved()) + " KG");
        lblTotalUsers.setText(Requests.instance.getTotalUsers() + " Users");

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10000), ae -> {
            lblSaved.setText(Math.floor(Requests.instance.getTotalCO2Saved()) + " KG");
            lblTotalUsers.setText(Requests.instance.getTotalUsers() + " Users");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        //add required events
        Events.addJfxButtonHover(submitButton);

        //setup fonts
        try {
            goGreen.setFont(Main.getReenieBeanie(100));
            lblLine1.setFont(Main.getReenieBeanie(40));
            lblLine2.setFont(Main.getReenieBeanie(40));
            lblLine3.setFont(Main.getReenieBeanie(50));
            line4.setFont(Main.getReenieBeanie(40));
            line5.setFont(Main.getReenieBeanie(40));
            lblSaved.setFont(Main.getReenieBeanie(30));
            lblTotalUsers.setFont(Main.getReenieBeanie(30));
        } catch (IOException exp) {
            System.out.println("Something went wrong");
        }

        // Helps to Keep the image scaling in check

        background.fitWidthProperty().bind(graphics.widthProperty());
        background.fitHeightProperty().bind(graphics.heightProperty());

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
            if ( !(textElectricity.getText().isEmpty() && textOil.getText().isEmpty())
                    && !(textCarUsage.getText().isEmpty()) ) {

                int householdMembers = Integer.parseInt(houseHoldNo.getValue().toString());
                double dailyElectricityConsumption =
                        Double.parseDouble(textElectricity.getText()) / 365 / householdMembers;
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

                String response = Requests.instance.signupRequest(thisUser);
                if (response != null) {
                    if (response.equals("success")) {
                        try {
                            Dialog.show(
                                    "Questionnaire Complete",
                                    "Questionnaire Completed Successfully\n\n"
                                            + "You will be redirected to the SignIn Page.",
                                    "DISMISS", "sucess", false
                            );
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        StageSwitcher.signInUpSwitch(Main.getPrimaryStage(), Main.getSignIn());
                    }
                }
            } else {
                textFieldValidate(textOil);
                textFieldValidate(textElectricity);
                textFieldValidate(textCarUsage);
                try {
                    Dialog.show("Questionnaire Incomplete",
                            "Please Complete the Questionnaire", "DISMISS", "error",
                            false);
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
