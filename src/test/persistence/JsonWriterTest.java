package persistence;
import model.Question;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import model.Quiz;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Quiz quiz = new Quiz(10);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:dummyFile.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // test should pass here
        }
    }

    @Test
    void testWriterGeneralQuiz() {
        try {
            ArrayList<String> question1PossibleResponses = new ArrayList<>();
            question1PossibleResponses.add("Response A");
            question1PossibleResponses.add("Response B");
            question1PossibleResponses.add("Response C");
            ArrayList<String> question2PossibleResponses = new ArrayList<>();
            question2PossibleResponses.add("Response D");
            question2PossibleResponses.add("Response E");
            question2PossibleResponses.add("Response F");
            Quiz quiz = new Quiz(10);
            Question question1 = new Question("This is a prompt to your quiz", "A",
                    10);
            quiz.insertQuestion(question1);
            question1.setListOfPossibleResponses(question1PossibleResponses);
            Question question2 = new Question("This is another prompt to your quiz", "B",
                    20);
            quiz.insertQuestion(question2);
            question2.setListOfPossibleResponses(question2PossibleResponses);

            JsonWriter writer = new JsonWriter("./data/testGeneralQuiz.json");
            writer.open();
            writer.write(quiz);
            writer.close();

            JsonReader reader = new JsonReader("./data/testGeneralQuiz.json");
            quiz = reader.read();
            assertEquals(2, quiz.getQuestions().size());
            assertEquals(3, quiz.getQuestions().get(0).getPossibleResponses().size());
            assertEquals(3, quiz.getQuestions().get(1).getPossibleResponses().size());

            assertEquals( "Response A", quiz.getQuestions().get(0).getPossibleResponses().get(0));
            assertEquals( "Response B", quiz.getQuestions().get(0).getPossibleResponses().get(1));
            assertEquals( "Response C", quiz.getQuestions().get(0).getPossibleResponses().get(2));

            assertEquals("Response D", quiz.getQuestions().get(1).getPossibleResponses().get(0)); // check here
            assertEquals("Response E", quiz.getQuestions().get(1).getPossibleResponses().get(1));
            assertEquals("Response F", quiz.getQuestions().get(1).getPossibleResponses().get(2));

            assertEquals(10, quiz.getQuestions().get(0).getPointValue());
            assertEquals(20, quiz.getQuestions().get(1).getPointValue());
            assertEquals("A",  quiz.getQuestions().get(0).getCorrectAnswer());
            assertEquals("B",  quiz.getQuestions().get(1).getCorrectAnswer());

            assertEquals(10, quiz.getMaxLength());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }




}

