package frontend.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import data.*;
import frontend.controllers.ActivitiesController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.List;

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
                if (input.getText() != null) {
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
                input.setText(null);
                input.setUnFocusColor(Color.rgb(77, 77, 77));
                input.setPromptText("number of km");
            }

            if (type == 1) {
                UseBikeInsteadOfCar travel = new UseBikeInsteadOfCar();
                travel.setKilometres(distance);
                travel.performActivity(loggedUser);
            } else if (type == 2) {
                // TODO
            } else if (type == 3) {
                // TODO
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
                filter.setDisable(  checkBox.isSelected());
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
                                    List<JFXRadioButton> radioList) {
        clear.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            for (JFXCheckBox filter : checkList) {
                filter.setDisable(false);
                filter.setSelected(false);
            }
            for (JFXRadioButton filter : radioList) {
                filter.setDisable(false);
                filter.setSelected(false);
            }
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
}

