package Tests;

import Server.QuizServer;
import Server.QuizService;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;

public class QuizServerTest {
    private static QuizService server;

    @BeforeClass
    public static void setUp() throws RemoteException {
        server = new QuizServer();
    }
    @Before
    public void buildUp() {
//        Quiz quiz1 = new QuizImpl("Majora's Mask Quiz");
//        Quiz quiz2 = new QuizImpl("General Knowledge");
//        List<String> possibleAnswers = new ArrayList<String>();
//        List<Question> questions = new ArrayList<Question>();
//        List<Quiz> quizzes = new ArrayList<Quiz>();

//        possibleAnswers.add("If you don't get that mask back soon, something terrible will happen!");
//        possibleAnswers.add("I...I shall consume. Consume... consume everything...");
//        possibleAnswers.add("A puppet that can no longer be used is mere garbage. This puppet's role has just ended....");
//        Question q1 = new QuestionImpl("What is the first thing the Happy Mask Salesman says to Link?", "You've met with a terrible fate, haven't you?", possibleAnswers);
//        questions.add(q1);
//        possibleAnswers.clear();
//        possibleAnswers.add("The Happy Mask Salesman");
//        possibleAnswers.add("Ganondorf");
//        possibleAnswers.add("Skull Kid");
//        Question q2 = new QuestionImpl("Who is going to destroy Termina?", "Majora's Mask" ,possibleAnswers);
//        questions.add(q2);
//        quiz1.addQuestions(questions);
//        quizzes.add(quiz1);

    }

    @After
    public void cleanUp() {
        File testFile = new File("Quiz.txt");
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testNewQuiz() throws RemoteException {
        int actual = server.newQuiz("Majora's Mask Quiz");
        int expected = 1;
        assertEquals(expected, actual);

        actual = server.currentQuizzes().size();
        assertEquals(expected, actual);
    }

    @Test
    public void testAddQuestion(){

    }

    @Test
    public void testCloseQuiz()  {

    }

    @Test
    public void testOpenQuiz() {

    }

    @Test
    public void testPlayQuiz()  {

    }


    @Test
    public void testIsCorrectAnswer()  {

    }

    @Test
    public void testFlush()  {

    }
}