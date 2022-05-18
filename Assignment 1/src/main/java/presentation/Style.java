package presentation;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import presentation.GUIElements.BFXButton;
import presentation.GUIElements.EventList;
import presentation.GUIElements.Interpolator;
import presentation.GUIElements.PropertiesList;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

abstract public class Style {


    static public void setButtonStyleLogInPrimary(BFXButton button){
        setButtonAnimationsBig(button);
        setButtonStylePrimary(button);
    }

    static public void setButtonStyleLogInSecondary(BFXButton button){
        setButtonAnimationsBig(button);
        setButtonStyleSecondary(button);
    }

    static private void setButtonAnimationsBig(BFXButton button){
        button.addAnimation(List.of(300, 320), Interpolator.CUBED, PropertiesList.PREF_WIDTH, 150, EventList.MOUSE_HOVER);
    }

    static private void setButtonAnimationsMedium(BFXButton button){
        button.addAnimation(List.of(150, 170), Interpolator.CUBED, PropertiesList.PREF_WIDTH, 150, EventList.MOUSE_HOVER);
    }

    static private void setButtonAnimationsSmall(BFXButton button){
        button.addAnimation(List.of(80, 90), Interpolator.CUBED, PropertiesList.PREF_WIDTH, 150, EventList.MOUSE_HOVER);
    }

    static private void setButtonStylePrimary(BFXButton button){

    }

    static private void setButtonStyleSecondary(BFXButton button){

    }

    public static void setBackgroundRed(Region p){

        p.setBackground(new Background(new BackgroundFill(Paint.valueOf("EE0000"),new CornerRadii(2), Insets.EMPTY)));
    }

    public static void setBackgroundWhite(Region p){
        p.setBorder(new Border(new BorderStroke(Paint.valueOf("99999999"),
                BorderStrokeStyle.SOLID,
                new CornerRadii(2),
                new BorderWidths(1) )));
        p.setBackground(new Background(new BackgroundFill(Paint.valueOf("FFFFFF"),new CornerRadii(2), Insets.EMPTY)));
    }

    public static String stringify(Date date){
        String day = switch (date.getDay()){
            case 0->"Monday";
            case 1->"Tuesday";
            case 2->"Wednesday";
            case 3->"Thursday";
            case 4->"Friday";
            case 5->"Saturday";
            case 6->"Sunday";
            default -> throw new IllegalStateException("Unexpected value: " + date.getDay());
        };

        String month = switch (date.getMonth()){
            case 0->"Jan";
            case 1->"Feb";
            case 2->"Mar";
            case 3->"Apr";
            case 4->"May";
            case 5->"Jun";
            case 6->"Jul";
            case 7->"Aug";
            case 8->"Sep";
            case 9->"Oct";
            case 10->"Nov";
            case 11->"Dec";
            default -> throw new IllegalStateException("Unexpected value: " + date.getMonth());
        };
        return  day+", "  + date.getDate() + " " + month + " " + ( date.getYear()+1900 );
    }

}
