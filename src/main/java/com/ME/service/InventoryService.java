package com.ME.service;

import com.ME.entity.Car;
import com.ME.entity.Member;
import com.ME.entity.Movie;
import com.ME.entity.Tool;
import com.ME.exception.CarNotFoundException;
import com.ME.repo.*;
import com.ME.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class InventoryService {

private final CarRepository carRepository;
private final MovieRepository movieRepository;
private final ToolRepository toolRepository;

public InventoryService (CarRepository carRepository, MovieRepository movieRepository, ToolRepository toolRepository) {
    this.carRepository = carRepository;
    this.movieRepository = movieRepository;
    this.toolRepository = toolRepository;
}

/* Kod innan ändring
    CarRepositoryImpl cRepo = new CarRepositoryImpl(HibernateUtil.getSessionFactory());
    MovieRepositoryImpl movieRepo = new MovieRepositoryImpl(HibernateUtil.getSessionFactory());
    ToolRepositoryImpl tRepo = new ToolRepositoryImpl(HibernateUtil.getSessionFactory());
*/


    private final ObservableList<Car> carList = FXCollections.observableArrayList();
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
        carList.setAll(carRepository.readCar());
    }

    public void loadMovie() {
        movieList.setAll(movieRepository.readMovie());
    }

    public void loadTool() {
        toolList.setAll(toolRepository.readTool());
    }

    public void addCar(TextField priceInput, TextField descriptionInput, TextField brandInput, TextField modelInput, TextField yearInput, TextField colorInput, TableView<Car> cTable) {
        double price = Double.parseDouble(priceInput.getText());
        String description = descriptionInput.getText();
        String brand = brandInput.getText();
        String model = modelInput.getText();
        String releaseYear = yearInput.getText();
        String color = colorInput.getText();

        Car car = new Car(price, description, brand, model, releaseYear, color);
        carRepository.saveCar(car);
        carList.add(car);

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
        movieRepository.saveMovie(movie);
        movieList.add(movie);

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
        toolRepository.saveTool(tool);
        toolList.add(tool);

        priceInput.clear();
        descriptionInput.clear();
        nameInput.clear();
        yearInput.clear();
        cordlessInput.clear();
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
