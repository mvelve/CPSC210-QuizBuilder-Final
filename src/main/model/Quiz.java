package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
//CLASS LEVEL COMMENT:
// creates an instance of a Quiz with list of questions and capability of inserting questions

public class Quiz {
    private List<Question> questions;
    private int maxLength;

    //REQUIRES: maxLength > 0
    //EFFECTS: constructs new quiz object and if max length is 0 sets quiz length to 1 by default
    public Quiz(int maxLength) {
        this.questions = new ArrayList<Question>();
        if (maxLength != 0) {
            this.maxLength = maxLength;
        } else {
            this.maxLength = 1;
        }
    }

    //MODIFIES:this
    //EFFECTS: inserts question into list of questions and returns true if doing so is less than max questions
    public void insertQuestion(Question question) {
        questions.add(question);
    }

    //EFFECTS: returns list of questions
    public List<Question> getQuestions() {
        return questions;
    }

    //MODIFIES: this
    //EFFECTS: sets list of questions
    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    //MODIFIES: this
    //EFFECTS: set maxLength if value of 0 given sets length to 1 by default
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    //EFFECTS: returns int maxLength of quiz
    public int getMaxLength() {
        return this.maxLength;
    }

    //REQUIRES: this(Quiz) to be valid
    //MODIFIES: this, JSOnObject
    //EFFECTS: converts quiz into JSONObject and returns it
    public JSONObject quizToJson() {
        JSONObject quizJson = new JSONObject();
        quizJson.put("maxLength", maxLength);
        quizJson.put("questions", questions); // doesn't need to be JSON ARRAY
        return quizJson;
    }

//FOr later functionality if needed
// MAYBE NOT NECCESSARY and REMOVE
//    public JSONArray questionsToJson() {
//        JSONArray questionJsonArray = new JSONArray();
//        for (Question question: questions) {
//            questionJsonArray.put(question.questionToJson());
//        }
//        return questionJsonArray;
}

// removed functionality to be implemented for future use
//REQUIRES: student responses <= maxLength
//MODIFIES:
//EFFECTS: sets and returns a students grade for a certain quiz
//    public int gradeQuiz() {
//        return 10;
//    } // not used currently
//
//}
