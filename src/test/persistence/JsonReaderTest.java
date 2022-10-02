package persistence;

import model.Quiz;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Quiz quiz = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // should pass here since the IO exception is caught
        }
    }

    @Test
    public void testGeneralQuizFile() {
        JsonReader reader = new JsonReader("./data/GeneralQuizTest.json");
        try {
            Quiz quiz = reader.read();
            assertEquals(2, quiz.getMaxLength());
            assertEquals(2, quiz.getQuestions().size());
        }
        catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
