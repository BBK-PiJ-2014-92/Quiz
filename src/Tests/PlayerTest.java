package Tests;

import Interfaces.Player;
import Interfaces.Question;
import Interfaces.Quiz;
import Quiz.PlayerImpl;
import Quiz.QuestionImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PlayerTest {
    private Player p1;
    private Quiz quiz = new QuizImpl("The art of Muda", null, null);

    @Before
    public void buildUp(){
        p1 = new PlayerImpl("Dio Brando");
        List<String> possibleAnswers = new ArrayList<String>();
        possibleAnswers.add("ORA ORA ORA ORA!");
        possibleAnswers.add("RERO RERO RERO RERO!");
        Question question = new QuestionImpl("Can a monkey fight a man?", "MUDA MUDA MUDA MUDA!", possibleAnswers);
        quiz.setQuestions(question);
    }

    @Test
    public void testGetQuizzes() {

    }

    @Test
    public void testAddScore() {

    }
}