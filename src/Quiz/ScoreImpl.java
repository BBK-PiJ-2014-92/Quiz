package Quiz;

import Interfaces.Score;

import java.io.Serializable;

/**
 * A class for holding the scores of quizzes. It contains the name of the player, and the score
 * NB: The same player can have the exact same score across many different quizzes
 * Created by Ahmed
 */
public class ScoreImpl implements Score, Serializable, Comparable<Score>{
    private String name;
    private int score;

    public ScoreImpl(String name, int score) {
        this.name =  name;
        this.score = score;
    }

    /**
     * Returns the score of the player
     *
     * @return the score of the player
     */
    public int getScore() {
        return score;
    }
    /**
     * Returns the name of the player
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the score of the player
     * @param score The new score to be set for the player
     */
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
