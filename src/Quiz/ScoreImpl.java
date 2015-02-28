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

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScoreImpl)) return false;

        ScoreImpl score = (ScoreImpl) o;

        if (!name.equals(score.name)) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Score{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(Score o) {
        int scoreDiff = o.getScore() - score;
        if(scoreDiff != 0) {
            return scoreDiff;
        }else {
            return name.compareToIgnoreCase(o.getName());
        }
    }
}
