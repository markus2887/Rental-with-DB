package com.ME.service;

import com.ME.entity.Car;
import com.ME.entity.Member;
import com.ME.entity.Movie;
import com.ME.entity.Tool;
import com.ME.exception.CarNotFoundException;
import com.ME.repo.CarRepositoryImpl;
import com.ME.repo.MovieRepositoryImpl;
import com.ME.repo.ToolRepository;
import com.ME.repo.ToolRepositoryImpl;
import com.ME.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class InventoryService {

    CarRepositoryImpl cRepo = new CarRepositoryImpl(HibernateUtil.getSessionFactory());
    MovieRepositoryImpl movieRepo = new MovieRepositoryImpl(HibernateUtil.getSessionFactory());
    ToolRepositoryImpl tRepo = new ToolRepositoryImpl(HibernateUtil.getSessionFactory());

    ObservableList<Car> carList = FXCollections.observableArrayList();
    private final ObservableList<Movie> movieList = FXCollections.observableArrayList();
    private final ObservableList<Tool> toolList = FXCollections.observableArrayList();

    public ObservableList<Car> getCarList() {
        return carList;
    }

    public ObservableList<Movie> getMovieList() {
        return movieList;
    }

    public ObservableList<Tool> getToolList() {
        return toolList;
    }

    public void loadCar() {
        carList.setAll(cRepo.readCar());
    }

    public void loadMovie() {
        movieList.setAll(movieRepo.readMovie());
    }

    public void loadTool() {
        toolList.setAll(tRepo.readTool());
    }

    public void addCar(TextField priceInput, TextField descriptionInput, TextField brandInput, TextField modelInput, TextField yearInput, TextField colorInput, TableView<Car> cTable) {
        double price = Double.parseDouble(priceInput.getText());
        String description = descriptionInput.getText();
        String brand = brandInput.getText();
        String model = modelInput.getText();
        String releaseYear = yearInput.getText();
        String color = colorInput.getText();

        Car car = new Car(price, description, brand, model, releaseYear, color);
        cRepo.saveCar(car);
        cTable.getItems().add(car);

        priceInput.clear();
        descriptionInput.clear();
        brandInput.clear();
        modelInput.clear();
        yearInput.clear();
        colorInput.clear();
    }

    public void addMovie(TextField priceInput, TextField descriptionInput, TextField titleInput, TextField genreInput, TextField yearInput, TableView<Movie> mTable) {
        double price = Double.parseDouble(priceInput.getText());
        String description = descriptionInput.getText();
        String title = titleInput.getText();
        String genre = genreInput.getText();
        String year = yearInput.getText();

        Movie movie = new Movie(price, description, title, genre, year);
        movieRepo.saveMovie(movie);
        mTable.getItems().add(movie);

        priceInput.clear();
        descriptionInput.clear();
        titleInput.clear();
        genreInput.clear();
        yearInput.clear();
    }

    public void addTool(TextField priceInput, TextField descriptionInput, TextField nameInput, TextField yearInput, TextField cordlessInput, TableView<Tool> tTable) {
        double price = Double.parseDouble(priceInput.getText());
        String description = descriptionInput.getText();
        String name = nameInput.getText();
        String year = yearInput.getText();
        String cordless = cordlessInput.getText();

        Tool tool = new Tool(price, description, name, year, cordless);
        tRepo.saveTool(tool);
        tTable.getItems().add(tool);

        priceInput.clear();
        descriptionInput.clear();
        nameInput.clear();
        yearInput.clear();
        cordlessInput.clear();
    }

    public void test() {
        System.out.println("Söker id: 2");

        carList.forEach(c ->
                System.out.println("Car i lista: " + c.getId())
        );
    }

    public Car searchCar(Long carId, ObservableList<Car> carListIn) throws CarNotFoundException {
        Car carFound = carListIn.stream()
                .filter(c -> c.getId().equals(carId))
                .findFirst()
                .orElseThrow(() ->
                        new CarNotFoundException("Bil med ID " + carId + " finns inte"));
        return carFound;
    }

    public Movie searchMovie(Long movie, ObservableList<Movie> movieListIn) {
        Movie foundMovie = movieListIn.stream()
                .filter(m -> m.getId() == movie)
                .findFirst()
                .orElse(null);
        return foundMovie;
    }

    public Tool searchTool(Long tool, ObservableList<Tool> toolListIn) {
        Tool foundTool = toolListIn.stream()
                .filter(t -> t.getId() == tool)
                .findFirst()
                .orElse(null);
        return foundTool;
    }
}
