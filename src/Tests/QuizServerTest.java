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

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class QuizServerTest {
    private static QuizService server;
    private static Quiz quiz1;
    private static Quiz quiz2;
    List<String> possibleAnswers;
    List<Question> questions;
    List<Quiz> quizzes;

    @BeforeClass
    public static void setUp() throws RemoteException {
        server = new QuizServer();
        quiz1 = new QuizImpl("Majora's Mask Quiz", server.getQuizIDs().generateID());
        quiz2 = new QuizImpl("Monty Python Quiz", server.getQuizIDs().generateID());
    }
    @Before
    public void buildUp() throws RemoteException {
        possibleAnswers = new ArrayList<String>();
        questions = new ArrayList<Question>();
        quizzes = new ArrayList<Quiz>();
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
    public void testAddQuestion() throws RemoteException {
        questions = new ArrayList<Question>();
        possibleAnswers = new ArrayList<String>();
        possibleAnswers.add("1999");
        possibleAnswers.add("2001");
        possibleAnswers.add("1998");
        Question q1 = new QuestionImpl("What year did Majora's Mask release?","2000", possibleAnswers);
        questions.add(q1);

        possibleAnswers.clear();
        possibleAnswers.add("Flute");
        possibleAnswers.add("Violin");
        possibleAnswers.add("Trumpet");
        Question q2 = new QuestionImpl("What instrument does Link play?" , "Ocarina", possibleAnswers);
        questions.add(q2);
        boolean actual = server.addQuestions(1, questions);
        assertTrue(actual);
    }

    @Test
    public void testCloseQuiz() throws RemoteException {
        server.closeQuiz(1);
        boolean actual = server.getQuiz(1).getClosed();
        assertTrue(actual);
    }

    @Test
    public void testOpenQuiz() throws RemoteException {
        server.openQuiz(1);
        boolean actual = server.getQuiz(1).getClosed();
        assertFalse(actual);
    }

    @Test
    public void testPlayQuiz() throws RemoteException {
        possibleAnswers.clear();
        possibleAnswers.add("You've met with a terrible fate, haven't you?");
        possibleAnswers.add("Majora's Mask");
        int actual = server.playQuiz("Skull Kid", 1, possibleAnswers);
        int expected = 2;
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFlush() throws IOException, ClassNotFoundException {
        server.flush();

        File newFile = new File("Quiz.txt");
        boolean actual = newFile.exists();
        assertTrue(actual);

        FileInputStream fis = new FileInputStream(newFile);
        ObjectInputStream input = new ObjectInputStream(fis);
        Object o = input.readObject();
        List<Quiz> actualObject = (ArrayList<Quiz>) o;
        List<Quiz> expected = server.currentQuizzes();
        assertEquals(expected, actualObject);
    }
}