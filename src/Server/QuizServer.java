package Server;

import Interfaces.Question;
import Interfaces.Quiz;
import Interfaces.Score;
import Quiz.QuizImpl;
import Quiz.ScoreImpl;
import Quiz.IdSingleton;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * The server in which the clients connect to in order to play/create quizzes
 * Created by Ahmed
 */
public class QuizServer extends UnicastRemoteObject implements QuizService {
    private List<Quiz> quizzes;
    private IdSingleton quizIDs;

    @SuppressWarnings("unchecked")
    public QuizServer() throws RemoteException {
        super();
        this.quizzes = new ArrayList<Quiz>();
        this.quizIDs = new IdSingleton();
        File newFile = new File("Quiz.txt");
        if (newFile.exists()){
            try {
                FileInputStream fis = new FileInputStream(newFile);
                ObjectInputStream input = new ObjectInputStream(fis);
                this.quizzes = (ArrayList<Quiz>) input.readObject();
                System.out.println("Quizzes successfully added");
                this.quizIDs = (IdSingleton) input.readObject();
                System.out.println("Quiz IDs successfully added");
                input.close();
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * Returns the current ID
     * @return the current ID
     */
    public IdSingleton getQuizIDs() {
        return quizIDs;
    }
    /**
     *
     * Creates a new quiz on the server and returns the quiz ID
     * @param quizName The name of the quiz
     * @return The ID of the newly created quiz
     */
    public int newQuiz(String quizName) {
        Quiz quiz = new QuizImpl(quizName, quizIDs.generateID());
        quizzes.add(quiz);
        flush();
        return quiz.getID();

    }
    /**
     * Returns the quiz with the specified ID
     * @param id The ID of the quiz
     * @return The quiz with the specified ID
     */
    public Quiz getQuiz(int id) {
        Quiz chosenQuiz = null;
        for (Quiz quiz : quizzes){
            if (quiz.getID() == id) {
                chosenQuiz = quiz;
            }
        }
        return chosenQuiz;
    }

    /**
     * Adds questions to a specified quiz ID
     * @param id The ID of the quiz that the questions will be placed in
     * @param questions A set of questions that will be added in the specified ID
     * @return true if add was successful
     * @throws RemoteException
     */
    public synchronized boolean addQuestions(int id, List<Question> questions) throws RemoteException{
        Quiz chosenQuiz = getQuiz(id);
        if (chosenQuiz != null) {
            chosenQuiz.addQuestions(questions);
            flush();
            return true;
        }else{
            throw new IllegalArgumentException("The Quiz with ID " + id + " does not exist");
        }
    }

    /**
     * Closes the quiz with the given ID and returns the winner of the specified quiz. Players would be
     * unable to play
     * @param quizID The ID of the quiz
     * @return The full details of the winning player of the specified quiz
     * @throws RemoteException
     */
    public synchronized Score closeQuiz(int quizID) throws RemoteException {
        Quiz chosenQuiz = getQuiz(quizID);
        if (chosenQuiz != null) {
            chosenQuiz.setClosed(true);
            TreeSet<Score> highScores = chosenQuiz.getHighScores();
            flush();
            return highScores.isEmpty() ? null : highScores.first();
        }else {
            throw new IllegalArgumentException("The Quiz with ID " + quizID + " does not exist");
        }
    }

    /**
     * Opens the quiz with the given ID, if it is already closed. Allows players to play the specified quiz
     * @param quizID The ID of the quiz
     * @throws RemoteException
     * @throws IllegalArgumentException if the quiz does not exist
     */
    public synchronized void openQuiz(int quizID) throws RemoteException {
        Quiz chosenQuiz = getQuiz(quizID);
        if (chosenQuiz != null) {
            chosenQuiz.setClosed(false);
            flush();
        }else {
            throw new IllegalArgumentException("The Quiz with ID " + quizID + " does not exist");
        }

    }

    /**
     * Allows the specified player to play a quiz matching the given quiz ID with a given list of choices and returns
     * that player's score
     * @param player The name of the player about to play the specified quiz
     * @param quizID The ID of the quiz about to be played
     * @param playerChoices A list of answers that the player has picked
     * @return The score the specified player achieved
     * @throws RemoteException
     */
    public synchronized int playQuiz(String player, int quizID, List<String> playerChoices) throws RemoteException {
        Quiz chosenQuiz = getQuiz(quizID);
        int score = 0;
        if (chosenQuiz != null) {
            if (!chosenQuiz.getClosed()) {
                for (int i = 0; i < playerChoices.size(); i++) {
                    if (isCorrectAnswer(chosenQuiz.getQuestions().get(i), playerChoices.get(i))) {
                        score++;
                    }
                }
                for (Score score1 : chosenQuiz.getHighScores()) {
                    if (score1.getName().equals(player) && score1.getScore() < score) {
                        score1.setScore(score);
                    }
                }
                if (!chosenQuiz.getPlayers().contains(player)) {
                    chosenQuiz.getHighScores().add(new ScoreImpl(player, score));
                }
            }else {
                throw new IllegalStateException("Quiz is not available to play");
            }
        }else {
            throw new IllegalArgumentException("This Quiz with ID " + quizID + " does not exist");
        }
        flush();
        return score;
    }

    /**
     * Returns a list of the current available quizzes
     * @return List of all available quizzes
     */
    public List<Quiz> currentQuizzes() {
        return quizzes;
    }

    /**
     * Sets the current list of quizzes with the specified list
     * @param quizzes The list that will replace the current list
     * @throws RemoteException
     */
    public synchronized void setQuizzes(List<Quiz> quizzes) throws RemoteException {
        this.quizzes = quizzes;
    }
    /**
     * Returns whether the answer selected by the player is the correct answer to the question
     * @param currentQuestion The question that is currently being asked
     * @param selectedAnswer The answer in which the player has selected
     * @return true if the answer is correct
     */
    public boolean isCorrectAnswer(Question currentQuestion, String selectedAnswer)  {
        return currentQuestion.getCorrectAnswer().equals(selectedAnswer);
    }
    /**
     * Saves the changes to the server on a text file
     */
    public synchronized void flush()  {
        File newFile = new File("Quiz.txt");
        try {
            FileOutputStream fos = new FileOutputStream(newFile);
            ObjectOutputStream output = new ObjectOutputStream(fos);
            output.reset(); // Similar to deleting the file, this just resets what is currently available in the file
            output.writeObject(quizzes);
            System.out.println("Quizzes successfully written");
            output.writeObject(quizIDs);
            System.out.println("Quiz IDs successfully written");
            output.close();
            fos.close();
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
