package frontend;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
//import tools.CarbonCalculator;
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
    public static Scene createScene() {
        // grid setup
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        // How many people live in your household?
        TextField household = new TextField();
        Label labelHousehold = new Label("How many people live in your household?");
        Spinner<Integer> spinnerHousehold = new Spinner<>(1, 10, 1);
        GridPane.setConstraints(labelHousehold, 0, 0);
        GridPane.setConstraints(spinnerHousehold, 1, 0);
        grid.getChildren().addAll(labelHousehold, spinnerHousehold);

        // Electricity
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

        // Heating Oil
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

        // Car Size
        Label labelCarSize = new Label("Size of car");
        ComboBox<String> carSizes = new ComboBox<String>();
        carSizes.getItems().addAll("I don't own a car","small", "medium", "large");
        GridPane.setConstraints(labelCarSize, 0, 3);
        GridPane.setConstraints(carSizes, 1, 3);
        grid.getChildren().addAll(labelCarSize, carSizes);

        // Car usage
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

        // Meat and Dairy
        Label labelMeatAndDairy = new Label("How much meat/dairy do you eat?");
        ComboBox<String> meatAndDairiyOptions = new ComboBox<String>();
        meatAndDairiyOptions.getItems().addAll(
                "above average", "average", "below average", "lactovegetarian", "vegan");
        meatAndDairiyOptions.getSelectionModel().selectFirst();
        GridPane.setConstraints(labelMeatAndDairy, 0, 5);
        GridPane.setConstraints(meatAndDairiyOptions, 1, 5);
        grid.getChildren().addAll(labelMeatAndDairy, meatAndDairiyOptions);

        // Food Miles
        Label labelFoodMiles = new Label("How much of your food is produced locally?");
        ComboBox<String> foodMilesOptions = new ComboBox<String>();
        foodMilesOptions.getItems().addAll("very little", "average", "above average", "almost all");
        foodMilesOptions.getSelectionModel().selectFirst();
        GridPane.setConstraints(labelFoodMiles, 0, 6);
        GridPane.setConstraints(foodMilesOptions, 1, 6);
        grid.getChildren().addAll(labelFoodMiles, foodMilesOptions);

        // Organic Food
        Label labelOrganic = new Label("How much of the food that you eat is organic?");
        ComboBox<String> organicOptions = new ComboBox<String>();
        organicOptions.getItems().addAll("none", "some", "most", "all");
        organicOptions.getSelectionModel().selectFirst();
        GridPane.setConstraints(labelOrganic, 0, 7);
        GridPane.setConstraints(organicOptions, 1, 7);
        grid.getChildren().addAll(labelOrganic, organicOptions);


        // Processed Food
        Label labelProcesed = new Label(
                "How much of your food is packaged / processed (e.g. 'ready meals', tins)?");
        ComboBox<String> processedOptions = new ComboBox<String>();
        processedOptions.getItems().addAll(
                "above average", "average", "below average", "very little");
        processedOptions.getSelectionModel().selectFirst();
        GridPane.setConstraints(labelProcesed, 0, 8);
        GridPane.setConstraints(processedOptions, 1, 8);
        grid.getChildren().addAll(labelProcesed, processedOptions);

        // Submit button
        Button submit = new Button("Submit");
        GridPane.setConstraints(submit, 0, 9);
        grid.getChildren().add(submit);

        Scene questionnaire = new Scene(grid, General.getBounds()[0], General.getBounds()[1]);
        return questionnaire;
    }
}
