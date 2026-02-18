package com.ME.repo;

import com.ME.entity.Rental;

import java.util.List;
import java.util.Optional;

public interface RentalRepository {
    List<Rental> readRental();
    void saveRental(Rental rental);
    void updateRental(Rental rental);
    void deleteRental(Rental rental);
    Optional<Rental> findById(long id);
    Optional<Rental> findByRentalObjectId(long id, String rentalType);
    double getTotalRevenue();
}
