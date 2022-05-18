package presentation.controller;

import Exceptions.InvalidRegisterInputException;
import bussiness.AgencyService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import model.Agency;
import model.Destination;
import model.PackageStatus;
import model.VacationPackage;
import presentation.Style;

import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AgencyController implements Initializable {


    private Agency agency = null;

    @FXML private Button addDestinationBtn;
    @FXML private Button logOutBtn;
    @FXML private TextField destinationNameFLd;
    @FXML private TextField destinationCountryFld;
    @FXML private Label welcomeLbl;
    @FXML private ScrollPane destinationPane;
    @FXML private VBox destinationBox;
    @FXML private VBox addLocationBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addLocationBox.managedProperty().bind(addLocationBox.visibleProperty());
        addLocationBox.setVisible(false);


    }

    @FXML void addDestination()  {

        if (destinationNameFLd.getText().equals("")) {
            Style.setBackgroundRed(destinationNameFLd);
            return;
        }
        if(destinationCountryFld.getText().equals("")){
            Style.setBackgroundRed(destinationCountryFld);
            return;
        }

        Destination destination = AgencyService.addDestination(destinationNameFLd.getText(),destinationCountryFld.getText());
        Style.setBackgroundWhite(destinationNameFLd);
        Style.setBackgroundWhite(destinationCountryFld);

        destinationBox.getChildren().add(generateDestinationBox(destination));

        addLocationBox.setVisible(false);
        destinationPane.setPrefHeight(605.0);
    }

    @FXML
    void logOut() {
        destinationNameFLd.clear();
        destinationCountryFld.clear();
        addLocationBox.getChildren().clear();
        agency = null;
        WrapperController.instance.toLogInView();
    }

    @FXML
    void openDestinationBox() {
        addLocationBox.setVisible(true);
        destinationPane.setPrefHeight(470.0);
    }

    public void setUp(Agency agency){

        destinationBox.getChildren().clear();

        List<Destination> destinations = AgencyService.getAllDestinations();
        List<Parent> _destinations = new ArrayList<>();

        for(Destination d: destinations){
            _destinations.add(generateDestinationBox(d));
        }

        this.destinationBox.getChildren().addAll(_destinations);
        this.agency = agency;
    }

    private Parent generateDestinationBox(Destination destination){
        VBox wrapper = new VBox(10);

        /** The Box that has all the package inputs */
        HBox addBox = new HBox(10);
        addBox.managedProperty().bind(addBox.visibleProperty());
        addBox.setVisible(false);

        /***************************** HEADER *************************************/
        Label destinationName = new Label(destination.getName());
        destinationName.setFont(new Font("System Default", 24));

        Label country = new Label(destination.getCountryName());

        VBox title = new VBox(10);
        title.getChildren().addAll(destinationName, country);
        title.setPrefWidth(1200);
        title.setAlignment(Pos.CENTER_LEFT);

        /* Buttons */
        Button expandBtn = new Button("Expand");
        Button collapseBtn = new Button("Collapse");
        Button deleteBtn = new Button("Delete Destination");
        Button showAddBtn = new Button("Add Package");

        expandBtn.managedProperty().bind(expandBtn.visibleProperty());
        collapseBtn.managedProperty().bind(collapseBtn.visibleProperty());
        collapseBtn.setVisible(false);

        expandBtn.setOnAction((e)->{
            List<VacationPackage> packages = AgencyService.getPackages(agency, destination);
            if(packages.isEmpty()){
                wrapper.getChildren().add(new Label("You have no packages for this Destination"));
            } else {
                List<Parent> packageBoxes = new ArrayList<>();
                for(VacationPackage p: packages){
                    packageBoxes.add(generateVacationPackageBox(wrapper, p));
                }
                wrapper.getChildren().addAll(packageBoxes);
            }
            collapseBtn.setVisible(true);
            expandBtn.setVisible(false);
        });

        collapseBtn.setOnAction((e)->{
            wrapper.getChildren().remove(1,wrapper.getChildren().size());
            collapseBtn.setVisible(false);
            expandBtn.setVisible(true);
        });

        deleteBtn.setOnAction((e)->{
//            AgencyService.removeAllPackages(agency, destination);
            destinationBox.getChildren().remove(wrapper);
            AgencyService.delete(agency, destination);
            if(wrapper.getChildren().size() > 1) { //is expanded
                wrapper.getChildren().remove(1,wrapper.getChildren().size());
                wrapper.getChildren().add(new Label("You have no packages for this Destination"));
            }
        });

        showAddBtn.setOnAction((e)->{
            addBox.setVisible(true);
        });

        expandBtn.setPrefHeight(35);        expandBtn.setPrefWidth(150);
        collapseBtn.setPrefHeight(35);      collapseBtn.setPrefWidth(150);
        deleteBtn.setPrefHeight(35);        deleteBtn.setPrefWidth(150);
        showAddBtn.setPrefHeight(35);       showAddBtn.setPrefWidth(150);

        VBox buttons = new VBox(10);
        buttons.getChildren().addAll(expandBtn,collapseBtn,deleteBtn, showAddBtn);

        HBox header = new HBox(10);
        header.getChildren().addAll(title,buttons);

        /********************************* ADD PACKAGE *************************************/

        Label nameLbl = new Label("Name:");
        TextField nameFld = new TextField();
        HBox nameBox = new HBox();
        nameBox.getChildren().addAll(nameLbl, nameFld);

        nameLbl.setPrefWidth(50);           nameLbl.setPrefHeight(35.0);
        nameFld.setPrefWidth(1150);         nameFld.setPrefHeight(35.0);

        Label priceLbl = new Label("Price:");
        Label maxBooksLbl = new Label("Max Books:");
        TextField priceFld = new TextField();
        TextField maxBooksFld = new TextField();

        priceLbl.setPrefWidth(40);          priceLbl.setPrefHeight(35.0);
        maxBooksFld.setPrefWidth(40);       maxBooksLbl.setPrefHeight(35.0);
        priceFld.setPrefWidth(545.0);       priceFld.setPrefHeight(35.0);
        maxBooksFld.setPrefWidth(525.0);    maxBooksFld.setPrefHeight(35.0);

        HBox numericBox = new HBox(10);
        numericBox.getChildren().addAll(priceLbl,priceFld,maxBooksLbl,maxBooksFld);

        DatePicker beginDate = new DatePicker();    beginDate.setPromptText("Start Date");
        DatePicker endDate = new DatePicker();      endDate.setPromptText("End Date");

        beginDate.setPrefWidth(595.0);      beginDate.setPrefHeight(35.0);
        endDate.setPrefWidth(595.0);        endDate.setPrefHeight(35.0);

        HBox dateBox = new HBox(10);
        dateBox.getChildren().addAll(beginDate,endDate);

        TextArea detailsFld = new TextArea();
        detailsFld.setPromptText("Enter Package Details Here");

        VBox addFieldsBox = new VBox(10);
        addFieldsBox.getChildren().addAll(nameBox,numericBox,dateBox, detailsFld);

        Button addBtn = new Button("Add");
        addBtn.setPrefHeight(35);
        addBtn.setPrefWidth(80);
        addBtn.setOnAction((e)->{
            try {
                AgencyService.addPackage(agency,
                        nameFld.getText(),
                        priceFld.getText(),
                        beginDate.getValue(),
                        endDate.getValue(),
                        detailsFld.getText(),
                        maxBooksFld.getText(),
                        destination);
            } catch (InvalidRegisterInputException ex) {
                Style.setBackgroundRed(nameFld);
                Style.setBackgroundRed(priceFld);
                Style.setBackgroundRed(beginDate);
                Style.setBackgroundRed(endDate);
                Style.setBackgroundRed(detailsFld);
                Style.setBackgroundRed(maxBooksFld);
            }
            nameFld.clear();
            priceFld.clear();
            maxBooksFld.clear();
            beginDate.setValue(null);
            endDate.setValue(null);
            detailsFld.clear();
            addBox.setVisible(false);
            Style.setBackgroundWhite(nameFld);
            Style.setBackgroundWhite(priceFld);
            Style.setBackgroundWhite(beginDate);
            Style.setBackgroundWhite(endDate);
            Style.setBackgroundWhite(detailsFld);
            Style.setBackgroundWhite(maxBooksFld);
        });

        Button hideBtn = new Button("Close");
        hideBtn.setPrefHeight(35);
        hideBtn.setPrefWidth(80);
        hideBtn.setOnAction((e)->{
            nameFld.clear();
            priceFld.clear();
            maxBooksFld.clear();
            beginDate.setValue(null);
            endDate.setValue(null);
            detailsFld.clear();
            addBox.setVisible(false);
            Style.setBackgroundWhite(nameFld);
            Style.setBackgroundWhite(priceFld);
            Style.setBackgroundWhite(beginDate);
            Style.setBackgroundWhite(endDate);
            Style.setBackgroundWhite(detailsFld);
            Style.setBackgroundWhite(maxBooksFld);
        });

        addBtn.setPrefWidth(150.0);     addBtn.setPrefHeight(35.0);
        hideBtn.setPrefWidth(150.0);    hideBtn.setPrefHeight(35.0);

        VBox addButtonsBox = new VBox(10);
        addButtonsBox.getChildren().addAll(addBtn, hideBtn);

        addBox.getChildren().addAll(addFieldsBox, addButtonsBox);

        /*********************************** WRAPPER ***************************************/

        VBox headerAndAdd = new VBox(10);
        headerAndAdd.getChildren().addAll(header, addBox);

        wrapper.getChildren().addAll(headerAndAdd);

        wrapper.setBackground(new Background(new BackgroundFill(Paint.valueOf("FFFFFF"),new CornerRadii(8), Insets.EMPTY)));
//        wrapper.setMaxHeight(Double.MAX_VALUE);
        wrapper.setMaxWidth(Double.MAX_VALUE);
        wrapper.prefHeight(Region.USE_COMPUTED_SIZE);
        wrapper.prefWidth(Region.USE_COMPUTED_SIZE);
        wrapper.setAlignment(Pos.CENTER);
        wrapper.setPadding(new Insets(10));

        return wrapper;
    }

    private Parent generateVacationPackageBox(VBox wrapper, VacationPackage vacationPackage){

        VBox details = new VBox(10);
        HBox _wrapper = new HBox();
        Label titleLbl = new Label(vacationPackage.getName());
        titleLbl.setFont(new Font("System Default", 24));
        TextField titleFld = new TextField();
        titleFld.setText(vacationPackage.getName());
        titleLbl.managedProperty().bind(titleLbl.visibleProperty());
        titleFld.managedProperty().bind(titleFld.visibleProperty());
        titleFld.setPrefHeight(35.0); titleFld.setPrefWidth(500);
        titleFld.setPromptText("Title/Name of the package");
        titleFld.setVisible(false);

        Label statusLbl = PackageStatus.labelify(PackageStatus.getStatus(vacationPackage));
        TextField maxBookingsFld = new TextField();
        maxBookingsFld.setPromptText("Maximum number of bookings");
        maxBookingsFld.setPrefWidth(50); maxBookingsFld.setPrefHeight(35);
        maxBookingsFld.managedProperty().bind(maxBookingsFld.visibleProperty());
        statusLbl.managedProperty().bind(statusLbl.visibleProperty());
        maxBookingsFld.setVisible(false); maxBookingsFld.setText(vacationPackage.getMaxBooks()+"");

        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.getChildren().addAll(titleLbl,titleFld,statusLbl,maxBookingsFld);

        Label priceLbl = new Label("Price: " + vacationPackage.getPrice());
        TextField priceFld = new TextField();
        priceFld.setPrefHeight(35); priceFld.setPrefWidth(50); priceFld.setPromptText("Price"); priceFld.setText(vacationPackage.getPrice()+"");
        priceLbl.managedProperty().bind(priceLbl.visibleProperty());
        priceFld.managedProperty().bind(priceFld.visibleProperty());
        priceFld.setVisible(false);

        Label destinationLbl = new Label("| " + vacationPackage.getDestination().toString());
        HBox priceDestinationBox = new HBox(5);
        priceDestinationBox.getChildren().addAll(priceLbl,priceFld,destinationLbl);
        priceDestinationBox.setAlignment(Pos.CENTER_LEFT);

        Label datesLbl = new Label(Style.stringify(vacationPackage.getPeriodStart()) + " - " + Style.stringify(vacationPackage.getPeriodEnd()));
        DatePicker beginDate = new DatePicker();    beginDate.setPromptText("Start Date");
        DatePicker endDate = new DatePicker();      endDate.setPromptText("End Date");
        beginDate.managedProperty().bind(beginDate.visibleProperty());
        endDate.managedProperty().bind(endDate.visibleProperty());
        datesLbl.managedProperty().bind(datesLbl.visibleProperty());
        beginDate.setPrefWidth(270.0);      beginDate.setPrefHeight(35.0);
        endDate.setPrefWidth(280.0);        endDate.setPrefHeight(35.0);
        beginDate.setVisible(false);        beginDate.setValue(vacationPackage.getPeriodStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        endDate.setVisible(false);          endDate.setValue(vacationPackage.getPeriodEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        HBox dateBox = new HBox(10);
        dateBox.getChildren().addAll(beginDate,endDate,datesLbl);

        Label detailsLbl = new Label(wrap(vacationPackage.getDetails(),100));
        TextArea detailsFld = new TextArea();
        detailsFld.setPrefWidth(560);
        detailsFld.setPromptText("Enter Package Details Here");
        detailsLbl.managedProperty().bind(detailsLbl.visibleProperty());
        detailsFld.managedProperty().bind(detailsFld.visibleProperty());
        detailsFld.setText(vacationPackage.getDetails());
        detailsFld.setVisible(false);

        HBox detailsBox = new HBox();
        detailsBox.getChildren().addAll(detailsFld,detailsLbl);

        Button deleteBtn = new Button("Delete");
        Button editBtn = new Button("Edit");
        Button applyBtn = new Button("Apply");
        deleteBtn.setOnAction((e)->{
            wrapper.getChildren().remove(_wrapper);
            AgencyService.delete(agency, vacationPackage);
        });
        editBtn.setOnAction((e)->{
            titleFld.setVisible(true);
            titleLbl.setVisible(false);

            priceLbl.setVisible(false);
            priceFld.setVisible(true);

            datesLbl.setVisible(false);
            beginDate.setVisible(true);
            endDate.setVisible(true);

            detailsFld.setVisible(true);
            detailsLbl.setVisible(false);

            statusLbl.setVisible(false);
            maxBookingsFld.setVisible(true);

            applyBtn.setVisible(true);
            editBtn.setVisible(false);
        });
        editBtn.managedProperty().bind(editBtn.visibleProperty());

        applyBtn.setOnAction((e)->{
            try {
                AgencyService.updatePackage(
                        vacationPackage,
                        titleFld.getText(),
                        priceFld.getText(),
                        beginDate.getValue(),
                        endDate.getValue(),
                        detailsFld.getText(),
                        maxBookingsFld.getText()
                );

                titleLbl.setText(titleFld.getText());
                priceLbl.setText("Price: " + priceFld);
                datesLbl.setText(Style.stringify(vacationPackage.getPeriodStart()) + " - " + Style.stringify(vacationPackage.getPeriodEnd()));
                detailsLbl.setText(wrap(vacationPackage.getDetails(),100));
                statusLbl.setText(PackageStatus.stringify(PackageStatus.getStatus(vacationPackage)));
                statusLbl.setTextFill(PackageStatus.getPaint(PackageStatus.getStatus(vacationPackage)));
            } catch (InvalidRegisterInputException ex) {
                ex.printStackTrace();
            }

            titleFld.setVisible(false);
            titleLbl.setVisible(true);

            priceLbl.setVisible(true);
            priceFld.setVisible(false);

            datesLbl.setVisible(true);
            beginDate.setVisible(false);
            endDate.setVisible(false);

            detailsFld.setVisible(false);
            detailsLbl.setVisible(true);

            statusLbl.setVisible(true);
            maxBookingsFld.setVisible(false);

            applyBtn.setVisible(false);
            editBtn.setVisible(true);
        });
        applyBtn.managedProperty().bind(applyBtn.visibleProperty());
        applyBtn.setVisible(false);

        deleteBtn.setPrefHeight(35);    deleteBtn.setPrefWidth(80);
        editBtn.setPrefHeight(35);      editBtn.setPrefWidth(80);
        applyBtn.setPrefHeight(35);     applyBtn.setPrefWidth(80);

        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(deleteBtn,editBtn,applyBtn);

        details.getChildren().addAll(titleBox, priceDestinationBox, dateBox, detailsBox);
        details.setPrefWidth(1150);

        _wrapper.getChildren().addAll(details,buttonBox);

        _wrapper.setBackground(new Background(new BackgroundFill(Paint.valueOf("FFFFFF"),new CornerRadii(8), Insets.EMPTY)));
//        wrapper.setMaxHeight(Double.MAX_VALUE);
        _wrapper.setMaxWidth(Double.MAX_VALUE);
        _wrapper.prefHeight(Region.USE_COMPUTED_SIZE);
        _wrapper.prefWidth(Region.USE_COMPUTED_SIZE);
        _wrapper.setAlignment(Pos.CENTER);
        _wrapper.setPadding(new Insets(10));

        return _wrapper;
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
