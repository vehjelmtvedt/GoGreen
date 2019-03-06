package frontend;

import backend.data.LoginDetails;
import backend.data.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class Questionnaire {

    /**
     * Creates the questionnaire scene,
     * where users provides information necessary to calculate their carbon emissions.
     * @return questionnaire scene
     */
    public static Scene createScene(User user, GridPane form) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        TextField household = new TextField();
        Label labelHousehold = new Label("How many people live in your household?");
        Spinner<Integer> spinnerHousehold = new Spinner<>(1, 10, 1);
        GridPane.setConstraints(labelHousehold, 0, 0);
        GridPane.setConstraints(spinnerHousehold, 1, 0);
        grid.getChildren().addAll(labelHousehold, spinnerHousehold);
        Label labelElectricity = new Label(
                "How much electricity is used in our household? (kWh per year)");
        TextField textElectricity = new TextField();
        GridPane.setConstraints(labelElectricity, 0, 1);
        GridPane.setConstraints(textElectricity, 1, 1);
        grid.getChildren().addAll(labelElectricity, textElectricity);
        textElectricity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("^[0-9]{0,7}$")) {
                    textElectricity.setText(oldValue);
                }
            }
        });
        Label labelOil = new Label(
                "How much heating oil is used in our household? (litres per year)");
        TextField textOil = new TextField();
        textOil.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("^[0-9]{0,7}$")) {
                    textOil.setText(oldValue);
                }
            }
        });
        GridPane.setConstraints(labelOil, 0, 2);
        GridPane.setConstraints(textOil, 1, 2);
        grid.getChildren().addAll(labelOil, textOil);
        Label labelCarSize = new Label("Size of car");
        ComboBox<String> carSizes = new ComboBox<String>();
        carSizes.getItems().addAll("I don't own a car","small", "medium", "large");
        GridPane.setConstraints(labelCarSize, 0, 3);
        GridPane.setConstraints(carSizes, 1, 3);
        grid.getChildren().addAll(labelCarSize, carSizes);
        Label labelCarUsage = new Label("How many kilometres do you travel by car each year?");
        TextField textCarUsage = new TextField();
        GridPane.setConstraints(labelCarUsage, 0, 4);
        GridPane.setConstraints(textCarUsage, 1, 4);
        grid.getChildren().addAll(labelCarUsage, textCarUsage);
        textCarUsage.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("^[0-9]{0,7}$")) {
                    textCarUsage.setText(oldValue);
                }
            }
        });
        carSizes.valueProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String>
                                        composant, String oldValue, String newValue) {
                if (newValue.equals("I don't own a car")) {
                    textCarUsage.setText("0");
                    textCarUsage.setEditable(false);
                } else {
                    textCarUsage.setEditable(true);
                }
            }
        });
        Label labelMeatAndDairy = new Label("How much meat/dairy do you eat?");
        ComboBox<String> meatAndDairiyOptions = new ComboBox<String>();
        meatAndDairiyOptions.getItems().addAll(
                "above average", "average", "below average", "lacto-vegetarian", "vegan");
        meatAndDairiyOptions.getSelectionModel().selectFirst();
        GridPane.setConstraints(labelMeatAndDairy, 0, 5);
        GridPane.setConstraints(meatAndDairiyOptions, 1, 5);
        grid.getChildren().addAll(labelMeatAndDairy, meatAndDairiyOptions);
        Label labelFoodMiles = new Label("How much of your food is produced locally?");
        ComboBox<String> locallyProducedFoodOptions = new ComboBox<String>();
        locallyProducedFoodOptions.getItems().addAll(
                "very little", "average", "above average", "almost all");
        locallyProducedFoodOptions.getSelectionModel().selectFirst();
        GridPane.setConstraints(labelFoodMiles, 0, 6);
        GridPane.setConstraints(locallyProducedFoodOptions, 1, 6);
        grid.getChildren().addAll(labelFoodMiles, locallyProducedFoodOptions);
        Label labelOrganic = new Label("How much of the food that you eat is organic?");
        ComboBox<String> organicOptions = new ComboBox<String>();
        organicOptions.getItems().addAll("none", "some", "most", "all");
        organicOptions.getSelectionModel().selectFirst();
        GridPane.setConstraints(labelOrganic, 0, 7);
        GridPane.setConstraints(organicOptions, 1, 7);
        grid.getChildren().addAll(labelOrganic, organicOptions);
        Label labelProcesed = new Label(
                "How much of your food is packaged / processed (e.g. 'ready meals', tins)?");
        ComboBox<String> processedOptions = new ComboBox<String>();
        processedOptions.getItems().addAll(
                "above average", "average", "below average", "very little");
        processedOptions.getSelectionModel().selectFirst();
        GridPane.setConstraints(labelProcesed, 0, 8);
        GridPane.setConstraints(processedOptions, 1, 8);
        grid.getChildren().addAll(labelProcesed, processedOptions);
        Button submit = new Button("Submit");
        GridPane.setConstraints(submit, 0, 9);
        grid.getChildren().add(submit);
        submit.setOnAction(e -> {
            int householdMembers = spinnerHousehold.getValue();
            int dailyElectricityConsumption =
                    Integer.parseInt(textElectricity.getText()) / 365 / householdMembers;
            double dailyHeatingOilConsumption =
                    Integer.parseInt(textOil.getText()) / 365.0 / householdMembers;
            String carType = carSizes.getValue();
            int dailyCarKilometres = Integer.parseInt(textCarUsage.getText()) / 365;
            String meatAndDairyConsumption = meatAndDairiyOptions.getValue();
            String locallyProducedFoodConsumption = locallyProducedFoodOptions.getValue();
            String organicFoodConsumption = organicOptions.getValue();
            String processedFoodConsumption = processedOptions.getValue();
            user.setElectricityDailyConsumption(dailyElectricityConsumption);
            user.setHeatingOilDailyConsumption(dailyHeatingOilConsumption);
            user.setCarType(carType);
            user.setDailyCarKilometres(dailyCarKilometres);
            user.setMeatAndDairyConsumption(meatAndDairyConsumption);
            user.setLocallyProducedFoodConsumption(locallyProducedFoodConsumption);
            user.setOrganicFoodConsumption(organicFoodConsumption);
            user.setProcessedFoodConsumption(processedFoodConsumption);

            String response = Requests.sendRequest(2, new LoginDetails(), user);
            if (response != null) {
                if (response.equals("success")) {
                    System.out.println("Success!");
                    StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getHomepage());
                } else if (response.equals("username exists")) {
                    System.out.println("username exists");
                } else {
                    System.out.println("A user already exists with this email.");
                }
            }
        });

        return new Scene(grid, General.getBounds()[0], General.getBounds()[1]);
    }
}

// used for debugging put them in the setOnAction method
//            System.out.println("household members: " + householdMembers);
//            System.out.println("daily electricity consumption: " + dailyElectricityConsumption);
//            System.out.println("daily oil consumption: " + dailyHeatingOilConsumption);
//            System.out.printf("You have a %s car\n", carType);
//            System.out.println("Kilometres per day: " + dailyCarKilometres);
//            System.out.println("Meat and Dairy consumption: " + meatAndDairyConsumption);
//            System.out.println("Locally produced food consumption: "
//            + locallyProducedFoodConsumption);
//            System.out.println("Organic Food Consumption: " + organicFoodConsumption);
//            System.out.println("Processed Food Consmption: " + processedFoodConsumption);
//            System.out.println("");

