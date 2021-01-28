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

    public HashMap<String, String> getYPList(){
        return yearPop;
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
        
        if(yearPop.containsKey(date) == true){
            year = new SimpleStringProperty(date);
            population = new SimpleStringProperty(yearPop.get(date));
        }else if(yearPop.containsKey(date) == false){
            System.out.println("Not Found");
        }
        
    }

    public String toString(){
        return Nation.get() + "     " + year.get() + "     " + population.get();
    }
}
