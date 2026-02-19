package com.ME.service;

import com.ME.entity.Car;
import com.ME.repo.CarRepository;
import com.ME.repo.MovieRepository;
import com.ME.repo.ToolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InventoryServiceTest {
    private CarRepository carRepository;
    private MovieRepository movieRepository;
    private ToolRepository toolRepository;

    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        // Skapa en Mockito-mock av repot (fejkat repo)
        carRepository = mock(CarRepository.class);
        movieRepository = mock(MovieRepository.class);
        toolRepository = mock(ToolRepository.class);

        // Skapa servicen och "injicera" mocken via konstruktorn
        inventoryService = new InventoryService(carRepository, movieRepository, toolRepository);
    }


    @Test
    void loadCarMethod_ShouldFillListNamedCars() {
        List<Car> cars = List.of(new Car(1, "Test", "Volvo", "V70", "1999", "Blå"),
                new Car(2, "Test", "Volvo", "V70", "1999", "Blå"));
        when(carRepository.readCar()).thenReturn(cars);

        inventoryService.loadCar();

        assertEquals(2, inventoryService.getCarList().size());
    }


}