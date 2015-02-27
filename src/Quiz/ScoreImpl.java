package Quiz;

import Interfaces.Score;

import java.io.Serializable;

/**
 * Created by Ahmed on 2/26/2015.
 */
public class ScoreImpl implements Score, Serializable, Comparable<Score>{
    private String name;
    private int score;

    public ScoreImpl(String name, int score) {
        this.name =  name;
        this.score = score;
    }


    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }


    @Override
    public int compareTo(Score o) {
        return 0;
    }
}
