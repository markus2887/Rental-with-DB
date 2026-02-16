package com.ME.repo;

import com.ME.entity.Car;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    List<Car> readCar();
    void saveCar(Car car);
    void updateCar(Car car);
    void deleteCar(Car car);
    Optional<Car> findById(long id);
}
