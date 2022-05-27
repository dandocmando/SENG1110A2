/*
Author: Daniel Ferguson
Auth ID: 3374690
Date: 09/05/2022
Description: Calculator Interface Class, outputs all the calculations inside the two other classes.
SENG1110 Programming Assignment 2
 */

import java.util.*;
import java.io.*;

public class CalculatorInterface
{
    private final Client[] cli = new Client[5]; // creates the array of Client objects
    private int numOfCliUsed = 0; // tracks the number of clients currently used

    public CalculatorInterface(){ // constructor for CalculatorInterface

        for (int i=0;i<5;i++){ // loops 5 times
            cli[i] = new Client(); // creates 5 new cli Client objects
        }

        cli[0] = new Client("daniel ferguson",90000,true,200);
        cli[1] = new Client("john kok",45000,true,350);
        cli[2] = new Client("zzz pppp",45000,true,350);
        cli[3] = new Client("aaa ddd",45000,true,350);

        cli[0].createAccount(0.2,52,200);
        cli[0].createAccount(0.2,35,600);

        cli[0].setClientUsed(true);
        cli[1].setClientUsed(true);
        cli[2].setClientUsed(true);
        cli[3].setClientUsed(true);

    }


    public void menu(){ // This is the main menu you access the required functions and methods from

        Scanner console = new Scanner(System.in); // creates new Scanner object as console

        boolean endProgram = false;
        while(!endProgram){

            numOfCliUsed = 0; // resets the var
            for (Client client : cli) { // this is an enhanced for loop, it was added to java in 2004
                if (client.getClientUsed()){ // if the client is used numofcli var is incremented by 1
                    numOfCliUsed++; // inside endProgram so var updates when each function modifies the clients
                }
            }

            System.out.print("\n"); // prints out menu
            System.out.println("Menu:");
            System.out.println("1. Add Client");
            System.out.println("2. Delete Client");
            System.out.println("3. Add Account");
            System.out.println("4. Delete Account");
            System.out.println("5. Display all Clients");
            System.out.println("6. Display Client");
            System.out.println("7. Display Account");
            System.out.println("8. Save Clients to a file");
            System.out.println("0. End Program");
            System.out.print("Choice: ");

            int menu_choice = console.nextInt(); // determines which case is used
            switch(menu_choice){
                case 1 -> addClient();
                case 2 -> delClient();
                case 3 -> addAccount();
                case 4 -> delAccount();
                case 5 -> disAllClient();
                case 6 -> disClient();
                case 7 -> disAccount();
                case 8 -> fileSave();
                case 0-> endProgram = true; // ends the program by stopping the while loop
                default -> endProgram = true; // if no option is chosen program ends
            }

            if(!endProgram){ // program would ask twice to end the program without this
                System.out.print("Would you like to return to the menu? (Y or N): ");
                String menu_return = console.next(); // shows up before the menu is launched again
                if (!menu_return.equalsIgnoreCase("Y")) {
                    endProgram = true;
                }
            }
        }
    }


    public void addClient(){ // adds data into an unused cli[] in the cli array of Clients

        Scanner console = new Scanner(System.in); // creates new Scanner object as console
        boolean accountUsed = true;
        int cliRem = 0; // var used to determine how many clients remain (Rem)
        while(accountUsed){ // loops until false
            try{
                if(cli[cliRem].getClientUsed()){ // checks if cli[i] is used, once out of bounds, catch is output
                    cliRem++;
                }
                else{
                    accountUsed = false; // ends loop

                    System.out.print("Please enter your full name: ");
                    String nameIn = console.nextLine();
                    nameIn = isNameAllowed(nameIn); // passes name back into nameIn, checks if allowed
                    cli[cliRem].setName(nameIn); // assigns the String name var in the Client object with name var defined by user
                    System.out.print("Gross income: $");
                    double gross = belowZeroChecker_double(inputChecker_double(console.next())); // uses inputChecker method to verify if input is a double
                    cli[cliRem].setGross(gross); // uses set method to assign grossSalary var in Client object

                    System.out.print("Resident(Enter true or false): ");
                    boolean resident = bool_check(console.next());
                    cli[cliRem].setResident(resident); // assigns the resident bool var in Client object with user true/false input

                    System.out.println("Weekly net Income: $"+cli[cliRem].calcWeeklyNet()); // outputs amount user cannot spend more than

                    System.out.print("Enter your weekly expenses (Enter an average): $");
                    double weeklyExpenses = belowZeroChecker_double(inputChecker_double(console.next()));
                    // uses belowZero and inputChecker methods to make sure String input is a double and also above 0

                    boolean spendingAllowed = false; // initialises the loop bool
                    while(!spendingAllowed){ // loops until spendingAllowed = true
                        if(cli[cliRem].calcWeeklyNet() < weeklyExpenses){ // checks if expenses is higher than weekly net
                            System.out.println("You currently spend more than your net earnings per week, would you like to enter a new value?");
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
                    cli[cliRem].setWeeklyExpenses(weeklyExpenses);
                    cli[cliRem].setClientUsed(true); // this bool is used all over the program to check if cli[] has been used
                }
            } catch (ArrayIndexOutOfBoundsException e) { // output when all 5 accounts are set as used
                accountUsed = false; // stops the acc used loop
                System.out.println("You have used all the 5 client slots.");
            }
        }
    }


    public void addAccount() { // adds an account to a specified Client object

        Scanner console = new Scanner(System.in);
        boolean allAccountsUsed = true;
        for(Client client : cli){
            if (client.getNumOfAccUsed() !=2) { // checks if the number of accounts equals 2 for each cli[]
                allAccountsUsed = false; // 2 means that both account slots in the array are used
                break;
            }
        }
        int accAdd = 0; // defined outside while loop so can be accessed later

        boolean accAddAllowed = false;
        while(!accAddAllowed){
            System.out.println("Which client would you like to add an account to?");
            for(int i = 0; i<5; i++){ // runs through the list of available clients, don't type a name, enter the number.
                if(cli[i].getClientUsed()){
                    {
                        System.out.println(i+1+". "+cli[i].getName()); // I have added 1 so selection starts at 1 not 0
                    }
                }
            }

            System.out.print("Choice (1,2,3 etc): ");
            accAdd = inBounds(belowZeroChecker_int(inputChecker_int(console.next())));
            // uses 3 methods together to ensure:
            // 1: input is an int. (inputChecker_int)
            // 2: input is above 0. (belowZeroChecker_int)
            // 3: input is inside the bounds, which means the user chose an option printed on the list above. (inBounds)
            // the methods will correct all these issues, before user passes this point, input will comply.

            if (cli[accAdd].getNumOfAccUsed() == 2){ // checks if the client already has two accounts
                System.out.print("\n");
                System.out.println("Client: "+cli[accAdd].getName()+" already has two accounts."); // outputs the client has 2 accounts
                System.out.print("\n");
            }
            else{
                accAddAllowed = true; // ends loop
            }
        }

        if(!allAccountsUsed){ // runs if one of the used clients has at least one account slot available

            System.out.println(accAdd);
            System.out.println("Maximum possible investment weekly: $" + cli[accAdd].calcPossibleInvestment());
            System.out.print("Enter the amount you would like to invest per week: ");
            double inv = belowZeroChecker_double(inputChecker_double(console.next()));
            boolean invAllowed = false; // initialises bool as false
            while (!invAllowed) { // loops until invAllowed = true
                if (inv > cli[accAdd].calcPossibleInvestment()) { // checks if inv is greater than the possible investment
                    System.out.print("Please enter an investment below $" + cli[accAdd].calcPossibleInvestment() + ": ");
                    inv = inputChecker_double(console.next());
                } else {
                    invAllowed = true; // stops loop
                    System.out.println("Investment value accepted.");
                }
            }
            System.out.print("Enter the investment rate % (between 0.01 and 0.2): ");
            double inv_rate = belowZeroChecker_double(inputChecker_double(console.next()));
            boolean invRateAllowed = false;
            while (!invRateAllowed) {
                if (inv_rate < 0.01 || inv_rate > 0.20) {
                    System.out.print("Please enter an investment rate between 0.01 and 0.2: ");
                    inv_rate = belowZeroChecker_double(inputChecker_double(console.next()));
                } else {
                    invRateAllowed = true;
                }
            }

            int num_wks;
            System.out.println("Would you like to invest for an entire year?");
            System.out.print("Enter Y or N: ");
            String invest_year = console.next();


            if (invest_year.equalsIgnoreCase("Y")) {
                num_wks = 52;
            }
            else{
                System.out.print("Enter the number of weeks you would like to invest for: ");
                num_wks = inputChecker_int(console.next());
                boolean invLength = false;
                while (!invLength) {
                    if (num_wks > 52 || num_wks <= 0) { // checks if weeks is inside boundaries
                        System.out.print("Please enter a number between 1-52 (weeks): ");
                        num_wks = inputChecker_int(console.next());
                    } else
                        invLength = true; // ends loop
                }
            }
            cli[accAdd].createAccount(inv_rate,num_wks,inv); // creates a new account
        }
        else{
            System.out.println("No clients currently exist, please add a client first.\n");
        }
    }


    public void delClient(){ // This function deletes a client from the cli array

        Scanner console = new Scanner(System.in);

        System.out.println("Which client would you like to delete?");
        for (int i = 0; i < 5;i++ ){
            if(cli[i].getClientUsed()){ // checks if clientUsed is true for each client
                System.out.println(i+1 + ". " + cli[i].getName()); // prints the list of clients
            }
        }
        System.out.print("Choice (1,2,3 etc): ");
        int cliDel = belowZeroChecker_int(inputChecker_int(console.next())); // uses 2 methods on cliDel input
        cliDel = inBounds(cliDel); // ensures user cannot choose client that doesn't exist

        String tempName = cli[cliDel].getName(); // creates tempName before client is deleted & thus getName is null
        cli[cliDel] = new Client(); // new client object created in specified cli
        clientShuffle(); // moves all clients up, empty space in array removed
        System.out.println("Client " + tempName + " deleted\n");
    }


    public void delAccount() { //This function deletes an account from a specified client in the cli array

        Scanner console = new Scanner(System.in);

        System.out.println("Which clients account would you like to delete?");
        for (int i = 0; i < 5; i++) {
            if(cli[i].getClientUsed()){ // prints out the list of cli objects used
                System.out.println(i+1+". "+cli[i].getName());
            }
        }

        System.out.print("Choice (1,2,3 etc): ");
        int cliNum = inBounds(belowZeroChecker_int(inputChecker_int(console.next()))); // refer to previous explanation

        System.out.println("Which account would you like to delete?");
        if (cli[cliNum].getNumOfAccUsed() == 0) { // checks if number of accounts for the specified accounts is 0
            System.out.println("No accounts created for this client, create an account first.");
        }
        if(cli[cliNum].getNumOfAccUsed()>0){
            if ((cli[cliNum].getNumOfAccUsed() == 1)) {
                System.out.println("1. Account One, investment: $" + cli[cliNum].getCalcInv(0));
            }
            if (cli[cliNum].getNumOfAccUsed() == 2) {
                System.out.println("1. Account One, investment: $" + cli[cliNum].getCalcInv(0));
                System.out.println("2. Account Two, investment: $" + cli[cliNum].getCalcInv(1));
            }
            System.out.print("Choice: ");
            int accDel = belowZeroChecker_int(inputChecker_int(console.next()))-1;

            System.out.println(accDel);
            cli[cliNum].deleteAccount(accDel); // uses the method inside client to delete the specified account
            cli[cliNum].accountShuffle(); // moves account 2 into account 1 slot if applicable
            cli[cliNum].setAccUsed(false, cliNum); // sets the account deleted to not used

        }
    }


    public void disClient(){ //This function displays a specified client

        Scanner console = new Scanner(System.in);
        System.out.println("Which client would you like to view?");
        for (int i = 0; i < 5;i++ ) {
            if(cli[i].getClientUsed()){ // prints list of clients
                System.out.println(i+1 + ". " + cli[i].getName());
            }
        }
        System.out.print("Choice (1,2,3 etc): ");
        int view = inBounds(belowZeroChecker_int(inputChecker_int(console.next()))); // selects the client to view

        //Prints out all user tax and salary values, shows pre-tax & post tax salary, plus tax applied & medicare levy
        System.out.print("\n");
        System.out.println("------------------------------------------------------------");
        System.out.println("Name:" + cli[view].getName() + "\n");
        System.out.println("Gross Salary");
        System.out.println("Per week: $" + cli[view].calcWeeklyGross());
        System.out.println("Per Year: $" + cli[view].getGross() + "\n");

        System.out.println("Tax Applied");
        System.out.println("Per week: $" + cli[view].calcWeeklyTax());
        System.out.println("Per year: $" + cli[view].calcTax() + "\n");

        System.out.println("Net Salary");
        System.out.println("Per week: $" + cli[view].calcWeeklyNet());
        System.out.println("Per year: $" + cli[view].calcNet() + "\n");

        System.out.println("Medicare Levy Per Year: $" + cli[view].calcMedicare()+"\n");

        System.out.println("Weekly Expenses: $"+cli[view].getWeeklyExpenses());
        System.out.println("Remaining money able to be invested: $"+cli[view].calcPossibleInvestment()+"\n");

        try{
            cli[view].getCalcInv(0); // if the account doesn't exist this will gen an error and the catch msg will display
            System.out.println("Investment Account One:"); // this will be displayed if the account exists
            System.out.println("Contributed: $"+cli[view].getCalcCont(0)+" over "+cli[view].getWks(0)+" weeks at "+cli[view].getRate(0)*100+"% interest.");
            System.out.println("Current balance: $"+ cli[view].getCalcInv(0)+"\n");

        } catch (NullPointerException e) { // this is the exception created by the first try line
            System.out.println("Account One: Does not exist.\n"); // informs user that the account doesn't exist
        }

        try{
            cli[view].getCalcInv(1);
            System.out.println("Investment results Account Two:");
            System.out.println("Contributed: $" + cli[view].getCalcCont(1) + " over " + cli[view].getWks(1) + " weeks at " + cli[view].getRate(1) * 100 + "% interest.");
            System.out.println("Current balance: $"+ cli[view].getCalcInv(1)+"\n");

        } catch(NullPointerException e){
            System.out.println("Account Two: Does not exist.");
        }
        System.out.println("------------------------------------------------------------");
        System.out.print("\n");
    }


    public void disAccount(){ // Displays the specified account

        Scanner console = new Scanner(System.in);
        System.out.println("Which clients account would you like to view?");
        for (int i = 0; i < 5;i++ ) {
            if(cli[i].getClientUsed()){ // displays the list of clients
                System.out.println(i+1 + ". " + cli[i].getName());
            }
        }
        System.out.print("Choice (1,2,3 etc): ");
        int view = inBounds(belowZeroChecker_int(inputChecker_int(console.next()))); // selects the client
        System.out.println("Client: "+cli[view].getName()+"\n"); // prints clients name


        try{
            cli[view].getCalcInv(0); // if the account doesn't exist this will gen an error and the catch msg will display
            System.out.println("Investment Account One:"); // this will be displayed if the account exists
            System.out.println("Contributed: $"+cli[view].getCalcCont(0)+" over "+cli[view].getWks(0)+" weeks at "+cli[view].getRate(0)*100+"% interest.");
            System.out.println("Current balance: $"+ cli[view].getCalcInv(0)+"\n");

            investTable(cli[view],0);

        } catch (NullPointerException e) { // this is the exception created
            System.out.println("Account One: Does not exist.\n"); // informs user that the account doesn't exist
        }

        try{
            cli[view].getCalcInv(1);
            System.out.println("Investment Account Two:");
            System.out.println("Contributed: $" + cli[view].getCalcCont(1) + " over " + cli[view].getWks(1) + " weeks at " + cli[view].getRate(1) * 100 + "% interest.");
            System.out.println("Current balance: $"+ cli[view].getCalcInv(1)+"\n");

            investTable(cli[view],1);

        } catch(NullPointerException e){
            System.out.println("Account Two: Does not exist.");
        }
    }


    public void disAllClient(){ // Displays all used clients inside the cli array

        clientSort(); // this function sorts the cli array by the name var in the client object
        for (Client client : cli) { // loops through the cli array of Client objects
            if (client.getClientUsed()) { // if ClientUsed is false then object cli[i] won't be printed.
                //Prints out all user tax and salary values, shows pre-tax & post tax salary, plus tax applied & medicare levy
                System.out.print("\n");
                System.out.println("------------------------------------------------------------");
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

                System.out.println("Medicare Levy Per Year: $" + client.calcMedicare() + "\n");

                System.out.println("Weekly Expenses: $"+client.getWeeklyExpenses());
                System.out.println("Remaining money able to be invested: $"+client.calcPossibleInvestment()+"\n");

                try {
                    client.getCalcInv(0); // if the account doesn't exist this will gen an error and the catch msg will display
                    System.out.println("Investment Account One:"); // this will be displayed if the account exists
                    System.out.println("Contributed: $" + client.getCalcCont(0) + " over " + client.getWks(0) + " weeks at " + client.getRate(0) * 100 + "% interest.");
                    System.out.println("Current balance: $" + client.getCalcInv(0) + "\n");
                } catch (NullPointerException e) { // this is the exception created by the first try line
                    System.out.println("Account One: Does not exist.\n"); // informs user that the account doesn't exist
                }

                try {
                    client.getCalcInv(1);
                    System.out.println("Investment Account Two:");
                    System.out.println("Contributed: $" + client.getCalcCont(1) + " over " + client.getWks(1) + " weeks at " + client.getRate(1) * 100 + "% interest.");
                    System.out.println("Current balance: $" + client.getCalcInv(1) + "\n");
                } catch (NullPointerException e) {
                    System.out.println("Account Two: Does not exist.");
                }
                System.out.println("------------------------------------------------------------");
            }
        }
        System.out.print("\n");
    }


    public void clientShuffle(){ // shuffles the array of Client objects cli, this is used when a client is deleted

        int index = 0;
        for(int i=0;i<cli.length;i++){ // loops for the length of cli
            if(cli[i].getClientUsed()){ // this is a var used in cli to declare if the cli object is used
                Client temp = cli[index]; // simple sorting array which moves all the used cli objects in the array
                cli[index] = cli[i]; // to the front of the array
                cli[i]= temp;
                index++;
            }
        }
    }


    public void clientSort(){ // sorts the array of Client objects cli into alphabetical order

        for(int i=0;i<cli.length-1;i++){ // loops for the length of cli -1
            for(int j=i+1;j<cli.length;j++){ // loops for the length of i + 1, this is done so i and j are different cli[]
                if(cli[i].getName().compareToIgnoreCase(cli[j].getName()) > 0){ // compares the name of cli[i] and cli[j]
                    // if the result of the above if statement is > 0, that means cli[j] is closer to the start of the
                    // alphabet compared to cli[i] (cli[j] is lexicographically first)
                    // if this is the case then cli[j] and cli[i] are swapped by the below code

                    // assignment specifications do not mention a specific sort, or that using certain java
                    // functions (compareToIgnoreCase) are prohibited

                    //"You can choose any sorting algorithm. The important point is that you need to implement it."
                    // - Regina Berretta 06/05/2022

                    Client temp = cli[i]; // creates a Client object temp
                    cli[i] = cli[j];
                    cli[j]=temp;
                    // these steps result in cli[j] and cli[i] being the value of the other.
                }
            }
        }
        clientShuffle(); // fixes the order of the sorted cli array
    }


    public void investTable(Client cliInput, int accNum){
        // This method prints out the investment table

        System.out.println("Investment\n Weeks        Balance");
        System.out.println("-------------------------");
        int tempWks = cliInput.getWks(accNum); // sets temp to the number of weeks the investment period is
        for(int i = 4;i<= tempWks;i+=4){ // loops at an interval of 4 until the loop reaches temp
            cliInput.setWks(i,accNum); // modifies the num_wks value to change getCalvInv output
            System.out.println(i+" :           $"+cliInput.getCalcInv(accNum)); // prints out the value at i week
        }
        System.out.println("-------------------------");
        System.out.print("\n");
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
        catch(NumberFormatException e){  // exception caused if the String cannot be a double
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


    public double belowZeroChecker_double(double numInput){
        // This function checks if the user entered a number below zero and asks them to enter a number above 0

        Scanner console = new Scanner(System.in);
        boolean above_zero = false;
        if(numInput <=0){ // checks if the value is already above 0, if so then the while below is bypassed
            while(!above_zero){ // loops until loop is true
                System.out.print("Please enter a number above 0: ");
                numInput = inputChecker_double(console.next()); // takes input, passes through input checker first.
                if(numInput > 0){ // checks if the new input is above 0
                    above_zero = true; // stops loop
                }
            }
        }
        return numInput; // returns modified value
    }


    public int belowZeroChecker_int(int numInput){
        // This function checks if the user entered a number below zero and asks them to enter a number above 0

        Scanner console = new Scanner(System.in);
        boolean above_zero = false;
        if(numInput <=0){ // checks if the value is already above 0, if so then the while below is bypassed
            while(!above_zero){ // loops until loop is true
                System.out.print("Please enter a number above 0: ");
                numInput = inputChecker_int(console.next()); // takes input, passes through input checker first.
                if(numInput > 0){ // checks if the new input is above 0
                    above_zero = true; // stops loop
                }
            }
        }
        return numInput; // returns modified value
    }


    public boolean bool_check(String boolInput){
        // This function checks if the string input can be parsed into boolean.

        Scanner console = new Scanner(System.in);
        boolean bool_allowed = false; // initialises variables (vars henceforth)
        boolean resident = false;
        while(!bool_allowed){ // loops until bool_allowed = true
            if (boolInput.equalsIgnoreCase("true") || boolInput.equalsIgnoreCase("false")) {
                // above if statement compares "true" & "false" to the user input in bool_check var
                resident = Boolean.parseBoolean(boolInput); // if bool_check var is a bool, it will be converted
                bool_allowed = true; // stops the loop
            } else {
                System.out.print("That's not a boolean, please enter a boolean: ");
                boolInput = console.next(); // loops until user enters a legal input
            }
        }
        return resident;
    }


    public String isNameAllowed(String nameIn){ // checks if the user has entered a first and last name

        Scanner console = new Scanner(System.in);
        String[] split_name = nameIn.split(" "); // splits input string into a String array

        boolean name_allowed = false;
        while(!name_allowed){ // loops until true
            if(split_name.length != 2){ // if this is false user hasn't entered two names
                System.out.print("Please enter a first and last name: ");
                nameIn = console.nextLine(); // takes new input
                split_name = nameIn.split(" "); // splits new input, loops until array len ==2
            }
            else{
                name_allowed = true; // ends loop
            }
        }
        return nameIn; // returns potentially modified nane.
    }


    public int inBounds(int clientChoice){
        // checks if user input is inside the bounds of the client array currently used
        Scanner console = new Scanner(System.in);
        boolean inBounds = false;
        while(!inBounds){
            if(clientChoice>numOfCliUsed){
                System.out.print("Please choose a client number listed: ");
                clientChoice = belowZeroChecker_int(inputChecker_int(console.next()));
            }
            else{
                inBounds = true; // ends loop
            }
        }
        clientChoice-=1; // because client list starts at 1, and cli[] at 0, user choice will be 1 high

        return clientChoice; // returns value that is inside bounds
    }


    public void fileSave(){ // This method writes all clients into the export file

        BufferedWriter bw = null; // creates the BufferedReader as null, so it's outside the try
        try{
            File file = new File("export.txt"); // new File class created as file object
            bw = new BufferedWriter(new FileWriter(file)); // new BufferedWriter and FileWriter class created
            if(!cli[0].getClientUsed()){ // this checks if cli[0] is flagged as used, because of the clientShuffle system
                // the first client slot will always be filled
                bw.write("No Clients."); // writes no clients to the file
            }
            else{ // if clients have been created this will execute
                for(Client client : cli){ // loops for the number of cli[] in cli array
                    if(client.getClientUsed()){ // stops empty clients being written
                        client.toStringPrep(); // check toStringPrep comments in Client Class
                        bw.write(client.toString()); // writes the toString method into the file
                        bw.write(System.getProperty("line.separator")); // each repetition a new line will be created
                        System.out.print("Client: "+client.getName()+" exported."); // tells user which clients exported
                    }
                }
            }

            System.out.print("\n");
            System.out.println("File written Successfully"); // tells user file has been written

        } catch (Exception e) { // catches required for BufferedWriter and FileWriter objects
            System.out.println("Unable to write to file. "+e);
        }
        finally // runs on try completion
        {
            try{
                if(bw!=null) // makes sure bw object isn't null before closing
                    bw.close(); // closes the BufferedReader, without this step data stream doesn't end = file blank

            } catch (IOException e) {
                System.out.println("Program unable to close BufferedWriter. "+e);
            }
        }
    }


    public static void main(String[] args){  // calc class main method
        CalculatorInterface calc = new CalculatorInterface(); // creates new CalculatorInterface object as calc
        calc.menu(); // launches the menu
    }
}
        
    

    
    
    
    
    
    
    


        

	