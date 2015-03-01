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
 * Created by Ahmed on 2/28/2015.
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

    public int newQuiz(String quizName) {
        Quiz quiz = new QuizImpl(quizName, quizIDs.generateID());
        quizzes.add(quiz);
        flush();
        return quiz.getID();

    }

    public Quiz getQuiz(int id) {
        Quiz chosenQuiz = null;
        for (Quiz quiz : quizzes){
            if (quiz.getID() == id) {
                chosenQuiz = quiz;
            }
        }
        return chosenQuiz;
    }

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

    public synchronized void openQuiz(int quizID) throws RemoteException {
        Quiz chosenQuiz = getQuiz(quizID);
        if (chosenQuiz != null) {
            chosenQuiz.setClosed(false);
            flush();
        }else {
            throw new IllegalArgumentException("The Quiz with ID " + quizID + " does not exist");
        }

    }

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

    public List<Quiz> currentQuizzes() {
        return quizzes;
    }

    public synchronized void setQuizzes(List<Quiz> quizzes) throws RemoteException {
        this.quizzes = quizzes;
    }

    public boolean isCorrectAnswer(Question currentQuestion, String selectedAnswer)  {
        return currentQuestion.getCorrectAnswer().equals(selectedAnswer);
    }

    public synchronized void flush()  {
        File newFile = new File("Quiz.txt");
        try {
            FileOutputStream fos = new FileOutputStream(newFile);
            ObjectOutputStream output = new ObjectOutputStream(fos);
            output.reset(); // Similar to deleting the file, this just resets what is currently available in the file
            output.writeObject(quizzes);
            System.out.println("Quizzes successfully written");
            output.close();
            fos.close();
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
