package com.ME.OOP2;

import com.ME.OOP2.entity.Car;
import com.ME.OOP2.entity.Member;
import com.ME.OOP2.entity.Rental;
import com.ME.OOP2.entity.SportsCar;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.geometry.Insets;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {

    private Label labelResult = new Label();
    private Label labelRevenue = new Label();
    private double totalRevenue = 0;
    private String hp = "Hästkrafter";
    private String price = "Pris";
    private String lvl = "Användarnivå 1-2";

    @Override
    public void start(Stage stage) throws Exception {

        labelResult.setPadding(new Insets(10,10,10,10));
        labelResult.setLineSpacing(10);

        Member member = new Member();
        MemberRegistry mReg = new MemberRegistry();
        MembershipService mSer = new MembershipService();
        Inventory inv = new Inventory();
        RentalService rSer = new RentalService();
        Validate val = new Validate();

        TabPane root = new TabPane();
        BorderPane borderPaneM = new BorderPane();

        BorderPane borderPaneC = new BorderPane();

        BorderPane borderPaneR = new BorderPane();

        Tab tab1 = new Tab("Bilar");
        tab1.setContent(borderPaneC);
        tab1.setClosable(false);

        Tab tab2 = new Tab("Hyr bil");
        tab2.setContent(borderPaneR);
        tab2.setClosable(false);

        Tab tab3 = new Tab("Medlemmar");
        tab3.setContent(borderPaneM);
        tab3.setClosable(false);

        Tab tab4 = new Tab("Intäkter");


        //labelRevenue.setPadding(new Insets(10));
        Button getRevenueButton = new Button("Visa totala intäkter");
        getRevenueButton.setOnAction(e -> {
            rSer.showRevenue(totalRevenue, labelRevenue);
        });


        VBox vBoxRevenue = new VBox();
        vBoxRevenue.setSpacing(10);
        vBoxRevenue.setPadding(new Insets(10));
        vBoxRevenue.getChildren().addAll(getRevenueButton, labelRevenue);

        tab4.setContent(vBoxRevenue);
        tab4.setClosable(false);

        root.getTabs().add(tab1);
        root.getTabs().add(tab2);
        root.getTabs().add(tab3);
        root.getTabs().add(tab4);


        //Tableview tabell som visar medlemmar

        TableColumn<Member, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        idColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Member, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Member, Integer> event) {
                Member member = event.getRowValue();
                member.setId(event.getNewValue());
            }
        });

        TableColumn<Member, String> nameColumn = new TableColumn<Member, String>("Namn");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Member, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Member, String> event) {
                Member member = event.getRowValue();
                member.setName(event.getNewValue());
            }
        });

        TableColumn<Member, Integer> lvlColumn = new TableColumn<>("Medlemsnivå");
        lvlColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        lvlColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        lvlColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Member, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Member, Integer> event) {
                Member member = event.getRowValue();
                member.setLevel(event.getNewValue());
            }
        });

        TableColumn<Member, String> historyColumn = new TableColumn<>("Historia");
        historyColumn.setMinWidth(200);
        historyColumn.setCellValueFactory(new PropertyValueFactory<>("history"));
        historyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        historyColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Member, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Member, String> event) {
                Member member = event.getRowValue();
                member.setHistory(event.getNewValue());
            }
        });

        //TextField för att söka medlem
        TextField nameInput;
        TextField lvlInput;
        TextField searchName = new TextField();
        searchName.setPromptText("Sök medlem, ange användarnamn");
        searchName.setMinWidth(200);

        //Funktionalitet för att lägga till medlemmar

        nameInput = new TextField();
        nameInput.setPromptText("Namn");
        nameInput.setMinWidth(100);

        lvlInput = new TextField();
        lvlInput.setPromptText("Användarnivå 1-2");
        lvlInput.setMinWidth(200);

        TableView<Member> mTable = new TableView<>();
        mTable.setEditable(true);
        mTable.setItems(mReg.getMember());
        mTable.getColumns().addAll(idColumn, nameColumn, lvlColumn, historyColumn);

        //Knappar
        Button addButton = new Button("Lägg till medlem");
        addButton.setOnAction(e -> {
            val.isInt(lvlInput, lvl);
            mSer.addButtonClicked(nameInput, lvlInput, mTable, labelResult);

        });

        Button deleteButton = new Button("Ta bort");
        deleteButton.setOnAction(e -> mSer.deleteButtonClicked(mTable));

        Button searchButton = new Button("Sök medlem");
        searchButton.setOnAction(e -> mSer.searchMember(mReg.membersList, searchName.getText(), labelResult));

        //Layout Members, Hbox inuti en Vbox i botten av borderpane. Center visar tabell.
        HBox hBox = new HBox();
        hBox.setPadding(new javafx.geometry.Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(nameInput, lvlInput, addButton, deleteButton);

        HBox hBox2 = new HBox();
        hBox2.setPadding(new javafx.geometry.Insets(10,10,10,10));
        hBox2.setSpacing(10);
        hBox2.getChildren().addAll(searchName, searchButton);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().addAll(hBox, hBox2, labelResult);

        borderPaneM.setCenter(mTable);
        borderPaneM.setBottom(vBox);

        //Tableview för bilar
        TableView<Car> cTable = new TableView<>();
        cTable.setEditable(true);

        TableColumn<Car, Integer> idColumnCar = new TableColumn<>("Bilnummer");
        idColumnCar.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumnCar.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        idColumnCar.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Car, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Car, Integer> event) {
                Car car = event.getRowValue();
                car.setId(event.getNewValue());
            }
        });

        TableColumn<Car, Double> priceColumnCar = new TableColumn<>("Pris/timme");
        priceColumnCar.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumnCar.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        priceColumnCar.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Car, Double>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Car, Double> event) {
                Car car = event.getRowValue();
                car.setPrice(event.getNewValue());
            }
        });

        TableColumn<Car, String> brandColumnCar = new TableColumn<>("Bilmärke");
        brandColumnCar.setCellValueFactory(new PropertyValueFactory<>("brand"));
        brandColumnCar.setCellFactory(TextFieldTableCell.forTableColumn());
        brandColumnCar.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Car, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Car, String> event) {
                Car car = event.getRowValue();
                car.setBrand(event.getNewValue());
            }
        });

        TableColumn<Car, String> modelColumnCar = new TableColumn<>("Modell:");
        modelColumnCar.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelColumnCar.setCellFactory(TextFieldTableCell.forTableColumn());
        modelColumnCar.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Car, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Car, String> event) {
                Car car = event.getRowValue();
                car.setModel(event.getNewValue());
            }
        });

        TableColumn<Car, String> yearColumnCar = new TableColumn<>("Årsmodell:");
        yearColumnCar.setCellValueFactory(new PropertyValueFactory<>("year"));
        yearColumnCar.setCellFactory(TextFieldTableCell.forTableColumn());
        yearColumnCar.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Car, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Car, String> event) {
                Car car = event.getRowValue();
                car.setYear(event.getNewValue());
            }
        });

        TableColumn<Car, String> colorColumnCar = new TableColumn<>("Färg:");
        colorColumnCar.setCellValueFactory(new PropertyValueFactory<>("color"));
        colorColumnCar.setCellFactory(TextFieldTableCell.forTableColumn());
        colorColumnCar.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Car, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Car, String> event) {
                Car car = event.getRowValue();
                car.setColor(event.getNewValue());
            }
        });

        TableColumn<Car, String> descriptionColumnCar = new TableColumn<>("Beskrivning:");
        descriptionColumnCar.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumnCar.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumnCar.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Car, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Car, String> event) {
                Car car = event.getRowValue();
                car.setDescription(event.getNewValue());
            }
        });

        cTable.setItems(inv.runJsonCars());
        cTable.getColumns().addAll(idColumnCar, priceColumnCar, brandColumnCar, modelColumnCar, yearColumnCar, colorColumnCar, descriptionColumnCar);

        TableView<SportsCar> cTable2 = new TableView<>();
        cTable2.setEditable(true);

        TableColumn<SportsCar, Integer> idColumnSportsCar = new TableColumn<>("Bilnummer");
        idColumnSportsCar.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumnSportsCar.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        idColumnSportsCar.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SportsCar, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<SportsCar, Integer> event) {
                SportsCar car = event.getRowValue();
                car.setId(event.getNewValue());
            }
        });

        TableColumn<SportsCar, Double> priceColumnSportsCar = new TableColumn<>("Pris/timme");
        priceColumnSportsCar.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumnSportsCar.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        priceColumnSportsCar.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SportsCar, Double>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<SportsCar, Double> event) {
                SportsCar car = event.getRowValue();
                car.setPrice(event.getNewValue());
            }
        });

        TableColumn<SportsCar, String> brandColumnSportsCar = new TableColumn<>("Bilmärke");
        brandColumnSportsCar.setCellValueFactory(new PropertyValueFactory<>("brand"));
        brandColumnSportsCar.setCellFactory(TextFieldTableCell.forTableColumn());
        brandColumnSportsCar.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SportsCar, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<SportsCar, String> event) {
                SportsCar car = event.getRowValue();
                car.setBrand(event.getNewValue());
            }
        });

        TableColumn<SportsCar, String> modelColumnSportsCar = new TableColumn<>("Modell:");
        modelColumnSportsCar.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelColumnSportsCar.setCellFactory(TextFieldTableCell.forTableColumn());
        modelColumnSportsCar.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SportsCar, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<SportsCar, String> event) {
                SportsCar car = event.getRowValue();
                car.setModel(event.getNewValue());
            }
        });

        TableColumn<SportsCar, String> yearColumnSportsCar = new TableColumn<>("Årsmodell:");
        yearColumnSportsCar.setCellValueFactory(new PropertyValueFactory<>("year"));
        yearColumnSportsCar.setCellFactory(TextFieldTableCell.forTableColumn());
        yearColumnSportsCar.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SportsCar, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<SportsCar, String> event) {
                SportsCar car = event.getRowValue();
                car.setYear(event.getNewValue());
            }
        });

        TableColumn<SportsCar, String> colorColumnSportsCar = new TableColumn<>("Färg:");
        colorColumnSportsCar.setCellValueFactory(new PropertyValueFactory<>("color"));
        colorColumnSportsCar.setCellFactory(TextFieldTableCell.forTableColumn());
        colorColumnSportsCar.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SportsCar, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<SportsCar, String> event) {
                SportsCar car = event.getRowValue();
                car.setColor(event.getNewValue());
            }
        });

        TableColumn<SportsCar, String> descriptionColumnSportsCar = new TableColumn<>("Beskrivning:");
        descriptionColumnSportsCar.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumnSportsCar.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumnSportsCar.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SportsCar, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<SportsCar, String> event) {
                SportsCar car = event.getRowValue();
                car.setDescription(event.getNewValue());
            }
        });

        TableColumn<SportsCar, Boolean> sportSeatsColumnSportsCar = new TableColumn<>("Sportstolar:");
        sportSeatsColumnSportsCar.setCellValueFactory(new PropertyValueFactory<>("sportSeats"));
        sportSeatsColumnSportsCar.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        sportSeatsColumnSportsCar.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SportsCar, Boolean>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<SportsCar, Boolean> event) {
                SportsCar car = event.getRowValue();
                car.setSportSeats(event.getNewValue());
            }
        });

        TableColumn<SportsCar, Integer> hpColumnSportsCar = new TableColumn<>("Hästkrafter:");
        hpColumnSportsCar.setCellValueFactory(new PropertyValueFactory<>("hp"));
        hpColumnSportsCar.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        hpColumnSportsCar.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SportsCar, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<SportsCar, Integer> event) {
                SportsCar car = event.getRowValue();
                car.setHP(event.getNewValue());
            }
        });

        cTable2.setItems(inv.runJsonSportsCars());
        cTable2.getColumns().addAll(idColumnSportsCar, priceColumnSportsCar, brandColumnSportsCar, modelColumnSportsCar, yearColumnSportsCar, colorColumnSportsCar, hpColumnSportsCar, sportSeatsColumnSportsCar, descriptionColumnSportsCar);


        //Layout för Tab1 Bilar

        TextField priceInput = new TextField();
        priceInput.setPromptText("Pris");
        priceInput.setMinWidth(200);
        TextField descriptionInput = new TextField();
        descriptionInput.setPromptText("Beskrivning");
        descriptionInput.setMinWidth(800);
        TextField brandInput = new TextField();
        brandInput.setPromptText("Märke");
        TextField modelInput = new TextField();
        modelInput.setPromptText("Modell");
        TextField yearInput = new TextField();
        yearInput.setPromptText("Årsmodell");
        TextField colorInput = new TextField();
        colorInput.setPromptText("Färg");

        //Sportbil
        TextField sportSeatsInput = new TextField();
        sportSeatsInput.setPromptText("Sportstolar(true eller false)");
        TextField hpInput = new TextField();
        hpInput.setMinWidth(200);
        hpInput.setPromptText("Hästkrafter");
        //String hp = "Hästkrafter";


        Button addSportsCarButton = new Button("Lägg till sportbil");
        addSportsCarButton.setOnAction(e -> {
            val.isInt(hpInput, hp);
            val.isDouble(priceInput, price);
            inv.addSportsCar(priceInput, descriptionInput, brandInput, modelInput, yearInput, colorInput, sportSeatsInput, hpInput, cTable2);
        });
        //Slut sportbil

        Button addCarButton = new Button("Lägg till bil");
        addCarButton.setOnAction(e -> {
            val.isDouble(priceInput, price);
            inv.addCar(priceInput, descriptionInput, brandInput, modelInput, yearInput, colorInput, cTable);
        });

        HBox hBoxC = new HBox();
        hBoxC.setPadding(new javafx.geometry.Insets(10,10,10,10));
        hBoxC.setSpacing(10);
        hBoxC.getChildren().addAll(priceInput, brandInput, modelInput, yearInput, colorInput);

        HBox hBoxC2 = new HBox();
        hBoxC2.setPadding(new javafx.geometry.Insets(10,10,10,10));
        hBoxC2.setSpacing(10);
        hBoxC2.getChildren().addAll(descriptionInput, addCarButton);

        HBox hBoxC3 = new HBox();
        hBoxC3.setPadding(new javafx.geometry.Insets(10,10,10,10));
        hBoxC3.setSpacing(10);
        hBoxC3.getChildren().addAll(sportSeatsInput, hpInput, addSportsCarButton);

        VBox vBoxCars = new VBox();
        vBoxCars.setSpacing(10);
        vBoxCars.getChildren().addAll(hBoxC, hBoxC2, hBoxC3);

        VBox vBoxCarTableViews = new VBox();
        vBoxCars.setSpacing(10);
        vBoxCars.getChildren().addAll(cTable, cTable2);

        borderPaneC.setTop(cTable);
        borderPaneC.setCenter(cTable2);
        borderPaneC.setBottom(vBoxCars);



        //Tableview som visar uthyrningar

        TableColumn<Rental, Integer> idColumnR = new TableColumn<>("Hyrnummer");
        idColumnR.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumnR.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        idColumnR.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Rental, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Rental, Integer> event) {
                Rental rental = event.getRowValue();
                rental.setId(event.getNewValue());
            }
        });

        TableColumn<Rental, String> nameColumnR = new TableColumn<Rental, String>("Namn");
        //nameColumnR.setMinWidth(200);
        nameColumnR.setCellValueFactory(new PropertyValueFactory<Rental, String>("name"));
        nameColumnR.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumnR.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Rental, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Rental, String> event) {
                Rental rental = event.getRowValue();
                rental.setName(event.getNewValue());
            }
        });

        TableColumn<Rental, Integer> carColumnR = new TableColumn<>("Bilnummer");
        carColumnR.setCellValueFactory(new PropertyValueFactory<>("car"));
        carColumnR.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        carColumnR.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Rental, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Rental, Integer> event) {
                Rental rental = event.getRowValue();
                rental.setCar(event.getNewValue());
            }
        });

        TableColumn<Rental, String> startColumnR = new TableColumn<>("Starttid");
        //startColumnR.setMinWidth(200);
        startColumnR.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        startColumnR.setCellFactory(TextFieldTableCell.forTableColumn());
        startColumnR.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Rental, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Rental, String> event) {
                Rental rental = event.getRowValue();
                rental.setStartTime(event.getNewValue());
            }
        });

        TableColumn<Rental, String> endColumnR = new TableColumn<>("Sluttid");
        //endColumnR.setMinWidth(200);
        endColumnR.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        endColumnR.setCellFactory(TextFieldTableCell.forTableColumn());
        endColumnR.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Rental, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Rental, String> event) {
                Rental rental = event.getRowValue();
                rental.setEndTime(event.getNewValue());
            }
        });

        TableColumn<Rental, Double> priceColumnR = new TableColumn<>("Pris/timme");
        priceColumnR.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumnR.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        priceColumnR.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Rental, Double>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Rental, Double> event) {
                Rental rental = event.getRowValue();
                rental.setPrice(event.getNewValue());
            }
        });


        TableColumn<Rental, Double> totalPriceColumnR = new TableColumn<>("Totalpris");
        totalPriceColumnR.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        totalPriceColumnR.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        totalPriceColumnR.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Rental, Double>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Rental, Double> event) {
                Rental rental = event.getRowValue();
                rental.setTotalprice(event.getNewValue());
            }
        });

        TableColumn<Rental, Integer> lvlColumnR = new TableColumn<>("Medlemsnivå");
        lvlColumnR.setCellValueFactory(new PropertyValueFactory<>("level"));
        lvlColumnR.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        lvlColumnR.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Rental, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Rental, Integer> event) {
                Rental rental = event.getRowValue();
                rental.setLevel(event.getNewValue());
            }
        });

        TableColumn<Rental, Integer> daysToRentColumnR = new TableColumn<>("Uthyrda dagar");
        daysToRentColumnR.setCellValueFactory(new PropertyValueFactory<>("daysToRent"));
        daysToRentColumnR.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        daysToRentColumnR.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Rental, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Rental, Integer> event) {
                Rental rental = event.getRowValue();
                rental.setDaysToRent(event.getNewValue());
            }
        });

        TableView<Rental> rTable = new TableView<>();
        rTable.setEditable(true);
        rTable.setItems(rSer.rentalList);
        rTable.getColumns().addAll(idColumnR, nameColumnR, carColumnR, startColumnR, endColumnR, priceColumnR, totalPriceColumnR, lvlColumnR, daysToRentColumnR);

        //Layout för Tab2 Uthyrning

        TextField nameInputR = new TextField();
        nameInputR.setPromptText("Medlemsnamn");
        TextField carInputR = new TextField();
        carInputR.setPromptText("Bilnummer");
        TextField priceInputR = new TextField();
        priceInputR.setPromptText("Pris/timme");

        Button addButtonR = new Button("Hyr bil");
        addButtonR.setOnAction(e -> rSer.rentButtonClicked(nameInputR, carInputR, priceInputR, rTable));

        HBox hBoxR = new HBox();
        hBoxR.setPadding(new javafx.geometry.Insets(10,10,10,10));
        hBoxR.setSpacing(10);
        hBoxR.getChildren().addAll(nameInputR, carInputR, priceInputR, addButtonR);

        TextField rNumberInput = new TextField();
        rNumberInput.setPromptText("Hyrnummer");
        TextField daysInput = new TextField();
        daysInput.setPromptText("Antal dagar hyra");

        Button endButtonR = new Button("Avsluta hyrperiod");
        endButtonR.setOnAction(e -> {
            rSer.updateRental(rNumberInput, daysInput, rTable);
        });

        HBox hBoxR2 = new HBox();
        hBoxR2.setPadding(new javafx.geometry.Insets(10,10,10,10));
        hBoxR2.setSpacing(10);
        hBoxR2.getChildren().addAll(rNumberInput, daysInput, endButtonR);

        VBox vBoxRental = new VBox();
        vBoxRental.setSpacing(10);
        vBoxRental.getChildren().addAll(hBoxR, hBoxR2);

        borderPaneR.setCenter(rTable);
        borderPaneR.setBottom(vBoxRental);


        Scene scene1 = new Scene(root, 1024, 768);
        //CSS scene1.getStylesheets().add("application/stylesheet.css");
        stage.setScene(scene1);
        stage.setTitle("Biluthyrning - Skapad av Markus Emanuelsson");
        stage.show();

        //Kör Json filer och lägger in members och items
        mReg.runJsonMembers();
        inv.getCars();
        inv.getSportsCars();
        inv.runJsonCars();
        inv.runJsonSportsCars();
        rSer.rentalList.add(new Rental("Markus", 2, "", "", 500, 1500, 2, 3));

    }

    public static void main(String[] args) {
        launch(args);
    }


}