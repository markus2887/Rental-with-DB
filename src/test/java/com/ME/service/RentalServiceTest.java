package com.ME.service;

import com.ME.entity.Member;
import com.ME.entity.Rental;
import com.ME.repo.*;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class RentalServiceTest {

    @BeforeAll
    static void initJavaFX() {
        javafx.application.Platform.startup(() -> {});
    }

    private RentalRepository rentalRepository;
    private RentalService rentalService;
    private MemberRepository memberRepository;
    private CarRepository carRepository;
    private MovieRepository movieRepository;
    private ToolRepository toolRepository;

    @BeforeEach
    void setUp() {
        // Skapa en Mockito-mock av repot (fejkat repo)
        rentalRepository = mock(RentalRepository.class);
        memberRepository = mock(MemberRepository.class);
        carRepository = mock(CarRepository.class);
        movieRepository = mock(MovieRepository.class);
        toolRepository = mock(ToolRepository.class);

        // Skapa servicen och "injicera" mocken via konstruktorn
        rentalService = new RentalService(rentalRepository, memberRepository, carRepository, movieRepository, toolRepository);
    }


    @Test
    void getTotalRevenueMethod_shouldReturnValueFromRepository() {

        when(rentalRepository.getTotalRevenueDB()).thenReturn(100d);

        double result = rentalService.getTotalRevenue();

        assertEquals(100d, result);
    }

}
