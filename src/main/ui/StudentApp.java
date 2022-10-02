package ui;

import model.Question;
import model.Quiz;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Scanner;

//CLASS LEVEL COMMENT: the studentApp class includes functionality capable of creating a quiz and printing
// a simple format of each quiz

public class StudentApp {
    private final Scanner input = new Scanner(System.in);
    private final String questionIds = "ABCDEFGHIJKLMOPQRSTUVWXYZ";
    private final ArrayList<Quiz> quizStorage;
    private static final String DESTINATION = "./data/Directory.json";
    private JsonWriter jsonWriter = new JsonWriter(DESTINATION);
    private JsonReader jsonReader = new JsonReader(DESTINATION);
    private Quiz finishedQuiz; // edit here REMOVE TO MAKE FUNCTIONALITY WORK AGAIN

    public StudentApp() {
        this.quizStorage = new ArrayList<>();
        displayMenu();
    }

    //MODIFIES: this
    //EFFECTS: prints out a display menu allowing users to select whether to create a quiz,
    // take a quiz (functionality not yet implemented), and to view the menu again


    @SuppressWarnings("methodlength")
    public void displayMenu() {
        boolean keepDisplay = true;
        while (keepDisplay) {
            System.out.println("Option 1: Type a to create quiz\nOption 2: Type p to see your printed quiz");
            System.out.println("Option 3: Type s to save your quiz\nOption 4: Type l to load your prior quiz");
            System.out.println("Option 5: Type m to open menu again\npress q to quit menu");
            switch (input.next()) {
                case "q":
                    keepDisplay = false;
                    System.out.println("You have exited the menu!");
                    break;
                case "a":
                    quizStorage.add(createQuiz());
                    break;
                case "p":
                    if (finishedQuiz == null) {
                        System.out.println("Please create a quiz  and save a quiz first!\n");
//                        quizStorage.add(createQuiz()); not needed this here
                    }
                    quizPrint(finishedQuiz);
                    break;
                case "s":
                    saveQuiz();
                    break;
                case "l":
                    loadQuiz();
                    break;
                default:
                    System.out.println("This is not an option select again");
            }
        }
    }
// abstracted methods for future implementation
//    //REQUIRES: nonPermissible must fall out of range for specified usage, function checks that input value is in
//    // bounds
//    //EFFECTS: returns integer
//    private int safeGetInt(int nonPermissibleValue, Function<Integer, Boolean> outOfBoundsLambda,
//                           String promptString, String errorString) {
//        int valueToGet = nonPermissibleValue;
//        while (outOfBoundsLambda.apply(valueToGet)) {
//            System.out.println(promptString);
//            valueToGet = input.nextInt();
//            input.nextLine();
//            if (outOfBoundsLambda.apply(valueToGet)) {
//                System.out.println(errorString);
//            }
//        }
//        return valueToGet;
//    }
//
//    public String safeGetString(String nonPermissibleString, String matchCase, String promptString, String
//            errorPrompt) {
//        String valueToGet = nonPermissibleString;
//        while (valueToGet.length() == 0 && !Pattern.matches(matchCase, valueToGet)) {
//            System.out.println(promptString);
//            valueToGet = input.nextLine();
//            if (valueToGet.length() == 0 && !Pattern.matches(matchCase, valueToGet)) {
//                System.out.println(errorPrompt);
//            }
//        }
//        return valueToGet;
//    }

    //REQUIRES: number of input questions must be of type integer
    //EFFECTS: returns the number of questions a user has specified
    private int safeGetInputQuestions() {
        int numberOfInputQuestions = -1;
        while (numberOfInputQuestions <= 0) {
            System.out.println("How many questions do you have in this quiz?");
            numberOfInputQuestions = input.nextInt();
            input.nextLine();
            if (numberOfInputQuestions <= 0) {
                System.out.println("Please enter a valid number of questions greater than 0");
            }
        }
        return numberOfInputQuestions;
    }

    //REQUIRES: number of points to be of type integer
    //EFFECTS: returns the number of points per question a user has specified
    private int safeGetPoints() {
        int numberOfPoints = 101;
        while (numberOfPoints > 100 || numberOfPoints < 0) {
            System.out.println("How many points is this question worth?");
            numberOfPoints = input.nextInt();
            input.nextLine();
            if (numberOfPoints > 100 || numberOfPoints < 0) {
                System.out.println("Please enter valid amount within range of 0 to 100");
            }
        }
        return numberOfPoints;
    }

    //REQUIRES: number of question options must be of type integer
    //EFFECTS: returns the number of question options (multiple choice) that a user has specified
    private int safeGetQuestionOptions() {
        int questionOptions = -1;
        while (questionOptions > 26 || questionOptions < 1) {
            System.out.println("How many options for this question?");
            questionOptions = input.nextInt();
            input.nextLine();
            if (questionOptions > 26 || questionOptions < 1) {
                System.out.println("This is not a valid question number please enter a number within 1 and 26");
            }
        }
        return questionOptions;
    }

    //REQUIRES:input response must be of type string
    //EFFECTS: returns a user's description/response for a multiple choice option
    private String safeGetResponse(int j) {
        String possibleResponse = "";
        while (possibleResponse.length() == 0 || possibleResponse.length() > 80) {
            System.out.println("What is option " + questionIds.charAt(j));
            possibleResponse = input.nextLine();
            if (possibleResponse.length() == 0 || possibleResponse.length() > 80) {
                System.out.println("Responses must be 80 characters including spaces please try again");
            }
        }

        return possibleResponse;
    }

    //REQUIRES: question prompt must be of type String
    //EFFECTS: returns the question (prompt) to which students would answer
    private String safeGetPrompt() {
        String promptResponse = "";
        while (promptResponse.length() == 0) {
            System.out.println("Enter your question prompt please?");
            promptResponse = input.nextLine();
            if (promptResponse.length() == 0) {
                System.out.println("Please enter a valid response");
            }
        }
        return promptResponse;
    }

    //REQUIRES: question options must be of type integer
    //EFFECTS: sets the correct multiple choice response
    private String safeGetCorrectResponse(int questionOptions) {
        String correctResponse = "";
        while (correctResponse.length() != 1 || !questionIds.substring(0,
                questionOptions).contains(correctResponse.toUpperCase())) {
            System.out.println("What is the correct response?");
            correctResponse = input.next();
            input.nextLine();
            if (correctResponse.length() != 1 || !questionIds.substring(0,
                    questionOptions).contains(correctResponse.toUpperCase())) {
                System.out.println("You have not entered a correct response please try again");
            }
        }
        return correctResponse;
    }

    //REQUIRES: valid parameters passed into safeGetInputQuestions,
    // numberOfInputQuestions, and safeGetQuestionOptions
    //MODIFIES: this, Question, Quiz
    //EFFECTS: creates a quiz based on user input dictating number of questions, their point value, and
    // number of multiple choice options
    public Quiz createQuiz() {
        int numberOfInputQuestions = safeGetInputQuestions();
        Quiz newQuiz = new Quiz(numberOfInputQuestions);
        for (int i = 0; i < numberOfInputQuestions; i++) {
            Question newQuestion = new Question();
            newQuestion.setPointValue(safeGetPoints());
            newQuestion.setQuestionPrompt(safeGetPrompt());
            newQuiz.insertQuestion(newQuestion);
            int questionOptions = safeGetQuestionOptions();
            for (int j = 0; j < questionOptions; j++) {
                String possibleResponse = safeGetResponse(j);
                String completeOption = questionIds.charAt(j) + " : " + possibleResponse;

                newQuestion.appendResponse(completeOption);
            }

            printResponses(newQuestion.getPossibleResponses());

            newQuestion.setCorrectAnswer(safeGetCorrectResponse(questionOptions));

            System.out.println("Begin next Question\n\n"); //ok should be simple error to fix but be careful

        }
        this.finishedQuiz = newQuiz;
        return newQuiz;
    }

    //REQUIRES: valid quiz input
    //MODIFIES: this
    //EFFECTS: prints a layout of a quiz showing which answer is correct and total point value
    public void quizPrint(Quiz printQuiz) {
        System.out.println("You have created the following quiz!\n");
//        printQuiz = finishedQuiz;
        int count = 0;
        int totalPoints = 0;
        for (Question question : printQuiz.getQuestions()) {
            count += 1;
            totalPoints += question.getPointValue();
            System.out.println("Question " + count);
            printResponses(question.getPossibleResponses());
            System.out.println("You have assigned " + question.getCorrectAnswer() + " as your correct answer\n");
        }
        System.out.println("The total number of points in this quiz is " + totalPoints + "\n");
    }

    // this commented out additional functionality allowing students to take quiz
//    public void takeQuiz() {
//        System.out.println("You are now taking the quiz begin!");
//        for (Question question : quizStorage.get(0).getQuestions()) {
//            System.out.println("Please select an answer from the following");
//
//            String questionResponse = input.nextLine();
//            // this is additional functionality you should do!
//
//        }
//
//    }

    //REQUIRES: listOfPossible responses is not empty
    //MODIFIES: this
    //EFFECTS: returns a print out of the multiple choice questions
    public void printResponses(ArrayList<String> listOfPossibleResponses) {
        for (String i : listOfPossibleResponses) {
            System.out.println(i);
        }
    }

    //MODIFIES: json Writer
    // EFFECTS: saves quiz
    public void saveQuiz() {
        try {
            jsonWriter.open();
            jsonWriter.write(finishedQuiz);
            jsonWriter.close();
            System.out.println("Your prior quiz has been saved in" + DESTINATION);

        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + DESTINATION);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads quiz from file
    public void loadQuiz() {
        try {
            finishedQuiz = jsonReader.read();
            System.out.println("Loaded prior quiz" + " from " + DESTINATION);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + DESTINATION);
        }
    }

}


