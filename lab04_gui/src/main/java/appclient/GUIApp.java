package appclient;
import dataparsingutils.DrugProgramCostsData;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

import static datarequesting.HTTPRequester.parseJSONResponse;
import static datarequesting.RequestBuilder.buildRefundInfoURL;
// java --module-path "C:\javafx-sdk-21.0.5\lib" --add-modules javafx.controls,javafx.fxml -jar C:\Users\Phario\IdeaProjects\lab04\lab04_gui\target\app-1.0-SNAPSHOT.jar

//java --module-path "C:\javafx-sdk-21.0.5\lib" --add-modules javafx.controls,javafx.fxml -jar C:\Users\Phario\IdeaProjects\lab04\lab04_gui\target\lab04_gui.jar
public class GUIApp extends Application {
    private static int graphNumber = 1;
    public void start(Stage stage) {
        var gridPane = new GridPane();
        var updateGraphButton = new Button("Aktualizuj wykres");
        var clearGraphButton = new Button("Wyczyść wykres");
        var provinceField = new TextField();
        var yearField = new TextField();
        var programField = new TextField();
        var drugField = new TextField();
        var genderField = new TextField();
        var provinceTip = new Tooltip();
        var genderTip = new Tooltip();

        NumberAxis xAxis = new NumberAxis(1,12,1);
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Miesiąc");
        yAxis.setLabel("Kwota refundacji");
        LineChart<Number, Number> costChart = new LineChart<>(xAxis, yAxis);
        costChart.setTitle("Kwota refundacji w danym miesiącu");

        CategoryAxis xBar = new CategoryAxis();
        NumberAxis yBar = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xBar, yBar);
        barChart.setTitle("Liczba osób uczestniczących w podanym programie lekowym");
        provinceTip.setText("Dostępne wartości:\n" +
                "01 – dolnośląskie\n" +
                "02 – kujawsko-pomorskie\n" +
                "03 – lubelskie\n" +
                "04 – lubuskie\n" +
                "05 - łódzkie\n" +
                "06 – małopolskie\n" +
                "07 – mazowieckie\n" +
                "08 – opolskie\n" +
                "09 – podkarpackie\n" +
                "10 – podlaskie\n" +
                "11 – pomorskie\n" +
                "12 – śląskie\n" +
                "13 – świętokrzyskie\n" +
                "14 – warmińsko-mazurskie\n" +
                "15 – wielkopolskie\n" +
                "16 – zachodniopomorskie");
        provinceField.setPromptText("Województwo");
        provinceField.setTooltip(provinceTip);
        yearField.setPromptText("Rok");
        programField.setPromptText("Kod programu lekowego");
        drugField.setPromptText("Kod subst. aktywnej");
        genderField.setPromptText("Płeć (opcjonalnie)");
        genderTip.setText("M || F");
        genderField.setTooltip(genderTip);

        gridPane.add(updateGraphButton, 0, 1);
        gridPane.add(clearGraphButton, 1, 1);
        gridPane.add(provinceField, 0, 0);
        gridPane.add(yearField, 1, 0);
        gridPane.add(programField, 2, 0);
        gridPane.add(drugField,3,0);
        gridPane.add(genderField,4,0);
        gridPane.setHgap(5);
        gridPane.setVgap(15);
        gridPane.add(costChart,0,2,5, 3);
        gridPane.add(barChart, 0, 5, 5, 3);
        String barLabel = String.valueOf(graphNumber);
        updateGraphButton.setOnAction(e -> {
            int patients = 0;
            XYChart.Series<Number, Number> chartSeries = new XYChart.Series<>();
            String province = provinceField.getText();
            String year = yearField.getText();
            String program = programField.getText();
            String drug = drugField.getText();
            String gender = genderField.getText();
            ArrayList<DrugProgramCostsData> dataList = parseJSONResponse(buildRefundInfoURL(province, year, program, drug, gender));
            for (DrugProgramCostsData data : dataList) {
                chartSeries.getData().add(new XYChart.Data<>(data.getMonth(), data.getRefund()));
                patients += data.getNumberOfPatients();
            }
            chartSeries.setName(graphNumber + ": " + province + " " + year + " " + gender);
            costChart.getData().add(chartSeries);


            XYChart.Series<String, Number> barSeries = new BarChart.Series<>();
            barSeries.getData().add(new BarChart.Data<>(barLabel, patients));
            barChart.getData().add(barSeries);
            graphNumber++;
        });
        clearGraphButton.setOnAction(e -> {
            costChart.getData().clear();
            barChart.getData().clear();
        });
        Scene scene = new Scene(gridPane, 800, 600);
        stage.setTitle("Client App NFZ");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {launch(args);}
}
