package Client;

import Interfaces.Question;
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
import java.util.TreeSet;

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

    public void launch() throws RemoteException {
        serverConnection();
        System.out.println("Welcome to the quiz server!");
        boolean finished = false;
        while (!finished) {
            System.out.println("Please select one of the following: ");
            System.out.println("1. Play a quiz");
            System.out.println("2. Highscores");
            System.out.println("3. Quit");
            Scanner sc = new Scanner(System.in);
            int number = integerCheck(sc);
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

    private int integerCheck(Scanner sc) {
        int number = 0;
        boolean done = false;
        while (!done) {
            try {
                number = Integer.parseInt(sc.nextLine());
                done = true;
            }catch (NumberFormatException e) {
                System.out.println("Please enter a number: ");
            }
        }
        return number;
    }

    private int getIDFromGivenList (List<Integer> listOfIDs, Scanner sc) {
        int id = 0;
        if (!sc.hasNextInt()) {
            System.out.println("Thanks for playing");
        }else {
            id = integerCheck(sc);
            while (!listOfIDs.contains(id)) {
                System.out.println("Please enter a number from the list");
                id = integerCheck(sc);
            }
        }
        return id;
    }

    public void playQuiz() throws RemoteException {
        List<Quiz> openedQuizzes = new ArrayList<Quiz>();
        List<Integer> idsOfQuizzes = new ArrayList<Integer>();
        int score = 0;
        for (Quiz quiz : server.currentQuizzes()) {
            if (!quiz.getClosed()) {
                openedQuizzes.add(quiz);
            }
        }
        if (openedQuizzes.isEmpty()) {
            System.out.println("There are no available quizzes");
        }else {
            System.out.println("Here is a list of opened quizzes: ");
            for (Quiz quiz : openedQuizzes) {
                System.out.println(quiz);
                idsOfQuizzes.add(quiz.getID());
            }
            System.out.println("Select which quiz to play by typing in the ID number (type in any non number to quit)");
            Scanner sc = new Scanner(System.in);
            int id = getIDFromGivenList(idsOfQuizzes,sc);
            System.out.println("Please enter your name: ");
            String player = sc.nextLine().toUpperCase();
            Quiz selectedQuiz = server.getQuiz(id);
            List<String> answers = new ArrayList<String>();
            for (Question question : selectedQuiz.getQuestions()) {
                System.out.println(question.getQuestionName());
                List<String> possibleAnswers = question.createChoices();
                for (int i = 0; i < possibleAnswers.size(); i++) {
                    System.out.println(i+1 + ". " + possibleAnswers.get(i));
                }
                int choice = integerCheck(sc);
                while (choice > 4) {
                    System.out.println("Please select an appropriate answer");
                    choice = Integer.parseInt(sc.nextLine());
                }
                answers.add(possibleAnswers.get(choice - 1));
                if (server.isCorrectAnswer(question, possibleAnswers.get(choice - 1))) {
                    System.out.println("Correct");
                }else {
                    System.out.println("The correct answer is " + question.getCorrectAnswer());
                }
            }
            score = server.playQuiz(player, id, answers);
        }
        System.out.println("Final score: " + score);
    }

    public void highScores() throws RemoteException {
        List<Integer> idsOfQuizzes = new ArrayList<Integer>();
        if (server.currentQuizzes().isEmpty()) {
            System.out.println("There are no available quizzes");
        } else {
            System.out.println("Here is a list of all the quizzes: ");
            for (Quiz quiz : server.currentQuizzes()) {
                System.out.println(quiz);
                idsOfQuizzes.add(quiz.getID());
            }
            System.out.println("Select which quiz to see the highscores of by typing in the ID number (type in any non number to quit)");
            Scanner sc = new Scanner(System.in);
            int id = getIDFromGivenList(idsOfQuizzes, sc);
            TreeSet<Score> highscores = server.getQuiz(id).getHighScores();
            for (Score score : highscores) {
                System.out.println(score.getName() + " " + score.getScore());
            }
        }
    }
}
