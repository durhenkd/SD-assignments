import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.controller.WrapperController;

public class GUI extends Application {

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("wrapper.fxml"));
        Parent mainLayout = loader.load();
        WrapperController.instance = loader.getController();

        Scene mainScene = new Scene(mainLayout, 1440, 800);
//        mainScene.getStylesheets().add("mainStyle.css");

        primaryStage.setResizable(false);
        primaryStage.minWidthProperty().setValue(1440);
        primaryStage.minHeightProperty().setValue(800);
        primaryStage.setTitle("Vacation Temptation");
        primaryStage.setScene(mainScene);

        primaryStage.show();
    }
}
