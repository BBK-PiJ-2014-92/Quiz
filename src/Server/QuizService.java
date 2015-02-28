package Server;

import Interfaces.Question;
import Interfaces.Quiz;
import Interfaces.Score;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

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
    public int newQuiz(String quizName) throws RemoteException;
    /**
     * Adds questions to a specified quiz ID
     * @param id The ID of the quiz that the questions will be placed in
     * @param questions A set of questions that will be added in the specified ID
     */
    public boolean addQuestion(int id, List<Question> questions) throws RemoteException;
    /**
     *
     * Closes the quiz with the given ID and returns the winner of the specified quiz
     * @param quizID The ID of the quiz
     * @return The full details of the winning player of the specified quiz
     */
    public Score closeQuiz(int quizID, Score score) throws RemoteException;
    /**
     *
     * Allows the specified score to play a quiz matching the given quiz ID
     * @param score The score that is going to play a quiz
     * @param quizID The ID of the quiz about to be played
     */
    public void playQuiz(Score score, int quizID) throws RemoteException;
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
}
