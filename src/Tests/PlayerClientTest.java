package Tests;

import Client.PlayerClient;
import Client.SetupClient;
import Interfaces.Score;
import Quiz.ScoreImpl;
import Server.QuizServerLauncher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;

public class PlayerClientTest {
    private static SetupClient maker;
    private static QuizServerLauncher serverLauncher;
    private static PlayerClient player;

    @BeforeClass
    public static void buildUp() throws RemoteException {
        serverLauncher = new QuizServerLauncher();
        serverLauncher.launch();
        maker = new SetupClient();
        player = new PlayerClient();
        String answers = "Conan the Barbarian\nWhat is best is life?\nCrush our enemies. See them driven before you and to hear the lamentation of the their women\n3\nA nice walk on the beach\nIce cream\nI don't know\nN";
        ByteArrayInputStream bais = new ByteArrayInputStream(answers.getBytes());
        System.setIn(bais);
        maker.serverConnection();
        maker.newQuiz();

    }

    @AfterClass
    public static void tearDown() {
        File testFile = new File("Quiz.txt");
        if(testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testPlayQuizAndHighScore() throws RemoteException {
        String answer = "1\nAhmed\n1\n3";
        ByteArrayInputStream bais = new ByteArrayInputStream(answer.getBytes());
        System.setIn(bais);
        player.serverConnection();
        player.playQuiz();
        String actual = player.getServer().getQuiz(1).getPlayers().get(0);
        String expected = "AHMED";
        assertEquals(expected, actual);

        answer = "2\n1";
        bais = new ByteArrayInputStream(answer.getBytes());
        System.setIn(bais);
        player.serverConnection();
        player.highScores();
        Score expected2= new ScoreImpl("AHMED", 0); //score doesn't really matter as equals only depends on the name
        Score actual2 = player.getServer().closeQuiz(1);
        assertEquals(expected2, actual2);
    }

}