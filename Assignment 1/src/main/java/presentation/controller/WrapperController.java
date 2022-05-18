package presentation.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Agency;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class WrapperController implements Initializable {

    public static WrapperController instance = null;

    @FXML VBox wrap;

    private Parent agencyLayout;
    private Parent userLayout;
    private Parent logInLayout;

    LogInController logInController;
    UserController userController;
    AgencyController agencyController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/logIn.fxml"));
            logInLayout = loader.load();
            logInController =  loader.getController();

            loader = new FXMLLoader(getClass().getResource("/user.fxml"));
            userLayout = loader.load();
            userController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/agency.fxml"));
            agencyLayout = loader.load();
            agencyController = loader.getController();

            toLogInView();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void toAgencyView(Agency agency){
        agencyController.setUp(agency);

        wrap.getChildren().clear();
        wrap.getChildren().add(agencyLayout);
    }

    public void toLogInView(){
        logInController.setUp();

        wrap.getChildren().clear();
        wrap.getChildren().add(logInLayout);
    }

    public void toUserView(User user){
        userController.setUp(user);

        wrap.getChildren().clear();
        wrap.getChildren().add(userLayout);
    }

}
