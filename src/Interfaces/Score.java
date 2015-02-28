package Interfaces;

/**
 * Created by Ahmed on 2/26/2015.
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
