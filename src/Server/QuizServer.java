package Server;

import Interfaces.Question;
import Interfaces.Quiz;
import Interfaces.Score;
import Quiz.QuizImpl;

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
                this.quizzes = (ArrayList<Quiz>) input.readObject();
                System.out.println("Quizzes successfully added");
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

    public int newQuiz(String quizName) throws RemoteException {
        Quiz quiz = new QuizImpl(quizName);
        quizzes.add(quiz);
        return quiz.getID();

    }

    public boolean addQuestion(int id, List<Question> questions) throws RemoteException{
        Quiz chosenQuiz = null;
        for (int i = 0; i < quizzes.size(); i++) {
            if (quizzes.get(i).getID() == id) {
                chosenQuiz = quizzes.get(i);
            }
        }
        if (chosenQuiz != null) {
            chosenQuiz.addQuestions(questions);
            return true;
        }else{
            return false;
        }
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

}
