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

    public void launch() throws RemoteException {
        serverConnection();
        System.out.println("Welcome to quiz creator!");
        boolean finished = false;
        while (!finished) {
            System.out.println("Please select one of the following: ");
            System.out.println("1. Create a new quiz");
            System.out.println("2. Close an existing quiz");
            System.out.println("3. Open a closed quiz");
            System.out.println("4. Add questions to an existing quiz");
            System.out.println("5. Quit");
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
    }

    public QuizService getServer() {
        return server;
    }

    public int newQuiz() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Name of Quiz: ");
        String name = sc.nextLine();
        name = whileBlank(name, sc);
        int id = server.newQuiz(name);
        addQuestions(id, sc);
        System.out.println("Quiz " + name + " has ID " + id);
        return id;
    }

    private String whileBlank(String parameter, Scanner sc) {
        while (parameter.trim().isEmpty()) {
            System.out.println("Cannot be blank. Please try again");
            parameter = sc.nextLine();
        }
        return parameter;
    }

    public void addQuestions(int id, Scanner sc) {
        boolean finished = false;
        List<Question> questions = new ArrayList<Question>();
        while (!finished) {
            System.out.println("Please enter a question: ");
            String question = sc.nextLine();
            question = whileBlank(question, sc);

            System.out.println("Please enter the correct answer to this question: ");
            String correctAnswer = sc.nextLine();
            correctAnswer = whileBlank(correctAnswer, sc);

            System.out.println("Please enter the number of wrong answers you want to include (must be at least 3): ");
            boolean isInt = sc.hasNextInt();
            while (!isInt) {
                System.out.println("Please enter a number");
                sc.nextLine();
                isInt = sc.hasNextInt();
            }
            int size = Integer.parseInt(sc.nextLine());
            while (size < 3) {
                System.out.println("Please enter a number greater than or equal to 3: ");
                size = Integer.parseInt(sc.nextLine());
            }
            List<String> possibleAnswers = new ArrayList<String>();
            String answer = null;
            for (int i = 0; i < size; i++) {
                answer = sc.nextLine();
                answer = whileBlank(answer, sc);
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
                    server.addQuestions(id, questions);
                    finished = true;
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
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

    public void addQuestions() throws RemoteException {
        List<Quiz> quizzes = server.currentQuizzes();
        List<Integer> idsOfQuizzes = new ArrayList<Integer>();
        System.out.println("Here is a current list of quizzes: ");
        for (Quiz quiz : quizzes) {
            System.out.println(quiz);
            idsOfQuizzes.add(quiz.getID());
        }
        System.out.println("Select which quiz to add questions to by typing in the ID number (type in any non number to quit)");
        Scanner sc = new Scanner(System.in);
        int id = getIDFromGivenList(idsOfQuizzes,sc);
        if (id != 0) {
            addQuestions(id, sc);
        }
    }

    public synchronized Score closeQuiz() throws RemoteException {
        List<Quiz> openedQuizzes = new ArrayList<Quiz>();
        List<Integer> idsOfQuizzes = new ArrayList<Integer>();
        Score topScore = null;
        for (Quiz quiz : server.currentQuizzes()) {
            if (!quiz.getClosed()) {
                openedQuizzes.add(quiz);
            }
        }
        if (openedQuizzes.isEmpty()) {
            System.out.println("There are no available open quizzes to be closed");
        }else {
            System.out.println("Here is a current list of opened quizzes: ");
            for (Quiz quiz : openedQuizzes) {
                System.out.println(quiz);
                idsOfQuizzes.add(quiz.getID());
            }
            System.out.println("Select which quiz to close by typing in the ID number (type in any non number to quit)");
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

    public void openQuiz() throws RemoteException {
        List<Quiz> closedQuizzes = new ArrayList<Quiz>();
        List<Integer> idsOfQuizzes = new ArrayList<Integer>();
        for (Quiz quiz : server.currentQuizzes()) {
            if (quiz.getClosed()) {
                closedQuizzes.add(quiz);
            }
        }
        System.out.println("Here is a current list of closed quizzes: ");
        for (Quiz quiz : closedQuizzes) {
            System.out.println(quiz);
            idsOfQuizzes.add(quiz.getID());
        }
        System.out.println("Select which quiz to open by typing in the ID number (type in any non number to quit)");
        Scanner sc = new Scanner(System.in);
        int id = getIDFromGivenList(idsOfQuizzes, sc);
        server.openQuiz(id);
        System.out.println("Quiz " + id + " is now open");
    }
}
