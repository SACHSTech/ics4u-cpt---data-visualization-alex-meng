package CPT;

import java.io.*;
import CPT.Country;
import CPT.Methods;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
//import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.ChoiceBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Population extends Application {

    public static void main(String[] args) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader("src/CPT/population.csv"));
        BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
        HashMap<String, Country> Nations = new HashMap<String, Country>();
        ArrayList<Country> Countries = new ArrayList<Country>();
        String cName = " ";
        String Year;
        String row;
        String Country;
        String prevC = "";
        int intCount = 0;

        csvReader.readLine();

        while ((row = csvReader.readLine()) != null) {
            String data[] = row.split(",");
            Country = data[0];
            Countries.add(new Country(data[0], data[2], data[3]));
            if (!Country.equals(prevC)) {
                Nations.put(data[0], new Country(data[0], data[2], data[3]));
            }
            if(Country.equals(prevC)){
                Nations.get(Country).addYearPop(data[2], data[3]);
            }
            prevC = Country;
        }

        Methods.setList(Countries);
        Methods.setHMap(Nations);
        csvReader.close();

        launch(args);
        /*
         * int intCount; Methods.sortPop(Countries);
         * 
         * for (intCount = 0; intCount < Countries.size(); intCount++) {
         * System.out.println(Countries.get(intCount)); }
         * 
         * while (!cName.equals("")) { System.out.println("Print Country Name: "); cName
         * = key.readLine();
         * 
         * if (!cName.equals("")) { if (Nations.containsKey(cName) == true) {
         * System.out.println("Print year:"); Year = key.readLine();
         * Nations.get(cName).setYear(Year);
         * 
         * System.out.println("Country     Year     Population");
         * System.out.println(Nations.get(cName)); System.out.println(" "); } else {
         * System.out.println("Error: Country Not Found"); } }
         * 
         * }
         */

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(mainMenu(primaryStage), 300, 250));
        primaryStage.setTitle("World Populations");
        primaryStage.show();
    }

    public Parent mainMenu(Stage primaryStage) {
        Label menu = new Label("Main Menu");

        Button viewAll = new Button("View All Entries");

        TextField searchYear = new TextField("Year");
        searchYear.setMaxSize(140, TextField.USE_COMPUTED_SIZE);

        TextField searchCountry = new TextField("Country");
        searchCountry.setMaxSize(140, TextField.USE_COMPUTED_SIZE);

        Button barSettings = new Button("Create Barchart");

        viewAll.setOnAction(e -> primaryStage.setScene(new Scene(createTable(Methods.All()), 350, 450)));
        searchYear.setOnAction(e -> primaryStage.setScene(new Scene(byYear(searchYear.getText()), 350, 450)));
        searchCountry.setOnAction(e -> primaryStage.setScene(new Scene(byCountry(searchCountry.getText()), 350, 450)));
        barSettings.setOnAction(e -> primaryStage.setScene(new Scene(barSettings(primaryStage))));

        VBox main = new VBox();
        main.getChildren().addAll(menu, viewAll, searchYear, searchCountry, barSettings);
        main.setAlignment(Pos.TOP_CENTER);

        return main;
    }

    //
    public Parent byYear(String year) {
        ObservableList<Country> data = Methods.byYear(year);
        Parent table = createTable(data);

        return table;
    }

    public Parent byCountry(String country) {
        ObservableList<Country> data = Methods.byCountry(country);
        Parent table = createTable(data);

        return table;
    }

    public Parent createBar(String dateStart, String dateEnd, Country Country1, Country Country2, Country Country3) {
        ArrayList<String> years = new ArrayList<String>();
        ArrayList<Country> Countries = new ArrayList<Country>();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Population");
        BarChart chart = new BarChart(xAxis, yAxis);
        int intCount;

        for (intCount = Integer.parseInt(dateStart); intCount < Integer.parseInt(dateEnd); intCount++) {
            years.add(String.valueOf(intCount));
        }
        years.add(String.valueOf(intCount));

        Countries.add(Country1);
        Countries.add(Country2);
        Countries.add(Country3);

        xAxis.setCategories(Methods.toOb(years));

        XYChart.Series C1 = new XYChart.Series();
        C1.setName(Countries.get(0).getNation());

        for (intCount = 0; intCount < years.size(); intCount++) {
            Countries.get(0).setYear(years.get(intCount));
            C1.getData().add(new XYChart.Data(years.get(intCount), Long.parseLong(Countries.get(0).getPopulation())));
        }

        chart.getData().add(C1);

        XYChart.Series C2 = new XYChart.Series();
        C2.setName(Countries.get(1).getNation());

        for (intCount = 0; intCount < years.size(); intCount++) {
            Countries.get(1).setYear(years.get(intCount));
            C2.getData().add(new XYChart.Data(years.get(intCount), Long.parseLong(Countries.get(1).getPopulation())));
        }

        chart.getData().add(C2);

        XYChart.Series C3 = new XYChart.Series();
        C3.setName(Countries.get(2).getNation());

        for (intCount = 0; intCount < years.size(); intCount++) {
            Countries.get(2).setYear(years.get(intCount));
            C3.getData().add(new XYChart.Data(years.get(intCount), Long.parseLong(Countries.get(2).getPopulation())));
        }

        chart.getData().add(C3);

        return chart;
    }

    public Parent barSettings(Stage primaryStage){
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
            if (!Country.equals(prevC)) {
                C1.getItems().add(C);
                C2.getItems().add(C);
                C3.getItems().add(C);
            }
            prevC = Country;
        }

        create.setOnAction(e -> primaryStage.setScene(new Scene(createBar(startY.getText(), endY.getText(), C1.getValue(), C2.getValue(), C3.getValue()))));

        layout.getChildren().addAll(C1, C2, C3, startY, endY, create);
        layout.setAlignment(Pos.CENTER_LEFT);

        return layout;
    }

    public Parent createTable(ObservableList <Country> data){

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

        return tableView;
    }
}
