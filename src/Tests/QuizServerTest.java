package Tests;

import Interfaces.Question;
import Interfaces.Quiz;
import Quiz.QuestionImpl;
import Quiz.QuizImpl;
import Server.QuizServer;
import Server.QuizService;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuizServerTest {
    private static QuizService server;
    private static Quiz quiz1;
    private static Quiz quiz2;

    @BeforeClass
    public static void setUp() throws RemoteException {
        server = new QuizServer();
        quiz1 = new QuizImpl("Majora's Mask Quiz");
        quiz2 = new QuizImpl("Monty Python Quiz");
    }
    @Before
    public void buildUp() throws RemoteException {
        List<String> possibleAnswers = new ArrayList<String>();
        List<Question> questions = new ArrayList<Question>();
        List<Quiz> quizzes = new ArrayList<Quiz>();

        possibleAnswers.add("If you don't get that mask back soon, something terrible will happen!");
        possibleAnswers.add("I...I shall consume. Consume... consume everything...");
        possibleAnswers.add("A puppet that can no longer be used is mere garbage. This puppet's role has just ended....");
        Question q1 = new QuestionImpl("What is the first thing the Happy Mask Salesman says to Link?", "You've met with a terrible fate, haven't you?", possibleAnswers);
        questions.add(q1);
        possibleAnswers.clear();
        possibleAnswers.add("The Happy Mask Salesman");
        possibleAnswers.add("Ganondorf");
        possibleAnswers.add("Skull Kid");
        Question q2 = new QuestionImpl("Who is going to destroy Termina?", "Majora's Mask" ,possibleAnswers);
        questions.add(q2);
        quiz1.addQuestions(questions);
        quizzes.add(quiz1);

        possibleAnswers.clear();
        questions.clear();
        possibleAnswers.add("Gerbil, Manure");
        possibleAnswers.add("Rat, Loganberries");
        possibleAnswers.add("Capybara, Horse's rear end");
        q1 = new QuestionImpl("Fill in the blanks: Your mother was a ____ and your father smelt of ____", "Hamster, Elderberries", possibleAnswers);
        questions.add(q1);
        possibleAnswers.clear();
        possibleAnswers.add("Blue...No, yel...");
        possibleAnswers.add("I don't know that");
        possibleAnswers.add("Can you repeat the question?");
        q2 = new QuestionImpl("What... is the air-speed velocity of an unladen swallow?", "What do you mean? An African or European swallow?", possibleAnswers);
        questions.add(q2);
        quiz2.addQuestions(questions);
        quizzes.add(quiz2);

        server.setQuizzes(quizzes);
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
        int actual = server.newQuiz("Test Quiz");
        int expected = 3;
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