package CPT;

import java.io.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.ArrayList;
import java.util.HashMap;

public class Country {
    private StringProperty Nation;
    private StringProperty year;
    private StringProperty population;
    private HashMap <String, String> yearPop = new HashMap<String, String>();


    public Country(String country, String date, String Population){
        this.Nation = new SimpleStringProperty(country);
        this.year = new SimpleStringProperty(date);
        this.population = new SimpleStringProperty(Population);
    }

    public void addYearPop(String y, String pop){
        yearPop.put(y, pop);
    }

    public String getYearPop(String y){
        return yearPop.get(y);
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

    
    public void setYear(String date){
        //BufferedReader csvReader = new BufferedReader(new FileReader("src/CPT/population.csv"));
        
        boolean Change = false;
        String nation;
        String targetDate;
        String Pop;
        String row;

        if(yearPop.containsKey(date) == true){
            year = new SimpleStringProperty(date);
            population = new SimpleStringProperty(yearPop.get(date));
        }else if(yearPop.containsKey(date) == false){
            System.out.println("Not Found");
        }

/**
        while((row = csvReader.readLine()) != null){
            String data[] = row.split(",");
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
        */
    }

    public String toString(){
        return Nation.get() + "     " + year.get() + "     " + population.get();
    }
}
