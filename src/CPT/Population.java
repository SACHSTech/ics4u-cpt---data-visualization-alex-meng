//package CPT;

import java.io.*;
//import Country;


public class Population {
    public static void main(String[] args) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader("src/CPT/population.csv"));
        String country;
        String prevCountry = "";
        String code;
        String year;
        String population;

        while(csvReader.readLine() != null){
            String data[] = csvReader.readLine().split(",");
            country = data[0];
            code = data[1];
            year = data[2];
            population = data[3];
            //if(!prevCountry.equalsIgnoreCase(country)){
             //   Country data[1] = new Country
           // }
           System.out.println(country + "     " + year + "     " + population);
        }
    }
}
