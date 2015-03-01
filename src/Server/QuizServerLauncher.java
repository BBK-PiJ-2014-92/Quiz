package Server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by Ahmed on 2/28/2015.
 */
public class QuizServerLauncher {
    public static void main(String[] args) {
        QuizServerLauncher launcher = new QuizServerLauncher();
        launcher.launch();
    }

    public void launch() {
        try {
            LocateRegistry.createRegistry(1099);
            QuizServer server = new QuizServer();
            Naming.rebind("quiz", server);
            System.out.println("Connecting to the QuizServer");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the QuizServer");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Could not find server");
        }
    }
}
