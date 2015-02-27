package Quiz;

import Interfaces.Player;
import Interfaces.Question;
import Interfaces.Quiz;

import java.io.Serializable;
import java.util.ArrayList;
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

    public QuizImpl(String name) {
        this.name = name;
        IDCOUNT++;
        this.quizID = IDCOUNT;
        questions = new ArrayList<Question>();
        highScores = new TreeMap<Player, Integer>();
    }

    public TreeMap<Player, Integer> getHighScores() {
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

    public int getID() {
        return quizID;
    }
}
