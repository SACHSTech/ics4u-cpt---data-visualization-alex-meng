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

    public static void sortPop(ArrayList <Country> Countries){
        ArrayList<Country> temp = new ArrayList<Country>();
        sortHelp(Countries, 0, Countries.size() - 1, temp);
        //return test;
        System.out.println(Countries);
    }

    private static void sortHelp(ArrayList <Country> Countries, int from, int to, ArrayList <Country> temp){
        ArrayList <Country> test = new ArrayList<Country>();
        if(to - from > 1){
            int mid = (from + to) / 2;
            sortHelp(Countries, from, mid, temp);
            sortHelp(Countries, mid + 1, to, temp);

            mergeT(Countries, from, mid, to, temp);
            test = Countries;
        }
        //return test;
    }

    private static void mergeT(ArrayList <Country> Countries, int from, int mid, int to, ArrayList <Country> temp){
        int i = from;
        int j = mid + 1;
        int k = from;

        while(i <= mid && j < to){
            if(Integer.parseInt(Countries.get(i).getPopulation()) < Integer.parseInt(Countries.get(j).getPopulation())){
               temp.set(k, Countries.get(i));
               i++;
           }else{
               temp.set(k, Countries.get(j));
               j++;
           }
           k++;
        }

        while(i < mid){
            temp.set(k, Countries.get(i));
            i++;
            k++;
        }

        while(j < to){
            temp.set(k, Countries.get(j));
            j++;
            k++;
        }

        for(k = from; k <= to; k++){
            Countries.set(k, temp.get(k));
        }
    }

    public String toString(){
        return Nation.get() + "     " + year.get() + "     " + population.get();
    }
}
