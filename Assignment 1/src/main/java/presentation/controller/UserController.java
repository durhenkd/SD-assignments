package presentation.controller;

import bussiness.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import model.Destination;
import model.User;
import model.VacationPackage;
import presentation.Style;

import java.net.URL;
import java.time.ZoneId;
import java.util.*;

public class UserController implements Initializable {

    @FXML private Button showBookingsBtn;
    @FXML private Button showBrowseBtn;
    @FXML private ComboBox<Destination> destinationsComboBox;
    @FXML private TextField minPriceFld;
    @FXML private TextField maxPriceFld;
    @FXML private DatePicker afterDate;
    @FXML private DatePicker beforeDate;
    @FXML private Button logOutBtn;
    @FXML private ScrollPane browsePane;
    @FXML private VBox browseBox;
    @FXML private ScrollPane bookingsPane;
    @FXML private VBox bookingsBox;

    @FXML private Label welcomeLbl;


    private Date after = null;
    private Date before = null;
    private double min = -1;
    private double max = Double.MAX_VALUE;

    private User currentUser = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        destinationsComboBox.getItems().add(null);
        destinationsComboBox.getItems().addAll(UserService.getAllDestinations());

        destinationsComboBox.valueProperty().addListener((som, oldV, newV)->{
            updateFilter(newV);
        });


        afterDate.valueProperty().addListener((som, oldV, newV) ->{
            if(newV != null) {
                after = Date.from(newV.atStartOfDay(ZoneId.systemDefault()).toInstant());
                updateFilter(destinationsComboBox.getValue());
            }else{
                after = null;
                updateFilter(destinationsComboBox.getValue());
            }

        });
        beforeDate.valueProperty().addListener((som, oldV, newV) ->{
            if(newV != null) {
                before = Date.from(newV.atStartOfDay(ZoneId.systemDefault()).toInstant());
                updateFilter(destinationsComboBox.getValue());
            }else{
                before = null;
                updateFilter(destinationsComboBox.getValue());
            }

        });

        minPriceFld.textProperty().addListener((som, oldV, newV)->{
            try{
                min = Double.parseDouble(newV);
                updateFilter(destinationsComboBox.getValue());
            } catch (NumberFormatException e) {
                min = -1;
            }
        });

        maxPriceFld.textProperty().addListener((som, oldV, newV)->{
            try{
                max = Double.parseDouble(newV);
                updateFilter(destinationsComboBox.getValue());
            } catch (NumberFormatException e) {
                max = Double.MAX_VALUE;
            }
        });

        showBookingsBtn.managedProperty().bind(showBookingsBtn.visibleProperty());
        showBrowseBtn.managedProperty().bind(showBrowseBtn.visibleProperty());

        browsePane.managedProperty().bind(browsePane.visibleProperty());
        bookingsPane.managedProperty().bind(bookingsPane.visibleProperty());

        toBrowsing();
    }



    @FXML
    void logOut() {
        currentUser = null;

        browseBox.getChildren().clear();
        bookingsBox.getChildren().clear();

        WrapperController.instance.toLogInView();
    }

    @FXML
    void toBookings() {
        welcomeLbl.setText("Your Bookings");

        showBookingsBtn.setVisible(false);
        bookingsPane.setVisible(true);

        showBrowseBtn.setVisible(true);
        browsePane.setVisible(false);
    }

    @FXML
    void toBrowsing() {
        welcomeLbl.setText("Browsing Vacation Packages");

        showBookingsBtn.setVisible(true);
        bookingsPane.setVisible(false);

        showBrowseBtn.setVisible(false);
        browsePane.setVisible(true);
    }

    public void setUp(User user){
        this.currentUser = user;
        welcomeLbl.setText("Hi, " + user.getFirstName() + "!");
        minPriceFld.clear();
        maxPriceFld.clear();
        afterDate.setValue(null);
        beforeDate.setValue(null);
        destinationsComboBox.setValue(null);
        updateFilter(null);
    }

    private void updateFilter(Destination newV){
        List<VacationPackage> filteredPackages;
        List<VacationPackage> filteredBookings;
        if (newV == null){
            filteredPackages = filterPackages(UserService.getAllPackages());
            filteredBookings = filterPackages(currentUser.getBookings());

        } else{
            filteredPackages = filterPackages(newV.getPackages());
            filteredBookings = filterPackages(currentUser.getBookings().stream().filter((e) -> e.getDestination().equals(newV)).toList());

        }
        updateBoxes(filteredBookings, filteredPackages);
    }

    private List<VacationPackage> filterPackages(List<VacationPackage> packages){
        return UserService.getFilteredPackages(
                packages,
                after,
                before,
                min,
                max);
    }

    private void updateBoxes(List<VacationPackage> bookings, List<VacationPackage> packages){
        updateBookingBox(bookings);
        updateBrowsingBox(packages);
    }

    private void updateBookingBox(List<VacationPackage> bookings){
        List<Parent> bookingLayouts = new ArrayList<>();
        for(VacationPackage p: bookings)
            bookingLayouts.add(generateBrowseVacationPackageBox(p));

        bookingsBox.getChildren().clear();
        bookingsBox.getChildren().addAll(bookingLayouts);
    }

    private void updateBrowsingBox(List<VacationPackage> packages){
        List<Parent> browseLayouts = new ArrayList<>();
        for(VacationPackage p: packages)
            browseLayouts.add(generateBrowseVacationPackageBox(p));

        browseBox.getChildren().clear();
        browseBox.getChildren().addAll(browseLayouts);
    }

    public Parent generateBrowseVacationPackageBox(VacationPackage vacationPackage){

        VBox details = new VBox(10);
        Label title = new Label(vacationPackage.getName());
//        Label agencyLbl = new Label("Provided by: " + vacationPackage.getAgency().getName());
        title.setFont(new Font("System Default", 24));
        Label destinationName = new Label("Price: " + vacationPackage.getPrice()
                + "| " + vacationPackage.getDestination().toString());
        Label datesLbl = new Label(Style.stringify(vacationPackage.getPeriodStart()) + " - " + Style.stringify(vacationPackage.getPeriodEnd()));
        Label description = new Label(wrap(vacationPackage.getDetails(),100));
        Button bookBtn = new Button("Book");
        bookBtn.setOnAction((e)->{
            if (!currentUser.getBookings().contains(vacationPackage)){
                currentUser.getBookings().add(vacationPackage);
                UserService.update(currentUser);

                updateBookingBox(filterPackages(currentUser.getBookings()));
            }
        });
        bookBtn.setPrefHeight(35);
        bookBtn.setPrefWidth(80);

        details.getChildren().addAll(title,/*agencyLbl,*/destinationName,datesLbl, description);
        details.setPrefWidth(1200);

        HBox wrapper = new HBox();
        wrapper.getChildren().addAll(details,bookBtn);

        wrapper.setBackground(new Background(new BackgroundFill(Paint.valueOf("FFFFFF"),new CornerRadii(8), Insets.EMPTY)));
//        wrapper.setMaxHeight(Double.MAX_VALUE);
        wrapper.setMaxWidth(Double.MAX_VALUE);
        wrapper.prefHeight(Region.USE_COMPUTED_SIZE);
        wrapper.prefWidth(Region.USE_COMPUTED_SIZE);
        wrapper.setAlignment(Pos.CENTER);
        wrapper.setPadding(new Insets(10));

        return wrapper;
    }

    private String wrap(String input, int n){
        StringBuilder sb = new StringBuilder();

        int c = 0;
        while (c+n < input.length()){
            String sub = input.substring(c, c+n);
            if(sub.contains("\n")){
                int i =  sub.indexOf("\n");
                sub = sub.substring(0, i+1);
                sb.append(sub);
                c += i+1;
            }else{
                sb.append(sub).append("\n");
                c += n;
            }
        }sb.append(input.substring(c, input.length()-1));

        return sb.toString();
    }
}
