package CPT;

import CPT.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Methods.java
 * Contains all of the methods needed for app funtionality
 * Also contains sorting and searching methods
 * Also serves as storage area for data to be called on later
 */

public class Methods{
    private static ArrayList<Country> list;
    private static HashMap<String, Country> map;
    private static ArrayList <Country> inputArray;
  
    public static ArrayList <Country> getList() {
        return list;
    }

    public static void setList(ArrayList<Country> newList) {
        list = newList;
    }

    public static void setHMap(HashMap <String, Country> newMap) {
        map = newMap;
    }

    public static HashMap <String, Country> getHMap() {
        return map;
    }

    /**
     * All
     * @return observableList of data set
     */
    public static ObservableList <Country> All() {
        ObservableList<Country> data = FXCollections.observableArrayList();
        int intCount;

        for (intCount = 0; intCount < list.size(); intCount ++) {
            data.add(list.get(intCount));
        }

        return data;
    }

    /**
     * toOb
     * Converts ArrayList to ObservableList
     * @param List
     * @return ObservableList data
     */
    public static ObservableList toOb(ArrayList List) {
        ObservableList data = FXCollections.observableArrayList();
        int intCount;

        for (intCount = 0; intCount < List.size(); intCount ++) {
            data.add(List.get(intCount));
        }

        return data;
    }

    /**
     * byYear
     * @param year
     * @return ObservableList of Countries matching input year
     */
    public static ObservableList <Country> byYear(String year) {
        ObservableList<Country> data = FXCollections.observableArrayList();

        for (int intCount = 0; intCount < list.size(); intCount ++) {

            if (year.equalsIgnoreCase(list.get(intCount).getYear())) {
                data.add(list.get(intCount));
            }

        }

        return data;
    }

    /**
     * searchYear
     * @param year
     * @return ArrayList of Entries matching input year
     */
    public static ArrayList <Country> searchYear(String year) {
        ArrayList<Country> data = new ArrayList <Country> ();

        for (int intCount = 0; intCount < list.size(); intCount ++) {

            if (year.equalsIgnoreCase(list.get(intCount).getYear())) {
                data.add(list.get(intCount));
            }

        }

        return data;
    }

    /**
     * byCountry
     * @param Country
     * @return ObservableList of entries matching input country name
     * 
     */
    public static ObservableList <Country> byCountry(String Country) {
        ObservableList<Country> data = FXCollections.observableArrayList();

        for (int intCount = 0; intCount < list.size(); intCount ++) {

            if (Country.equalsIgnoreCase(list.get(intCount).getNation())) {
                data.add(list.get(intCount));
            }

        }

        return data;
    }

    /**
     * listByCountry
     * @param Country
     * @return ArrayList of entries matching String Country
     */
    public static ArrayList <Country> listByCountry(String Country) {
        ArrayList <Country> data = new ArrayList<Country>();

        for (int intCount = 0; intCount < list.size(); intCount ++) {

            if (Country.equalsIgnoreCase(list.get(intCount).getNation())) {
                data.add(list.get(intCount));
            }

        }

        return data;
    }

    /**
     * avgByYear
     * @param year
     * @return average population by year
     * excludes World and Continents
     */
    public static Integer avgByYear(String year) {
        ArrayList <Country> Countries = searchYear(year);
        double total = 0;
        double avg;
        Country c;
        int average;
        String nation;

        for (int intCount = 0; intCount < Countries.size(); intCount ++) {
            c = Countries.get(intCount);
            nation = c.getNation();

            if (!nation.equals("World") && !nation.equals("Asia") && !nation.equals("Europe") && !nation.equals("Africa") && !nation.equals("North America") && !nation.equals("Latin America")) {
                total = total + Double.parseDouble(Countries.get(intCount).getPopulation());
            }

        }

        avg = total / Countries.size();
        average = (int) Math.round(avg);

        return average;
    }

    /**
     * popDensity
     * Calculates Population density of the world
     * on any given year
     * @param year
     * @return Population Density
     * 
     */
    public static double popDensity(String year) {
        final int landArea = 148900000;
        ArrayList <Country> Countries = searchYear(year);
        double total = 0;
        double density;
        Country c;
        String nation;

        for (int intCount = 0; intCount < Countries.size(); intCount ++) {
            c = Countries.get(intCount);
            nation = c.getNation();

            if (nation.equals("World")) {
                total = Double.parseDouble(c.getPopulation());
            }

        }

        density = total / landArea;
        return density;
    }

    // Getters and setters for merge sort
    public ArrayList getSortedArray() {
        return inputArray;
    }

    public static void setMSortArray(ArrayList<Country> input) {
        inputArray = input;
    }
    
    /**
     * mergeSort:
     * @param input
     * @return Sorted Array by population
     */
    public static ArrayList <Country> mergeSort(ArrayList<Country> input) {
        setMSortArray(input);
        divide(0, inputArray.size()-1);

        return inputArray;
    }
    
    private static void divide(int startIndex,int endIndex) {

        if (startIndex < endIndex && (endIndex - startIndex) >= 1) {
            int mid = (endIndex + startIndex) / 2;
            divide(startIndex, mid);
            divide(mid + 1, endIndex);        
            
            merger(startIndex,mid,endIndex);            
        }       
    }   
    
    private static void merger(int startIndex,int midIndex,int endIndex) {

        ArrayList <Country> mergedSortedArray = new ArrayList<Country>();
        
        int leftIndex = startIndex;
        int rightIndex = midIndex+1;
        
        while (leftIndex <= midIndex && rightIndex <= endIndex) {
            if (Long.parseLong(inputArray.get(leftIndex).getPopulation()) <= Long.parseLong(inputArray.get(rightIndex).getPopulation())) {
                mergedSortedArray.add(inputArray.get(rightIndex));
                rightIndex++;

            } else {
                mergedSortedArray.add(inputArray.get(leftIndex));
                leftIndex++;

            }
        }       
        
        while (leftIndex <= midIndex) {
            mergedSortedArray.add(inputArray.get(leftIndex));
            leftIndex++;
        }
        
        while (rightIndex <= endIndex) {
            mergedSortedArray.add(inputArray.get(rightIndex));
            rightIndex++;
        }
        
        int i = 0;
        int j = startIndex;

        while (i < mergedSortedArray.size()) {
            inputArray.set(j, mergedSortedArray.get(i++));
            j++;
        }

    }
    
}
