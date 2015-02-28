package Quiz;

import Interfaces.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ahmed on 2/26/2015.
 */
public class QuestionImpl implements Question, Serializable {
    private String question;
    private String correctAnswer;
    private List<String> possibleAnswers = new ArrayList<String>();

    public QuestionImpl(String question, String correctAnswer, List<String> possibleAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        addPossibleAnswers(possibleAnswers);
    }
    /**
     * Returns the name of the question
     *
     * @return the name of the question
     */
    public String getQuestionName() {
        return question;
    }

    /**
     * Sets the name of the question
     *
     * @param questionName the name of the question to be set
     */
    public void setQuestionName(String questionName) {
        question = questionName;
    }

    /**
     * Returns the correct answer of the question
     *
     * @return the correct answer of the question
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Sets the answer for the question
     *
     * @param answer the answer for the question
     */
    public void setCorrectAnswer(String answer) {
        correctAnswer = answer;
    }

    /**
     * Returns a list of possible answers for the question (not including the actual answer)
     *
     * @return a list of possible answers for the question
     */
    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    /**
     * Replaces the list of possible answer with another list of possible answers (not including the actual answer)
     *
     * @param newPossibleAnswers the new list of possible answers to replace the existing list
     */
    public void setPossibleAnswers(List<String> newPossibleAnswers) {
        possibleAnswers = newPossibleAnswers;
    }

    /**
     * Adds a list of possible answers to the end of the existing list of possible answers
     *
     * @param newPossibleAnswers the new list of possible answers to be added to the end of the existing list
     */
    public void addPossibleAnswers(List<String> newPossibleAnswers) {
        for(String answer: newPossibleAnswers) {
            if (answer.equals(correctAnswer)) {
                newPossibleAnswers.remove(answer);
            }
        }
        possibleAnswers.addAll(newPossibleAnswers);

    }

    public List<String> createChoices(int size) {
        List<String> result = new ArrayList<String>();
        Collections.shuffle(possibleAnswers);
        if (possibleAnswers.isEmpty()) {
            throw new IllegalStateException("There are no possible answers to choose from");
        }else if (correctAnswer == null) {
            throw new IllegalStateException("Please set the correct answer");
        }
        for (int i = 0; i < size - 2; i++) { //Size is subtracted by 2 to prevent NullPointerExceptions from occurring and to allow the actual answer to be added in
            result.add(possibleAnswers.get(i));
        }
        result.add(correctAnswer);
        Collections.shuffle(result);
        return result;
    }
}
