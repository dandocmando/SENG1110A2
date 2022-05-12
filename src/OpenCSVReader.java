import com.opencsv.CSVReader;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.*;

public class OpenCSVReader{

    public static void main(String[] args) throws IOException, CsvException {

        String fileName = "Users.csv";
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            List<String[]> r = reader.readAll();
            r.forEach(x -> System.out.println(Arrays.toString(x)));
        }

    }




}