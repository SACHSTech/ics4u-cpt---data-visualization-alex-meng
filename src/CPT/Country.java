//package CPT;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Country {
    private StringProperty Country;
    private StringProperty year;
    private StringProperty population;

    public Country(String country, String date, String Population){
        this.Country = new SimpleStringProperty(country);
        this.year = new SimpleStringProperty(date);
        this.population = new SimpleStringProperty(Population);
    }

    public void setYear(String date) throws IOException{
        BufferedReader csvReader = new BufferedReader(new FileReader("src/CPT/population.csv"));
        
        year = new SimpleStringProperty(date);
        boolean Change = false;
        StringProperty nation;
        StringProperty targetDate;
        StringProperty pop;

        while(csvReader.readLine() != null){
            String data[] = csvReader.readLine().split(",");
            nation = new SimpleStringProperty(data[0]);
            targetDate = new SimpleStringProperty(data[2]);
            pop = new SimpleStringProperty(data[3]);

            if(nation == Country && year == targetDate){
                population = pop;
                Change = true;
                break;
            }
        }
        
        if(Change == true){
            population = new SimpleStringProperty("Not Found");
        }
        csvReader.close();
    }

 
    public Parent createContent() throws IOException{
        BufferedReader Reader = new BufferedReader(new FileReader("src/CPT/population.csv"));
        
        String country;
        String prevCountry = "";
        String code;
        String date;
        String population;
        ArrayList <String> Countries = new ArrayList <String>();
        ArrayList <String> Dates = new ArrayList <String>();
        ArrayList <String> Populations = new ArrayList <String>();
        
        while(Reader.readLine() != null){
            String data[] = Reader.readLine().split(",");
            country = data[0];
            code = data[1];
            date = data[2];
            population = data[3];
            if(!prevCountry.equalsIgnoreCase(country)){
                Countries.add(data[0]);
                Dates.add(data[2]);
                Populations.add(data[3]);
            }
        }

        Reader.close();

        final ObservableList<Country> data = FXCollections.observableArrayList(
            new Country("Test", "2000", "1000")
        );
 
        TableColumn Cname = new TableColumn();
        Cname.setText("Country");
        Cname.setCellValueFactory(new PropertyValueFactory("Country"));
        TableColumn year = new TableColumn();
        year.setText("Year");
        year.setCellValueFactory(new PropertyValueFactory("year"));
        TableColumn Pop = new TableColumn();
        Pop.setText("Population");
        Pop.setMinWidth(200);
        Pop.setCellValueFactory(new PropertyValueFactory("population"));
        final TableView tableView = new TableView();
        tableView.setItems(data);
        tableView.getColumns().addAll(Cname, year, Pop);
        return tableView;
    }
 
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }
 
    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }

    private static void launch(String[] args) {
    }
}
