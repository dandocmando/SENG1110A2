/*
Author: Daniel Ferguson
Auth ID: 3374690
Date: 09/05/2022
Description: Client Class, calculates the users tax and salary
SENG1110 Programming Assignment 2
 */
import java.math.BigDecimal; // used to round vars to 2 dec places for weekly calc
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Scanner;

public class Client
{
    private String name; // all instance variables have been set to private as requested
    private String accA = "No Account";
    private String accB = "No Account";
    private double grossSalary;
    private boolean resident;
    private double medicare;
    private double weeklyExpenses;
    private double ctx; // calculate tax
    private boolean clientUsed;
    private int numOfAccUsed;
    private Account[] acc;


    public Client(String name, double grossSalary, boolean resident, double weeklyExpenses){
        this.name = name;
        this.grossSalary = grossSalary;
        this.resident = resident;
        this.weeklyExpenses = weeklyExpenses;
        clientUsed = false;
        numOfAccUsed = 0;
        acc = new Account[2];
    }

    public Client(){
        name = "";
        grossSalary = 0;
        resident = false;
        weeklyExpenses = 0;
        clientUsed = false;
        numOfAccUsed = 0;
        acc = new Account[2];
    }

    //create and delete methods for Account class
    public void createAccount(double investmentRate, int numberOfWeeks, double investment){
        acc[numOfAccUsed]= new Account(investmentRate,numberOfWeeks,investment,true);
        numOfAccUsed++; // this is used to determine the number of accounts used
    }
    public void deleteAccount(int accountToDelete){
        acc[accountToDelete] = new Account(0,0,0,false);
        numOfAccUsed--;
    }




    public void accountShuffle(){ // this is a duplicate of clientShuffle
        int index = 0; // used to move acc[1] to acc[0] if acc[1] is deleted
        System.out.println(getNumOfAccUsed());
        for(int i = 0; i<acc.length; i++){ //refer to clientShuffle for comments
            if(getAccUsed(i)){
                Account temp = acc[index];
                acc[index] = acc[i];
                acc[i] = temp;
                index++;
            }
        }
    }


    public double calcTax() {  // calculates yearly tax
        if(resident) { // checks if user is a resident
            if(grossSalary > 180001){ // each if statement is another tax bracket
                ctx = (grossSalary-180000)*0.45 + 51667;
            }
            else if(grossSalary >= 120001 && grossSalary <= 180000){
                ctx = (grossSalary-120000)*0.37 + 29467;
            }
            else if(grossSalary >= 45001 && grossSalary <= 120000){
                ctx = (grossSalary-45000)*0.325 + 5092;
            }
            else if (grossSalary >= 18201) {
                ctx = (grossSalary - 18200) * 0.19;
            }
        }

        if(!resident) { // if user isn't a resident they have different tax rules applied
            if(grossSalary > 180001){
                ctx = (grossSalary-180000)*0.45 + 61200;
            }
            else if(grossSalary >= 120001 && grossSalary <= 180000){
                ctx = (grossSalary-120000)*0.37 + 39000;
            }
            else if(grossSalary <= 120000){
                ctx = grossSalary*0.325;
            }
        }
        return ctx;
    }

    public double calcMedicare(){
        if(resident){ // only calculates medicare if they are a resident
            medicare = grossSalary*0.02;  //sets medicare var to 2% of gross (taxable) income
        }
        return medicare; // if they aren't a resident this method will return 0
    }
    // calculates net by deducting medicare and tax
    public double calcNet(){ return grossSalary - (calcMedicare() + calcTax());}

    //Weekly calculation section
    //calculates weekly gross by dividing yearly gross by 52
    public double calcWeeklyGross(){ double weeklyGross = grossSalary / 52;
        return new BigDecimal(weeklyGross).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }
    //calculates weekly tax by dividing yearly tax by 52
    public double calcWeeklyTax(){ double weeklyTax = calcTax() / 52;
        return new BigDecimal(weeklyTax).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }
    // calculates weekly net earnings by dividing yearly net by 52
    public double calcWeeklyNet(){ double weeklyNet = (grossSalary - (calcMedicare() + calcTax())) / 52;
        return new BigDecimal(weeklyNet).setScale(2,RoundingMode.HALF_EVEN).doubleValue();
    }

    public double calcPossibleInvestment(){ double weeklyNet = calcWeeklyNet();
        // BigDecimal is only used to round -> safe to swap back to double
        if(getNumOfAccUsed()==0){ // this won't deduct the getInvOne as account one doesn't exist yet
            return new BigDecimal(weeklyNet-weeklyExpenses-(calcMedicare()/52)).setScale(2,RoundingMode.HALF_EVEN).doubleValue();
        }
        else{ // this deducts account one investment from the possible investment for account two
            return new BigDecimal(weeklyNet-weeklyExpenses-(calcMedicare()/52)-getInv(0)).setScale(2,RoundingMode.HALF_EVEN).doubleValue();
        }        //doubleValue converts BigDecimal to double, I like this way because it rounds and returns val in one line
    }




    //Setter and Getter section
    public String getName(){ return (name);} //gets name, used in calcinfo
    public void setName(String inputName){name = inputName;} // sets name, takes input and assigns to var

    public double getGross(){ return (grossSalary);} // gets gross income, used in calcinfo
    public void setGross(double inputGross){grossSalary = inputGross;}  // sets gross, takes input from calcinfo

    public boolean getResident(){ return (resident);} // some aren't currently in use, I have left in-case assign 2 is similar
    public void setResident(boolean inputResident){resident = inputResident;}

    public double getWeeklyExpenses(){ return weeklyExpenses;}
    public void setWeeklyExpenses(double inputExpenses){weeklyExpenses = inputExpenses;}

    public boolean getClientUsed(){return clientUsed;}
    public void setClientUsed(boolean clientUsed){this.clientUsed = clientUsed;}

    public int getNumOfAccUsed(){return numOfAccUsed;}


    //These setters and getters allow me to access methods inside the acc Account objects
    public double getCalcInv(int num){  // used to access Account methods inside CalculatorInterface
        if(num==0){return acc[0].calcInv();} // returns acc[0] calcInv method
        else{return acc[1].calcInv();} // returns acc[1] calcInv method
    }

    public int getWks(int num){
        if(num==0){return acc[0].getNum_wks();}
        else{return acc[1].getNum_wks();}
    }

    public void setWks(int num_wks,int num){
        if(num==0){acc[0].setNum_wks(num_wks);}
        else{acc[1].setNum_wks(num_wks);}
    }

    public double getInv(int num){
        if(num==0){return acc[0].getInv();}
        else{return acc[1].getInv();}
    }

    public double getCalcCont(int num){
        if(num==0){return acc[0].calcContribution();}
        else{return acc[1].calcContribution();}
    }

    public double getRate(int num){
        if (num == 0){return acc[0].getInvRate();}
        else{return acc[1].getInvRate();}
    }

    public boolean getAccUsed(int num) {
        if (num == 0) {return acc[0].getAccUsed();}
        else{return acc[1].getAccUsed();}
    }

    public void setAccUsed(boolean setValue, int num) {
        if (num == 0) {acc[num].setAccUsed(setValue);}
        if(num == 1){acc[num].setAccUsed(setValue);}
    }

    public void toStringPrep() {
        accA = "No Account";
        accB = "No Account";
        try{
            if (getAccUsed(0)) {
                accA = String.valueOf(getCalcInv(0));
            }
            if (getAccUsed(1)) {
                accB = String.valueOf(getCalcInv(1));
            }
        }
        catch(NullPointerException e){
            System.out.println("");
        }
    }

    @Override
    public String toString() {

        return "Client{" +
                "name='" + name + '\'' +
                ", Gross Salary=" + grossSalary +
                ", Gross Salary Weekly="+ calcWeeklyGross()+
                ", Net Salary=" + calcNet() +
                ", Net Salary Weekly="+calcWeeklyNet()+
                ", Tax=" + calcTax() +
                ", Tax Weekly="+calcWeeklyTax() +
                ", Account One="+accA+
                ", Account Two="+accB+
                '}';
    }
}
