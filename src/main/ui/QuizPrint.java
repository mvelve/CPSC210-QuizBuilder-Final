package ui;

import model.Question;
import model.Quiz;

import javax.swing.*;
import java.util.ArrayList;

// CLASS LEVEL COMMENT: creates printed layout of the quiz

public class QuizPrint {
    private Quiz quiz;
    private JLabel displayType;
    private JPanel centrePanel;


    //MODIFIES: quiz, centrePanel
    //EFFECTS: creates a layout of a printed quiz
    public QuizPrint(Quiz quiz, JPanel centrePanel) {
        this.centrePanel = centrePanel;
        this.quiz = quiz;
        for (Question question: quiz.getQuestions()) {
            createDisplayLabel("Question prompt is: " + question.getQuestionPrompt());
            createDisplayLabel("Correct Answer: " + question.getCorrectAnswer());
            createDisplayLabel("Question point value is: " + String.valueOf(question.getPointValue()));

            int x = 65;
            String myString = Character.toString((char) x);
            System.out.println(myString);

            for (int i = 0; i < 4; i++) {
                String s = question.getPossibleResponses().get(i);
                createDisplayLabel("Option " + Character.toString((char) (i + 65)) + " " + s + " \n");
            }
        }
    }

    //MODIFIES: displayType
    //EFFECTS:
    public void createDisplayLabel(String answer) {
        displayType = new JLabel();
        displayType.setText(answer);
        centrePanel.add(displayType);
    }


}
