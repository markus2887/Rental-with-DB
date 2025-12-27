package com.ME.OOP2;

import com.ME.OOP2.entity.Car;
import com.ME.OOP2.entity.Member;
import com.ME.OOP2.entity.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inventory {

    public Inventory() throws Exception {
    }
    //List<Car> carList = new ArrayList<>();
    //List<Movie> movieList1 = new ArrayList<>();
    //ObservableList<Car> carList = FXCollections.observableArrayList();
    //ObservableList<SportsCar> sportsCarList = FXCollections.observableArrayList();

    public ObservableList<Movie> runJsonMovie() throws Exception {
        //Json funktionalitet
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        /*Läggs in via movie.json istället
        movieList1.add(new Movie(30, "Högt över Los Angeles har en grupp terrorister intagit en byggnad, tagit gisslan och förklarat krig. Men en man har lyckats undgå att bli upptäckt...en polisman som inte är i tjänst. Han är ensam...trött...och det sista hoppet för alla. New York-detektiven John McClane har just anlänt till Los Angeles för att fira jul med sin frånskilda fru. Medan McClane väntar på att hans frus kontorsfest ska sluta, tar terroristerna kontrollen över byggnaden. Medan terroristernas ledare, Hans Gruber och hans brutale bödel samlar ihop gisslan, lyckas McClane att smita undan. Med bara en tjänstepistol och sin list, startar McClane ett enmans krig mot terroristerna.", "Die hard 2", "Action", "1990"));
        movieList1.add(new Movie(40, "Marinkårssoldaten Jake Sully kommer till planeten Pandora med ett mycket speciellt uppdrag. Han styr en avatar, en konstgjord kropp som ser exakt ut som Na'vi, planetens humanoida...", "Avatar", "Adventure/Epic", "2009"));
        */

        //Spara till JSON movie.json
        //mapper.writeValue(new File("movie.json"), movieList1);

        //Läsa in JSON movie.json
        List<Movie> fromFile3 = Arrays.asList(mapper.readValue(new File("movie.json"), Movie[].class));
        ObservableList<Movie> movieList = FXCollections.observableArrayList(fromFile3);
        return movieList;
    }

    ObservableList<Movie> movieList = runJsonMovie();

    public ObservableList<Car> runJsonCars() throws Exception {
        //Json funktionalitet
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        //Spara till JSON Cars
        //mapper.writeValue(new File("car.json"), carList);

        //Läsa in JSON Cars
        List<Car> fromFile2 = Arrays.asList(mapper.readValue(new File("car.json"), Car[].class));
        ObservableList<Car> carList = FXCollections.observableArrayList(fromFile2);
        return carList;
    }

    ObservableList<Car> carList = runJsonCars();

    public ObservableList<Car> getCars() {
        /* Hämtas från car.json istället.
        carList.add(new Car(600, "Fint skick. Perfekt för affärsresan.", "Audi", "A4", "2018", "Blå"));
        carList.add(new Car(500, "Kördugligt skick. Bra rymlig bil för mycket packning.", "Volvo", "V70", "2012", "Svart"));
        carList.add(new Car(500, "Kördugligt skick. Bra rymlig bil för mycket packning.", "Volvo", "V70", "2012", "Svart"));
        carList.add(new Car(600, "Beskrivning", "Audi", "A4", "2018", "Blå"));
        carList.add(new Car(360, "Beskrivning", "Volvo", "V40", "2002", "Silver"));
        carList.add(new Car(500, "Beskrivning", "Volkswagen", "Sharan", "2011", "Vit"));
        carList.add(new Car(650, "Beskrivning", "BMW", "320", "2022", "Blå"));
        carList.add(new Car(600, "Beskrivning", "Citroen", "C4", "2020", "Röd"));
        carList.add(new Car(600, "Beskrivning", "Mercedes", "E320", "2023", "Svart"));
        carList.add(new SportsCar(5000, "Utsökt skick, En upplevelse du aldrig glömmer.", "Ferrari", "F50", "1997", "Röd", true, 520));
        carList.add(new SportsCar(3000, "Om du vill ha en körupplevelse bortom alla drömmar.", "Porsche", "911 GT3", "2022", "Orange", true, 510));
        carList.add(new SportsCar(10000, "Värstingen", "Koenigsegg", "Jesko", "2025", "Vit", true, 1600));
        */
        return carList;
    }

    public void addCar(TextField priceInput, TextField descriptionInput, TextField brandInput, TextField modelInput, TextField yearInput, TextField colorInput, TableView<Car> cTable) {
        double price = Double.parseDouble(priceInput.getText());
        String description = descriptionInput.getText();
        String brand = brandInput.getText();
        String model = modelInput.getText();
        String year = yearInput.getText();
        String color = colorInput.getText();

        Car car = new Car(price, description, brand, model, year, color);
        cTable.getItems().add(car);

        priceInput.clear();
        descriptionInput.clear();
        brandInput.clear();
        modelInput.clear();
        yearInput.clear();
        colorInput.clear();
    }

    public void addMovie(TextField priceInput, TextField descriptionInput, TextField titleInput, TextField genreInput, TextField yearInput, TableView<Movie> cTable2) {
        double price = Double.parseDouble(priceInput.getText());
        String description = descriptionInput.getText();
        String title = titleInput.getText();
        String genre = genreInput.getText();
        String year = yearInput.getText();

        Movie movie = new Movie(price, description, title, genre, year);
        cTable2.getItems().add(movie);

        priceInput.clear();
        descriptionInput.clear();
        titleInput.clear();
        genreInput.clear();
        yearInput.clear();
    }
}
