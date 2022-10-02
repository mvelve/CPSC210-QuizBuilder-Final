package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//CLASS LEVEL COMMENT: This class tests the functionality of methods found in the model package using Junit5

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class QuestionQuizTest {
    private Quiz testQuiz;
    private Question testQuestion1;
    private Question testQuestion2;
    private Question testQuestion3;
    private Question testQuestion4;


    @BeforeEach
    public void setUp() {
        testQuiz = new Quiz(10);
        testQuestion1 = new Question();
        testQuestion2 = new Question();
        testQuestion3 = new Question();
        testQuestion4 = new Question();
    }

    @Test
    public void testInsertSingleQuestion() {
        testQuiz.insertQuestion(testQuestion1);
        assertEquals(1, testQuiz.getQuestions().size());
    }

    @Test
    public void testInsertMultipleQuestions() {
        testQuiz.insertQuestion(testQuestion1);
        testQuiz.insertQuestion(testQuestion2);
        assertEquals(2, testQuiz.getQuestions().size());
    }

    @Test
    public void testOverloadedQuestionConstructor() {
        Question overloadedQuestion = new Question("This is a prompt", "A",4);
        assertEquals(overloadedQuestion.getQuestionPrompt(), "This is a prompt");
        assertEquals(overloadedQuestion.getPointValue(), 4);
        assertEquals(overloadedQuestion.getCorrectAnswer(), "A");
    }

    @Test
    public void testQuizConstructor() {
        Quiz testQuiz = new Quiz(10);
        assertEquals(10, testQuiz.getMaxLength());
        testQuiz.setMaxLength(12);
        assertEquals(12, testQuiz.getMaxLength());
        testQuiz.insertQuestion(testQuestion1);
        assertEquals(testQuestion1, testQuiz.getQuestions().get(0));
    }

    @Test
    public void testAppendSingleResponse() {
        assertEquals(0, testQuestion1.getPossibleResponses().size());
        testQuestion1.appendResponse("This is the option you should choose");
        assertEquals(1, testQuestion1.getPossibleResponses().size());
        assertEquals("This is the option you should choose", testQuestion1.getPossibleResponses().get(0));
    }

    @Test
    public void testAppendMultipleResponse() {
        assertEquals(0, testQuestion1.getPossibleResponses().size());
        testQuestion1.appendResponse("This is the answer choose me");
        testQuestion1.appendResponse("Insert description that is tricky");
        assertEquals("This is the answer choose me",testQuestion1.getPossibleResponses().get(0));
        assertEquals("Insert description that is tricky",testQuestion1.getPossibleResponses().get(1));
        assertEquals(2, testQuestion1.getPossibleResponses().size());
    }

    @Test
    public void testAppendResponseGreaterThanThreshold() {
        testQuestion1.appendResponse("This is going to be long then 80 charcaters. " +
                "this string is huge that will be unable to be passed through given the conditional is correct");
        assertEquals(0, testQuestion1.getPossibleResponses().size());
    }

    @Test
    public void testAppendAtThreshold() {
        testQuestion1.appendResponse("");
        assertEquals(0, testQuestion1.getPossibleResponses().size());
        testQuestion1.appendResponse("I want to make this line exactly 80 characters so " +
                "I can test this within my quiz");
        assertEquals(1,testQuestion1.getPossibleResponses().size());
    }

    @Test
    public void testSetCorrectAnswer() {
        testQuestion1.setCorrectAnswer("A");
        assertEquals("A", testQuestion1.getCorrectAnswer());
    }

    @Test
    public void testSetCorrectAnswerInvalidString() {
        testQuestion1.setCorrectAnswer("ABC");
        assertEquals(null, testQuestion1.getCorrectAnswer());
    }

    @Test
    public void testSetCorrectAnswerMultipleQuestions() {
        testQuestion1.setCorrectAnswer("A");
        testQuestion2.setCorrectAnswer("B");
        testQuestion3.setCorrectAnswer("C");
        assertEquals("A", testQuestion1.getCorrectAnswer());
        assertEquals("B", testQuestion2.getCorrectAnswer());
        assertEquals("C", testQuestion3.getCorrectAnswer());
    }

    @Test
    public void testSetStudentPrompt() {
        testQuestion1.setQuestionPrompt("This a prompt");
    }

    @Test
    public void getPossibleResponsesMultiple() {
        testQuestion1.appendResponse("This is an option");
        testQuestion1.appendResponse("This is yet another response");
        assertEquals("This is an option", testQuestion1.getPossibleResponses().get(0));
        assertEquals("This is yet another response", testQuestion1.getPossibleResponses().get(1));
        assertEquals(2, testQuestion1.getPossibleResponses().size());

    }

    @Test
    public void testConstructorOneParameter() {
        Quiz testQuizConstructor = new Quiz(10);
        testQuizConstructor.insertQuestion(testQuestion2);
        testQuizConstructor.insertQuestion(testQuestion1);
        assertEquals(2, testQuizConstructor.getQuestions().size());
    }

    @Test
    public void quizConstructorInvalidMaxLength() {
        Quiz testQuizLength = new Quiz(0);
        assertEquals(1, testQuizLength.getMaxLength());
    }

    @Test
    public void testSetPointValue() {
        testQuestion1.setPointValue(4);
        assertEquals(4, testQuestion1.getPointValue());
    }

    @Test
    public void testSetPointValueInvalid() {
        testQuestion1.setPointValue(-1);
        assertEquals(0, testQuestion1.getPointValue());
    }

    @Test
    public void testQuizToJson() {
        JSONObject questionJSon = new JSONObject();
        Question testSpecificQuestion = new Question("This is a prompt response", "A",
                10);
        Question testSpecificQuestion2 = new Question("This is yet another prompt response",
                "B",
                20);
        testQuiz.insertQuestion(testSpecificQuestion);
        testQuiz.insertQuestion(testSpecificQuestion2);
        JSONObject quizJSon = testQuiz.quizToJson();
        assertEquals(10,quizJSon.getInt("maxLength"));
        assertEquals(2, quizJSon.getJSONArray("questions").length());
    }


    @Test
    public void testListResponsesToJson() {
        ArrayList<String> possibleResponses = new ArrayList<>();
        possibleResponses.add("Response A");
        possibleResponses.add("Response B");
        possibleResponses.add("Response C");
        Question testSpecificQuestion = new Question("This is a prompt","A",30);
        testSpecificQuestion.setListOfPossibleResponses(possibleResponses);
        JSONArray jsonListResponseArray = testSpecificQuestion.listResponsesToJson();
        assertEquals(3, jsonListResponseArray.length());
    }

    @Test
    public void testQuestionTOJson() {
        ArrayList<String> possibleResponses = new ArrayList<>();
        possibleResponses.add("Response A");
        possibleResponses.add("Response B");
        possibleResponses.add("Response C");
        Question testSpecificQuestion = new Question("This is a prompt","B",50);
        testSpecificQuestion.setListOfPossibleResponses(possibleResponses);
        JSONObject questionJson = testSpecificQuestion.questionToJson();
        assertEquals("This is a prompt", questionJson.getString("questionPrompt"));
        assertEquals(50, questionJson.getInt("pointValue"));
        assertEquals(3, questionJson.getJSONArray("listOfPossibleResponses").length());
        assertEquals("B", questionJson.getString("correctAnswer")) ;
    }

    @Test
    public void testResponseToJson() {
        Question testSpecificQuestion = new Question("This is a prompt","A",30);
        JSONObject responseJson = testSpecificQuestion.responseToJson("This is a test response");
        assertEquals("This is a test response", responseJson.getString("response"));
    }

    @Test
    public void testSetQuestions() {
        ArrayList<String> possibleResponsesList =  new ArrayList<>();
        possibleResponsesList.add("Response A");
        possibleResponsesList.add("Response B");
        possibleResponsesList.add("Response C");
        Question testSpecificQuestionSet = new Question("This is a prompt","B",75);
        testSpecificQuestionSet.setListOfPossibleResponses(possibleResponsesList);
        assertEquals(3, testSpecificQuestionSet.getPossibleResponses().size());
    }

}