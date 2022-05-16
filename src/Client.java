/*
Author: Daniel Ferguson
Auth ID: 3374690
Date: 09/05/2022
Description: Client Class, calculates the users tax and salary
SENG1110 Programming Assignment 2
 */
import java.math.BigDecimal; // used to round vars to 2 dec places for weekly calc
import java.math.RoundingMode;

public class Client
{
    private String name; // all instance variables have been set to private as requested
    private double grossSalary;
    private boolean resident;
    private double medicare;
    private double weeklyExpenses;
    private double ctx; // calculate tax
    private boolean clientUsed;
    private int accUsed;
    private Account[] acc;


    public Client(String name, double grossSalary, boolean resident, double weeklyExpenses){
        this.name = name;
        this.grossSalary = grossSalary;
        this.resident = resident;
        this.weeklyExpenses = weeklyExpenses;
        clientUsed = false;
        accUsed = 0;
        acc = new Account[2];
    }

    public Client(){
        name = "";
        grossSalary = 0;
        resident = false;
        weeklyExpenses = 0;
        clientUsed = false;
        accUsed = 0;
        acc = new Account[2];
    }


    public void createAccount(double investmentRate, int numberOfWeeks, double investment){
        acc[accUsed]= new Account(investmentRate,numberOfWeeks,investment);
        accUsed++;
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
        if(getAccUsed()==0){ // this won't deduct the getInvOne as account one doesn't exist yet
            return new BigDecimal(weeklyNet-weeklyExpenses-(calcMedicare()/52)).setScale(2,RoundingMode.HALF_EVEN).doubleValue();
        }
        else{ // this deducts account one investment from the possible investment for account two
            return new BigDecimal(weeklyNet-weeklyExpenses-(calcMedicare()/52)-getInvOne()).setScale(2,RoundingMode.HALF_EVEN).doubleValue();
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

    public int getAccUsed(){return accUsed;}


    //These setters and getters allow me to access methods inside the acc Account objects
    public double getCalcInv(int num){
        if(num==0){return acc[0].calcInv();}
        else{return acc[1].calcInv();}
    }


    public int getWks(int num){
        if(num==0){return acc[0].getNum_wks();}
        else{return acc[1].getNum_wks();}
    }

    public int getWksTwo(){return acc[1].getNum_wks();}

    public double getInvOne(){return acc[0].getInv();}
    public double getInvTwo(){return acc[1].getInv();}

    public double getCalcContOne(){return acc[0].calcContribution();}
    public double getCalcContTwo(){return acc[1].calcContribution();}

    public double getRateOne(){return acc[0].getInvRate();}
    public double getRateTwo(){return acc[1].getInvRate();}
}
