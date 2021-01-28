package CPT;

import java.io.*;

import javafx.scene.Scene;
import javafx.application.Application;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.stage.Stage;

/**
 * Population.java
 * Main program
 * Reads through data set and stores in Methods.java
 * as HashMap and ArrayList
 * Creates Country object out of each line
 * Country objects in HashMap get yearPop filled up
 */

public class Population extends Application {

    public static void main(String[] args) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader("src/CPT/population.csv"));
        HashMap<String, Country> Nations = new HashMap<String, Country>();
        ArrayList<Country> Countries = new ArrayList<Country>();
        String row;
        String country;
        String prevC = "";

        csvReader.readLine();

        while ((row = csvReader.readLine()) != null) {
            String data[] = row.split(",");
            country = data[0];

            Countries.add(new Country(data[0], data[2], data[3]));

            if (!country.equals(prevC)) {
                Nations.put(data[0], new Country(data[0], data[2], data[3]));

            }

            if (country.equals(prevC)) {
                Nations.get(country).addYearPop(data[2], data[3]);
            }

            prevC = country;
        }

        Methods.setList(Countries);
        Methods.setHMap(Nations);
        csvReader.close();

        launch(args);
    }

    // Start app
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(PopApp.mainMenu(primaryStage), 300, 250));
        primaryStage.setTitle("World Populations");
        primaryStage.show();
    }
}
