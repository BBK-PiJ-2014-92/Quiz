package Server;

import Interfaces.Question;
import Interfaces.Quiz;
import Interfaces.Score;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed on 2/28/2015.
 */
public class QuizServer extends UnicastRemoteObject implements QuizService {
    private List<Quiz> quizzes;

    public QuizServer() throws RemoteException {
        super();
        this.quizzes = new ArrayList<Quiz>();
        File newFile = new File("Quiz.txt");
        if (newFile.exists()){
            try {
                FileInputStream fis = new FileInputStream(newFile);
                ObjectInputStream input = new ObjectInputStream(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public int newQuiz(String quizName, Question... questions) throws RemoteException {
        return 0;
    }

    public Score closeQuiz(int quizID) throws RemoteException {
        return null;
    }

    public void playQuiz(Score score, int quizID) throws RemoteException {

    }

    public List<Quiz> currentQuizzes() throws RemoteException {
        return null;
    }

    public boolean isCorrectAnswer(Question currentQuestion, String selectedAnswer) throws RemoteException {
        return false;
    }

    public int totalScore(Score score, Quiz quiz) throws RemoteException {
        return 0;
    }
}
