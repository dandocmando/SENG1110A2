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
        StringBuilder names = new StringBuilder();

        int i = 0;
        while ((line = br.readLine()) != null) {
            values = line.split(",");
            names.append(values[i]+", ");


            

        }
        String name1;
        for (int x=0; x<values.length; x++){
            //name1 =names.codePointAt(0);
        }
        System.out.println(Arrays.toString(values));

        System.out.println(names);


       //System.out.println("test"+ Arrays.toString(names));
    }

}
