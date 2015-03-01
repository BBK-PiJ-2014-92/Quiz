package Quiz;

import Interfaces.Score;
import Interfaces.Question;
import Interfaces.Quiz;

import java.io.Serializable;
import java.util.*;

/**
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

    public TreeSet<Score> getHighScores() {
        return highScores;
    }

    public boolean getClosed() {
        return closed;
    }

    public void setClosed(boolean closed){
        this.closed = closed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestions(List<Question> newQuestions) {
        this.questions.addAll(newQuestions);
    }

    public List<String> getPlayers() {
        List<String> players = new ArrayList<String>();
        for (Score score : highScores) {
            players.add(score.getName());
        }
        return players;
    }

    public void addScore(Score score) {
        highScores.add(score);
    }

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
