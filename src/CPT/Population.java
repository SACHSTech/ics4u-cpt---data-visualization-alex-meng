package CPT;

import java.io.*;
//import CPT.Country;
//import CPT.Methods;

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
        int intCount = 0;

        csvReader.readLine();

        while ((row = csvReader.readLine()) != null) {
            String data[] = row.split(",");
            Countries.add(new Country(data[0], data[2], data[3]));
            intCount ++;
        }

        Methods.setList(Countries);
        csvReader.close();

        launch(args);
    /*
        int intCount;
        Methods.sortPop(Countries);

        for (intCount = 0; intCount < Countries.size(); intCount++) {
            System.out.println(Countries.get(intCount));
        }

        while (!cName.equals("")) {
            System.out.println("Print Country Name: ");
            cName = key.readLine();

            if (!cName.equals("")) {
                if (Nations.containsKey(cName) == true) {
                    System.out.println("Print year:");
                    Year = key.readLine();
                    Nations.get(cName).setYear(Year);

                    System.out.println("Country     Year     Population");
                    System.out.println(Nations.get(cName));
                    System.out.println(" ");
                } else {
                    System.out.println("Error: Country Not Found");
                }
            }

        }
        */

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //primaryStage.setScene(new Scene(mainMenu(primaryStage), 300, 250));
        primaryStage.setScene(new Scene(createBar("2000", "2003")));
        primaryStage.setTitle("World Populations");
        primaryStage.show();
    }

    public Parent mainMenu(Stage primaryStage){
        Label menu = new Label("Main Menu");

        Button viewAll = new Button("View All Entries");

        TextField searchYear = new TextField("Year");
        searchYear.setMaxSize(140, TextField.USE_COMPUTED_SIZE);

        TextField searchCountry = new TextField("Country");
        searchCountry.setMaxSize(140, TextField.USE_COMPUTED_SIZE);
        
        viewAll.setOnAction(e -> primaryStage.setScene(new Scene(createTable(Methods.All()), 350, 450)));
        searchYear.setOnAction(e -> primaryStage.setScene(new Scene(byYear(searchYear.getText()), 350, 450)));
        searchCountry.setOnAction(e -> primaryStage.setScene(new Scene(byCountry(searchCountry.getText()), 350, 450)));

        VBox main = new VBox();
        main.getChildren().addAll(menu, viewAll, searchYear, searchCountry);
        main.setAlignment(Pos.TOP_CENTER);

        return main;
    }

    // 
    public Parent byYear(String year){
        ObservableList <Country> data = Methods.byYear(year);
        Parent table = createTable(data);

        return table;
    }

    public Parent byCountry(String country){
        ObservableList <Country> data = Methods.byCountry(country);
        Parent table = createTable(data);

        return table;
    }

    public Parent createBar(String dateStart, String dateEnd){
        ArrayList <Integer> years = new ArrayList<Integer>();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis("Population", 0.0d, 1000000.0d, 1000000.0d);

        for(int intCount = Integer.parseInt(dateStart); intCount < Integer.parseInt(dateEnd); intCount ++){
            years.add(intCount);
        }

        xAxis.setCategories(Methods.toOb(years));

        XYChart.Series Canada = new XYChart.Series();
        Canada.setName("Canada");

        for(int intCount = 0; intCount < years.size(); intCount ++){
            Canada.getData().add(new XYChart.Data(years.get(intCount), 3000000));
        }

        BarChart chart = new BarChart(xAxis, yAxis);
        chart.getData().add(Canada);
        
        return chart;
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
