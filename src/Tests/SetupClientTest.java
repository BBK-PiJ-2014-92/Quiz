package Tests;

import Client.SetupClient;
import Interfaces.Question;
import Quiz.QuestionImpl;
import Server.QuizServerLauncher;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SetupClientTest {
    private static SetupClient maker;
    private static QuizServerLauncher serverLauncher;

    @BeforeClass
    public static void buildUp() throws RemoteException {
        serverLauncher = new QuizServerLauncher();
        serverLauncher.launch();
        maker = new SetupClient();

    }

    @After
    public void tearDown() throws RemoteException {
        File testFile = new File("Quiz.txt");
        if(testFile.exists()) {
            testFile.delete();
        }
        maker.getServer().setQuizzes(null);
    }

    @Test
    public void testNewQuiz() throws RemoteException {
        String answers = "Conan the Barbarian\nWhat is best is life?\nCrush our enemies. See them driven before you and to hear the lamentation of the their women\n3\nA nice walk on the beach\nA nice walk on the beach\nIce cream\nI don't know\nN";
        ByteArrayInputStream bais = new ByteArrayInputStream(answers.getBytes());
        System.setIn(bais);
        maker.serverConnection();
        int actual = maker.newQuiz();
        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    public void testAddQuestions() throws RemoteException {
        String answers = "1\nWho played Conan the Barbarian (1982)\nArnold Schwarzenegger\n3\nSylvester Stallone\nJason Statham\nJason Momoa\nN";
        ByteArrayInputStream bais = new ByteArrayInputStream(answers.getBytes());
        System.setIn(bais);
        maker.serverConnection();
        maker.addQuestions();
        List<String> possibleAnswers = new ArrayList<String>();
        Question actual = maker.getServer().getQuiz(1).getQuestions().get(1);
        Question expected = new QuestionImpl("Who played Conan the Barbarian (1982", "Arnold Schwarzenegger",possibleAnswers); //Overrode equals and hashCode function such that it only checks that the question and the correct answer are equal
        assertEquals(expected, actual);
    }

    @Test
    public void testAddQuestions1() {

    }

    @Test
    public void testCloseQuiz() {

    }

    @Test
    public void testOpenQuiz() {

    }
}