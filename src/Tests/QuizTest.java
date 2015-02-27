package Tests;

import Interfaces.Score;
import Interfaces.Question;
import Interfaces.Quiz;
import Quiz.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuizTest {
    private Quiz quiz;
    private List<String> answers;
    private Score p1;

    @Before
    public void buildUp() {
        List<String> answers1 = new ArrayList<String>();
        answers1.add("ORA ORA ORA!");
        answers1.add("RERO RERO RERO!");
        answers1.add("I don't know");
        Question q1 = new QuestionImpl("Can a monkey fight a man?", "MUDA MUDA MUDA!", answers1 );
        List<Question> questions = new ArrayList<Question>();
        questions.add(q1);
        quiz = new QuizImpl("The Art of Muda!");
        quiz.addQuestions(questions);
        answers = new ArrayList<String>();
    }

    @Test
    public void testAddQuestions() {
        List<Question> newQuestions = new ArrayList<Question>();
        answers.add("I don't know that?");
        answers.add("Blue...No, yel...");
        answers.add("What's a swallow?");
        newQuestions.add(new QuestionImpl("What... is the air-speed velocity of an unladen swallow?", "What do you mean? An African or European swallow?", answers));
        quiz.addQuestions(newQuestions);
        int actual = quiz.getQuestions().size();
        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    public void testAddPlayer() {
        p1 = new ScoreImpl("Dio Brando", 1);
        quiz.addScore(p1);
        int actual = quiz.getPlayers().size();
        int expected = 1;
        assertEquals(expected, actual);
    }

}