package appclient;
import dataparsingutils.DrugProgramCostsData;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

import static datarequesting.HTTPRequester.parseJSONResponse;
import static datarequesting.RequestBuilder.buildRefundInfoURL;

public class GUIApp extends Application {
    public void start(Stage stage) {
        var gridPane = new GridPane();
        var showGraphButton = new Button("Update Graph");
        var clearGraphButton = new Button("Clear Graph");
        var provinceField = new TextField();
        var yearField = new TextField();
        var programField = new TextField();
        var drugField = new TextField();
        var genderField = new TextField();
        NumberAxis xAxis = new NumberAxis(1,12,1);
        NumberAxis yAxis = new NumberAxis(10000, 1000000, 50000);
        LineChart<Number, Number> costChart = new LineChart<>(xAxis, yAxis);


        provinceField.setPromptText("Województwo");
        yearField.setPromptText("Rok");
        programField.setPromptText("Kod programu lekowego");
        drugField.setPromptText("Kod subst. aktywnej");
        genderField.setPromptText("Płeć (opcjonalnie)");

        gridPane.add(showGraphButton, 0, 1);
        gridPane.add(clearGraphButton, 1, 1);
        gridPane.add(provinceField, 0, 0);
        gridPane.add(yearField, 1, 0);
        gridPane.add(programField, 2, 0);
        gridPane.add(drugField,3,0);
        gridPane.add(genderField,4,0);
        gridPane.setHgap(5);
        gridPane.setVgap(15);
        gridPane.add(costChart,0,2,5, 3);
        showGraphButton.setOnAction(e -> {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            String province = provinceField.getText();
            String year = yearField.getText();
            String program = programField.getText();
            String drug = drugField.getText();
            String gender = genderField.getText();
            ArrayList<DrugProgramCostsData> dataList = parseJSONResponse(buildRefundInfoURL(province, year, program, drug, gender));
            for (DrugProgramCostsData data : dataList) {
                series.getData().add(new XYChart.Data<>(data.getMonth(), data.getRefund()));
            }
            costChart.getData().add(series);
        });
        clearGraphButton.setOnAction(e -> costChart.getData().clear());
        Scene scene = new Scene(gridPane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {launch(args);}
}
