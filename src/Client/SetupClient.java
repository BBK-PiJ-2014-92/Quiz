package Client;

import Interfaces.Question;
import Interfaces.Quiz;
import Interfaces.Score;
import Quiz.QuestionImpl;
import Server.QuizService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Ahmed on 3/1/2015.
 */
public class SetupClient {
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

    public int newQuiz() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Name of Quiz: ");
        String name = sc.nextLine();
        name = whileBlank(name);
        int id = server.newQuiz(name);
        addQuestions(id);
        System.out.println("Quiz " + name + " has ID " + id);
        return id;
    }

    private String whileBlank(String parameter) {
        Scanner sc = new Scanner(System.in);
        while (parameter.trim().isEmpty()) {
            System.out.println("Cannot be blank. Please try again");
            parameter = sc.nextLine();
        }
        return parameter;
    }

    public void addQuestions(int id) {
        boolean finished = false;
        List<Question> questions = new ArrayList<Question>();
        Scanner sc = new Scanner(System.in);
        while (!finished) {
            System.out.println("Please enter a question: ");
            String question = sc.nextLine();
            question = whileBlank(question);

            System.out.println("Please enter the correct answer to this question: ");
            String correctAnswer = sc.nextLine();
            correctAnswer = whileBlank(correctAnswer);

            System.out.println("Please enter the number of wrong answers you want to include (must be at least 3): ");
            while (!sc.hasNextInt()) {
                System.out.println("Please enter a number: ");
            }
            int size = sc.nextInt();
            while (size < 3) {
                System.out.println("Please enter a number greater than or equal to 3: ");
                size = sc.nextInt();
            }
            List<String> possibleAnswers = new ArrayList<String>();
            for (int i = 0; i < size; i++) {
                String answer = sc.nextLine();
                answer = whileBlank(answer);
                possibleAnswers.add(answer);
            }
            Question q = new QuestionImpl(question, correctAnswer, possibleAnswers);
            System.out.println("Add another question? Y/N");
            String yesOrNo = sc.nextLine();
            if (yesOrNo.equals("Y") || yesOrNo.equals("y")) {
                questions.add(q);
            }else { //Interpret every other answer as no, in order to save the progress made
                questions.add(q);
                try {
                    server.getQuiz(id).addQuestions(questions);
                    finished = true;
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Score closeQuiz() throws RemoteException {
        List<Quiz> openedQuizzes = server.currentQuizzes();
        List<Integer> idsOfQuizzes = new ArrayList<Integer>();
        for (Quiz quiz : openedQuizzes) {
            if (quiz.getClosed()) {
                openedQuizzes.remove(quiz);
            }
        }
        System.out.println("Here is a current list of opened quizzes: ");
        for (Quiz quiz : openedQuizzes) {
            System.out.println(quiz);
            idsOfQuizzes.add(quiz.getID());
        }
        System.out.println("Select which quiz to close by typing in the ID number (type in any non number to quit)");
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {
            System.out.println("Thanks for playing");
            return null;
        }else {
            int id = sc.nextInt();
            while (!idsOfQuizzes.contains(id)) {
                System.out.println("Please enter a number from the list");
                id = sc.nextInt();
            }
            Score topScore = server.closeQuiz(id);
            System.out.println("The top score for this quiz is " + topScore.getScore() + " by " + topScore.getName());
            return topScore;
        }
    }

    public void openQuiz() throws RemoteException {
        List<Quiz> closedQuizzes = server.currentQuizzes();
        List<Integer> idsOfQuizzes = new ArrayList<Integer>();
        for (Quiz quiz : closedQuizzes) {
            if (!quiz.getClosed()) {
                closedQuizzes.remove(quiz);
            }
        }
        System.out.println("Here is a current list of closed quizzes: ");
        for (Quiz quiz : closedQuizzes) {
            System.out.println(quiz);
            idsOfQuizzes.add(quiz.getID());
        }
        System.out.println("Select which quiz to close by typing in the ID number (type in any non number to quit)");
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {
            System.out.println("Thanks for playing");
        }else {
            int id = sc.nextInt();
            while (!idsOfQuizzes.contains(id)) {
                System.out.println("Please enter a number from the list");
                id = sc.nextInt();
            }
            server.openQuiz(id);
            ;
            System.out.println("Quiz " + id + " is now open");
        }
    }
}
