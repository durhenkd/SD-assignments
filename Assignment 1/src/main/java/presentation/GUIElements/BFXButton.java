package presentation.GUIElements;

import javafx.scene.control.Button;


public class BFXButton extends BFXRegion {

    public final Button button;

    public BFXButton(Button button) {super(button); this.button = button;}

    @Override
    void setChildCallBacks() {

    }

    @Override
    void setChildProperty(PropertiesList property, double value) {

    }
}
