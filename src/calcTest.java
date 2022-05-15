import java.util.*;
public class calcTest {
    private Client[] cli = new Client[5];
    private int cliRM;
    public calcTest(){
        for (int i=0;i<5;i++){ // loops 5 times
            cli[i] = new Client(); // creates 5 new cli Client objects
        }
        cli[0] = new Client("daniel ferguson",90000,true,200);
        cli[1] = new Client("john nigga",45000,true,350);
        cli[2] = new Client("penis nigga",45000,true,350);
        cli[3] = new Client("rat nigga",45000,true,350);
        cli[4] = new Client("jew nigga",45000,true,350);
        //cliRM=1;
        cli[0].setClientUsed(true);
        cli[1].setClientUsed(true);
        cli[2].setClientUsed(true);
        cli[3].setClientUsed(true);
        cli[4].setClientUsed(true);

    }
    public void tester() {
        Scanner console = new Scanner(System.in);
        boolean accountUsed = true;
        cliRM = 0;

        while (accountUsed) {
            if (cli[cliRM].getClientUsed()) {
                if (cliRM==4){
                    accountUsed = false;
                    System.out.println(cliRM);
                    System.out.println("nigger");
                }
                else{
                    cliRM++;
                }

            }
            System.out.println(cliRM);
            //accountUsed = false;

        }
        System.out.println("Which client would you like to delete?");
        for (int i = 0; i < 5;i++ ) {
            if(cli[i].getClientUsed()){
                System.out.println(i+1 + ". " + cli[i].getName());
            }


        }
    }
    public void testerV2(){
        Scanner console = new Scanner(System.in);
        System.out.println("Which client would you like to delete?");
        int i =1;
        for (Client client : cli) {
            if(client.getClientUsed()){
                System.out.println(i + ". " + client.getName());
                i++;
            }
        }
        System.out.print("Choice (1,2,3 etc): ");
        int cliDel = console.nextInt()-1;
        String tempName = cli[cliDel].getName();
        cli[cliDel].setClientUsed(false);
        clientShuffle();
        System.out.println("Client " + tempName + " deleted\n");


    }
    public static void main(String[] args){
        calcTest calc = new calcTest();


        calc.testerV2();

    }
    public void clientShuffle(){
        int index = 0;
        for(int i=0;i<cli.length;i++){
            if(cli[i].getClientUsed()){
                Client temp = cli[index];
                cli[index] = cli[i];
                cli[i]= temp;
                index++;
            }
        }
        cliRM--;
        System.out.println("new list");
        for (int i = 0; i < 5;i++ ) {
            if(cli[i].getClientUsed()){
                System.out.println(i+1 + ". " + cli[i].getName());
            }


        }
    }
}
