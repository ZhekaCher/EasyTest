package GUI;

import Data.Global;
import Data.Question;
import Data.Serialization;
import Data.Test;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Training_Controller {

    @FXML
    private AnchorPane answers_AnchorPane;

    @FXML
    private CheckBox flag_CheckBox;

    @FXML
    private CheckBox showAll_CheckBox;

    @FXML
    private RadioButton green_RadioButton;

    @FXML
    private RadioButton plus_RadioButton;

    @FXML
    private Label question_Label;

    @FXML
    private Label testName_Label;

    @FXML
    private Slider textSize_Slider;

    private static Test test;

    private static boolean fullRand;

    int i = 0;

    private ArrayList<Question> questions = new ArrayList<>();

    @FXML
    void initialize() {
        testName_Label.setText(test.getName());
        questions = test.getQuestions();
        for (Question question : test.getQuestions()) {
            if (question.getRightAnswers().size() < 1)
                System.out.println(question.getQuestion());
        }
       // if (!fullRand)
           questions =  randomizeArrayList(questions);
        i = questions.size() - 1;
    }

    @FXML
    void openMenuScene(ActionEvent event) {
        Global.loadMenu();
        test = null;
    }

    @FXML
    void next(ActionEvent event) {
        for (Question question : test.getQuestions()) {
            if (question.getQuestion().equals(question_Label.getText())) {
                question.setFlag(flag_CheckBox.isSelected());
                Serialization.serialize(test);
                break;
            }
        }
        Question question = null;
        if (fullRand) {
            question = getRandomQuestion();
        } else {
            if (i >= 0) {
                question = questions.get(i);
                --i;
            }
            else {
                openMenuScene(event);
            }
        }
        flag_CheckBox.setSelected(question.isFlag());
        question_Label.setText(question.getQuestion());

        ArrayList<AnchorPane> anchorPanes = new ArrayList<>();
        for (String rightAnswer : question.getRightAnswers())
            anchorPanes.add(answerPane(rightAnswer, true));
        for (String wrongAnswer : question.getWrongAnswers())
            anchorPanes.add(answerPane(wrongAnswer, false));
        updateAnswers_AnchorPane(randomizeArrayList(anchorPanes));

    }

    public static void setArgs(Test test, boolean fullRand) {
        Training_Controller.test = test;
        Training_Controller.fullRand = fullRand;
    }

    private ArrayList randomizeArrayList(ArrayList arrayList) {
        ArrayList list = new ArrayList();

        while (arrayList.size() > 0) {
            Random random = new Random(System.nanoTime());
            int rand = random.nextInt(arrayList.size());
            list.add(arrayList.get(rand));
            arrayList.remove(rand);
        }

        return list;
    }

    private AnchorPane answerPane(String answer, boolean type) {
        AnchorPane anchorPane = new AnchorPane();

        anchorPane.setPrefHeight(50);
        anchorPane.setPrefWidth(1920);

        Label label = new Label(answer);
        label.setLayoutX(10);
        label.setLayoutY(10);
        label.setWrapText(true);
        label.minWidth(0);
        label.maxWidth(1920);
        label.setPrefHeight(40);
        label.setFont(Font.font(textSize_Slider.getValue()));

        Tooltip tooltip = new Tooltip();
        tooltip.setText("Wrong Answer");
        tooltip.setAnchorX(0);
        tooltip.setAnchorY(0);
        anchorPane.setOnMouseClicked(mouseEvent -> {
            tooltip.show(Global.getStage());
            anchorPane.setStyle("-fx-border-width: 3; -fx-border-color: #ff0028");
            if (type)
                anchorPane.setStyle("-fx-border-width: 3; -fx-border-color: #4cff07");

        });
        tooltip.setAutoHide(true);
        tooltip.setHideDelay(new Duration(1000));
        tooltip.setFont(Font.font(40));
        tooltip.setStyle("-fx-border-width: 3; -fx-border-color: RED");
        anchorPane.setStyle("-fx-border-width: 1; -fx-border-radius: 10; -fx-border-color: #000000");
        if (type) {
            tooltip.setText("Right Answer");
            tooltip.setStyle("-fx-border-width: 3; -fx-border-color: #4cff07");

            if (plus_RadioButton.isSelected())
                label.setText("+" + answer);
            if (green_RadioButton.isSelected()) {
                label.setTextFill(Paint.valueOf("#1D8A00"));
                anchorPane.setStyle("-fx-border-width: 3; -fx-border-radius: 10; -fx-border-color: #4cff07");
            }
        }

        anchorPane.getChildren().add(label);

        return anchorPane;
    }

    public void updateAnswers_AnchorPane(ArrayList<AnchorPane> anchorPanes) {
        answers_AnchorPane.getChildren().clear();
        answers_AnchorPane.setPrefHeight(5);
        int y = 5;
        for (AnchorPane anchorPane : anchorPanes) {
            answers_AnchorPane.setPrefHeight(y + 65);
            anchorPane.setLayoutX(0);
            anchorPane.setLayoutY(y);
            answers_AnchorPane.getChildren().add(anchorPane);
            y += 65;
        }
    }

    private Question getRandomQuestionDelete() {
        Question question = null;
        int i = 0;
        while (true) {
            if (i > 10000) {
                Global.loadMenu();
                JOptionPane.showMessageDialog(null, "There's no any reachable questions");
                test = null;
                break;
            }
            Random random = new Random(System.nanoTime());
            int r = random.nextInt(questions.size());
            question = questions.get(r);
            questions.remove(r);
            ++i;
            if (showAll_CheckBox.isSelected()) {
                break;
            } else {
                if (question.isFlag()) {
                    continue;
                } else {
                    break;
                }
            }
        }
        return question;
    }

    private Question getRandomQuestion() {
        Question question = null;
        int i = 0;
        while (true) {
            if (i > 10000) {
                Global.loadMenu();
                JOptionPane.showMessageDialog(null, "There's no any reachable questions");
                test = null;
                break;
            }
            Random random = new Random(System.nanoTime());
            int r = random.nextInt(questions.size());
            question = questions.get(r);
            ++i;
            if (showAll_CheckBox.isSelected()) {
                break;
            } else {
                if (question.isFlag()) {
                    continue;
                } else {
                    break;
                }
            }

        }
        return question;
    }
}
