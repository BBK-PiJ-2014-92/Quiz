package Client;

import Interfaces.Score;
import Server.QuizService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Created by Ahmed
 */
public class PlayerClient {
    private QuizService server;

    public void serverConnection() {
        Registry registry;
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

    public void launch() {
        serverConnection();
        System.out.println("Welcome to the quiz server!");
        boolean finished = false;
        while (!finished) {
            System.out.println("Please select one of the following: ");
            System.out.println("1. Play a quiz");
            System.out.println("2. Highscores");
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
                    newQuiz();
                    break;
                case 2:
                    closeQuiz();
                    break;
                case 3:
                    openQuiz();
                    break;
                case 4:
                    addQuestions();
                    break;
                case 5:
                    System.out.println("Thanks for playing");
                    finished = true;
                    break;
                default:
                    System.out.println("Please enter one of the available options");
                    break;
        }
    }

    public Score playQuiz() {

    }

    public void highScores() {

    }
}
