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
    private double inv_rate; // investment rate as a % (0.29)
    private int num_wks; // number of weeks the investment stays invested
    private double inv; // value of weekly contribution

    public Account(){


    }


    public double calcInv(){
        double FV; // value of investment after it's been compounded, means Future Value
        double t = 1; // if we wanted to calculate investment over multiple years this would be used
        int n = 52; // number of weeks in a year, I have used small var names to condense the FV line of code

        //FA = inv*(1+(inv_rate/100)/numberOfWeeks)*Math.pow(51,1); // previous attempts at creating algo
        //FA = Math.pow((1+inv_rate/12), 12)+(Math.pow((1+inv_rate/12), 12)-1)/(inv_rate/12);
        //FA = ((Math.pow((1+(inv_rate/12)), 12)*inv-1) / (inv_rate/12)) + 5*Math.pow((1+(inv_rate/12)), 12);

        FV = inv*((Math.pow((1+inv_rate/n),(num_wks*t))-1)/(inv_rate/n))*(1+inv_rate/n);
        // a complicated (but far superior) way of finding the compound interest from
        // regular contributions. This one line was the hardest part of the assignment to create.
        // this formula compounds weekly and can calculate the value from 1 to 52 weeks, years can be easily implemented.
        // also calculates assuming that the contribution is made at the start of each week.

        return new BigDecimal(FV).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    public void setinv_Rate(double inputRate){ inv_rate = inputRate/100;}
    public double getinvRate()
    {
        return(inv_rate);
    }

    public void setInv(double inputInvestment){ inv = inputInvestment;}
    public double getInv(){return inv;}

    public void setNum_wks(int inputWeeks){ num_wks = inputWeeks;}
    public int getNum_wks(){return num_wks;}
}
