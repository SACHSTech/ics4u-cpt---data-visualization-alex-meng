package CPT;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;

public class Charts {

    public static Parent mainMenu(Stage primaryStage) {
        Label menu = new Label("Main Menu");

        Button viewAll = new Button("View All Entries");
        viewAll.setOnAction(e -> primaryStage.setScene(new Scene(createTable(Methods.All(), primaryStage), 350, 450)));

        TextField searchYear = new TextField("Year");
        searchYear.setMaxSize(140, TextField.USE_COMPUTED_SIZE);
        searchYear.setOnAction(
                e -> primaryStage.setScene(new Scene(byYear(searchYear.getText(), primaryStage), 350, 450)));

        TextField searchCountry = new TextField("Country");
        searchCountry.setMaxSize(140, TextField.USE_COMPUTED_SIZE);
        searchCountry.setOnAction(
                e -> primaryStage.setScene(new Scene(byCountry(searchCountry.getText(), primaryStage), 350, 450)));

        Button barSettings = new Button("Create Barchart");
        barSettings.setOnAction(e -> primaryStage.setScene(new Scene(barSettings(primaryStage), 300, 250)));

        TextField searchEntry = new TextField("Search Entry(Country Year)");
        searchEntry.setMaxSize(140, TextField.USE_COMPUTED_SIZE);
        searchEntry.setOnAction(
                e -> primaryStage.setScene(new Scene(searchEntry(searchEntry.getText(), primaryStage), 350, 450)));

        Label sorting = new Label("Sort By:");
        Button sort = new Button("Sort");

        sort.setOnAction(e -> primaryStage.setScene(new Scene(
                createTable(Methods.toOb(Methods.mergeSort(Methods.searchYear(searchYear.getText()))), primaryStage),
                350, 450)));

        Button lineSettings = new Button("Create LineChart");
        lineSettings.setOnAction(e -> primaryStage.setScene(new Scene(lineSettings(primaryStage), 300, 250)));

        Button summary = new Button("Summary");
        summary.setOnAction(e -> primaryStage.setScene(new Scene(summary(primaryStage), 300, 250)));

        VBox main = new VBox();
        main.getChildren().addAll(menu, viewAll, searchCountry, searchEntry, sorting, searchYear, sort, barSettings, lineSettings, summary);
        main.setAlignment(Pos.TOP_CENTER);

        return main;
    }

    public static Parent searchEntry(String input, Stage primaryStage) {
        String[] data = input.split(" ");

        Country C = Methods.getHMap().get(data[0]);
        C.setYear(data[1]);
        Label entry = new Label(C.toString());

        Button back = new Button("Back");
        back.setOnAction(e -> primaryStage.setScene(new Scene(mainMenu(primaryStage), 300, 250)));

        VBox box = new VBox();
        box.getChildren().addAll(entry, back);
        box.setAlignment(Pos.TOP_CENTER);

        return box;
    }

    public static Parent byYear(String year, Stage primaryStage) {
        ObservableList<Country> data = Methods.byYear(year);
        Parent table = createTable(data, primaryStage);

        return table;
    }

    public static Parent byCountry(String country, Stage primaryStage) {
        ObservableList<Country> data = Methods.byCountry(country);
        Parent table = createTable(data, primaryStage);

        return table;
    }

    public static Parent barSettings(Stage primaryStage) {
        VBox layout = new VBox();
        ArrayList<Country> list = Methods.getList();
        String Country;
        String prevC = "";
        Country C;
        ChoiceBox<Country> C1 = new ChoiceBox<Country>();
        ChoiceBox<Country> C2 = new ChoiceBox<Country>();
        ChoiceBox<Country> C3 = new ChoiceBox<Country>();
        TextField startY = new TextField("Start Year");
        TextField endY = new TextField("End Year");
        Button create = new Button("Create!");

        for (int intCount = 0; intCount < list.size(); intCount++) {
            C = list.get(intCount);
            Country = C.getNation();
            C = Methods.getHMap().get(Country);
            if (!Country.equals(prevC)) {
                C1.getItems().add(C);
                C2.getItems().add(C);
                C3.getItems().add(C);
            }
            prevC = Country;
        }

        create.setOnAction(e -> primaryStage.setScene(new Scene(createBar(startY.getText(), endY.getText(),
                C1.getValue(), C2.getValue(), C3.getValue(), primaryStage))));

        Button back = new Button("Back to Menu");
        back.setOnAction(e -> primaryStage.setScene(new Scene(mainMenu(primaryStage), 300, 250)));

        layout.getChildren().addAll(C1, C2, C3, startY, endY, create, back);
        layout.setAlignment(Pos.CENTER_LEFT);

        return layout;
    }

    public static Parent createBar(String dateStart, String dateEnd, Country Country1, Country Country2,
            Country Country3, Stage primaryStage) {
        ArrayList<String> years = new ArrayList<String>();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Population");
        BarChart chart = new BarChart(xAxis, yAxis);
        int intCount;

        for (intCount = Integer.parseInt(dateStart); intCount < Integer.parseInt(dateEnd); intCount++) {
            years.add(String.valueOf(intCount));
        }
        years.add(String.valueOf(intCount));

        xAxis.setCategories(Methods.toOb(years));

        XYChart.Series C1 = new XYChart.Series();
        C1.setName(Country1.getNation());

        for (intCount = 0; intCount < years.size(); intCount++) {
            Country1.setYear(years.get(intCount));
            C1.getData().add(new XYChart.Data(years.get(intCount), Long.parseLong(Country1.getPopulation())));
        }

        chart.getData().add(C1);

        XYChart.Series C2 = new XYChart.Series();
        C2.setName(Country2.getNation());

        for (intCount = 0; intCount < years.size(); intCount++) {
            Country2.setYear(years.get(intCount));
            C2.getData().add(new XYChart.Data(years.get(intCount), Long.parseLong(Country2.getPopulation())));
        }

        chart.getData().add(C2);

        XYChart.Series C3 = new XYChart.Series();
        C3.setName(Country3.getNation());

        for (intCount = 0; intCount < years.size(); intCount++) {
            Country3.setYear(years.get(intCount));
            C3.getData().add(new XYChart.Data(years.get(intCount), Long.parseLong(Country3.getPopulation())));
        }

        chart.getData().add(C3);

        Button back = new Button("Back to Menu");
        back.setOnAction(e -> primaryStage.setScene(new Scene(mainMenu(primaryStage), 300, 250)));

        Button Settings = new Button("Settings");
        Settings.setOnAction(e -> primaryStage.setScene(new Scene(barSettings(primaryStage), 300, 250)));

        VBox box = new VBox();

        box.getChildren().addAll(chart, back, Settings);

        return box;
    }

    public static Parent createTable(ObservableList<Country> data, Stage primaryStage) {

        TableColumn Nation = new TableColumn();
        Nation.setText("Country");
        Nation.setCellValueFactory(new PropertyValueFactory("Nation"));

        TableColumn Year = new TableColumn();
        Year.setText("Year");
        Year.setCellValueFactory(new PropertyValueFactory("year"));

        TableColumn Population = new TableColumn();
        Population.setText("Population");
        Population.setCellValueFactory(new PropertyValueFactory("population"));

        final TableView tableView = new TableView();
        tableView.setItems(data);
        tableView.getColumns().addAll(Nation, Year, Population);

        Button back = new Button("Back to Menu");
        back.setOnAction(e -> primaryStage.setScene(new Scene(mainMenu(primaryStage), 300, 250)));

        VBox box = new VBox();
        box.getChildren().addAll(tableView, back);

        return box;
    }

    public static Parent lineChart(String Country, Stage primaryStage) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Long> chart = new LineChart(xAxis, yAxis);
        ArrayList<Country> cData = Methods.listByCountry(Country);
        Country C;

        chart.setTitle(Country);
        xAxis.setLabel("Year");
        yAxis.setLabel("Population");

        XYChart.Series<String, Long> data = new XYChart.Series<String, Long>();

        for(int intCount = 0; intCount < cData.size(); intCount ++){
            C = cData.get(intCount);
            data.getData().add(new XYChart.Data <String, Long> (C.getYear(), Long.parseLong(C.getPopulation())));
        }

        chart.getData().add(data);

        Button back = new Button("Back to Menu");
        back.setOnAction(e -> primaryStage.setScene(new Scene(mainMenu(primaryStage), 300, 250)));

        Button Settings = new Button("Settings");
        Settings.setOnAction(e -> primaryStage.setScene(new Scene(lineSettings(primaryStage), 300, 250)));

        VBox box = new VBox();
        box.getChildren().addAll(chart, Settings, back);

        return box;
    }

    public static Parent lineSettings(Stage primaryStage){
        TextField Country = new TextField("Country");
        Button create = new Button("Create!");
        Button back = new Button("Back");
        back.setOnAction(e -> primaryStage.setScene(new Scene(mainMenu(primaryStage), 300, 250)));

        Country.setMaxSize(140, TextField.USE_COMPUTED_SIZE);

        create.setOnAction(e -> primaryStage.setScene(new Scene(lineChart(Country.getText(), primaryStage))));

        VBox box = new VBox();
        box.getChildren().addAll(Country, create, back);
        box.setAlignment(Pos.TOP_CENTER);

        return box;
    }

    public static Parent summary(Stage primaryStage){
        TextField year = new TextField("Year");
        Label avg = new Label("Average Population/Country By Year: ");
        Label pDense = new Label("Average Population Density By Year: ");
        Button find = new Button("Calculate Population");
        Button density = new Button("Calculate Density");
        Label countries = new Label("Countries: 242");
        
        find.setOnAction(e -> avg.setText("Average Population/Country By Year: " + Methods.avgByYear(year.getText())));
        density.setOnAction(e -> pDense.setText("Average Population Density By Year: " + Methods.popDensity(year.getText()) + "/km\u00B2"));
        year.setMaxSize(140, TextField.USE_COMPUTED_SIZE);

        Button back = new Button("Back");
        back.setOnAction(e -> primaryStage.setScene(new Scene(mainMenu(primaryStage), 300, 250)));

        HBox line = new HBox();
        line.getChildren().addAll(find, density);

        VBox box = new VBox();
        box.getChildren().addAll(avg, pDense, year, line, back);

        return box;
    }

}
