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

    public List<String> createChoices() {
        List<String> result = new ArrayList<String>();
        Collections.shuffle(possibleAnswers);
        if (possibleAnswers.isEmpty()) {
            throw new IllegalStateException("There are no possible answers to choose from");
        }else if (correctAnswer == null) {
            throw new IllegalStateException("Please set the correct answer");
        }else if (possibleAnswers.size() <= 1) {
            throw new IllegalStateException("There are not enough possible answers to choose from");
        }
        for (int i = 0; i < 2; i++) {
            result.add(possibleAnswers.get(i));
        }
        result.add(correctAnswer);
        Collections.shuffle(result);
        return result;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionImpl)) return false;

        QuestionImpl question1 = (QuestionImpl) o;

        if (!correctAnswer.equals(question1.correctAnswer)) return false;
        if (!question.equals(question1.question)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = question.hashCode();
        result = 31 * result + correctAnswer.hashCode();
        return result;
    }
}
