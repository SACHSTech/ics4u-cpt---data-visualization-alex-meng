package CPT;

import java.io.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.ArrayList;

public class Country {
    private StringProperty Nation;
    private StringProperty year;
    private StringProperty population;


    public Country(String country, String date, String Population){
        this.Nation = new SimpleStringProperty(country);
        this.year = new SimpleStringProperty(date);
        this.population = new SimpleStringProperty(Population);
    }

    public String getNation(){
        return Nation.get();
    }

    public String getYear(){
        return year.get();
    }

    public String getPopulation(){
        return population.get();
    }

    
    public void setYear(String date) throws IOException{
        BufferedReader csvReader = new BufferedReader(new FileReader("src/CPT/population.csv"));
        
        boolean Change = false;
        String nation;
        String targetDate;
        String Pop;

        while(csvReader.readLine() != null){
            String data[] = csvReader.readLine().split(",");
            nation = data[0];
            targetDate = data[2];
            Pop = data[3];

            if(nation.equals(Nation.get()) && date.equals(targetDate)){
                population = new SimpleStringProperty(Pop);
                year = new SimpleStringProperty(date);
                Change = true;
                break;
            }
        }
        
        if(Change == false){
            System.out.println("Not Found");
        }
        csvReader.close();
    }

    public String toString(){
        return Nation.get() + "     " + year.get() + "     " + population.get();
    }
}
