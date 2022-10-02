package persistence;

import org.json.JSONObject;
import model.Quiz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

//CLASS LEVEL COMMENT: This class creates a Json writer capable of writing quizzes in the Json format
// ATTRIBUTION: the following class has been modeled after JsonSerializationDemo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    //EFFECTS: constructs a writer to write file to destination
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //MODIFIES: this
    //EFFECTS: opens file and throws FileNotFoundException if destination unreachable to write
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Quiz
    public void write(Quiz quiz) {
        JSONObject json = quiz.quizToJson();
        saveToFile(json.toString(TAB));
    }

    //MODIFIES: this
    //EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: writes String representation to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
