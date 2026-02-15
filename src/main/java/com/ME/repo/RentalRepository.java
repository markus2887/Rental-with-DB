package com.ME.repo;

import com.ME.entity.Rental;

import java.util.List;

public interface RentalRepository {
    List<Rental> readRental();
    void saveRental(Rental rental);
    void updateRental(Rental rental);
    void deleteRental(Rental rental);
}
