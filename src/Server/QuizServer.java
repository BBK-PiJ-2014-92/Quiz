package Server;

import Interfaces.Question;
import Interfaces.Quiz;
import Interfaces.Score;
import Quiz.QuizImpl;
import Quiz.ScoreImpl;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Ahmed on 2/28/2015.
 */
public class QuizServer extends UnicastRemoteObject implements QuizService {
    private List<Quiz> quizzes;

    @SuppressWarnings("unchecked")
    public QuizServer() throws RemoteException {
        super();
        this.quizzes = new ArrayList<Quiz>();
        File newFile = new File("Quiz.txt");
        if (newFile.exists()){
            try {
                FileInputStream fis = new FileInputStream(newFile);
                ObjectInputStream input = new ObjectInputStream(fis);
                Object o = input.readObject();
                if (o instanceof ArrayList<?>) {
                    this.quizzes = (ArrayList<Quiz>) o;
                    System.out.println("Quizzes successfully added");
                }
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

    private Quiz getQuiz(int id) throws RemoteException{
        Quiz chosenQuiz = null;
        for (Quiz quiz : quizzes){
            if (quiz.getID() == id) {
                chosenQuiz = quiz;
            }
        }
        return chosenQuiz;
    }

    public boolean addQuestion(int id, List<Question> questions) throws RemoteException{
        Quiz chosenQuiz = getQuiz(id);
        if (chosenQuiz != null) {
            chosenQuiz.addQuestions(questions);
            return true;
        }else{
            throw new IllegalArgumentException("The Quiz with ID " + id + " does not exist");
        }
    }

    public Score closeQuiz(int quizID) throws RemoteException {
        Quiz chosenQuiz = getQuiz(quizID);
        if (chosenQuiz != null) {
            chosenQuiz.setClosed(true);
            TreeSet<Score> highScores = chosenQuiz.getHighScores();
            return highScores.isEmpty() ? null : highScores.first();
        }else {
            throw new IllegalArgumentException("The Quiz with ID " + quizID + " does not exist");
        }
    }

    public void openQuiz(int quizID) throws RemoteException {
        Quiz chosenQuiz = getQuiz(quizID);
        if (chosenQuiz != null) {
            chosenQuiz.setClosed(false);
        }else {
            throw new IllegalArgumentException("The Quiz with ID " + quizID + " does not exist");
        }

    }

    public int playQuiz(String player, int quizID, List<String> playerChoices) throws RemoteException {
        Quiz chosenQuiz = getQuiz(quizID);
        int score = 0;
        if (chosenQuiz != null) {
            if (!chosenQuiz.getClosed()) {
                for (int i = 0; i < playerChoices.size() - 1; i++) {
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
        return score;
    }

    public List<Quiz> currentQuizzes() throws RemoteException {
        return quizzes;
    }

    public boolean isCorrectAnswer(Question currentQuestion, String selectedAnswer) throws RemoteException {
        return currentQuestion.getCorrectAnswer().equals(selectedAnswer);
    }

}
