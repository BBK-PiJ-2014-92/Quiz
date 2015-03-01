package Client;

import Server.QuizService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Created by Ahmed on 3/1/2015.
 */
public class SetupClient {
    private QuizService server;

    public void serverConnection() {
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(1099);
            server = (QuizService) registry.lookup("quiz");
            System.out.println("Connected to QuizServer");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }

    public int newQuiz() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Name of Quiz: ");
        String name = sc.nextLine();
        while (name.trim().isEmpty() || name == null) {
            System.out.println("Quiz name cannot be blank nor null. Pleas try again");
            name = sc.nextLine();
        }
        return server.newQuiz(name);
    }

    public void addQuestions
}
