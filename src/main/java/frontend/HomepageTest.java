package frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Homepage {

    /**.
     * returns the scene of the Homepage to Input Validation, once user is logged in
     * @return Scene of Homepage
     */
    public static Scene setHomepage() {
        BorderPane border = new BorderPane();
        HBox hbox = Homepage.addHBox();
        border.setTop(hbox);
        border.setLeft(Homepage.addVBox());
        border.setCenter(Homepage.addCenter());
        border.setRight(Homepage.addFlowPane());
        border.getStylesheets().add(Main.getCssHomepage());
        return new Scene(border, SetupStructure.getBounds()[0], SetupStructure.getBounds()[1]);
    }

    private static HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #1BBC2E;");

        Button buttonLogout = new Button("Logout");
        buttonLogout.setPrefSize(100, 20);
        hbox.setMargin(buttonLogout, new Insets(0, 0, 0, 100));
        StageSwitcher.buttonSwitch(buttonLogout, Main.getPrimaryStage(), Main.getSignIn());

        hbox.getChildren().addAll(buttonLogout);

        return hbox;
    }

    private static VBox addVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Text title = new Text("Activities");
        title.setFont(Font.font("Franklin Gothic Book", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        Hyperlink options[] = new Hyperlink[]{
                new Hyperlink("Food"),
                new Hyperlink("Transport"),
                new Hyperlink("Others"),
        };
        for (int i = 0; i < 3; i++) {
            vbox.setMargin(options[i], new Insets(0, 0, 0, 8));
            vbox.getChildren().add(options[i]);
        }

        return vbox;
    }

    private static FlowPane addFlowPane() {
        FlowPane flow = new FlowPane();
        flow.setPadding(new Insets(5, 0, 5, 0));
        flow.setVgap(4);
        flow.setHgap(4);
        flow.setPrefWrapLength(170); // preferred width allows for two columns
        flow.setStyle("-fx-background-color: DAE6F3;");

        return flow;
    }

    private static BorderPane addCenter() {
        BorderPane border = new BorderPane();
        border.setMaxWidth(600);
        border.setPadding(new Insets(20, 0, 20, 20));

        Button btnAdd = new Button("Add");
        Button btnDelete = new Button("Delete");
        Button btnMoveUp = new Button("Move Up");
        Button btnMoveDown = new Button("Move Down");

        btnAdd.setMaxWidth(Double.MAX_VALUE);
        btnDelete.setMaxWidth(Double.MAX_VALUE);
        btnMoveUp.setMaxWidth(Double.MAX_VALUE);
        btnMoveDown.setMaxWidth(Double.MAX_VALUE);

        VBox vbButtons = new VBox();
        vbButtons.setSpacing(10);
        vbButtons.setPadding(new Insets(0, 20, 10, 20));
        vbButtons.getChildren().addAll(btnAdd, btnDelete, btnMoveUp, btnMoveDown);


        /////
        Button btnApply = new Button("Apply");
        Button btnContinue = new Button("Continue");
        Button btnExit = new Button("Exit");
        btnExit.setStyle("-fx-font-size: 15pt;");

        btnApply.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnContinue.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnExit.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        TilePane tileButtons = new TilePane(Orientation.HORIZONTAL);
        tileButtons.setPadding(new Insets(20, 10, 20, 0));
        tileButtons.setHgap(10.0);
        tileButtons.setVgap(8.0);
        tileButtons.getChildren().addAll(btnApply, btnContinue, btnExit);

        /////


        ListView<String> lvList = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList (
                "Hot dog", "Hamburger", "French fries",
                "Carrot sticks", "Chicken salad");
        lvList.setItems(items);
        lvList.setMaxHeight(Control.USE_PREF_SIZE);


        /////

        btnMoveDown.setMinWidth(Control.USE_PREF_SIZE);


        /////
        border.setRight(vbButtons);
        border.setCenter(lvList);
        border.setBottom(tileButtons);

        return border;
    }
}
