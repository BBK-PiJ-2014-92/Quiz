package Tests;

import Interfaces.Question;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class QuestionTest {
    Question question1;

    @Before
    public void buildUp(){
        List<String> possibleAnswers = new ArrayList<String>();
        possibleAnswers.add("But I wanted a peanut");
        possibleAnswers.add("I don't know");
        possibleAnswers.add("Invest it in a timeshare");
        question1 = new QuestionImpl("What can you do with $20?", "Buy several peanuts", possibleAnswers);
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
}