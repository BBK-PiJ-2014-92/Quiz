package Interfaces;

/**
 * A class for holding the scores of quizzes. It contains the name of the player, and the score
 * NB: The same player can have the exact same score across many different quizzes
 * Created by Ahmed
 */
public interface Score {
    /**
     * Returns the name of the player
     *
     * @return the name of the player
     */
    public String getName();
    /**
     * Returns the score of the player
     *
     * @return the score of the player
     */
    public int getScore();
    /**
     * Sets the score of the player
     * @param score The new score to be set for the player
     */
    public void setScore(int score);
}
