package Client;

import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Created by Ahmed on 3/1/2015.
 */
public class ClientLauncher {
    public static void main(String[] args) throws RemoteException {
        ClientLauncher launcher = new ClientLauncher();
        launcher.launch();
    }

    public void launch() throws RemoteException {
        System.out.println("Welcome to Quiz Server!");
        boolean finished = false;
        while (!finished) {
            System.out.println("Please select one of the following: ");
            System.out.println("1. Setup Quiz");
            System.out.println("2. Play Quiz");
            System.out.println("3. Quit");
            Scanner sc = new Scanner(System.in);
            boolean isInt = sc.hasNextInt();
            while (!isInt) {
                System.out.println("Please enter a number");
                sc.nextLine();
                isInt = sc.hasNextInt();
            }
            int number = Integer.parseInt(sc.nextLine());
            switch (number) {
                case 1:
                    new SetupClient().launch();
                    break;
                case 2:
                    new PlayerClient().launch();
                    break;
                case 3:
                    System.out.println("Thanks for playing");
                    finished = true;
                    break;
                default:
                    System.out.println("Please enter one of the available options");
                    break;
            }

        }
    }
}
