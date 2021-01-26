package CPT;

import CPT.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;

public class Methods{
    private static ArrayList<Country> list;
  
    public static ArrayList <Country> getList(){
        return list;
    }

    public static void setList(ArrayList<Country> newList){
        list = newList;
    }

    // converts arraylist to observable list
    public static ObservableList <Country> All(){
        ObservableList<Country> data = FXCollections.observableArrayList();
        int intCount;

        for(intCount = 0; intCount < list.size(); intCount ++){
            data.add(list.get(intCount));
        }
        return data;
    }

    public static ObservableList toOb(ArrayList List){
        ObservableList data = FXCollections.observableArrayList();
        int intCount;

        for (intCount = 0; intCount < List.size(); intCount ++) {
            data.add(List.get(intCount));
        }
        return data;
    }

    // searches for entries matching input year and adds to observable list
    public static ObservableList <Country> byYear(String year){
        ObservableList<Country> data = FXCollections.observableArrayList();

        for(int intCount = 0; intCount < list.size(); intCount ++){

            if(year.equalsIgnoreCase(list.get(intCount).getYear())){
                data.add(list.get(intCount));
            }
        }

        return data;
    }

    // Searches for entries matching input country and adds to observable list
    public static ObservableList <Country> byCountry(String Country){
        ObservableList<Country> data = FXCollections.observableArrayList();

        for(int intCount = 0; intCount < list.size(); intCount ++){

            if(Country.equalsIgnoreCase(list.get(intCount).getNation())){
                data.add(list.get(intCount));
            }
        }

        return data;
    }

    public static double avgByYear(String year){
        ObservableList <Country> List = byYear(year);
        int size = List.size();
        double total = 0;
        double avg;

        for(int intCount = 0; intCount < size; intCount ++){
            if(!List.get(intCount).getNation().equals("World")){
                total = total + Double.parseDouble(List.get(intCount).getPopulation());
            }
        }

        avg = total / size;

        return avg;
    }

    // Sort by population size
    public static ArrayList <Country> sortPop(ArrayList <Country> Countries){
        int currentMinIndex;
        int intCount;
        for(intCount = 0; intCount < Countries.size(); intCount ++){
            currentMinIndex = intCount;

            for(int j = intCount + 1; j < Countries.size(); j ++){
                double curMin = Double.parseDouble(Countries.get(currentMinIndex).getPopulation());
                double curNum = Double.parseDouble(Countries.get(j).getPopulation());
                if(curNum > curMin){
                    currentMinIndex = j;
                }
            }

            if(intCount != currentMinIndex){
                Country temp = Countries.get(currentMinIndex);
                Countries.set(currentMinIndex, Countries.get(intCount));
                Countries.set(intCount, temp);
            }
        }
        return Countries;
    }
    
}
