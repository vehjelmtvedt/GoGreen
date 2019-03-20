package frontend.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Events {
    public static void addImageSceneSwitch(ImageView image, Scene nextScene) {
        image.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->
                StageSwitcher.sceneSwitch(Main.getPrimaryStage(), nextScene));
    }

    /**.
     * Add styledCursor on ImageViews
     * @param image image to add events to
     */
    public static void addImageCursor(ImageView image) {
        image.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            image.setCursor(Cursor.HAND);
        });
        image.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            image.setCursor(Cursor.DEFAULT);
        });
    }

    /**.
     * Add styledCursor on Buttons
     * @param button button to add events to
     */
    public static void addButtonCursor(JFXButton button) {
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            button.setCursor(Cursor.HAND);
        });
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            button.setCursor(Cursor.DEFAULT);
        });
    }

    /**.
     * Add styledCursor on menu burger
     * @param menu menu to add events to
     */
    public static void addMenuCursor(JFXHamburger menu) {
        menu.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            menu.setCursor(Cursor.HAND);
        });
        menu.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            menu.setCursor(Cursor.DEFAULT);
        });
    }

    /**.
     * Add hover events to button
     * @param button button to style
     */
    public static void addButtonHover(JFXButton button) {
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            button.setStyle("-fx-background-color: #e2e0e0;");
        });
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            button.setStyle("-fx-background-color: transparent;");
        });
    }
}
