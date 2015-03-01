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
        while (name.trim().isEmpty()) {
            System.out.println("Quiz name cannot be blank. Please try again");
            name = sc.nextLine();
        }
        int id = server.newQuiz(name);
        System.out.println("Quiz " + name + " has ID " + id);
        return id;
    }

    public void addQuestions(int id) {
        boolean finished = false;
        Scanner sc = new Scanner(System.in);
        while (!finished) {
            System.out.println("Please enter a question: ");
            String question = sc.nextLine();
            while (question.trim().isEmpty()) {
                System.out.println("Question cannot be blank. Please try again");
                question = sc.nextLine();
            }
            System.out.println("Please enter the correct answer to this question: ");
            String correctAnswer = sc.nextLine();
            while (correctAnswer.trim().isEmpty()) {
                System.out.println("Answer cannot be blank. Please try again");
                correctAnswer = sc.nextLine();
            finished = true;
        }
    }
}
