package presentation.controller;

import Exceptions.AlreadyExistsException;
import Exceptions.DoesNotExistException;
import Exceptions.InvalidRegisterInputException;
import Exceptions.WrongPasswordException;
import bussiness.AccountService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import model.Agency;
import model.User;
import presentation.GUIElements.BFXButton;
import presentation.Style;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    @FXML private VBox          userLogInBox;
    @FXML private TextField     userLogInEmailFld;
    @FXML private PasswordField userLogInPassFld;
    @FXML private Button        userLogInBtn;           private BFXButton userLogInBtnBFX;
    @FXML private Button        toUserRegisterBtn;      private BFXButton toUserRegisterBtnBFX;
    @FXML private Button        toAgencyLogInBtn;       private BFXButton toAgencyLogInBtnBFX;
    @FXML private VBox          agencyLogInBox;
    @FXML private TextField     agencyLogInEmailFld;
    @FXML private PasswordField agencyLogInPassFld;
    @FXML private Button        agencyLogInBtn;         private BFXButton agencyLogInBtnBFX;
    @FXML private Button        toAgencyRegisterBtn;    private BFXButton toAgencyRegisterBtnBFX;
    @FXML private Button        agencyLogInBackBtn;     private BFXButton agencyLogInBackBtnBFX;
    @FXML private VBox          userRegisterBox;
    @FXML private TextField     userRegisterFirstFld;
    @FXML private TextField     userRegisterLastFld;
    @FXML private TextField     userRegisterNameFld;
    @FXML private TextField     userRegisterEmailFld;
    @FXML private PasswordField userRegisterPassFld;
    @FXML private PasswordField userRegisterPassConFld;
    @FXML private Button        userRegisterBtn;        private BFXButton userRegisterBtnBFX;
    @FXML private Button        userRegisterBackBtn;    private BFXButton userRegisterBackBtnBFX;
    @FXML private VBox          agencyRegisterBox;
    @FXML private TextField     agencyRegisterNameFld;
    @FXML private TextField     agencyRegisterEmailFld;
    @FXML private TextArea      agencyRegisterDescFld;
    @FXML private PasswordField agencyRegisterPassFld;
    @FXML private PasswordField agencyRegisterPassConFld;
    @FXML private Button        agencyRegisterBtn;      private BFXButton agencyRegisterBtnBFX;
    @FXML private Button        agencyRegisterBackBtn;  private BFXButton agencyRegisterBackBtnBFX;

    @FXML private Label         errorLbl;
    @FXML private Label         errorLbl1;
    @FXML private Label         errorLbl2;
    @FXML private Label         errorLbl3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userLogInBtnBFX =           new BFXButton(userLogInBtn);
        toUserRegisterBtnBFX =      new BFXButton(toUserRegisterBtn);
        toAgencyLogInBtnBFX =       new BFXButton(toAgencyLogInBtn);
        agencyLogInBtnBFX =         new BFXButton(agencyLogInBtn);
        toAgencyRegisterBtnBFX =    new BFXButton(toAgencyRegisterBtn);
        agencyLogInBackBtnBFX =     new BFXButton(agencyLogInBackBtn);
        userRegisterBtnBFX =        new BFXButton(userRegisterBtn);
        userRegisterBackBtnBFX =    new BFXButton(userRegisterBackBtn);
        agencyRegisterBtnBFX =      new BFXButton(agencyRegisterBtn);
        agencyRegisterBackBtnBFX =   new BFXButton(agencyRegisterBackBtn);

        Style.setButtonStyleLogInPrimary(userLogInBtnBFX);
        Style.setButtonStyleLogInPrimary(toUserRegisterBtnBFX);
        Style.setButtonStyleLogInPrimary(toAgencyLogInBtnBFX);
        Style.setButtonStyleLogInPrimary(agencyLogInBtnBFX );
        Style.setButtonStyleLogInPrimary(toAgencyRegisterBtnBFX);
        Style.setButtonStyleLogInPrimary(agencyLogInBackBtnBFX);
        Style.setButtonStyleLogInPrimary(userRegisterBtnBFX);
        Style.setButtonStyleLogInPrimary(userRegisterBackBtnBFX);
        Style.setButtonStyleLogInPrimary(agencyRegisterBtnBFX);
        Style.setButtonStyleLogInPrimary(agencyRegisterBackBtnBFX);

        userLogInBox.managedProperty().bind(userLogInBox.visibleProperty());
        agencyLogInBox.managedProperty().bind(agencyLogInBox.visibleProperty());
        userRegisterBox.managedProperty().bind(userRegisterBox.visibleProperty());
        agencyRegisterBox.managedProperty().bind(agencyRegisterBox.visibleProperty());

        toUserLogIn();

        //hot to color
        setFieldListeners(agencyRegisterPassConFld, agencyRegisterPassFld);
        setFieldListeners(userRegisterPassConFld, userRegisterPassFld);
    }

    private void setFieldListeners(PasswordField userRegisterPassConFld, PasswordField userRegisterPassFld) {
        userRegisterPassConFld.textProperty().addListener((e)->{
            if(!userRegisterPassFld.getText().equals(userRegisterPassConFld.getText()))
                userRegisterPassConFld.setBackground(new Background(new BackgroundFill(Paint.valueOf("EE0000"),new CornerRadii(2), Insets.EMPTY)));
            else
                userRegisterPassConFld.setBackground(new Background(new BackgroundFill(Paint.valueOf("FFFFFF"),new CornerRadii(2), Insets.EMPTY)));
        });
    }

    @FXML void agencyLogIn() {
        try {
            Agency agency = AccountService.agencyLogIn(
                    agencyLogInEmailFld.getText(),
                    agencyLogInPassFld.getText()
            );

            WrapperController.instance.toAgencyView(agency);

        } catch (DoesNotExistException | WrongPasswordException e) {
            errorLbl1.setText(e.getMessage());
            errorLbl1.setVisible(true);
        }
    }

    @FXML void agencyRegister() {
        try {

            if(!agencyRegisterPassFld.getText().equals(agencyRegisterPassConFld.getText()))
                throw new InvalidRegisterInputException("Passwords do not match!");

            Agency agency = AccountService.agencyRegister(
                    agencyRegisterNameFld.getText(),
                    agencyRegisterEmailFld.getText(),
                    agencyRegisterPassFld.getText(),
                    agencyRegisterDescFld.getText()
            );

            WrapperController.instance.toAgencyView(agency);

        } catch (InvalidRegisterInputException | AlreadyExistsException e) {
            errorLbl3.setText(e.getMessage());
            errorLbl3.setVisible(true);
        }
    }

    @FXML void userLogIn() {
        try {
            User user = AccountService.userLogIn(
                    userLogInEmailFld.getText(),
                    userLogInPassFld.getText()
                    );

            WrapperController.instance.toUserView(user);

        } catch (DoesNotExistException | WrongPasswordException e) {
            errorLbl.setText(e.getMessage());
            errorLbl.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML void userRegister() {

        try {
            if(!userRegisterPassFld.getText().equals(userRegisterPassConFld.getText()))
                throw new InvalidRegisterInputException("Passwords do not match!");

            User user = AccountService.userRegister(
                    userRegisterFirstFld.getText(),
                    userRegisterLastFld.getText(),
                    userRegisterEmailFld.getText(),
                    userRegisterNameFld.getText(),
                    userRegisterPassFld.getText()
            );

            WrapperController.instance.toUserView(user);

        } catch (AlreadyExistsException | InvalidRegisterInputException e) {
            errorLbl2.setText(e.getMessage());
            errorLbl2.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML void toAgencyLogIn() {
        setVisibility(false,false,true,false);
    }

    @FXML void toAgencyRegister() {
        setVisibility(false,false,false,true);
    }

    @FXML void toUserLogIn() {
        setVisibility(true,false,false,false);
    }

    @FXML void toUserRegister() {
        setVisibility(false,true,false,false);
    }

    private void setVisibility(boolean userLogIn, boolean userRegister, boolean agencyLogIn, boolean agencyRegister){
        clearAllFields();
        hideErrorLabels();

        userLogInBtn.setDefaultButton(userLogIn);
        userRegisterBtn.setDefaultButton(userRegister);
        agencyLogInBtn.setDefaultButton(agencyLogIn);
        agencyRegisterBtn.setDefaultButton(agencyRegister);

        userLogInBox.setVisible(userLogIn);
        agencyLogInBox.setVisible( agencyLogIn);
        agencyRegisterBox.setVisible(agencyRegister);
        userRegisterBox.setVisible(userRegister);
    }

    private void clearAllFields(){
        userLogInEmailFld.clear();
        userLogInPassFld.clear();

        userRegisterFirstFld.clear();
        userRegisterLastFld.clear();
        userRegisterEmailFld.clear();
        userRegisterNameFld.clear();
        userRegisterPassFld.clear();
        userRegisterPassConFld.clear();

        agencyLogInEmailFld.clear();
        agencyLogInPassFld.clear();

        agencyRegisterNameFld.clear();
        agencyRegisterEmailFld.clear();
        agencyRegisterDescFld.clear();
        agencyRegisterPassFld.clear();
        agencyRegisterPassConFld.clear();
    }

    private void hideErrorLabels(){
        errorLbl.setVisible(false);
        errorLbl1.setVisible(false);
        errorLbl2.setVisible(false);
        errorLbl3.setVisible(false);
    }

    public void setUp(){
        toUserLogIn();
    }

}
