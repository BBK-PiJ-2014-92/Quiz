package Quiz;

import Interfaces.Player;
import Interfaces.Question;
import Interfaces.Quiz;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Ahmed on 2/27/2015.
 */
public class QuizImpl implements Quiz, Serializable {
    private String name;
    private int quizID;
    private static int IDCOUNT = 0;
    private List<Question> questions;
    private TreeMap<Player, Integer> highScores;

    public TreeMap<Player, Integer> getHighScores() {
        return highScores;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<Question> getQuestions() {
        return questions;
    }

    @Override
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getID() {
        return quizID;
    }
}
