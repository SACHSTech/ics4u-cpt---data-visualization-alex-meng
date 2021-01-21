package CPT;

import java.io.*;
import CPT.Country;
import CPT.Lists;

import javafx.scene.Scene;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.control.ChoiceBox;
import javafx.geometry.Pos;


public class Population extends Application{
    
    public static void main(String[] args) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader("src/CPT/population.csv"));
        BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
        HashMap<String, Country> Nations = new HashMap<String, Country>();
        ArrayList <Country> Countries = new ArrayList<Country>();
        String country;
        String prevCountry = "";
        String cName = " ";
        String Year;

        while(csvReader.readLine() != null){
            String data[] = csvReader.readLine().split(",");
            country = data[0];
            if(!prevCountry.equalsIgnoreCase(country)){
                Nations.put(data[0], new Country(data[0], data[2], data[3]));
                Countries.add(new Country(data[0], data[2], data[3]));
            }

            prevCountry = country;
        }

        csvReader.close();
       // final Lists list = new Lists();
        //list.setList(Countries);

        //launch(args);

        int intCount;
        Country.sortPop(Countries);
        //int[] arr = {1, 5, 3, 6, 9, 2, 3, 4, 8};
        //Lists.mergeSort(arr);
        //for(intCount = 0; intCount < Countries.size(); intCount ++){
            //System.out.println(Countries.get(intCount));
        //}

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
        primaryStage.setScene(new Scene(createTable()));
        primaryStage.show();
    }

    public Parent createTable() throws IOException{
        BufferedReader csvReader = new BufferedReader(new FileReader("src/CPT/population.csv"));
        ObservableList<Country> data = FXCollections.observableArrayList();
        String country;
        String prevCountry = "";

        while(csvReader.readLine() != null){
            String line[] = csvReader.readLine().split(",");
            country = line[0];
            //if(!prevCountry.equalsIgnoreCase(country)){
                data.add(new Country(line[0], line[2], line[3]));
            //}

            prevCountry = country;
        }

        csvReader.close();

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

        ChoiceBox cb = new ChoiceBox();
        cb.getItems().addAll(" ", "Cat", "Horse");
        cb.getSelectionModel().selectFirst();

        VBox UI = new VBox(2);
        UI.setAlignment(Pos.CENTER_LEFT);
        UI.getChildren().addAll(tableView, cb);
        return UI;
    }
}
