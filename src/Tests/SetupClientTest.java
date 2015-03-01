package Tests;

import Client.SetupClient;
import Server.QuizServerLauncher;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;

public class SetupClientTest {
    private static SetupClient maker;

    @BeforeClass
    public void buildUp() throws RemoteException {
        QuizServerLauncher.main();
        maker = new SetupClient();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testServerConnection() {

    }

    @Test
    public void testLaunch() {

    }

    @Test
    public void testNewQuiz() {

    }

    @Test
    public void testAddQuestions()  {

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