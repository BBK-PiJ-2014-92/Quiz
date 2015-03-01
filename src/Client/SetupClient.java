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
 * A client class used for generating quizzes on the server
 * Created by Ahmed
 */
public class SetupClient {
    private QuizService server;

    /**
     * Connects the user to the server
     */
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

    /**
     * Gives the user choices of creating, closing, opening or adding questions to quizzes
     * @throws RemoteException
     */
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
            int number = integerCheck(sc);
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

    /**
     * Returns the server
     * @return the server
     */
    public QuizService getServer() {
        return server;
    }

    /**
     * Returns the ID of the newly created quiz on the server. This method calls another method, addQuestions before
     * terminating.
     * @return the ID of the newly created quiz
     * @throws RemoteException
     */
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

    /**
     * A checker method that checks to see if the inputted parameter is blank. It is to ensure that the user does not
     * enter a blank string into the other methods, and would loop until the user types in an appropriate parameter
     * @param parameter the parameter to be tested to see if it is blank
     * @param sc the input of the user
     * @return an appropriate string parameter to be used in other methods
     */
    private String whileBlank(String parameter, Scanner sc) {
        while (parameter.trim().isEmpty()) {
            System.out.println("Cannot be blank. Please try again");
            parameter = sc.nextLine();
        }
        return parameter;
    }

    /**
     * A checker method that loops if the user tries to parse an inappropriate string into an integer
     * @param sc the input from the user
     * @return an appropriately parsed integer
     */
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

    /**
     * A method which allows the user to add questions to existing or newly created quizzes
     * @param id the ID of the quiz in which the new questions are going to be added to
     * @param sc the input of the user in order to decide question name and answers
     */
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
            int size = integerCheck(sc);
            while (size < 3) {
                System.out.println("Please enter a number greater than or equal to 3: ");
                size = integerCheck(sc);
            }
            List<String> possibleAnswers = new ArrayList<String>();
            String answer;
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

    /**
     * Returns ID of a quiz from a given list of IDs. Usually used in smaller more focused lists
     * @param listOfIDs the list of IDs of quizzes
     * @param sc the input of the user
     * @return the ID of a chosen quiz that are available in the list of IDs
     */
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

    /**
     * A method which allows the user to add questions to strictly existing quizzes
     * @throws RemoteException
     */
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

    /**
     * Closes the quiz from being played and returns the top player
     * @return The top player of the quiz or null if none attempted the quiz
     * @throws RemoteException
     */
    public Score closeQuiz() throws RemoteException {
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

    /**
     * Opens a previously closed quiz to be played on by players
     * @throws RemoteException
     */
    public void openQuiz() throws RemoteException {
        List<Quiz> closedQuizzes = new ArrayList<Quiz>();
        List<Integer> idsOfQuizzes = new ArrayList<Integer>();
        for (Quiz quiz : server.currentQuizzes()) {
            if (quiz.getClosed()) {
                closedQuizzes.add(quiz);
            }
        }
        if (closedQuizzes.isEmpty()) {
            System.out.println("There are no closed quizzes to choose from");
        }else {
            System.out.println("Here is a current list of closed quizzes: ");
            for (Quiz quiz : closedQuizzes) {
                System.out.println(quiz);
                idsOfQuizzes.add(quiz.getID());
            }
            System.out.println("Select which quiz to open by typing in the ID number (type in any non number to quit)");
            Scanner sc = new Scanner(System.in);
            int id = getIDFromGivenList(idsOfQuizzes, sc);
            if (id != 0) {
                server.openQuiz(id);
                System.out.println("Quiz " + id + " is now open");
            }

        }
    }
}
