package model;

import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

public enum PackageStatus {

    NO_BOOKINGS,
    IN_PROGRESS,
    FULLY_BOOKED,
    OVERBOOKED;

    public static PackageStatus getStatus(VacationPackage vacationPackage){
        if(vacationPackage.getBookedUsers().size() > vacationPackage.getMaxBooks())
            return OVERBOOKED;
        else if (vacationPackage.getBookedUsers().size() == vacationPackage.getMaxBooks())
            return FULLY_BOOKED;
        else if (vacationPackage.getBookedUsers().isEmpty())
            return NO_BOOKINGS;
        else return IN_PROGRESS;
    }

    public static String stringify(PackageStatus status){
        return switch (status){
            case OVERBOOKED -> "OVERBOOKED";
            case FULLY_BOOKED -> "FULLY BOOKED";
            case NO_BOOKINGS -> "NO BOOKINGS YET";
            case IN_PROGRESS -> "IN PROGRESS";
        };
    }

    public static Label labelify(PackageStatus status){
        Label label = new Label(stringify(status));
        label.setTextFill(getPaint(status));
        return label;
    }

    public static Paint getPaint(PackageStatus status){
        return switch (status){
            case OVERBOOKED -> Paint.valueOf("7d1b06");
            case FULLY_BOOKED -> Paint.valueOf("07be18");
            case IN_PROGRESS -> Paint.valueOf("d6cc08");
            case NO_BOOKINGS -> Paint.valueOf("f04141");
        };
    }
}
