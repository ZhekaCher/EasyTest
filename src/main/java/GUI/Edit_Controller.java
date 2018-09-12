package GUI;

import Data.Global;
import Data.Question;
import Data.Serialization;
import Data.Test;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.swing.*;


public class Edit_Controller {

    @FXML
    private TextField find_TextField;

    @FXML
    private ListView<String> listOfQuestions_ListView;

    @FXML
    private ListView<String> listOfAnswers_ListView;

    @FXML
    private CheckBox flag_CheckBox;

    private static Test test;

    @FXML
    void initialize() {
        int i = 1;
        for (Question question : test.getQuestions()) {
            listOfQuestions_ListView.getItems().add(i + ". " + question.getQuestion());
            ++i;
        }
    }

    @FXML
    void reloadListOfAnswers() {
        if (!listOfQuestions_ListView.getSelectionModel().isEmpty()) {
            String question = listOfQuestions_ListView.getSelectionModel().getSelectedItem();
            listOfAnswers_ListView.getItems().clear();

            for (Question testQuestion : test.getQuestions()) {
                if (question.endsWith(testQuestion.getQuestion())) {
                    for (String rightAnswer : testQuestion.getRightAnswers())
                        listOfAnswers_ListView.getItems().add("+" + rightAnswer);
                    for (String wrongAnswer : testQuestion.getWrongAnswers())
                        listOfAnswers_ListView.getItems().add(wrongAnswer);
                    if (testQuestion.isFlag())
                        flag_CheckBox.setSelected(true);
                    else
                        flag_CheckBox.setSelected(false);
                    break;
                }
            }

        }
    }

    @FXML
    void addQuestion(ActionEvent event) {
        String questionText = JOptionPane.showInputDialog("Введите ваш вопрос");
        Question question = new Question(questionText);
        test.addQuestion(question);
        listOfQuestions_ListView.getItems().add(questionText);
    }

    @FXML
    void deleteQuestion(ActionEvent event) {
        if (!listOfQuestions_ListView.getSelectionModel().isEmpty()) {
            String question = listOfQuestions_ListView.getSelectionModel().getSelectedItem();
            listOfAnswers_ListView.getItems().clear();

            for (Question testQuestion : test.getQuestions()) {
                if (testQuestion.getQuestion().equals(question)) {
                    test.getQuestions().remove(testQuestion);
                    listOfQuestions_ListView.getItems().remove(question);
                    break;
                }
            }
        }
    }

    @FXML
    void addRightAnswer(ActionEvent event) {
        if (!listOfQuestions_ListView.getSelectionModel().isEmpty()) {
            String question = listOfQuestions_ListView.getSelectionModel().getSelectedItem();

            for (Question testQuestion : test.getQuestions()) {
                if (testQuestion.getQuestion().equals(question)) {
                    String answer = JOptionPane.showInputDialog("Input right answer:");
                    testQuestion.addRightAnswer(answer);
                    listOfAnswers_ListView.getItems().add("+" + answer);
                    break;
                }
            }
        }
    }

    @FXML
    void addWrongAnswer(ActionEvent event) {
        if (!listOfQuestions_ListView.getSelectionModel().isEmpty()) {
            String question = listOfQuestions_ListView.getSelectionModel().getSelectedItem();

            for (Question testQuestion : test.getQuestions()) {
                if (testQuestion.getQuestion().equals(question)) {
                    String answer = JOptionPane.showInputDialog("Input wrong answer:");
                    testQuestion.addWrongAnswer(answer);
                    listOfAnswers_ListView.getItems().add(answer);
                    break;
                }
            }
        }
    }

    @FXML
    void deleteAnswer() {
        if (!listOfQuestions_ListView.getSelectionModel().isEmpty()) {
            if (!listOfAnswers_ListView.getSelectionModel().isEmpty()) {
                String question = listOfQuestions_ListView.getSelectionModel().getSelectedItem();
                String answer = listOfAnswers_ListView.getSelectionModel().getSelectedItem();
                listOfAnswers_ListView.getItems().remove(answer);
                for (Question testQuestion : test.getQuestions()) {
                    if (testQuestion.getQuestion().equals(question)) {

                        for (String rightAnswer : testQuestion.getRightAnswers())
                            if (("+" + rightAnswer).equals(answer))
                                testQuestion.getRightAnswers().remove(rightAnswer);

                        for (String wrongAnswer : testQuestion.getWrongAnswers())
                            if (wrongAnswer.equals(answer))
                                testQuestion.getWrongAnswers().remove(wrongAnswer);

                    }
                }
            }
        }
    }

    @FXML
    void changeFlag() {
        if (!listOfQuestions_ListView.getSelectionModel().isEmpty()) {
            String question = listOfQuestions_ListView.getSelectionModel().getSelectedItem();
            for (Question testQuestion : test.getQuestions()) {
                if (testQuestion.getQuestion().equals(question)) {
                    if (flag_CheckBox.isSelected())
                        testQuestion.setFlag(true);
                    else
                        testQuestion.setFlag(false);
                }
            }
        }
    }

    public static void setTest(Test t) {
        test = t;
    }

    @FXML
    void save(ActionEvent event) {
        Serialization.serialize(test);
        Global.loadMenu();
        test = null;
    }

    @FXML
    void back(ActionEvent event) {
        Global.loadMenu();
        test = null;
    }

    @FXML
    void find() {
        listOfQuestions_ListView.getItems().clear();
        int i = 1;
        for (Question question : test.getQuestions()) {
            if (question.getQuestion().toLowerCase().contains(find_TextField.getText().toLowerCase())) {
                listOfQuestions_ListView.getItems().add(i + ". " + question.getQuestion());
                ++i;
            }
        }
    }
}
