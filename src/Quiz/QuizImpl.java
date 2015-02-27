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
    private static int IDCOUNT = 0;
    private List<Question> questions;
    private Set<Score> highScores;

    public QuizImpl(String name) {
        this.name = name;
        IDCOUNT++;
        this.quizID = IDCOUNT;
        questions = new ArrayList<Question>();
        highScores = new TreeSet<Score>();
    }

    public Set<Score> getHighScores() {
        return highScores;
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
}
