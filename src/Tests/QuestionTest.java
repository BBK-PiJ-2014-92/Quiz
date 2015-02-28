package Tests;

import Interfaces.Question;
import Quiz.QuestionImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class QuestionTest {
    Question question1;

    @Before
    public void buildUp(){
        List<String> possibleAnswers = new ArrayList<String>();
        possibleAnswers.add("But I wanted a peanut");
        possibleAnswers.add("I don't know");
        possibleAnswers.add("Invest it in a timeshare");
        question1 = new QuestionImpl("What can you do with $20?", "Can be exchanged for goods and services", possibleAnswers);
    }

    @Test
    public void testAddPossibleAnswers() {
        List<String> newPossibleAnswers = new ArrayList<String>();
        newPossibleAnswers.add("Stop asking me questions!");
        question1.addPossibleAnswers(newPossibleAnswers);
        String actual = question1.getPossibleAnswers().get(3);
        String expected = "Stop asking me questions!";
        assertEquals(actual, expected);
    }

    @Test
    public void testCreateChoices() {
        List<String> possibleAnswers = question1.createChoices();
        int actual = possibleAnswers.size();
        int expected = 4;
        assertEquals(expected, actual);
    }
}