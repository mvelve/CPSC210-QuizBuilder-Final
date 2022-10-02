package ui;

import model.Question;
import model.Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// CLASS LEVEL COMMENT: creates submission form for users to submit question prompts

public class QuestionForm {
    private JPanel questionFormPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private Quiz quiz;

    public QuestionForm(JPanel centrePanel, Quiz quiz) {
        createQuestionForm(centrePanel);
        this.quiz = quiz;
    }

    //MODIFIES: questionFormPanel, textField1, textField2, textField3 ... TextField7
    // label1, label2m label3 ... label7.
    //EFFECTS: creates question form with question prompt, options A,B,C,D labels
    public void createQuestionForm(JPanel centrePanel) {

        questionFormPanel = new JPanel();
        questionFormPanel.setBackground(new Color(232, 211, 185));
        questionFormPanel.setPreferredSize(new Dimension(500, 600));
        questionFormPanel.setLayout(new FlowLayout());
        createQuestionPromptFormat();
        createPointPromptFormat();
        createOptionAFormat();
        createOptionBFormat();
        createOptionCFormat();
        createOptionDFormat();
        createCorrectAnswerFormat();
        centrePanel.add(questionFormPanel); // need to add here
    }

    public void createQuestionPromptFormat() {
        textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(250, 40));
        questionFormPanel.add(textField1);
        label1 = createJLabelPrompt("Enter your question prompt?");
        questionFormPanel.add(label1);
    }

    public void createPointPromptFormat() {
        label2 = createJLabelPrompt("How many points is this question worth?");
        questionFormPanel.add(label2);
        textField2 = new JTextField();
        textField2.setPreferredSize(new Dimension(250, 40));
        questionFormPanel.add(textField2);
    }

    public void createOptionAFormat() {
        textField3 = new JTextField();
        textField3.setPreferredSize(new Dimension(250, 40));
        questionFormPanel.add(textField3);
        label3 = createJLabelPrompt("What is option A");
        questionFormPanel.add(label3);
    }

    public void createOptionBFormat() {
        textField4 = new JTextField();
        textField4.setPreferredSize(new Dimension(250, 40));
        questionFormPanel.add(textField4);
        label4 = createJLabelPrompt("What is option B");
        questionFormPanel.add(label4);
    }

    public void createOptionCFormat() {
        textField5 = new JTextField();
        textField5.setPreferredSize(new Dimension(250, 40));
        questionFormPanel.add(textField5);
        label5 = createJLabelPrompt("What is option C");
        questionFormPanel.add(label5); // edit here
    }

    public void createOptionDFormat() {
        textField6 = new JTextField();
        textField6.setPreferredSize(new Dimension(250, 40));
        questionFormPanel.add(textField6);
        label6 = createJLabelPrompt("What is option D");
        questionFormPanel.add(label6);
    }

    public void createCorrectAnswerFormat() {
        textField7 = new JTextField();
        textField7.setPreferredSize(new Dimension(250, 40));
        questionFormPanel.add(textField7);
        label7 = createJLabelPrompt("What is the correct answer?");
        questionFormPanel.add(label7);
        questionFormPanel.add(createJButtonSubmit());
    }

    @SuppressWarnings("methodlength")
    public JButton createJButtonSubmit() {
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Question newQuestion = new Question();
                String questionPrompt = textField1.getText();
                ArrayList<String> possibleResponses = new ArrayList<>();
                newQuestion.setQuestionPrompt(questionPrompt);
                int pointValue = Integer.parseInt(textField2.getText());
                newQuestion.setPointValue(pointValue);
                String optionA = textField3.getText();
                String optionB = textField4.getText();
                String optionC = textField5.getText();
                String optionD = textField6.getText();
                possibleResponses.add(optionA);
                possibleResponses.add(optionB);
                possibleResponses.add(optionC);
                possibleResponses.add(optionD);
                newQuestion.setListOfPossibleResponses(possibleResponses);
                String correctAnswer = textField7.getText();
                newQuestion.setCorrectAnswer(correctAnswer);
                quiz.insertQuestion(newQuestion); // inserts question
                questionFormPanel.setVisible(false);
                new QuizMenu(quiz);
            }
        });
        return submitButton;
    }

    public JLabel createJLabelPrompt(String prompt) {
        JLabel questionPrompt = new JLabel(prompt);
        questionPrompt.setFont(new Font("Calibri", Font.PLAIN, 15));
        questionPrompt.setForeground(Color.darkGray);
        return questionPrompt;
    }

}
