import java.util.Scanner;

public class Menu {
    Client[] client;
    public Menu(){}
    public Menu(Client[] clientImport){
        this.client = clientImport;
    }
    public static void main(String[] args){


    }
    public void menu_launcher(){
        Scanner console = new Scanner(System.in); // creates new Scanner object as console

        Account account = new Account(0.3,52,300);
        final int maxClients = 5;
        //Client[] client = new Client[maxClients];
        for (int i=0;i<5;i++){
            client[i] = new Client();
        }


        System.out.println(client[0].getName());
        client[0].setAccountOne(account);
        //client[0].setName("john");
        System.out.println(client[0].getAccountOne().calcInv());


        System.out.println("Menu:");
        System.out.println("1. Add Client");
        System.out.println("2. Delete Client");
        System.out.println("3. Display Client");
        System.out.print("Choice: ");

        int menu_choice = console.nextInt();
        switch (menu_choice) {
            case 1 -> {
                //addClient(client);
            }
            case 2 -> {
                //delClient();
            }
            case 3 -> {
                //dispClient(client);
            }
        }
    }
    }

