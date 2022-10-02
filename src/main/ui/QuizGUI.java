package ui;

import jdk.nashorn.internal.ir.debug.JSONWriter;
import model.Question;
import model.Quiz;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

//CLASS LEVEL COMMENT: creates layout for initial introductory menu after user has specified all prompts
// supports action.

public class QuizGUI extends JFrame implements ActionListener, KeyListener {
    private final JPanel topPanel;
    private final JLabel introLabel;
    protected JPanel centrePanel;
    protected JLabel introStatement;
    protected JLabel firstPoint;
    protected JLabel secondPoint;
    protected JLabel thirdPoint;
    private Quiz quiz;
    private JsonReader reader;
    private JsonWriter writer;
    private JLabel imageLabel;

    public QuizGUI() {
        quiz = new Quiz(1000000);
        reader = new JsonReader("./data/Directory.json");
        writer = new JsonWriter("./data/Directory.json");

        createFirstPoint();
        createSecondPoint();
        createThirdPoint();
        createIntroLabel();
        createCentrePanel();

        topPanel = new JPanel();
        topPanel.setBackground(Color.blue);
        topPanel.setPreferredSize(new Dimension(500, 50));
        topPanel.setBounds(0, 0, 500, 50);

        introLabel = new JLabel("QuizBuilder 1.0");
        introLabel.setFont(new Font("Snell Roundhouse", Font.BOLD, 20));
        introLabel.setForeground(Color.white);
        topPanel.add(introLabel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Quiz_Builder V1.0");
        this.setSize(800, 500);
        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(centrePanel, BorderLayout.CENTER);
        this.addKeyListener(this);
        this.setVisible(true);
    }

    public void createCentrePanel() {

        imageLabel = new JLabel();
        ImageIcon quizIntroIcon = new ImageIcon("./images/quizIntro.jpg");
        Image quizIntroImage = quizIntroIcon.getImage();
        Image resizedQuizIntro = quizIntroImage.getScaledInstance(250, 200,  java.awt.Image.SCALE_SMOOTH);
        quizIntroIcon = new ImageIcon(resizedQuizIntro);

        imageLabel.setIcon(quizIntroIcon);
        centrePanel = new JPanel();
        centrePanel.setPreferredSize(new Dimension(700, 500));
        centrePanel.setBackground(Color.white);
        centrePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // check this here!!!!
        // centrePanel.add(beginButton);
        centrePanel.add(introStatement);
        centrePanel.add(firstPoint);
        centrePanel.add(secondPoint);
        centrePanel.add(thirdPoint);
        centrePanel.add(imageLabel);
    }
    // this is the main panel you will be working with

    public void createIntroLabel() {
        introStatement = new JLabel();
        introStatement.setText("Welcome to QuizBuilder please adhere to the following instructions:");
        introStatement.setFont(new Font("Times", Font.ROMAN_BASELINE, 15));
        introStatement.setVisible(true);
    }

    public void createFirstPoint() {
        firstPoint = new JLabel();
        firstPoint.setText("1. Press a to create a quiz");
        firstPoint.setFont(new Font("Times", Font.ROMAN_BASELINE, 15));
        firstPoint.setVisible(true);
    }

    public void createSecondPoint() {
        secondPoint = new JLabel();
        secondPoint.setText("2. Press s to save a quiz and l to load the last made quiz");
        secondPoint.setFont(new Font("Times", Font.ROMAN_BASELINE, 15));
        secondPoint.setVisible(true);
    }

    public void createThirdPoint() {
        thirdPoint = new JLabel();
        thirdPoint.setText("3. Press v to view your completed quiz");
        thirdPoint.setFont(new Font("Times", Font.ROMAN_BASELINE, 15));
        thirdPoint.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


    public void turnInstructions(boolean b) {
        introStatement.setVisible(b);
        firstPoint.setVisible(b);
        secondPoint.setVisible(b);
        thirdPoint.setVisible(b);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 65:
                turnInstructions(false);
                imageLabel.setVisible(false);
                new QuestionForm(centrePanel, quiz);
                break;
            case 83:
                imageLabel.setVisible(false);
                saveQuiz();
                break;
            case 76:
                imageLabel.setVisible(false);
                loadQuiz();
                System.out.println("Your quiz has loaded");
                break;
            case 86:
                imageLabel.setVisible(false);
                turnInstructions(false);
                new QuizPrint(quiz, centrePanel);
                turnInstructions(true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public JLabel createJlabelPrompt(String prompt) {
        JLabel questionPrompt = new JLabel(prompt);
        questionPrompt.setFont(new Font("Calibri", Font.PLAIN, 15));
        questionPrompt.setForeground(Color.darkGray);
        return questionPrompt;
    }

    //MODIFIES: json Writer
    // EFFECTS: saves quiz
    public void saveQuiz() {
        try {
            writer.open();
            writer.write(quiz);
            writer.close();
            System.out.println("Saved Quiz");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file");
        }
    }

    //MODIFIES: this
    //EFFECTS: loads quiz from file
    public void loadQuiz() {
        try {
            quiz = reader.read();
        } catch (IOException e) {
            System.out.println("Not found");
        }
    }
}