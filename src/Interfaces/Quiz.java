package Interfaces;

import java.util.List;
import java.util.Set;

/**
 * Created by Ahmed on 2/25/2015.
 */
public interface Quiz {
    /**
     * Returns the ID of the quiz
     *
     * @return the ID of the quiz
     */
    public int getID();
    /**
     * Returns the name of the quiz
     *
     * @return the name of the quiz
     */
    public String getName();
    /**
     * Sets the name of the quiz to the specified string
     * @param name The name to be set as the name of the quiz
     */
    public void setName(String name);
    /**
     * Returns a list with all the questions in the quiz
     *
     * @return a list with all the questions
     */
    public List<Question> getQuestions();
    /**
     * Replaces the current available questions with a new specified set of questions
     * @param newQuestions A new set of questions that will replace the old set of questions
     */
    public void setQuestions(List<Question> newQuestions);
    /**
     * Adds new questions to the list of existing questions
     * @param newQuestions A new set of questions that will be added to the existing set of questions
     */
    public void addQuestions(List<Question> newQuestions);
    /**
     * Returns a set of players who have attempted this quiz
     *
     * @return a set of players who have attempted this quiz
     */
    public List<String> getPlayers();
    /**
     * Adds a new player with their corresponding score. It will sort the players with the highest scores. The highscores
     * will not contain duplicates
     * @param score The name of the player and the score that player achieved
     */
    public void addScore(Score score);
    /**
     * Returns a set of the current HighScore for this quiz
     *
     * @return A set of the current HighScore for this quiz
     */
    public Set<Score> getHighScores();

}
