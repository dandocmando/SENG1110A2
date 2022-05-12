import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class ObjectIO {
    public void main(String[] args){
        try{
            FileOutputStream fileOutput = new FileOutputStream("ObjectIO_storage.ser");
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);

            FileInputStream fileInput = new FileInputStream("ObjectIO_storage.ser");
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error initialising stream");
        }
    }
    public void outputAccount(){
        Account account_1 = new Account(20,52,300);

    }
    public void inputAccount(double investmentRate, int numberOfWeeks, double investment){
        ArrayList<Account> AccountList = new ArrayList<>();


        AccountList.add(new Account(investmentRate,numberOfWeeks,investment));

        Iterator<Account> itr = AccountList.iterator();
        while(itr.hasNext()){
            Account list = itr.next();
            System.out.println("final: "+list.calcInv());
        }








    }
}
