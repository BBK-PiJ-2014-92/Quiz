package Server;

import Interfaces.Player;
import Interfaces.Question;
import Interfaces.Quiz;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Ahmed on 2/25/2015.
 */
public interface QuizService extends Remote{
    /**
     *
     * Creates a new quiz on the server and returns the quiz ID
     * @param quizName The name of the quiz
     * @param questions An arbitrary number of questions in which the quiz will contain
     * @return The ID of the newly created quiz
     */
    public int newQuiz(String quizName, Question... questions) throws RemoteException;
    /**
     *
     * Closes the quiz with the given ID and returns the winner of the specified quiz
     * @param quizID The ID of the quiz
     * @return The full details of the winning player of the specified quiz
     */
    public Player closeQuiz(int quizID) throws RemoteException;
    /**
     *
     * Allows the specified player to play a quiz matching the given quiz ID
     * @param player The player that is going to play a quiz
     * @param quizID The ID of the quiz about to be played
     */
    public void playQuiz(Player player, int quizID) throws RemoteException;
    /**
     *
     * Returns a list of the current available quizzes
     * @return List of all available quizzes
     */
    public List<Quiz> currentQuizzes() throws RemoteException;
    /**
     * Returns whether the answer selected by the player is the correct answer to the question
     * @param currentQuestion The question that is currently being asked
     * @param selectedAnswer The answer in which the player has selected
     */
    public boolean isCorrectAnswer(Question currentQuestion, String selectedAnswer) throws RemoteException;
    /**
     *
     * Returns the total score for a given player at a given quiz
     * @param player The current player
     * @param quiz The current quiz
     * @return The total score for a specific player playing a specific quiz
     */
    public int totalScore(Player player, Quiz quiz) throws RemoteException;
}
