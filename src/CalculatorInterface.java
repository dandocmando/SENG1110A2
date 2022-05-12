/*
Author: Daniel Ferguson
Auth ID: 3374690
Date: 09/05/2022
Description: Calculator Interface Class, outputs all the calculations inside the two other classes.
SENG1110 Programming Assignment 2
 */

import java.util.*;

public class CalculatorInterface
{
    public void run() {
        // This is the main function all data is displayed in
        Scanner console = new Scanner(System.in); // creates new Scanner object as console
        Client client = new Client(); // creates the required objects from Client & Account classes
        Account account = new Account();
        System.out.print("Please enter your full name: ");
        String nameIn = console.nextLine();
        nameIn = client.isNameAllowed(nameIn); // passes name back into nameIn

        client.setName(nameIn); // assigns the String name var in the client object with name var defined by user

        System.out.print("Gross income: $");
        double gross = inputChecker_double(console.next()); // uses inputChecker method to verify if input is a double
        boolean isPositive = false;
        while(!isPositive){
            if(gross < 0){
                System.out.print("Please enter a value above 0: $");
                gross = inputChecker_double(console.next());
            }
            else
                isPositive = true;

        }
        client.setGross(gross);
        //System.out.println(client.getGross()); // pulls the grossSalary var from the client object using the getGross getter method

        System.out.print("Resident: (Enter true or false)");
        // can't build a function for bool as the parsebool only accepts true, all other inputs default to false
        String bool_check = console.next(); // user inputs bool as a string, so I can use the below if function
        boolean bool_allowed = false; // initialises variables (vars henceforth)
        boolean resident = false;
        while(!bool_allowed){ // loops until bool_allowed = true
            if (bool_check.equalsIgnoreCase("true") || bool_check.equalsIgnoreCase("false")) {
                // above if statement compares "true" & "false" to the user input in bool_check var
                resident = Boolean.parseBoolean(bool_check); // if bool_check var is a bool, it will be converted
                bool_allowed = true; // stops the loop
            } else {
                System.out.print("That's not a boolean, please enter a boolean: ");
                bool_check = console.next(); // loops until user enters a legal input
            }
        }
        client.setResident(resident); // assigns the resident bool var in client object with user true/false input
        //System.out.println(client.getResident()); // used for debugging

        //Prints out all user tax and salary values, shows pre-tax & post tax salary, plus tax applied & medicare levy
        System.out.println("Name:" + client.getName() + "\n");
        System.out.println("Gross Salary");
        System.out.println("Per week: $" + client.calcWeeklyGross());
        System.out.println("Per Year: $" + client.getGross() + "\n");

        System.out.println("Tax Applied");
        System.out.println("Per week: $" + client.calcWeeklyTax());
        System.out.println("Per year: $" + client.calcTax() + "\n");

        System.out.println("Net Salary");
        System.out.println("Per week: $" + client.calcWeeklyNet());
        System.out.println("Per year: $" + client.calcNet() + "\n");

        System.out.println("Medicare Levy Per Year: $" + client.calcMedicare());

        System.out.print("Enter your weekly expenses (Enter an average): $");
        double weeklyExpenses = inputChecker_double(console.next());

        while(weeklyExpenses <= 0){ // ensures user doesn't enter 0 or below
            System.out.print("Please enter a value above 0: $");
            weeklyExpenses = inputChecker_double(console.next());
        }
        boolean spendingAllowed = false; // initialises the loop bool
        while(!spendingAllowed){ // loops until spendingAllowed = true
            if(client.calcWeeklyNet() < weeklyExpenses){ // checks if expenses is higher than weekly net
                System.out.println("You currently spend more than your net earnings per week, would you like to enter a new value or end the program?");
                System.out.print("(Y or N): ");
                String willCont = console.next();
                if(willCont.equalsIgnoreCase("Y")){
                    System.out.print("Please enter a new weekly expense value: $");
                    double newSpending = inputChecker_double(console.next());
                    while(newSpending <= 0){ // ensures user doesn't enter a new number that is negative
                        System.out.print("Please enter a value above 0: $");
                        newSpending = inputChecker_double(console.next());
                    }
                    weeklyExpenses = newSpending; // assigns new weekly expenses from newSpending input
                }
                else
                    System.exit(0); // ends program
            }
            else
                spendingAllowed = true; // ends the loop
        }

        client.setWeeklyExpenses(weeklyExpenses);
        System.out.println("Maximum possible investment weekly: $" + client.calcPossibleInvestment());
        System.out.print("Enter desired investment: $");
        double inv = inputChecker_double(console.next());
        while(inv <= 0){
            System.out.print("Please enter a value above 0: $");
            inv = inputChecker_double(console.next());
        }

        boolean invAllowed = false; // initialises bool as false
        while (!invAllowed) { // loops until invAllowed = true
            if (inv > client.calcPossibleInvestment()) { // checks if inv is greater than the possible investment
                System.out.print("Please enter an investment below $" + client.calcPossibleInvestment() + ": ");
                inv = inputChecker_double(console.next());
            } else {
                invAllowed = true; // stops loop
                System.out.println("Investment value accepted.");
                account.setInv(inv);
            }
        }

        System.out.print("Enter the investment rate as a %: ");
        double inv_rate = inputChecker_double(console.next());
        account.setinv_Rate(inv_rate);

        System.out.println("Would you like to invest for an entire year?");
        System.out.print("Enter Y or N: ");
        String invest_year = console.next();

        if (invest_year.equalsIgnoreCase("Y")) {
            account.setNum_wks(52); // assigns weeks to 52 as that is an entire year in wks
        } else {
            System.out.print("Enter the number of weeks you would like to invest for: ");
            int invest_weeks = inputChecker_int(console.next());
            boolean overYear = false;
            while(!overYear){
                if(invest_weeks > 52 || invest_weeks <= 0){ // checks if weeks is inside boundaries
                    System.out.print("Please enter a number between 1-52 (weeks): ");
                    invest_weeks = inputChecker_int(console.next());
                }
                else
                    overYear = true; // ends loop
            }
            account.setNum_wks(invest_weeks); // allows the user to set custom number of weeks
        }

        System.out.println("\nInvestment value after " + account.getNum_wks() + " weeks: $" + account.calcInv());
    }


     public double inputChecker_double(String input){
        // This function checks if an input String can be turned into a double
        Scanner console = new Scanner(System.in);

        double inVar = 0;  // initialises variables
        boolean loop;
        String in = input;

         //This is needed so if the user initially enters a double they aren't asked to re-enter it in the while loop
         try{
             inVar = Double.parseDouble(in);  // attempts to convert String into a double
             loop = false;  // the above line doesn't give an error then the loop isn't required
         }
         catch(NumberFormatException e){  // this is the error caused if the String cannot be a double
             loop = true;
         }

         while(loop){ // loops until loop = false
             try {
                 System.out.print("Enter a double: ");
                 in = console.next(); // assigns in the users input
                 inVar = Double.parseDouble(in);
                 loop = false;
             }
             catch(NumberFormatException e){
                 System.out.println("That wasn't a double.");
             }
        }
         return inVar; // returns value modified or not
     }

    public int inputChecker_int(String input){
        // This function is the same as inputChecker_double, refer to that function for comments
        Scanner console = new Scanner(System.in);

        int inVar = 0;
        boolean loop;
        String in = input;

        try{
            inVar = Integer.parseInt(in);  // attempts to convert String to an int
            loop = false;
        }
        catch(NumberFormatException e){
            loop = true;
        }

        while(loop){
            try {
                System.out.print("Enter an Integer: ");
                in = console.next();
                inVar = Integer.parseInt(in);
                loop = false;
            }
            catch(NumberFormatException e){
                System.out.println("That wasn't an Integer.");
            }
        }
        return inVar;
    }
    public void menu(){
        Scanner console = new Scanner(System.in); // creates new Scanner object as console
        System.out.println("Menu:");
        System.out.println("1. Add Client");
        System.out.println("2. Delete Client");
        System.out.println("3. Display Client");
        System.out.print("Choice: ");

        int menu_choice = console.nextInt();
        switch (menu_choice) {
            case 1 -> {
                System.out.print("1");
                addClient();
            }
            case 2 -> {
                System.out.print("2");
                delClient();
            }
            case 3 -> {
                System.out.print("3");
                dispClient();
            }
        }
    }

    public void addClient(){
        Scanner console = new Scanner(System.in); // creates new Scanner object as console
        Client client = new Client(); // creates the required objects from Client & Account classes
        Account account = new Account();
        OpenCSVReader openCsvReader = new OpenCSVReader();


        System.out.print("Please enter your full name: ");
        String nameIn = console.nextLine();
        nameIn = client.isNameAllowed(nameIn); // passes name back into nameIn


        client.setName(nameIn); // assigns the String name var in the client object with name var defined by user





    }
    public void addAccount(){

    }
    public void delClient(){

    }
    public void delAccount(){

    }

    public void dispClient(){

    }
    public void dispAccount(){

    }
    public void dispAllClient(){

    }


    public static void main(String[] args)
    {
        CalculatorInterface calc = new CalculatorInterface(); // creates new CalculatorInterface object as calc
        //calc.run(); // runs run function inside calc object
        calc.menu();
    }
}
        
    

    
    
    
    
    
    
    


        

	