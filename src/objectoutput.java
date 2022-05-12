import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class objectoutput{
    public static void main(String[] args){
        Account p1 = new Account(20,52,300);
        try {
            FileOutputStream f = new FileOutputStream(new File("test.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(p1);

            o.close();
            f.close();

            FileInputStream fileInput = new FileInputStream(new File("test.txt"));
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);

            Account pr1 = (Account) objectInput.readObject();

            System.out.println(pr1.getinvRate());



        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Finished");
        }

    }


}
