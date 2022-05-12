import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class CSVSplit {
    public static void main(String[] args) throws IOException {
        String file = "Users.csv";
        String line;
        BufferedReader br = new BufferedReader(new FileReader(file));

        String[] values = new String[0];
        String[] names = new String[2];


        while ((line = br.readLine()) != null) {
            values = line.split(",");


            

        }
        System.out.println(Arrays.toString(values));
        for (int i=0;i<values.length;i++){
            names[i] += values[i];
        }
        System.out.println(Arrays.toString(names));


       //System.out.println("test"+ Arrays.toString(names));
    }

}
