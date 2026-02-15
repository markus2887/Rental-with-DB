package com.ME.repo;

import com.ME.entity.Car;
import javafx.collections.ObservableList;

import java.util.List;

public interface CarRepository {
    List<Car> readCar();
    void saveCar(Car car);
    void updateCar(Car car);
    void deleteCar(Car car);
}
