package Quiz;

import Interfaces.Score;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Ahmed on 2/26/2015.
 */
public class ScoreImpl implements Score, Serializable, Comparator<Score> {
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
    public int compare(Score score1, Score score2) {
        if (score1.getScore() > score2.getScore()) {
            return -1;
        }else if (score1.getScore() < score2.getScore()) {
            return 1;
        }else {
            return 0;
        }
    }
}
