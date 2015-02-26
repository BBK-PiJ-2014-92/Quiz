package Quiz;

import Interfaces.Question;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ahmed on 2/26/2015.
 */
public class QuestionImpl implements Question, Serializable {
    /**
     * Returns the name of the question
     *
     * @return the name of the question
     */
    public String getQuestionName() {
        return null;
    }

    /**
     * Sets the name of the question
     *
     * @param questionName the name of the question to be set
     */
    public void setQuestionName(String questionName) {

    }

    /**
     * Returns the correct answer of the question
     *
     * @return the correct answer of the question
     */
    public String getCorrectAnswer() {
        return null;
    }

    /**
     * Sets the answer for the question
     *
     * @param answer the answer for the question
     */
    public void setCorrectAnswer(String answer) {

    }

    /**
     * Returns a list of possible answers for the question (not including the actual answer)
     *
     * @return a list of possible answers for the question
     */
    public List<String> getPossibleAnswers() {
        return null;
    }

    /**
     * Replaces the list of possible answer with another list of possible answers (not including the actual answer)
     *
     * @param newPossibleAnswers the new list of possible answers to replace the existing list
     */
    public void setPossibleAnswers(List<String> newPossibleAnswers) {

    }

    /**
     * Adds a list of possible answers to the end of the existing list of possible answers
     *
     * @param newPossibleAnswers the new list of possible answers to be added to the end of the existing list
     */
    public void addPossibleAnswers(List<String> newPossibleAnswers) {

    }
}
