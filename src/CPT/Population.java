package CPT;

import java.io.*;
import CPT.Country;
import CPT.Methods;

import javafx.scene.Scene;
import javafx.application.Application;
import java.util.HashMap;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class Population extends Application{
    public static void main(String[] args) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader("src/CPT/population.csv"));
        BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
        HashMap<String, Country> Nations = new HashMap<String, Country>();
        String country;
        String prevCountry = "";
        String cName = " ";
        String Year;

        while(csvReader.readLine() != null){
            String data[] = csvReader.readLine().split(",");
            country = data[0];
            if(!prevCountry.equalsIgnoreCase(country)){
                Nations.put(data[0], new Country(data[0], data[2], data[3]));
            }

            prevCountry = country;
        }

        csvReader.close();

        launch(args);

       while(!cName.equals("")){
        System.out.println("Print Country Name: ");
        cName = key.readLine();

           if(!cName.equals("")){
                if(Nations.containsKey(cName) == true){
                    System.out.println("Print year:");
                    Year = key.readLine();
                    Nations.get(cName).setYear(Year);

                    System.out.println("Country     Year     Population");
                    System.out.println(Nations.get(cName));
                    System.out.println(" ");
                }else{
                    System.out.println("Error: Country Not Found");
                }
           }
        
       }

       
    }

    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public Parent createContent(){

        final ObservableList<Country> data = FXCollections.observableArrayList(
            new Country("Canada", "2012", "34922000"),
            new Country("United States", "2012", "314044000"),
            new Country("Mexico", "2012", "117274000")
        );

        TableColumn Nation = new TableColumn();
        Nation.setText("Country");
        Nation.setCellValueFactory(new PropertyValueFactory("Nation"));
        
        TableColumn Year = new TableColumn();
        Year.setText("Year");
        Year.setCellValueFactory(new PropertyValueFactory("year"));
        
        TableColumn Population = new TableColumn();
        Population.setText("Population");
        Population.setMinWidth(200);
        Population.setCellValueFactory(new PropertyValueFactory("population"));

        final TableView tableView = new TableView();
        tableView.setItems(data);
        tableView.getColumns().addAll(Nation, Year, Population);

        return tableView;
    }
}
