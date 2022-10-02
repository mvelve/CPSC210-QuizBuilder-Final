package ui;

import model.Quiz;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;

//CLASS LEVEL COMMENT: creates layout for introductory menu which reappears after user has specified all prompts
// THe quiz Menu is class is used and persists after user has completed one iteration of creating a question

public class QuizMenu extends JFrame implements KeyListener {
    private JLabel introLabel;
    private JLabel firstPoint;
    private JLabel secondPoint;
    private JLabel thirdPoint;
    private JLabel introStatement;
    private JPanel centrePanel;
    private JPanel topPanel;
    private Quiz quiz;
    private JsonReader reader;
    private JsonWriter writer;
    private JLabel imageLabel;


    //REQUIRES: valid quiz with parameters set to eb passed in
    //MODIFIES: quiz, this
    //EFFECTS: constructs quiz menu frame
    public QuizMenu(Quiz quiz) {
        reader = new JsonReader("./data/Directory.json");
        writer = new JsonWriter("./data/Directory.json");
        this.quiz = quiz;

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

    //MODIFIES :firstpoint
    //EFFECTS: creates Jlabel with first instruction, sets Font, and visibility
    public void createFirstPoint() {
        firstPoint = new JLabel();
        firstPoint.setText("1. Press a to create a quiz");
        firstPoint.setFont(new Font("Times", Font.ROMAN_BASELINE, 15));
        firstPoint.setVisible(true);
    }

    //MODIFIES: secondPoint
    //EFFECTS: creates Jlabel with second instruction, sets Font, and visibility
    public void createSecondPoint() {
        secondPoint = new JLabel();
        secondPoint.setText("2. Press s to save a quiz and l to load the last made quiz");
        secondPoint.setFont(new Font("Times", Font.ROMAN_BASELINE, 15));
        secondPoint.setVisible(true);
    }

    //MODIFIES: thirdPoint
    //EFFECTS: creates Jlabel with second instruction, sets Font, and visibility
    public void createThirdPoint() {
        thirdPoint = new JLabel();
        thirdPoint.setText("3. Press v to view your completed quiz");
        thirdPoint.setFont(new Font("Times", Font.ROMAN_BASELINE, 15));
        thirdPoint.setVisible(true);
    }

    //MODIFIES: introStatement
    //EFFECTS: creates Jlabel with second instruction, sets Font, and visibility
    public void createIntroLabel() {
        introStatement = new JLabel();
        introStatement.setText("Welcome to QuizBuilder please adhere to the following instructions:");
        introStatement.setFont(new Font("Times", Font.ROMAN_BASELINE, 15));
        introStatement.setVisible(true);
    }


    // MODIFIES: centrePanel, firstPoint, secondPoint, thirdPoint, imageLabel
    //EFFECTS: creates centre panel and places introStatement, firstPoint, secondPoint, thirdPoint within, and
    // image label within it
    public void createCentrePanel() {

        imageLabel = new JLabel();
        ImageIcon quizIntroIcon = new ImageIcon("./images/quizIntro.jpg");
        Image quizIntroImage = quizIntroIcon.getImage();
        Image resizedQuizIntro = quizIntroImage.getScaledInstance(250, 200,  java.awt.Image.SCALE_SMOOTH);
        quizIntroIcon = new ImageIcon(resizedQuizIntro);

        centrePanel = new JPanel();
        centrePanel.setPreferredSize(new Dimension(700, 500));
        centrePanel.setBackground(Color.white);
        centrePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // create possibly just change where the button is
        centrePanel.add(introStatement);
        centrePanel.add(firstPoint);
        centrePanel.add(secondPoint);
        centrePanel.add(thirdPoint);
        centrePanel.add(imageLabel);
    }

    //EFFECTS: records key typed method, overwirtten methd not used
    @Override
    public void keyTyped(KeyEvent e) {

    }

    //MODIFIES: this, QuestionFrom, quiz
    //EFFECTS: listens to keys "a", "s", "v" and either creates questionForm, savesQuiz, or loadsQuiz
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 65:
                imageLabel.setVisible(false);
                turnInstructions(false);
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


    // EFFECTS: listens to keys released, overwritten method not used
    @Override
    public void keyReleased(KeyEvent e) {

    }

    //MODIFIES: intoStatement, firstPoint, secondPoint, thirdPoint
    //EFFECTS: sets visibility of introStatement, firstPoint, secondPoint, thirdPoint
    public void turnInstructions(boolean b) {
        introStatement.setVisible(b);
        firstPoint.setVisible(b);
        secondPoint.setVisible(b);
        thirdPoint.setVisible(b);

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
