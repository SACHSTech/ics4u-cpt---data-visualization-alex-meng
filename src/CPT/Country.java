package CPT;

import java.io.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.HashMap;

/**
 * Country.java
 * Country Object
 * Stores country name, year, and population
 * Has a HashMap called yearPop that stores
 * all the years and corresponding population sizes for that country
 * 
 */
public class Country {
    private StringProperty nation;
    private StringProperty year;
    private StringProperty population;
    private HashMap <String, String> yearPop = new HashMap<String, String>();

    // Constructor
    public Country(String country, String date, String Population){
        this.nation = new SimpleStringProperty(country);
        this.year = new SimpleStringProperty(date);
        this.population = new SimpleStringProperty(Population);
    }

    public void addYearPop(String y, String pop){
        yearPop.put(y, pop);
    }

    public String getYearPop(String y){
        return yearPop.get(y);
    }

    public HashMap<String, String> getYPList(){
        return yearPop;
    }

    public String getNation(){
        return nation.get();
    }

    public String getYear(){
        return year.get();
    }

    public String getPopulation(){
        return population.get();
    }

    /**
     * setYear
     * Changes the current year and population of the country
     * If the year isn't found, prints out "Not Found"
     * @param date
     */
    public void setYear(String date){
        
        if(yearPop.containsKey(date) == true){
            year = new SimpleStringProperty(date);
            population = new SimpleStringProperty(yearPop.get(date));

        }else if(yearPop.containsKey(date) == false){
            System.out.println("Not Found");
        }
        
    }

    public String toString(){
        return nation.get() + "     " + year.get() + "     " + population.get();
    }
}
