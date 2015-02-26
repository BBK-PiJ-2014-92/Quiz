package Interfaces;

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
    public Set<Player> getPlayers();
    /**
     * Adds a new player with their corresponding score. It will sort the current TreeMap with the highest scores,
     * and will replace the current score for the player if said player achieves a higher score
     * @param player The player who just attempted this quiz
     * @param score The score that the specified player just received for this quiz
     */
    public void addPlayer(Player player, int score);
    /**
     * Returns a Map for the current HighScore for this quiz
     *
     * @return A Map of the current HighScore for this quiz
     */
    public TreeMap<Player, Integer> getHighScores();

}
