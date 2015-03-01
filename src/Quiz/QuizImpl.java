package Quiz;

import Interfaces.Score;
import Interfaces.Question;
import Interfaces.Quiz;

import java.io.Serializable;
import java.util.*;

/**
 * A class for quizzes. Contains quiz name, ID, questions and highscores
 * Created by Ahmed on 2/27/2015.
 */
public class QuizImpl implements Quiz, Serializable {
    private String name;
    private int quizID;
    private List<Question> questions;
    private TreeSet<Score> highScores;
    private boolean closed;

    public QuizImpl(String name, int quizID) {
        this.name = name;
        this.quizID = quizID;
        questions = new ArrayList<Question>();
        highScores = new TreeSet<Score>();
        closed = false;
    }
    /**
     * Returns a set of the current HighScore for this quiz
     *
     * @return A set of the current HighScore for this quiz
     */
    public TreeSet<Score> getHighScores() {
        return highScores;
    }
    /**
     * Returns whether the quiz is closed
     *
     * @return whether the quiz is closed
     */
    public boolean getClosed() {
        return closed;
    }
    /**
     * Sets the closed boolean to the specified boolean i.e. closes/opens the quiz
     */
    public void setClosed(boolean closed){
        this.closed = closed;
    }
    /**
     * Returns the name of the quiz
     *
     * @return the name of the quiz
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name of the quiz to the specified string
     * @param name The name to be set as the name of the quiz
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Returns a list with all the questions in the quiz
     *
     * @return a list with all the questions
     */
    public List<Question> getQuestions() {
        return questions;
    }
    /**
     * Replaces the current available questions with a new specified set of questions
     * @param newQuestions A new set of questions that will replace the old set of questions
     */
    public void setQuestions(List<Question> newQuestions) {
        this.questions = questions;
    }
    /**
     * Adds new questions to the list of existing questions
     * @param newQuestions A new set of questions that will be added to the existing set of questions
     */
    public void addQuestions(List<Question> newQuestions) {
        this.questions.addAll(newQuestions);
    }
    /**
     * Returns a set of players who have attempted this quiz
     *
     * @return a set of players who have attempted this quiz
     */
    public List<String> getPlayers() {
        List<String> players = new ArrayList<String>();
        for (Score score : highScores) {
            players.add(score.getName());
        }
        return players;
    }
    /**
     * Returns a set of the current HighScore for this quiz
     *
     * @return A set of the current HighScore for this quiz
     */
    public void addScore(Score score) {
        highScores.add(score);
    }
    /**
     * Returns the ID of the quiz
     *
     * @return the ID of the quiz
     */
    public int getID() {
        return quizID;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizID=" + quizID +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuizImpl)) return false;

        QuizImpl quiz = (QuizImpl) o;

        if (quizID != quiz.quizID) return false;
        if (!name.equals(quiz.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + quizID;
        return result;
    }
}
