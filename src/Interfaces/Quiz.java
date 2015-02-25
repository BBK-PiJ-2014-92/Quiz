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
}
