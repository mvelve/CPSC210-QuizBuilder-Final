package persistence;

import model.Quiz;

import java.util.ArrayList;

import model.Question;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// CLASS LEVEL COMMENT: This class creates a Json reader capable of reading quizzes represented in Json
// ATTRIBUTION: the following class has been modeled after JsonSerializationDemo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read Quiz Json from source file
    public JsonReader(String source) {
        this.source = source;
    }


    // EFFECTS: reads Quiz from file and returns it;
    // throws IOException if an error occurs reading Quiz data from the file
    public Quiz read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseQuiz(jsonObject);
    }

    //EFFECTS: parses Quiz from Json Object to Quiz and returns it
    public Quiz parseQuiz(JSONObject jsonObject) {
        int setMaxLength = jsonObject.getInt("maxLength");
        Quiz quiz = new Quiz(setMaxLength);
        ArrayList<Question> questions = new ArrayList<>();
        JSONArray questionJsonArray = jsonObject.getJSONArray("questions");

        for (Object o : questionJsonArray) {
            ArrayList<String> listResponses = new ArrayList<>();
            JSONObject json = (JSONObject) o;
            String questionPrompt = json.getString("questionPrompt");
            String correctAnswer = json.getString("correctAnswer");
            int pointValue = json.getInt("pointValue");
            JSONArray responses = json.getJSONArray("possibleResponses");
            for (int i = 0; i < responses.length(); i++) {
                listResponses.add(responses.getString(i));
            }
            Question question = new Question(questionPrompt, correctAnswer, pointValue);
            question.setListOfPossibleResponses(listResponses);

            questions.add(question);
        }
       // addQuizzes(quiz, jsonObject);
        quiz.setQuestions(questions); // change here
        return quiz;
    }

//    public ArrayList<Question> parseQuestion(JSONObject jsonObject) {
//        ArrayList<Question> questions = new ArrayList<>();
//        JSONArray questionJsonArray = jsonObject.getJSONArray("possibleResponses");
//
//        for (Object o : questionJsonArray) {
//            JSONObject json = (JSONObject) o;
//            String questionPrompt = jsonObject.getString("questionPrompt");
//            String correctAnswer = jsonObject.getString("correctAnswer");
//            int pointValue = jsonObject.getInt("pointValue");
//            Question question = new Question(questionPrompt, correctAnswer, pointValue);
//            questions.add(question);
//        }
//        return questions;
//    }

//    private void addQuizzes(Quiz quiz, JSONObject jsonObject) {
//        JSONArray jsonArray = jsonObject.getJSONArray("questions");
//        for (Object json : jsonArray) {
//            JSONObject nextQuestion = (JSONObject) json;
//            addQuestion(quiz, nextQuestion);
//        }
//    }
//
//    public void addQuestion(Quiz quiz, JSONObject jsonObject) {
//        int setMaxLength = jsonObject.getInt("maxLength");
//    }

// EFFECTS: reads source file as string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }
}
