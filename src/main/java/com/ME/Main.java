package com.ME;
import com.ME.entity.*;
import com.ME.repo.*;
import com.ME.service.InventoryService;
import com.ME.service.MembershipService;
import com.ME.service.RentalService;
import com.ME.service.RentalType;
import com.ME.util.HibernateUtil;
import com.ME.util.Validate;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.geometry.Insets;
import javafx.util.converter.LongStringConverter;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class Main extends Application {

    private Label labelAddCars = new Label("Lägg till ny bil");
    private Label labelAddMovies = new Label("Lägg till ny film");
    private Label labelAddTools = new Label("Lägg till nytt verktyg");
    private Label labelResult = new Label();
    private Label labelRevenue = new Label();
    private Label labelErrorNewCar = new Label();
    private Label labelErrorNewMovie = new Label();
    private Label labelErrorNewTool = new Label();
    private Label labelErrorRent = new Label();
    private Label labelErrorMember =  new Label();
    private String price = "Pris/dag";
    private String lvl = "Användarnivå 1-2";
    private String carInput = "Bil/film/verktygsnummer";
    private String daysToRent = "Antal dagar hyra";
    private String rentalNumber = "Hyrnummer";
    private ComboBox<RentalType> rentalTypeCombo = new ComboBox<>();
    TableView<Rental> rTable = new TableView<>();

    @Override
    public void start(Stage stage) throws Exception {

        labelResult.setPadding(new Insets(10,10,10,10));
        labelResult.setLineSpacing(10);
        labelErrorNewCar.setPadding(new Insets(10,10,10,10));
        labelAddCars.setPadding(new Insets(10,10,10,10));
        labelAddMovies.setPadding(new Insets(10,10,10,10));
        labelAddTools.setPadding(new Insets(10,10,10,10));
        labelErrorNewMovie.setPadding(new Insets(10,10,10,10));
        labelErrorNewTool.setPadding(new Insets(10,10,10,10));
        labelErrorRent.setPadding(new Insets(10,10,10,10));
        labelErrorMember.setPadding(new Insets(10,10,10,10));

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //Repo
        RentalRepository rentalRepo = new RentalRepositoryImpl(sessionFactory);
        MemberRepository memberRepo = new MemberRepositoryImpl(sessionFactory);
        CarRepository cRepo = new CarRepositoryImpl(sessionFactory);
        MovieRepository movieRepo = new MovieRepositoryImpl(sessionFactory);
        ToolRepository tRepo = new ToolRepositoryImpl(sessionFactory);

        //Service
        MembershipService membershipService = new MembershipService(memberRepo);
        InventoryService inventoryService = new InventoryService(cRepo, movieRepo, tRepo); //ändra sen= bilar/filmer/verktyg?
        RentalService rentalService = new RentalService(rentalRepo, memberRepo, cRepo, movieRepo, tRepo);

        Validate val = new Validate();

         /*
        MemberRepositoryImpl memberRepo = new MemberRepositoryImpl(HibernateUtil.getSessionFactory());
        CarRepositoryImpl cRepo = new CarRepositoryImpl(HibernateUtil.getSessionFactory());
        MovieRepositoryImpl movieRepo = new MovieRepositoryImpl(HibernateUtil.getSessionFactory());
        ToolRepositoryImpl tRepo = new ToolRepositoryImpl(HibernateUtil.getSessionFactory());
        RentalRepositoryImpl rentalRepo = new RentalRepositoryImpl(HibernateUtil.getSessionFactory());
        */

        // TESTDATA TILL H2 DATABASEN
        //Lägg till 3 medlemmar
        memberRepo.saveMember(new Member("Markus", 2, ""));
        memberRepo.saveMember(new Member("Bo", 1, ""));
        memberRepo.saveMember(new Member("Eva", 1, ""));
        membershipService.loadMember();

        //Lägg till 4 bilar
        cRepo.saveCar(new Car(500, "Fin bil i bra skick.", "Volvo", "V70", "2015", "Blå"));
        cRepo.saveCar(new Car(600, "Fint skick. Perfekt för affärsresan.", "Audi", "A4", "2018", "Vit"));
        cRepo.saveCar(new Car(360, "Äldre bil i okej skick.", "Volvo", "V40", "2002", "Grå"));
        cRepo.saveCar(new Car(650, "Fin lyxig bil.", "BMW", "320", "2022", "Blå"));
        inventoryService.loadCar();

        //Lägg till 2 filmer
        movieRepo.saveMovie(new Movie(30, "Högt över Los Angeles har en grupp terrorister intagit en byggnad, tagit gisslan och förklarat krig. Men en man har lyckats undgå att bli upptäckt...en polisman som inte är i tjänst. Han är ensam...trött...och det sista hoppet för alla. New York-detektiven John McClane har just anlänt till Los Angeles för att fira jul med sin frånskilda fru. Medan McClane väntar på att hans frus kontorsfest ska sluta, tar terroristerna kontrollen över byggnaden. Medan terroristernas ledare, Hans Gruber och hans brutale bödel samlar ihop gisslan, lyckas McClane att smita undan. Med bara en tjänstepistol och sin list, startar McClane ett enmans krig mot terroristerna.", "Die hard 2", "Action", "1990"));
        movieRepo.saveMovie(new Movie(40, "Marinkårssoldaten Jake Sully kommer till planeten Pandora med ett mycket speciellt uppdrag. Han styr en avatar, en konstgjord kropp som ser exakt ut som Na'vi, planetens humanoida...", "Avatar", "Adventure/Epic", "2009"));
        inventoryService.loadMovie();

        //Lägg till 2 verktyg
        tRepo.saveTool(new Tool(150, "Lättanvänd skruvdragare med batteritid på 5 timmar.", "Skruvdragare", "2022", "Ja"));
        tRepo.saveTool(new Tool(300, "Avancerad häcksax med batteritid på 3 timmar.", "Häcksax", "2025", "Ja"));
        inventoryService.loadTool();

        //Lägg till en uthyrning
        Optional<Member> me = memberRepo.findByName("Markus");
        rentalRepo.saveRental(new Rental(me.get(), 1L, "2026-02-02 16:02", "", 500, 0, 2, 0, RentalType.CAR));
        rentalService.loadRental();


        //Borderpanes och Tabpanes
        TabPane root = new TabPane();
        BorderPane borderPaneM = new BorderPane();
        BorderPane borderPaneC = new BorderPane();
        BorderPane borderPaneR = new BorderPane();
        ScrollPane scroll1 = new ScrollPane();
        scroll1.setContent(borderPaneC);
        ScrollPane scroll2 = new ScrollPane();
        scroll2.setContent(borderPaneR);
        ScrollPane scroll3 = new ScrollPane();
        scroll3.setContent(borderPaneM);

        Tab tab1 = new Tab("Bilar, filmer och verktyg");
        tab1.setContent(scroll1);
        tab1.setClosable(false);

        Tab tab2 = new Tab("Hyr");
        tab2.setContent(borderPaneR);
        tab2.setClosable(false);

        Tab tab3 = new Tab("Medlemmar");
        tab3.setContent(borderPaneM);
        tab3.setClosable(false);

        Tab tab4 = new Tab("Intäkter");

        Button getRevenueButton = new Button("Visa totala intäkter");
        getRevenueButton.setOnAction(e -> {
            double totalRevenue = rentalService.getTotalRevenue();
            labelRevenue.setText(Double.toString(totalRevenue));
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


        //Tableview som visar medlemmar

        TableView<Member> mTable = new TableView<>();
        mTable.setEditable(true);

        TableColumn<Member, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));


        TableColumn<Member, String> nameColumn = new TableColumn<Member, String>("Namn");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(e -> {
            Member m = e.getRowValue();
            m.setName(e.getNewValue());
            memberRepo.updateMember(m);
        });

        TableColumn<Member, Integer> lvlColumn = new TableColumn<>("Medlemsnivå");
        lvlColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        lvlColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        lvlColumn.setOnEditCommit(e -> {
            Member m = e.getRowValue();
            m.setLevel(e.getNewValue());
            memberRepo.updateMember(m);
        });

        TableColumn<Member, String> historyColumn = new TableColumn<>("Historia");
        historyColumn.setMinWidth(200);
        historyColumn.setCellValueFactory(new PropertyValueFactory<>("history"));
        historyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        historyColumn.setOnEditCommit(e -> {
            Member m = e.getRowValue();
            m.setHistory(e.getNewValue());
            memberRepo.updateMember(m);
        });

        mTable.setItems(membershipService.getMemberList());
        mTable.getColumns().addAll(idColumn, nameColumn, lvlColumn, historyColumn);

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

        //Knappar "Ta bort medlem", "Lägg till medlem" och "Sök medlem"
        Button deleteButton = new Button("Ta bort");
        deleteButton.setOnAction(e -> {
            membershipService.deleteButtonClicked(mTable);
            rentalService.updateRentalList();
        });

        Button addButton = new Button("Lägg till medlem");
        addButton.setOnAction(e -> {
            if (nameInput.getText().isEmpty()) {
                labelErrorMember.setText("Alla fält måste fyllas i!");
                labelErrorMember.setTextFill(Color.RED);
                nameInput.setStyle("-fx-border-color:red;");
            } else {
                labelErrorMember.setText("");
                //labelErrorMember.setStyle(null);
                nameInput.setStyle(null);
                val.isInt(lvlInput, lvl);
                membershipService.addButtonClicked(nameInput, lvlInput, mTable, labelResult);
            }
        });

        Button searchButton = new Button("Sök medlem");
        searchButton.setOnAction(e -> {
            String resultText = membershipService.searchMemberByName(searchName.getText());
            labelResult.setText(resultText);
        });


        //Layout Members, Hbox inuti en Vbox i botten av borderpane. Center visar tabell.
        HBox hBox = new HBox();
        hBox.setPadding(new javafx.geometry.Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(nameInput, lvlInput, addButton, deleteButton, labelErrorMember);

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
        cTable.setMinHeight(150);
        cTable.setPrefHeight(150);

        TableColumn<Car, Integer> idColumnCar = new TableColumn<>("Bilnummer");
        idColumnCar.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Car, Double> priceColumnCar = new TableColumn<>("Pris/dag");
        priceColumnCar.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumnCar.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        priceColumnCar.setOnEditCommit(e -> {
            Car c = e.getRowValue();
            c.setPrice(e.getNewValue());
            cRepo.updateCar(c);
        });

        TableColumn<Car, String> brandColumnCar = new TableColumn<>("Bilmärke");
        brandColumnCar.setCellValueFactory(new PropertyValueFactory<>("brand"));
        brandColumnCar.setCellFactory(TextFieldTableCell.forTableColumn());
        brandColumnCar.setOnEditCommit(e -> {
            Car c = e.getRowValue();
            c.setBrand(e.getNewValue());
            cRepo.updateCar(c);
        });

        TableColumn<Car, String> modelColumnCar = new TableColumn<>("Modell");
        modelColumnCar.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelColumnCar.setCellFactory(TextFieldTableCell.forTableColumn());
        modelColumnCar.setOnEditCommit(e -> {
            Car c = e.getRowValue();
            c.setModel(e.getNewValue());
            cRepo.updateCar(c);
        });

        TableColumn<Car, String> yearColumnCar = new TableColumn<>("Årsmodell");
        yearColumnCar.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        yearColumnCar.setCellFactory(TextFieldTableCell.forTableColumn());
        yearColumnCar.setOnEditCommit(e -> {
            Car c = e.getRowValue();
            c.setReleaseYear(e.getNewValue());
            cRepo.updateCar(c);
        });

        TableColumn<Car, String> colorColumnCar = new TableColumn<>("Färg");
        colorColumnCar.setCellValueFactory(new PropertyValueFactory<>("color"));
        colorColumnCar.setCellFactory(TextFieldTableCell.forTableColumn());
        colorColumnCar.setOnEditCommit(e -> {
            Car c = e.getRowValue();
            c.setColor(e.getNewValue());
            cRepo.updateCar(c);
        });

        TableColumn<Car, String> descriptionColumnCar = new TableColumn<>("Beskrivning");
        descriptionColumnCar.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumnCar.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumnCar.setOnEditCommit(e -> {
            Car c = e.getRowValue();
            c.setDescription(e.getNewValue());
            cRepo.updateCar(c);
        });

        cTable.getColumns().addAll(idColumnCar, priceColumnCar, brandColumnCar, modelColumnCar, yearColumnCar, colorColumnCar, descriptionColumnCar);
        cTable.setItems(inventoryService.getCarList());


        //Tableview för Movie
        TableView<Movie> movieTable = new TableView<>();
        movieTable.setEditable(true);
        movieTable.setMinHeight(150);
        movieTable.setPrefHeight(150);

        TableColumn<Movie, Integer> idColumnMovie = new TableColumn<>("Filmnummer");
        idColumnMovie.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Movie, Double> priceColumnMovie = new TableColumn<>("Pris/dag");
        priceColumnMovie.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumnMovie.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        priceColumnMovie.setOnEditCommit(e -> {
            Movie m = e.getRowValue();
            m.setPrice(e.getNewValue());
            movieRepo.updateMovie(m);
        });

        TableColumn<Movie, String> titleColumnMovie = new TableColumn<>("Titel");
        titleColumnMovie.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumnMovie.setCellFactory(TextFieldTableCell.forTableColumn());
        titleColumnMovie.setOnEditCommit(e -> {
            Movie m = e.getRowValue();
            m.setTitle(e.getNewValue());
            movieRepo.updateMovie(m);
        });

        TableColumn<Movie, String> genreColumnMovie = new TableColumn<>("Genre");
        genreColumnMovie.setCellValueFactory(new PropertyValueFactory<>("genre"));
        genreColumnMovie.setCellFactory(TextFieldTableCell.forTableColumn());
        genreColumnMovie.setOnEditCommit(e -> {
            Movie m = e.getRowValue();
            m.setGenre(e.getNewValue());
            movieRepo.updateMovie(m);
        });

        TableColumn<Movie, String> relYearColumnMovie = new TableColumn<>("Produktionsår");
        relYearColumnMovie.setCellValueFactory(new PropertyValueFactory<>("relYear"));
        relYearColumnMovie.setCellFactory(TextFieldTableCell.forTableColumn());
        relYearColumnMovie.setOnEditCommit(e -> {
            Movie m = e.getRowValue();
            m.setRelYear(e.getNewValue());
            movieRepo.updateMovie(m);
        });

        TableColumn<Movie, String> descriptionColumnMovie = new TableColumn<>("Beskrivning");
        descriptionColumnMovie.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumnMovie.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumnMovie.setOnEditCommit(e -> {
            Movie m = e.getRowValue();
            m.setDescription(e.getNewValue());
            movieRepo.updateMovie(m);
        });

        movieTable.getColumns().addAll(idColumnMovie, priceColumnMovie, titleColumnMovie, genreColumnMovie, relYearColumnMovie, descriptionColumnMovie);
        movieTable.setItems(inventoryService.getMovieList());

        //Tableview för Tool
        TableView<Tool> toolTable = new TableView<>();
        toolTable.setEditable(true);
        toolTable.setMinHeight(150);
        toolTable.setPrefHeight(150);

        TableColumn<Tool, Integer> idColumnTool = new TableColumn<>("Verktygsnummer");
        idColumnTool.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Tool, Double> priceColumnTool = new TableColumn<>("Pris/dag");
        priceColumnTool.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumnTool.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        priceColumnTool.setOnEditCommit(e -> {
            Tool t = e.getRowValue();
            t.setPrice(e.getNewValue());
            tRepo.updateTool(t);
        });

        TableColumn<Tool, String> nameColumnTool = new TableColumn<>("Verktyg");
        nameColumnTool.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumnTool.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumnTool.setOnEditCommit(e -> {
            Tool t = e.getRowValue();
            t.setName(e.getNewValue());
            tRepo.updateTool(t);
        });

        TableColumn<Tool, String> fromYearColumnTool = new TableColumn<>("Årsmodell");
        fromYearColumnTool.setCellValueFactory(new PropertyValueFactory<>("fromYear"));
        fromYearColumnTool.setCellFactory(TextFieldTableCell.forTableColumn());
        fromYearColumnTool.setOnEditCommit(e -> {
            Tool t = e.getRowValue();
            t.setFromYear(e.getNewValue());
            tRepo.updateTool(t);
        });

        TableColumn<Tool, String> cordlessColumnTool = new TableColumn<>("Batteridriven");
        cordlessColumnTool.setCellValueFactory(new PropertyValueFactory<>("cordless"));
        cordlessColumnTool.setCellFactory(TextFieldTableCell.forTableColumn());
        cordlessColumnTool.setOnEditCommit(e -> {
            Tool t = e.getRowValue();
            t.setCordless(e.getNewValue());
            tRepo.updateTool(t);
        });

        TableColumn<Tool, String> descriptionColumnTool = new TableColumn<>("Beskrivning");
        descriptionColumnTool.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumnTool.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumnTool.setOnEditCommit(e -> {
            Tool t = e.getRowValue();
            t.setDescription(e.getNewValue());
            tRepo.updateTool(t);
        });

        toolTable.getColumns().addAll(idColumnTool, priceColumnTool, nameColumnTool, fromYearColumnTool, cordlessColumnTool, descriptionColumnTool);
        toolTable.setItems(inventoryService.getToolList());

        //Lägg till bilar

        TextField priceInput = new TextField();
        priceInput.setPromptText("Pris/dag");
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


        Button addCarButton = new Button("Lägg till bil");
        addCarButton.setOnAction(e -> {
            if (descriptionInput.getText().isEmpty()) {
                labelErrorNewCar.setText("Alla fält måste fyllas i!");
                labelErrorNewCar.setTextFill(Color.RED);
                descriptionInput.setStyle("-fx-border-color:red;");
            } else if (brandInput.getText().isEmpty()) {
                labelErrorNewCar.setText("Alla fält måste fyllas i!");
                labelErrorNewCar.setTextFill(Color.RED);
                brandInput.setStyle("-fx-border-color:red;");
            } else if (modelInput.getText().isEmpty()) {
                labelErrorNewCar.setText("Alla fält måste fyllas i!");
                labelErrorNewCar.setTextFill(Color.RED);
                modelInput.setStyle("-fx-border-color:red;");
            } else if (yearInput.getText().isEmpty()) {
                labelErrorNewCar.setText("Alla fält måste fyllas i!");
                labelErrorNewCar.setTextFill(Color.RED);
                yearInput.setStyle("-fx-border-color:red;");
            } else if (colorInput.getText().isEmpty()) {
                labelErrorNewCar.setText("Alla fält måste fyllas i!");
                labelErrorNewCar.setTextFill(Color.RED);
                colorInput.setStyle("-fx-border-color:red;");
            }

            else {
                labelErrorNewCar.setText("");
                descriptionInput.setStyle(null);
                brandInput.setStyle(null);
                modelInput.setStyle(null);
                yearInput.setStyle(null);
                colorInput.setStyle(null);
                val.isNumber(priceInput, price);
                inventoryService.addCar(priceInput, descriptionInput, brandInput, modelInput, yearInput, colorInput, cTable);
            }
        });

        //Lägg till Film

        TextField mPriceInput = new TextField();
        mPriceInput.setPromptText("Pris/dag");
        mPriceInput.setMinWidth(200);

        TextField titleInput = new TextField();
        titleInput.setPromptText("Filmtitel");
        titleInput.setMinWidth(200);

        TextField genreInput = new TextField();
        genreInput.setPromptText("Genre");
        genreInput.setMinWidth(200);

        TextField mYearInput = new TextField();
        mYearInput.setPromptText("Produktionsår");
        mYearInput.setMinWidth(200);

        TextField mDescInput = new TextField();
        mDescInput.setPromptText("Beskrivning");
        mDescInput.setMinWidth(200);

        Button addMovieButton = new Button("Lägg till film");
        addMovieButton.setOnAction(e -> {
            if (mDescInput.getText().isEmpty()) {
                labelErrorNewMovie.setText("Alla fält måste fyllas i!");
                labelErrorNewMovie.setTextFill(Color.RED);
                mDescInput.setStyle("-fx-border-color:red;");
            } else if (mPriceInput.getText().isEmpty()) {
                labelErrorNewMovie.setText("Alla fält måste fyllas i!");
                labelErrorNewMovie.setTextFill(Color.RED);
                mPriceInput.setStyle("-fx-border-color:red;");
            } else if (titleInput.getText().isEmpty()) {
                labelErrorNewMovie.setText("Alla fält måste fyllas i!");
                labelErrorNewMovie.setTextFill(Color.RED);
                titleInput.setStyle("-fx-border-color:red;");
            } else if (genreInput.getText().isEmpty()) {
                labelErrorNewMovie.setText("Alla fält måste fyllas i!");
                labelErrorNewMovie.setTextFill(Color.RED);
                genreInput.setStyle("-fx-border-color:red;");
            } else if (mYearInput.getText().isEmpty()) {
                labelErrorNewMovie.setText("Alla fält måste fyllas i!");
                labelErrorNewMovie.setTextFill(Color.RED);
                mYearInput.setStyle("-fx-border-color:red;");
            }

            else {
                labelErrorNewMovie.setText("");
                mPriceInput.setStyle(null);
                mDescInput.setStyle(null);
                titleInput.setStyle(null);
                genreInput.setStyle(null);
                mYearInput.setStyle(null);

                val.isNumber(mPriceInput, price);
                inventoryService.addMovie(mPriceInput, mDescInput, titleInput, genreInput, mYearInput, movieTable);
            }
        });

        //Lägg till Verktyg

        TextField tPriceInput = new TextField();
        tPriceInput.setPromptText("Pris/dag");
        tPriceInput.setMinWidth(200);

        TextField tNameInput = new TextField();
        tNameInput.setPromptText("Namn");
        tNameInput.setMinWidth(200);

        TextField tYearInput = new TextField();
        tYearInput.setPromptText("Årsmodell");
        tYearInput.setMinWidth(200);

        TextField tCordlessInput = new TextField();
        tCordlessInput.setPromptText("Batteridriven");
        tCordlessInput.setMinWidth(200);

        TextField tDescInput = new TextField();
        tDescInput.setPromptText("Beskrivning");
        tDescInput.setMinWidth(200);

        Button addToolButton = new Button("Lägg till verktyg");
        addToolButton.setOnAction(e -> {
            if (tDescInput.getText().isEmpty()) {
                labelErrorNewTool.setText("Alla fält måste fyllas i!");
                labelErrorNewMovie.setTextFill(Color.RED);
                tDescInput.setStyle("-fx-border-color:red;");
            } else if (tPriceInput.getText().isEmpty()) {
                labelErrorNewTool.setText("Alla fält måste fyllas i!");
                labelErrorNewTool.setTextFill(Color.RED);
                tPriceInput.setStyle("-fx-border-color:red;");
            } else if (tNameInput.getText().isEmpty()) {
                labelErrorNewTool.setText("Alla fält måste fyllas i!");
                labelErrorNewTool.setTextFill(Color.RED);
                tNameInput.setStyle("-fx-border-color:red;");
            } else if (tYearInput.getText().isEmpty()) {
                labelErrorNewTool.setText("Alla fält måste fyllas i!");
                labelErrorNewTool.setTextFill(Color.RED);
                tYearInput.setStyle("-fx-border-color:red;");
            } else if (tCordlessInput.getText().isEmpty()) {
                labelErrorNewTool.setText("Alla fält måste fyllas i!");
                labelErrorNewTool.setTextFill(Color.RED);
                tCordlessInput.setStyle("-fx-border-color:red;");
            }

            else {
                labelErrorNewTool.setText("");
                tPriceInput.setStyle(null);
                tDescInput.setStyle(null);
                tNameInput.setStyle(null);
                tYearInput.setStyle(null);
                tCordlessInput.setStyle(null);

                val.isNumber(tPriceInput, price);
                inventoryService.addTool(tPriceInput, tDescInput, tNameInput, tYearInput, tCordlessInput, toolTable);
            }
        });

        HBox hBoxC = new HBox();
        hBoxC.setPadding(new javafx.geometry.Insets(10,10,10,10));
        hBoxC.setSpacing(10);
        hBoxC.getChildren().addAll(priceInput, brandInput, modelInput, yearInput, colorInput);

        HBox hBoxC2 = new HBox();
        hBoxC2.setPadding(new javafx.geometry.Insets(10,10,10,10));
        hBoxC2.setSpacing(10);
        hBoxC2.getChildren().addAll(descriptionInput, addCarButton, labelErrorNewCar);

        HBox hBoxC3 = new HBox();
        hBoxC3.setPadding(new javafx.geometry.Insets(10,10,10,10));
        hBoxC3.setSpacing(10);
        hBoxC3.getChildren().addAll(mPriceInput, titleInput, genreInput, mYearInput);

        HBox hBoxC4 = new HBox();
        hBoxC4.setPadding(new javafx.geometry.Insets(10,10,10,10));
        hBoxC4.setSpacing(10);
        hBoxC4.getChildren().addAll(mDescInput, addMovieButton, labelErrorNewMovie);

        HBox hBoxC5 = new HBox();
        hBoxC5.setPadding(new javafx.geometry.Insets(10,10,10,10));
        hBoxC5.setSpacing(10);
        hBoxC5.getChildren().addAll(tPriceInput, tNameInput, tYearInput, tCordlessInput);

        HBox hBoxC6 = new HBox();
        hBoxC6.setPadding(new javafx.geometry.Insets(10,10,10,10));
        hBoxC6.setSpacing(10);
        hBoxC6.getChildren().addAll(tDescInput, addToolButton, labelErrorNewTool);

        VBox vBoxCars = new VBox();
        vBoxCars.setSpacing(10);
        vBoxCars.getChildren().addAll(labelAddCars, hBoxC, hBoxC2, labelAddMovies, hBoxC3, hBoxC4, labelAddTools, hBoxC5, hBoxC6);

        VBox vBoxInventory = new VBox();
        vBoxInventory.setSpacing(10);
        vBoxInventory.getChildren().addAll(cTable, movieTable, toolTable);

        //borderPaneC.setTop(vBoxTest);
        borderPaneC.setCenter(vBoxInventory);
        borderPaneC.setBottom(vBoxCars);



        //Tableview som visar uthyrningar

        TableColumn<Rental, Integer> idColumnR = new TableColumn<>("Hyrnummer");
        idColumnR.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Rental, Long> memberColumnR = new TableColumn<>("MedlemsID");
        //nameColumnR.setMinWidth(200);
        memberColumnR.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getMember().getId()));

        TableColumn<Rental, RentalType> rTypeColumnR = new TableColumn<>("Uthyrningstyp");
        rTypeColumnR.setCellValueFactory(new PropertyValueFactory<>("rentalType"));

        TableColumn<Rental, Long> objColumnR = new TableColumn<>("Objektnummer");
        objColumnR.setCellValueFactory(new PropertyValueFactory<>("rentalObjectId"));
        objColumnR.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        objColumnR.setOnEditCommit(e -> {
            Rental r = e.getRowValue();
            r.setRentalObjectId(e.getNewValue());
            rentalRepo.updateRental(r);
        });

        TableColumn<Rental, String> startColumnR = new TableColumn<>("Starttid");
        startColumnR.setMinWidth(170);
        startColumnR.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        startColumnR.setCellFactory(TextFieldTableCell.forTableColumn());
        startColumnR.setOnEditCommit(e -> {
            Rental r = e.getRowValue();
            r.setStartTime(e.getNewValue());
            rentalRepo.updateRental(r);
        });

        TableColumn<Rental, String> endColumnR = new TableColumn<>("Sluttid");
        endColumnR.setMinWidth(170);
        endColumnR.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        endColumnR.setCellFactory(TextFieldTableCell.forTableColumn());
        endColumnR.setOnEditCommit(e -> {
            Rental r = e.getRowValue();
            r.setEndTime(e.getNewValue());
            rentalRepo.updateRental(r);
        });

        TableColumn<Rental, Double> priceColumnR = new TableColumn<>("Pris/dag");
        priceColumnR.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumnR.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        priceColumnR.setOnEditCommit(e -> {
            Rental r = e.getRowValue();
            r.setPrice(e.getNewValue());
            rentalRepo.updateRental(r);
        });


        TableColumn<Rental, Double> totalPriceColumnR = new TableColumn<>("Totalpris");
        totalPriceColumnR.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        totalPriceColumnR.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        totalPriceColumnR.setOnEditCommit(e -> {
            Rental r = e.getRowValue();
            r.setTotalPrice(e.getNewValue());
            rentalRepo.updateRental(r);
        });

        TableColumn<Rental, Integer> lvlColumnR = new TableColumn<>("Medlemsnivå");
        lvlColumnR.setCellValueFactory(new PropertyValueFactory<>("level"));
        lvlColumnR.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        lvlColumnR.setOnEditCommit(e -> {
            Rental r = e.getRowValue();
            r.setLevel(e.getNewValue());
            rentalRepo.updateRental(r);
        });

        TableColumn<Rental, Integer> daysToRentColumnR = new TableColumn<>("Uthyrda dagar");
        daysToRentColumnR.setCellValueFactory(new PropertyValueFactory<>("daysToRent"));
        daysToRentColumnR.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        daysToRentColumnR.setOnEditCommit(e -> {
            Rental r = e.getRowValue();
            r.setDaysToRent(e.getNewValue());
            rentalRepo.updateRental(r);
        });

        TableColumn<Rental, Integer> rentalTypeColumnR = new TableColumn<>("Hyrtyp");
        daysToRentColumnR.setCellValueFactory(new PropertyValueFactory<>("daysToRent"));
        daysToRentColumnR.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        daysToRentColumnR.setOnEditCommit(e -> {
            Rental r = e.getRowValue();
            r.setDaysToRent(e.getNewValue());
            rentalRepo.updateRental(r);
        });

        rTable.setEditable(true);
        rTable.setItems(rentalService.getRentalList());
        rTable.getColumns().addAll(idColumnR, memberColumnR, rTypeColumnR, objColumnR, startColumnR, endColumnR, priceColumnR, totalPriceColumnR, lvlColumnR, daysToRentColumnR);


        //Layout för Tab2 Uthyrning

        TextField nameInputR = new TextField();
        nameInputR.setPromptText("Medlemsnamn");
        nameInputR.setMinWidth(200);
        TextField objInputR = new TextField();
        objInputR.setPromptText("Bil/film/verktygsnummer");
        objInputR.setMinWidth(200);

        Button addButtonR = new Button("Starta uthyrning");
        addButtonR.setOnAction(e -> {
                if (nameInputR.getText().isEmpty() | objInputR.getText().isEmpty()) {
                    labelErrorRent.setText("Alla fält måste fyllas i!");
                    labelErrorRent.setTextFill(Color.RED);

                } else {
                    labelErrorRent.setText("");
                    labelErrorRent.setStyle(null);
                    val.isInt(objInputR, carInput);
                    rentalService.rentButtonClicked(nameInputR, objInputR, rentalTypeCombo, rTable, membershipService.getMemberList(), inventoryService.getCarList(), inventoryService.getMovieList(), inventoryService.getToolList(), labelErrorRent);
                }

        });

        rentalTypeCombo.getItems().addAll(RentalType.values());
        rentalTypeCombo.getSelectionModel().selectFirst();

        HBox hBoxR = new HBox();
        hBoxR.setPadding(new javafx.geometry.Insets(10,10,10,10));
        hBoxR.setSpacing(10);
        hBoxR.getChildren().addAll(nameInputR, objInputR, rentalTypeCombo, addButtonR, labelErrorRent);

        TextField rNumberInput = new TextField();
        rNumberInput.setPromptText("Hyrnummer");
        TextField daysInput = new TextField();
        daysInput.setPromptText("Antal dagar hyra");

        Button endButtonR = new Button("Avsluta hyrperiod");
        endButtonR.setOnAction(e -> {

            val.isInt(rNumberInput, rentalNumber);
            val.isInt(daysInput, daysToRent);
            rentalService.updateRental(rNumberInput, daysInput);
            rNumberInput.clear();
            daysInput.clear();
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


        Scene scene1 = new Scene(root, 1250, 800);
        stage.setScene(scene1);
        stage.setTitle("Uthyrning - Skapad av Markus Emanuelsson");
        stage.show();

    }

    @Override
    public void stop() {
        HibernateUtil.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }


}