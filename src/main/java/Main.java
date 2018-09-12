import Data.Global;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Global.globalInit();
        Global.getStage().setScene(Global.getMenu_Scene());
      //  Global.getStage().setResizable(false);
        Global.getStage().setTitle("Easy Test");
        Global.getStage().show();

        // Serialization.serialize(new Test("Hello1", 4));
    }
}
