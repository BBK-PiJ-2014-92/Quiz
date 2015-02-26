package Interfaces;

import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Ahmed on 2/26/2015.
 */
public interface Player {
    /**
     * Returns the name of the player
     *
     * @return the name of the player
     */
    public String getName();
    /**
     * Sets the name of the player
     * @param name The name of the player to be set
     */
    public void setName(String name);
    /**
     * Returns the ID of the player
     *
     * @return the ID of the player
     */
    public int getID();
    /**
     * Returns a set of quizzes attempted by the player
     *
     * @return a set of quizzes attempted by the player
     */
    public Set<Quiz> getQuizzes();
    /**
     * Returns the current scores with their corresponding quizzes
     *
     * @return a TreeMap of the current scores with their corresponding quizzes
     */
    public TreeMap<Quiz, Integer> getCurrentScores();

}
