package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//CLASS LEVEL COMMENT: Creates a question instance with correct answer, question prompt, and point value fields
// Contains methods to convert Question type into Json attributed from JsonSerializationDemo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class Question {
    private ArrayList<String> listOfPossibleResponses; // removing final here to see if this works
    private String correctAnswer;
    private String questionPrompt;
    private int pointValue;

    //REQUIRES: choiceAnswer must be present in possibleAnswers
    //EFFECTS: constructs a new Question with  prompt, multiple choice options, point value, and answer
    public Question() {
        this.listOfPossibleResponses = new ArrayList<String>(); // allows you to add a, b, c, d, and e etc.
    }

    //REQUIRES: question prompt string length >= 1, correct answer length = 1, and point value != 0
    //EFFECTS: creates an overloaded constructor for question with fields for prompt, correct answer, and point value
    public Question(String questionPrompt, String correctAnswer, int pointValue) {
        this.listOfPossibleResponses = new ArrayList<String>();
        this.questionPrompt = questionPrompt;
        this.correctAnswer = correctAnswer;
        this.pointValue = pointValue;
    }


    //MODIFIES: this
    //EFFECTS: sets listOfPossibleQuestions
    public void setListOfPossibleResponses(ArrayList<String> possibleResponses) {
        this.listOfPossibleResponses = possibleResponses;
    }

    //REQUIRES: input to be non-null string
    //MODIFIES: this
    //EFFECTS: adds a multiple choice response to the list of possible responses if it is greater than 0 characters
    // but less then 80 characters including spaces. Invalid responses are not added to list to be appended.
    public void appendResponse(String option) {
        if (option.length() <= 80 && option.length() > 0) {
            this.listOfPossibleResponses.add(option);
        }
    }

    //REQUIRES: choice answer must be within possible answers
    //MODIFIES: this
    //EFFECTS: sets correct multiple choice letter answer only if length is 1 otherwise does not set
    public void setCorrectAnswer(String correctAnswer) {
        if (correctAnswer.length() == 1) {
            this.correctAnswer = correctAnswer;
        }
    }

    //MODIFIES: this
    //EFFECTS: sets a questions prompt to the given string
    public void setQuestionPrompt(String questionPrompt) {
        this.questionPrompt = questionPrompt;
    }

    //MODIFIES: this
    //EFFECTS: set a questions point value to the given integer if negative given defaults to 0 points
    public void setPointValue(int pointValue) {
        if (pointValue >= 0) {
            this.pointValue = pointValue;
        }
    }

    //EFFECTS: returns the possible responses for each multiple choice answer
    public ArrayList<String> getPossibleResponses() {
        return listOfPossibleResponses;
    }

    //EFFECTS:  returns the set correct answer
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    //EFFECTS: returns the question prompt that has been set
    public String getQuestionPrompt() {
        return questionPrompt;
    }

    //EFFECTS: returns the point value of each question
    public int getPointValue() {
        return pointValue;
    }

    //REQUIRES: this (Question) has correct parameters
    //MODIFIES: this, JSONObject
    //EFFECTS: converts a Question type into JSON object and returns it
    public JSONObject questionToJson() {
        JSONObject questionJson = new JSONObject();
        questionJson.put("questionPrompt", questionPrompt);
        questionJson.put("pointValue", pointValue);
        questionJson.put("listOfPossibleResponses", listResponsesToJson());
        questionJson.put("correctAnswer", correctAnswer);
        return questionJson;
    }

    //

    // Modeled after JsonSerializationDemo attribution above
    //REQUIRES: response != null
    //MODIFIES: this, JSONObject
    //EFFECTS: converts a response within a question to a JsonObject and returns it
    public JSONObject responseToJson(String response) {
        JSONObject responseJson = new JSONObject();
        responseJson.put("response", response);
        return responseJson;
    }

    //Modeled after JsonSerializationDemo attribution above
    //REQUIRES: listOfPossibleResponses.size() != 0
    //MODIFIES: this, JSONArray
    //EFFECTS: converts a list of responses into a JSONArray and returns it
    public JSONArray listResponsesToJson() {
        JSONArray responseJsonArray = new JSONArray();
        for (String response: listOfPossibleResponses) {
            responseJsonArray.put(responseToJson(response));
        }
        return responseJsonArray;
    }
    // you may need to alter this well to make it work!

}
