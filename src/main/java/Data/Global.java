package Data;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Global {

    private static Stage stage;
    private static Scene menu_Scene;
    private static Scene training_Scene;
    private static Scene edit_Scene;
    private static File easyTestDir;
    private static File testsDir;
    private static File filesDir;

    public static void globalInit() {
        stage = new Stage();


        easyTestDir = new File(System.getenv("SystemDrive") + System.getenv("HOMEPATH") + "\\Documents\\EasyTest");
        if (!easyTestDir.exists()) {
            try {
                easyTestDir.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        filesDir = new File(System.getenv("SystemDrive") + System.getenv("HOMEPATH") + "\\Documents\\EasyTest\\Files");
        if (!filesDir.exists()) {
            try {
                filesDir.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        testsDir = new File(System.getenv("SystemDrive") + System.getenv("HOMEPATH") + "\\Documents\\EasyTest\\Tests");
        if (!testsDir.exists()) {
            try {
                testsDir.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void nullTests(){
        testsDir = null;
    }

    public static Scene getEdit_Scene() {
        try {
            edit_Scene = new Scene(FXMLLoader.load(Global.class.getResource("/FXML/Edit.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return edit_Scene;
    }

    public static File getEasyTestDir(){
        return easyTestDir;
    }

    public static File getFilesDir() {
        return filesDir;
    }

    public static File getTestsDir() {
        return testsDir;
    }

    public static Stage getStage() {
        return stage;
    }

    public static Scene getMenu_Scene() {
        try {
            menu_Scene = new Scene(FXMLLoader.load(Global.class.getResource("/FXML/Menu.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return menu_Scene;
    }

    public static Scene getTraining_Scene() {
        try {
            training_Scene = new Scene(FXMLLoader.load(Global.class.getResource("/FXML/Training.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return training_Scene;
    }

    public static void loadMenu(){
        getStage().setScene(getMenu_Scene());
        getStage().setTitle("Easy Test");
    }
}
