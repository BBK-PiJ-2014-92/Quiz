package Client;

import Interfaces.Quiz;
import Interfaces.Score;
import Server.QuizService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
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
                    playQuiz();
                    break;
                case 2:
                    highScores();
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

    public QuizService getServer() {
        return server;
    }

    private int getIDFromGivenList (List<Integer> listOfIDs, Scanner sc) {
        int id = 0;
        if (!sc.hasNextInt()) {
            System.out.println("Thanks for playing");
        }else {
            id = Integer.parseInt(sc.nextLine());
            while (!listOfIDs.contains(id)) {
                System.out.println("Please enter a number from the list");
                id = Integer.parseInt(sc.nextLine());
            }
        }
        return id;
    }

    public Score playQuiz() throws RemoteException {
        List<Quiz> openedQuizzes = new ArrayList<Quiz>();
        List<Integer> idsOfQuizzes = new ArrayList<Integer>();
        for (Quiz quiz : server.currentQuizzes()) {
            if (!quiz.getClosed()) {
                openedQuizzes.add(quiz);
            }
        }
        if (openedQuizzes.isEmpty()) {
            System.out.println("There are no available quizzes available");
        }else {
            System.out.println("Here is a current list of opened quizzes: ");
            for (Quiz quiz : openedQuizzes) {
                System.out.println(quiz);
                idsOfQuizzes.add(quiz.getID());
            }
            System.out.println("Select which quiz to play by typing in the ID number (type in any non number to quit)");
            Scanner sc = new Scanner(System.in);
            int id = getIDFromGivenList(idsOfQuizzes,sc);
            if (id != 0) {
                topScore = server.closeQuiz(id);
                if (topScore == null) {
                    System.out.println("No one has attempted this quiz");
                }else {
                    System.out.println("The top score for this quiz is " + topScore.getScore() + " by " + topScore.getName());
                }
            }
        }

        return topScore;
    }

    public void highScores() {

    }
}
