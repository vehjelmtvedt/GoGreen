package frontend.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import data.Activity;
import data.BuyLocallyProducedFood;
import data.BuyNonProcessedFood;
import data.BuyOrganicFood;
import data.EatVegetarianMeal;
import data.User;
import frontend.controllers.ActivitiesController;
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
     * Add activities to the user upon clicking
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
                BuyLocallyProducedFood food = new BuyLocallyProducedFood();
                food.performActivity(loggedUser);
            } else if (type == 3) {
                BuyOrganicFood food = new BuyOrganicFood();
                food.performActivity(loggedUser);
            } else if (type == 4) {
                BuyNonProcessedFood food = new BuyNonProcessedFood();
                food.performActivity(loggedUser);
            } else {
                if (type == 5) {
                    // TODO: 20/03/2019
                }
            }
            ObservableList<Activity> activities = ActivitiesController.getActivities(loggedUser);
            activityTable.setItems(activities);
        });
    }

    /**
     * .
     * Add activities to the user upon clicking
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
                if (input != null) {
                    distance = Integer.parseInt(input.getText());
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            if (distance == -1) {
                verify.setVisible(true);
            } else {
                verify.setVisible(false);
                input.setText(null);
                input.setPromptText("number of km");
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
}
