package CPT;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Methods{
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
