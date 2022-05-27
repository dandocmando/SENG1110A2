/*
Author: Daniel Ferguson
Auth ID: 3374690
Date: 09/05/2022
Description: Account Class, calculates the users' investment valuation
SENG1110 Programming Assignment 2
 */

import java.math.BigDecimal; // used to round vars to 2 dec places x2
import java.math.RoundingMode;

public class Account
{
    private double inv_rate; // investment rate as a % eg 0.2
    private int num_wks; // number of weeks the investment stays invested
    private double inv; // value of weekly contribution
    private boolean accUsed; // used to query the object as to whether the account has been used


    public Account(double investmentRate, int numberOfWeeks, double investment, boolean accUsed){
        // constructor used for all Account object creation
        inv_rate = investmentRate;
        num_wks = numberOfWeeks;
        inv = investment;
        this.accUsed = accUsed;
    }

    public Account(){}


    public double calcInv(){
        double FV; // value of investment after it's been compounded, means Future Value
        double t = 1; // if we wanted to calculate investment over multiple years this would be used
        int n = 52; // number of weeks in a year, I have used small var names to condense the FV line of code

        FV = inv*((Math.pow((1+inv_rate/n),(num_wks*t))-1)/(inv_rate/n)); //*(1+inv_rate/n); // removed so calc is at weeks end
        // calculates the investment, desc removed

        return new BigDecimal(FV).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    public double calcContribution(){ // calculates investment without interest applied
        return new BigDecimal(inv*num_wks).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }


    // setters and getters
    public void setInvRate(double inputRate){ inv_rate = inputRate/100;}
    public double getInvRate()
    {
        return(inv_rate);
    }

    public void setInv(double inputInvestment){ inv = inputInvestment;}
    public double getInv(){return inv;}

    public void setNum_wks(int inputWeeks){ num_wks = inputWeeks;}
    public int getNum_wks(){return num_wks;}

    public void setAccUsed(boolean inputUsed){accUsed = inputUsed;}
    public boolean getAccUsed(){return accUsed;}
}
