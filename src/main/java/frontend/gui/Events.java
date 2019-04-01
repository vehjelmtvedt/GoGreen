package frontend.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import data.Activity;
import data.BuyLocallyProducedFood;
import data.BuyNonProcessedFood;
import data.BuyOrganicFood;
import data.EatVegetarianMeal;
import data.InstallSolarPanels;
import data.LowerHomeTemperature;
import data.UseBikeInsteadOfCar;
import data.UseBusInsteadOfCar;
import data.UseTrainInsteadOfCar;
import data.User;
import frontend.controllers.ActivitiesController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import tools.ActivityQueries;
import tools.DateUnit;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class Events {

    /**
     * .
     * add image cursor on hover
     *
     * @param image image to add hover event to
     */
    public static void addImageCursor(ImageView image) {
        image.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            image.setCursor(Cursor.HAND);
        });

        image.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            image.setCursor(Cursor.DEFAULT);
        });
    }

    /**
     * .
     * Add hover events to button
     *
     * @param button button to style
     */
    public static void addActivityHover(AnchorPane pane, JFXButton button) {
        pane.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            button.setStyle("-fx-background-color: #00db00;");
        });
        pane.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            button.setStyle("-fx-background-color: green;");
        });
    }

    /**
     * .
     * Add hover event for navigation panel buttons
     *
     * @param button button to add hover to inside nav bar
     */
    public static void addNavButtonHover(Button button) {
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            button.setOpacity(1);
        });
        button.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            button.setOpacity(0.75);
        });
    }

    /**.
     * Add hover event for JFX buttons
     * @param button - button to add hover event to
     */
    public static void addJfxButtonHover(JFXButton button) {
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            button.setOpacity(1);
        });
        button.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            button.setOpacity(0.75);
        });
    }

    /**
     * .
     * Add food activities to the user upon clicking
     *
     * @param pane          pane to be clicked
     * @param type          type of activity
     * @param loggedUser    user to update
     * @param activityTable table to set history to
     */
    public static void addFoodActivity(AnchorPane pane, int type,
                                       User loggedUser, TableView<Activity> activityTable) {
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (type == 1) {
                EatVegetarianMeal meal = new EatVegetarianMeal();
                meal.performActivity(loggedUser);
            } else if (type == 2) {
                BuyOrganicFood food = new BuyOrganicFood();
                food.performActivity(loggedUser);
            } else if (type == 3) {
                BuyLocallyProducedFood food = new BuyLocallyProducedFood();
                food.performActivity(loggedUser);
            } else {
                if (type == 4) {
                    BuyNonProcessedFood food = new BuyNonProcessedFood();
                    food.performActivity(loggedUser);
                }
            }
            ObservableList<Activity> activities = ActivitiesController.getActivities(loggedUser);
            activityTable.setItems(activities);
        });
    }

    /**
     * .
     * Add transport activities to the user upon clicking
     *
     * @param pane          pane to click
     * @param input         textfield with input
     * @param verify        input label for user verification
     * @param type          type of activity
     * @param loggedUser    user to modify
     * @param activityTable activity history table
     */
    public static void addTransportActivity(AnchorPane pane, JFXTextField input, Label verify,
                                            int type, User loggedUser,
                                            TableView<Activity> activityTable) {
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int distance = -1;
            try {
                if (!input.getText().equals("")) {
                    distance = Integer.parseInt(input.getText());
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            }

            if (distance == -1) {
                verify.setVisible(true);
                input.setUnFocusColor(Color.rgb(255, 0, 0));
                return;
            } else {
                verify.setVisible(false);
                input.setText("");
                input.setUnFocusColor(Color.rgb(77, 77, 77));
                input.setPromptText("number of km");
            }

            if (type == 1) {
                UseBikeInsteadOfCar travel = new UseBikeInsteadOfCar();
                travel.setKilometres(distance);
                travel.performActivity(loggedUser);
            } else if (type == 2) {
                UseBusInsteadOfCar travel = new UseBusInsteadOfCar();
                travel.setKilometres(distance);
                travel.performActivity(loggedUser);
            } else if (type == 3) {
                UseTrainInsteadOfCar travel = new UseTrainInsteadOfCar();
                travel.setKilometres(distance);
                travel.performActivity(loggedUser);
            }

            ObservableList<Activity> activities = ActivitiesController.getActivities(loggedUser);
            activityTable.setItems(activities);
        });
        input.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("^[0-9]{0,4}$")) {
                    input.setText(oldValue);
                }
            }
        });
    }

    /**.
     * Add household activities to the user upon clicking
     * @param pane          - pane to be clicked
     * @param type          - type of activity
     * @param loggedUser    - user to update
     * @param activityTable - table to set history to
     */
    public static void addHouseholdActivity(AnchorPane pane, Label installedPanels,
                                            Label loweredTemp, int type, User loggedUser,
                                            TableView<Activity> activityTable) {
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (type == 1) {
                InstallSolarPanels panels = new InstallSolarPanels();
                if (loggedUser.getSimilarActivities(panels).size() > 0) {
                    InstallSolarPanels installed = (InstallSolarPanels) loggedUser
                            .getSimilarActivities(new InstallSolarPanels()).get(0);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("GoGreen");
                    alert.setHeaderText("You have already installed solar panels!");
                    alert.setContentText("Total CO2 saved by your solar panels: "
                            + ChronoUnit.DAYS.between(loggedUser
                                            .getSimilarActivities(panels)
                                            .get(0).getDate().toInstant(),
                                    Calendar.getInstance().getTime().toInstant())
                                    * installed.getDailyCarbonSaved());
                    alert.showAndWait();
                } else {
                    TextInputDialog dialog = new TextInputDialog("0");
                    dialog.setTitle("Install Solar Panels");
                    dialog.setHeaderText(
                            "Amount of kwh that your solar panel installation produces per year: ");
                    dialog.setContentText("kwh:");
                    dialog.getEditor().textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(
                                ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                            if (!newValue.matches("^[0-9]{0,7}$")) {
                                dialog.getEditor().setText(oldValue);
                            }
                        }
                    });
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        System.out.println("kwh: " + result.get());
                        panels.setKwhSavedPerYear(Integer.parseInt(result.get()));
                        panels.performActivity(loggedUser);
                        installedPanels.setVisible(true);
                    }
                }
            } else {
                if (type == 2) {
                    LowerHomeTemperature temp = new LowerHomeTemperature();
                    if (temp.timesPerformedInTheSameDay(loggedUser) > 0) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Oops");
                        alert.setContentText(
                                "It looks like you already did this today,"
                                        + " you can try again tomorrow!");
                        alert.showAndWait();
                    } else {
                        List<String> choices = Arrays.asList( "1", "2", "3", "4", "5");
                        ChoiceDialog<String> dialog = new ChoiceDialog<>("1", choices);
                        dialog.setTitle("Lower Home Temperature");
                        dialog.setHeaderText("How many degrees did you turn your thermostat down?");
                        dialog.setContentText("Degrees:");
                        Optional<String> result = dialog.showAndWait();
                        if (result.isPresent()) {
                            System.out.println("Degrees: " + result.get());
                            temp.setDegrees(Integer.parseInt(result.get()));
                            temp.performActivity(loggedUser);
                            loweredTemp.setVisible(true);
                        }
                    }
                }
            }

            ObservableList<Activity> activities = ActivitiesController.getActivities(loggedUser);
            activityTable.setItems(activities);
        });
    }

    /**
     * .
     * Add logout event handling
     *
     * @param image     image to add logout option to
     * @param nextScene Scene after logout
     */
    public static void addLogout(ImageView image, Scene nextScene) {
        image.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            image.setImage(new Image("frontend/Pics/logoutIconOpen.png"));
            ConfirmBox.logout(Main.getPrimaryStage(), nextScene, image, "Are"
                    + "you sure you want to logout?");
        });
    }

    /**
     * .
     * Add hover event on labels for filtering
     *
     * @param label - the label to be edited
     */
    public static void addHoverOnFilter(Label label) {
        label.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            label.setUnderline(true);
            label.setOpacity(1);
        });
        label.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            label.setUnderline(false);
            label.setOpacity(0.75);
        });
    }

    /**
     * .
     * Display all activities
     *
     * @param checkBox  - checkbox to add event to
     * @param checkList - list containing category filtering
     * @param radioList - list containing date filtering
     */
    public static void showAllFilters(JFXCheckBox checkBox, List<JFXCheckBox> checkList,
                                      List<JFXRadioButton> radioList) {
        checkBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            for (JFXCheckBox filter : checkList) {
                filter.setDisable(checkBox.isSelected());
            }
            for (JFXRadioButton filter : radioList) {
                filter.setDisable(checkBox.isSelected());
            }
            checkBox.setDisable(false);
        });
    }

    /**
     * .
     * Clear all activity history filters
     *
     * @param clear     - label to add event to
     * @param checkList - list containing category filtering
     * @param radioList - list containing date filtering
     */
    public static void clearFilters(Label clear, List<JFXCheckBox> checkList,
                                    List<JFXRadioButton> radioList,
                                    JFXTextField min, JFXTextField max,
                                    User loggedUser,
                                    TableView<Activity> activityTable) {
        clear.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            for (JFXCheckBox filter : checkList) {
                filter.setDisable(false);
                filter.setSelected(false);
            }
            for (JFXRadioButton filter : radioList) {
                filter.setDisable(false);
                filter.setSelected(false);
            }
            max.setUnFocusColor(Color.rgb(77, 77, 77));
            max.setFocusColor(Color.rgb(0, 128, 0));
            max.setText("");

            min.setUnFocusColor(Color.rgb(77, 77, 77));
            min.setFocusColor(Color.rgb(0, 128, 0));
            min.setText("");

            ObservableList<Activity> activities = ActivitiesController.getActivities(loggedUser);
            activityTable.setItems(activities);
        });
    }

    /**
     * .
     * Add Radio toggling on radio buttons
     *
     * @param radioList - radio list to apply event to
     */
    public static void addRadioToggle(List<JFXRadioButton> radioList) {
        for (JFXRadioButton filter : radioList) {
            filter.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                for (JFXRadioButton other : radioList) {
                    if (!other.equals(filter)) {
                        other.setSelected(false);
                    }
                }
            });
        }
    }

    private static List<Activity> applyCategoryFilters(List<Activity> activities,
                                             List<JFXCheckBox> checkList) {
        ActivityQueries activityQueries = new ActivityQueries(activities);
        List<String> categoryFilters = new ArrayList<>();
        for (JFXCheckBox filter : checkList) {
            if (filter.isSelected()) {
                categoryFilters.add(filter.getText());
            }
        }
        return activityQueries.filterActivitiesByCategories(categoryFilters);
    }

    private static List<Activity> applyTimeFilters(List<Activity> activities,
                                         List<JFXRadioButton> radioList) {
        ActivityQueries activityQueries = new ActivityQueries(activities);
        for (JFXRadioButton filter : radioList) {
            if (filter.isSelected()) {
                if (filter.getText().contains("Today")) {
                    activities = activityQueries.filterActivitiesByDate(DateUnit.TODAY);
                } else if (filter.getText().contains("7")) {
                    activities = activityQueries.filterActivitiesByDate(DateUnit.WEEK);
                } else {
                    if (filter.getText().contains("30")) {
                        activities = activityQueries.filterActivitiesByDate(DateUnit.MONTH);
                    }
                }
            }
        }

        return activities;
    }

    private static List<Activity> applyCarbonFilters(List<Activity> activities,
                                           JFXTextField min, JFXTextField max) {
        ActivityQueries activityQueries = new ActivityQueries(activities);

        if (!min.getText().equals("") && !max.getText().equals("")) {
            double minValue = Integer.parseInt(min.getText());
            double maxValue = Integer.parseInt(max.getText());
            if (minValue > maxValue) {
                max.setUnFocusColor(Color.rgb(255, 0, 0));
                max.setFocusColor(Color.rgb(255, 0, 0));
                min.setUnFocusColor(Color.rgb(255, 0, 0));
                min.setFocusColor(Color.rgb(255, 0, 0));
            } else {
                max.setUnFocusColor(Color.rgb(77, 77, 77));
                max.setFocusColor(Color.rgb(0, 128, 0));
                min.setUnFocusColor(Color.rgb(77, 77, 77));
                min.setFocusColor(Color.rgb(0, 128, 0));
                return activityQueries.filterActivitiesByCO2Saved(minValue, maxValue);
            }
        }
        return activities;
    }

    /**
     * .
     * Apply the selected filters to the activity history table
     *
     * @param label         - label to add event handle to
     * @param checkList     - list containing category checkboxes
     * @param radioList     - list containing time radio buttons
     * @param loggedUser    - the logged in user
     * @param activityTable - the table to reset with filters
     */
    public static void applyFilters(Label label, List<JFXCheckBox> checkList,
                                    List<JFXRadioButton> radioList, JFXTextField min,
                                    JFXTextField max, User loggedUser,
                                    TableView<Activity> activityTable) {
        label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            List<Activity> activities = loggedUser.getActivities();
            activities = applyCarbonFilters(applyTimeFilters(
                    applyCategoryFilters(activities, checkList), radioList), min, max);
            ObservableList<Activity> filteredActivities =
                    FXCollections.observableArrayList(activities);
            activityTable.setItems(filteredActivities);
        });
    }

    /**.
     * Add leaderboards events to the the leaderboards buttons
     * @param leaderboards - list containing buttons for all types of leaderboards
     */
    public static void addLeaderboards(List<JFXButton> leaderboards) {
        for (JFXButton button : leaderboards) {
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                button.setStyle("-fx-background-color: #00db00;");
                for (JFXButton otherButton : leaderboards) {
                    if (!otherButton.equals(button)) {
                        otherButton.setStyle("-fx-background-color: transparent;");
                    }
                }
            });

            button.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                button.setUnderline(true);
            });

            button.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                button.setUnderline(false);
            });
        }
    }
}

