package GUI;

import Data.Global;
import Data.GlobalFunc;
import Data.Serialization;
import Data.Test;
import com.panemu.tiwulfx.control.NumberField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Menu_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private ListView<String> listOfTests_ListView;

    @FXML
    private Button addTest_Btn;

    @FXML
    private Button deleteTest_Btn;

    @FXML
    private Button training_Btn;

    @FXML
    private Button generateRandomTest_Btn;

    @FXML
    private NumberField<?> generateRandomTest_NumField;

    @FXML
    private Button openTestsFolder_Btn;

    @FXML
    private Button addTestFromFile_Btn;


    @FXML
    private CheckBox random;

    @FXML
    private ListView<File> listOfFiles_ListView;

    @FXML
    private Button update_Btn;

    private ArrayList<Test> tests = new ArrayList<>();

    @FXML
    void initialize() {

        for (File file: Global.getFilesDir().listFiles())
            listOfFiles_ListView.getItems().add(file);

        tests = Serialization.deserializeAll();

        for (Test test: tests)
            listOfTests_ListView.getItems().add(test.getName());

    }

    @FXML
    void openTestsFolder(ActionEvent event) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(Global.getEasyTestDir());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteTest(ActionEvent event) {
        if (!listOfTests_ListView.getSelectionModel().isEmpty()) {
            String testName = listOfTests_ListView.getSelectionModel().getSelectedItem();
            for (Test test: tests) {
                if (test.getName().equals(testName)) {
                    listOfTests_ListView.getItems().remove(testName);
                    tests.remove(test);
                    System.gc();
                    GlobalFunc.removeTest(testName);
                }
            }
        }
    }

    @FXML
    void update(ActionEvent event) {

        listOfFiles_ListView.getItems().clear();
        listOfTests_ListView.getItems().clear();

        for (File file: Global.getFilesDir().listFiles())
            listOfFiles_ListView.getItems().add(file);

        tests = Serialization.deserializeAll();


        for (Test test: tests)
            listOfTests_ListView.getItems().add(test.getName());

    }

    @FXML
    void openEditWindow() {
        if (!listOfTests_ListView.getSelectionModel().isEmpty()) {
            String testName = listOfTests_ListView.getSelectionModel().getSelectedItem();
            for (Test test: tests) {
                if (test.getName().equals(testName)) {
                    Edit_Controller.setTest(test);
                    Global.getStage().setScene(Global.getEdit_Scene());
                    break;
                }
            }
        }
    }

    @FXML
    void addTest(ActionEvent event) {
        String testName = JOptionPane.showInputDialog("Input test name:");
        Test test = new Test(testName);
        Serialization.serialize(test);
        update(event);
    }

    @FXML
    void openTrainingWindow(ActionEvent event) {

        if (!listOfTests_ListView.getSelectionModel().isEmpty()) {
            String testName = listOfTests_ListView.getSelectionModel().getSelectedItem();
            for (Test test: tests) {
                if (test.getName() != null)
                    if (test.getName().equals(testName)) {
                        Training_Controller.setArgs(test, random.isSelected());
                        Global.getStage().setScene(Global.getTraining_Scene());
                        break;
                    }
            }

        }
    }

    @FXML
    void addTestFromFile(ActionEvent event) {
        if (!listOfFiles_ListView.getSelectionModel().isEmpty()) {
            boolean flag = true;
            String name = "";
            while (flag) {
                flag = false;
                name = JOptionPane.showInputDialog("TestName");
                for (Test test: tests) {
                    if (test.getName().equals(name)) {
                        flag = true;
                    }
                }
            }
            Test test = new Test(name);
            test.setQuestions(GlobalFunc.getQuestions(listOfFiles_ListView.getSelectionModel().getSelectedItem()));
            System.out.println(test.getQuestions().get(0).getQuestion());
            Serialization.serialize(test);
        }
    }

}
