package frontend.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {
    @FXML
    private JFXHamburger menu;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane headerPane;
    @FXML
    private PieChart chart1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        chart1.setData(fillPieChart());
        chart1.setLegendSide(Side.LEFT);
        try {
            NotificationPanelController.addNotificationPanel(headerPane, mainPane);
            NavPanelController.setup(drawer, menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ObservableList<PieChart.Data> fillPieChart() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Food", 1),
                        new PieChart.Data("Transportation", 2),
                        new PieChart.Data("Household", 3)
                );


        return pieChartData;
    }
}
