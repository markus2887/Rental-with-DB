package com.ME.OOP2;

import com.ME.OOP2.entity.Car;
import com.ME.OOP2.entity.Member;
import com.ME.OOP2.entity.SportsCar;
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
    //List<SportsCar> sportsCarList = new ArrayList<>();
    //ObservableList<Car> carList = FXCollections.observableArrayList();
    //ObservableList<SportsCar> sportsCarList = FXCollections.observableArrayList();

    public ObservableList<SportsCar> runJsonSportsCars() throws Exception {
        //Json funktionalitet
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        //Spara till JSON Cars
        //mapper.writeValue(new File("sportsCar.json"), sportsCarList);

        //Läsa in JSON Cars

        List<SportsCar> fromFile3 = Arrays.asList(mapper.readValue(new File("sportsCar.json"), SportsCar[].class));
        ObservableList<SportsCar> sportsCarList = FXCollections.observableArrayList(fromFile3);
        return sportsCarList;
    }

    ObservableList<SportsCar> sportsCarList = runJsonSportsCars();

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

    public ObservableList<SportsCar> getSportsCars() {
        /* Hämtas från sportsCar.json istället.
        sportsCarList.add(new SportsCar(5000, "Utsökt skick, En upplevelse du aldrig glömmer.", "Ferrari", "F50", "1997", "Röd", true, 520));
        sportsCarList.add(new SportsCar(3000, "Om du vill ha en körupplevelse bortom alla drömmar.", "Porsche", "911 GT3", "2022", "Orange", true, 510));
        sportsCarList.add(new SportsCar(10000, "Värstingen", "Koenigsegg", "Jesko", "2025", "Vit", true, 1600));
        */
        return sportsCarList;
    }

    public void addCar(TextField priceInput, TextField descriptionInput, TextField brandInput, TextField modelInput, TextField yearInput, TextField colorInput, TableView<Car> cTable) {
        int price = Integer.parseInt(priceInput.getText());
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

    public void addSportsCar(TextField priceInput, TextField descriptionInput, TextField brandInput, TextField modelInput, TextField yearInput, TextField colorInput, TextField sportSeatsInput, TextField hpInput, TableView<SportsCar> cTable2) {
        double price = Double.parseDouble(priceInput.getText());
        String description = descriptionInput.getText();
        String brand = brandInput.getText();
        String model = modelInput.getText();
        String year = yearInput.getText();
        String color = colorInput.getText();
        Boolean sportSeats = Boolean.parseBoolean(sportSeatsInput.getText());
        int hp = Integer.parseInt(hpInput.getText());

        SportsCar sportsCar = new SportsCar(price, description, brand, model, year, color, sportSeats, hp);
        cTable2.getItems().add(sportsCar);

        priceInput.clear();
        descriptionInput.clear();
        brandInput.clear();
        modelInput.clear();
        yearInput.clear();
        colorInput.clear();
        sportSeatsInput.clear();
        hpInput.clear();
    }
}
