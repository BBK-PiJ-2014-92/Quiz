package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

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
}
