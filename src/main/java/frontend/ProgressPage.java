package frontend;

import backend.data.Progress;
import backend.data.User;
import backend.data.UserAchievement;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Date;


public class ProgressPage {

    /**
     * creates progress scene.
     *
     * @return the scene
     */

    public static Scene createScene() {

        BorderPane borderPane = new BorderPane();

        borderPane.setPadding(new Insets(10, 10, 10, 10));

        GridPane cm = new GridPane();

        borderPane.setCenter(cm);

        cm.setVgap(10);
        cm.setHgap(10);

        //user get progress/level/score
        //for now filler content is used
        User user = new User();

        Progress progress = user.getProgress();
        UserAchievement test = new UserAchievement(1, true, new Date(1999, 02, 22));
        progress.getAchievements().add(test);


        HBox hboxtop = new HBox();

        Label labeltop = new Label("Current Progress");

        hboxtop.getChildren().add(labeltop);

        borderPane.setTop(hboxtop);

        int countcompleted = 0;
        for (int i = 0; i < progress.getAchievements().size(); i++) {

            countcompleted++;
            Text temp = new Text(progress.getAchievements().get(i).toString());
            temp.setFill(Color.GREEN);
            cm.add(temp, 2, i + 1);


        }

        Label completedtab = new Label("Completed");
        completedtab.setFont(Font.font("Verdana", FontWeight.BOLD, 10));


        if (countcompleted != 0) {

            cm.add(completedtab, 1, 1);

        }

        int levelint = user.getProgress().getLevel();

        Label level = new Label("You are level" + levelint);
        ImageView badge = new ImageView();
        String badgepath = "badges/" + levelint + ".png";

        //   URL url = this.getClass().getResource(badgepath);
        Image image = new Image(badgepath);
        badge.setImage(image);

        VBox right = new VBox();
        right.getChildren().addAll(level, badge);

        borderPane.setRight(right);

        Scene page = new Scene(borderPane, 800, 400);

        //   page.getStylesheets().add("/frontend/Style.css");

        return page;


    }

}
