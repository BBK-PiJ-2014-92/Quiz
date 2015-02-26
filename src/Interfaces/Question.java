package Interfaces;

import java.util.List;

/**
 * Created by Ahmed on 2/26/2015.
 */
public interface Question {
    /**
     * Returns the name of the question
     *
     * @return the name of the question
     */
    public String getQuestionName();
    /**
     * Sets the name of the question
     * @param questionName the name of the question to be set
     */
    public void setQuestionName(String questionName);
    /**
     * Returns the correct answer of the question
     *
     * @return the correct answer of the question
     */
    public String getCorrectAnswer();
    /**
     * Sets the answer for the question
     * @param answer the answer for the question
     */
    public void setCorrectAnswer(String answer);
    /**
     * Returns a list of possible answers for the question (not including the actual answer)
     *
     * @return a list of possible answers for the question
     */
    public List<String> getPossibleAnswers();
    /**
     * Replaces the list of possible answer with another list of possible answers (not including the actual answer)
     * @param newPossibleAnswers the new list of possible answers to replace the existing list
     */
    public void setPossibleAnswers(List<String> newPossibleAnswers);

}
