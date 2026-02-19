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
    void updateRental_ShouldCalculatePriceAndUpdateRental() {

        TextField idField = mock(TextField.class);
        TextField daysField = mock(TextField.class);

        when(idField.getText()).thenReturn("10");
        when(daysField.getText()).thenReturn("5");

        doNothing().when(idField).clear();
        doNothing().when(daysField).clear();

        Member member = new Member("Test", 2, "");

        Rental rental = new Rental(member, 10L, "2025-02-01", "",
                500, 0, 2, 0, RentalType.CAR);

        when(rentalRepository.findById(10L)).thenReturn(Optional.of(rental));
        when(rentalRepository.readRental()).thenReturn(List.of(rental));

        boolean result = rentalService.updateRental(idField, daysField);

        assertTrue(result);
        assertEquals(1875, rental.getTotalPrice());
        verify(rentalRepository).updateRental(rental);
    }
}
