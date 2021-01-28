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

/**
 * PopApp.java
 * Contains all of the javafx UIs
 * Each page has a back to menu button(except for the main menu)
 * Makes all the charts and tables for the app
 */

public class PopApp {

    /**
     * mainMenu
     * The first page on start-up
     * Allows access to all other pages
     * @param primaryStage
     * @return Main Menu Page
     */
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

        Label sorting = new Label("Sort: ");
        Button sort = new Button("Sort");

        sort.setOnAction(e -> primaryStage.setScene(new Scene(
                createTable(Methods.toOb(Methods.mergeSort(Methods.searchYear(searchYear.getText()))), primaryStage),
                350, 450)));

        Button lineSettings = new Button("Create LineChart");
        lineSettings.setOnAction(e -> primaryStage.setScene(new Scene(lineSettings(primaryStage), 300, 250)));

        Button summary = new Button("Summary");
        summary.setOnAction(e -> primaryStage.setScene(new Scene(summary(primaryStage), 450, 250)));

        HBox line = new HBox();
        line.getChildren().addAll(barSettings, lineSettings);
        line.setAlignment(Pos.CENTER);

        VBox main = new VBox();
        main.getChildren().addAll(menu, viewAll, searchCountry, searchEntry, sorting, searchYear, sort, line, summary);
        main.setAlignment(Pos.TOP_CENTER);

        return main;
    }

    /**
     * searchEntry
     * Takes input in the format "Country Year"
     * Displays specific entry
     * Has a back button
     * @param input
     * @param primaryStage
     * @return entry
     */
    public static Parent searchEntry(String input, Stage primaryStage) {
        String[] data = input.split(" ");

        Country c = Methods.getHMap().get(data[0]);
        c.setYear(data[1]);
        Label entry = new Label(c.toString());

        Button back = new Button("Back");
        back.setOnAction(e -> primaryStage.setScene(new Scene(mainMenu(primaryStage), 300, 250)));

        VBox layout = new VBox();
        layout.getChildren().addAll(entry, back);
        layout.setAlignment(Pos.TOP_CENTER);

        return layout;
    }

    /**
     * byYear
     * Displays a table of entries matching input year
     * Sorted by alphabetical order(default because of data set)
     * @param year
     * @param primaryStage
     * @return table
     */
    public static Parent byYear(String year, Stage primaryStage) {
        ObservableList<Country> data = Methods.byYear(year);
        Parent table = createTable(data, primaryStage);

        return table;
    }

    /**
     * byCountry
     * Displays a table of entries matching input country
     * Sorted by earliest entry to latest(default because of data set)
     * @param country
     * @param primaryStage
     * @return table
     */
    public static Parent byCountry(String country, Stage primaryStage) {
        ObservableList<Country> data = Methods.byCountry(country);
        Parent table = createTable(data, primaryStage);

        return table;
    }

    /**
     * barSettings
     * Settings menu for barchart
     * Create button sends data to createBar
     * @param primaryStage
     * @return layout
     */
    public static Parent barSettings(Stage primaryStage) {
        VBox layout = new VBox();
        ArrayList<Country> list = Methods.getList();
        String cName;
        String prevC = "";
        Country country;
        ChoiceBox<Country> c1 = new ChoiceBox <Country>();
        c1.setMaxSize(140, ChoiceBox.USE_COMPUTED_SIZE);

        ChoiceBox<Country> c2 = new ChoiceBox <Country>();
        c2.setMaxSize(140, ChoiceBox.USE_COMPUTED_SIZE);

        ChoiceBox<Country> c3 = new ChoiceBox <Country>();
        c3.setMaxSize(140, ChoiceBox.USE_COMPUTED_SIZE);

        TextField startY = new TextField("Start Year");
        startY.setMaxSize(140, TextField.USE_COMPUTED_SIZE);

        TextField endY = new TextField("End Year");
        endY.setMaxSize(140, TextField.USE_COMPUTED_SIZE);

        Button create = new Button("Create!");
        Label settingLabel = new Label("BarChart Settings");

        for (int intCount = 0; intCount < list.size(); intCount++) {
            country = list.get(intCount);
            cName = country.getNation();
            country = Methods.getHMap().get(cName);

            if (!cName.equals(prevC)) {
                c1.getItems().add(country);
                c2.getItems().add(country);
                c3.getItems().add(country);
            }

            prevC = cName;
        }

        create.setOnAction(e -> primaryStage.setScene(new Scene(createBar(startY.getText(), endY.getText(),
                c1.getValue(), c2.getValue(), c3.getValue(), primaryStage))));

        Button back = new Button("Back to Menu");
        back.setOnAction(e -> primaryStage.setScene(new Scene(mainMenu(primaryStage), 300, 250)));

        layout.getChildren().addAll(settingLabel, c1, c2, c3, startY, endY, create, back);
        layout.setAlignment(Pos.CENTER);

        return layout;
    }

    /**
     * createBar
     * Gets input from barSettings
     * Has a back to settings button
     * Creates and displays BarChart given:
     * @param dateStart
     * @param dateEnd
     * @param country1
     * @param country2
     * @param country3
     * @param primaryStage
     * @return barChart
     */
    public static Parent createBar(String dateStart, String dateEnd, Country country1, Country country2,
            Country country3, Stage primaryStage) {
        ArrayList<String> years = new ArrayList<String>();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Population");
        BarChart <CategoryAxis, NumberAxis> chart = new BarChart(xAxis, yAxis);
        int intCount;

        for (intCount = Integer.parseInt(dateStart); intCount < Integer.parseInt(dateEnd); intCount++) {
            years.add(String.valueOf(intCount));
        }

        years.add(String.valueOf(intCount));

        xAxis.setCategories(Methods.toOb(years));

        XYChart.Series c1 = new XYChart.Series<>();
        c1.setName(country1.getNation());

        for (intCount = 0; intCount < years.size(); intCount++) {
            country1.setYear(years.get(intCount));
            c1.getData().add(new XYChart.Data(years.get(intCount), Long.parseLong(country1.getPopulation())));
        }

        chart.getData().add(c1);

        XYChart.Series c2 = new XYChart.Series<>();
        c2.setName(country2.getNation());

        for (intCount = 0; intCount < years.size(); intCount++) {
            country2.setYear(years.get(intCount));
            c2.getData().add(new XYChart.Data(years.get(intCount), Long.parseLong(country2.getPopulation())));
        }

        chart.getData().add(c2);

        XYChart.Series c3 = new XYChart.Series<>();
        c3.setName(country3.getNation());

        for (intCount = 0; intCount < years.size(); intCount++) {
            country3.setYear(years.get(intCount));
            c3.getData().add(new XYChart.Data(years.get(intCount), Long.parseLong(country3.getPopulation())));
        }

        chart.getData().add(c3);

        Button back = new Button("Back to Menu");
        back.setOnAction(e -> primaryStage.setScene(new Scene(mainMenu(primaryStage), 300, 250)));

        Button Settings = new Button("Settings");
        Settings.setOnAction(e -> primaryStage.setScene(new Scene(barSettings(primaryStage), 300, 250)));

        VBox box = new VBox();

        box.getChildren().addAll(chart, back, Settings);

        return box;
    }

    /**
     * createTable
     * Creates and displays a table given an observable list of data
     * @param data
     * @param primaryStage
     * @return table
     */
    public static Parent createTable(ObservableList<Country> data, Stage primaryStage) {

        TableColumn <String, String> nation = new TableColumn <String, String>();
        nation.setText("Country");
        nation.setCellValueFactory(new PropertyValueFactory <String, String> ("Nation"));

        TableColumn <String, String> year = new TableColumn <String, String>();
        year.setText("Year");
        year.setCellValueFactory(new PropertyValueFactory <String, String> ("year"));

        TableColumn <String, String> population = new TableColumn <String, String>();
        population.setText("Population");
        population.setCellValueFactory(new PropertyValueFactory <String, String> ("population"));

        final TableView tableView = new TableView();
        tableView.setItems(data);
        tableView.getColumns().addAll(nation, year, population);

        Button back = new Button("Back to Menu");
        back.setOnAction(e -> primaryStage.setScene(new Scene(mainMenu(primaryStage), 300, 250)));

        VBox layout = new VBox();
        layout.getChildren().addAll(tableView, back);

        return layout;
    }

    /**
     * lineChart
     * Creates and displays line chart given a country from lineSettings
     * Displays population changes from earliest to latest entry
     * Has a back to settings button
     * @param country
     * @param primaryStage
     * @return lineChart
     */
    public static Parent lineChart(String country, Stage primaryStage) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Long> chart = new LineChart(xAxis, yAxis);
        ArrayList<Country> cData = Methods.listByCountry(country);
        Country c;

        chart.setTitle(country);
        xAxis.setLabel("Year");
        yAxis.setLabel("Population");

        XYChart.Series <String, Long> data = new XYChart.Series <String, Long>();
        data.setName(country);

        for (int intCount = 0; intCount < cData.size(); intCount ++) {
            c = cData.get(intCount);
            data.getData().add(new XYChart.Data <String, Long> (c.getYear(), Long.parseLong(c.getPopulation())));
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

    /**
     * lineSettings
     * Settings menu for lineChart
     * Create button sends data(One Country) to lineChart
     * @param primaryStage
     * @return layout
     */
    public static Parent lineSettings(Stage primaryStage) {
        TextField country = new TextField("Country");
        Button create = new Button("Create!");
        Button back = new Button("Back");
        back.setOnAction(e -> primaryStage.setScene(new Scene(mainMenu(primaryStage), 300, 250)));

        country.setMaxSize(140, TextField.USE_COMPUTED_SIZE);

        create.setOnAction(e -> primaryStage.setScene(new Scene(lineChart(country.getText(), primaryStage))));

        VBox layout = new VBox();
        layout.getChildren().addAll(country, create, back);
        layout.setAlignment(Pos.TOP_CENTER);

        return layout;
    }

    /**
     * summary
     * A displays a page summarizing the data set
     * @param primaryStage
     * @return layout(summary)
     */
    public static Parent summary(Stage primaryStage) {
        TextField year = new TextField("Year");
        Label avg = new Label("Average Population/Country By Year: ");
        Label pDense = new Label("Average Population Density By Year: ");
        Button find = new Button("Calculate Population");
        Button density = new Button("Calculate Density");
        Label countries = new Label("Countries: 242");
        Label entries = new Label("Entries: 46,883");
        Label other = new Label("Including Continents and the World");
        Label date = new Label("Data Last Updated: 2019");
        
        find.setOnAction(e -> avg.setText("Average Population/Country By Year: " + Methods.avgByYear(year.getText())));
        density.setOnAction(e -> pDense.setText("Average Population Density By Year: " + Methods.popDensity(year.getText()) + " people/km\u00B2"));
        year.setMaxSize(140, TextField.USE_COMPUTED_SIZE);

        Button back = new Button("Back");
        back.setOnAction(e -> primaryStage.setScene(new Scene(mainMenu(primaryStage), 300, 250)));

        HBox line = new HBox();
        line.getChildren().addAll(find, density);

        VBox layout = new VBox();
        layout.getChildren().addAll(avg, pDense, year, line, countries, entries, other, date, back);

        return layout;
    }

}
