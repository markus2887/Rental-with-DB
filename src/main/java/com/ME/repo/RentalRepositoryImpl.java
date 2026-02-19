package com.ME.repo;

import com.ME.entity.Rental;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class RentalRepositoryImpl implements RentalRepository {

    private final SessionFactory sessionFactory;

    public RentalRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Rental> readRental() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createNativeQuery("SELECT * FROM rentals", Rental.class)
                    .getResultList();
        }
    }

    @Override
    public void saveRental(Rental rental) {
        try (Session session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();
            session.persist(rental);
            tx.commit();
        }
    }

    @Override
    public void updateRental(Rental rental) {
        try (Session session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();
            session.merge(rental);
            tx.commit();
        }
    }

    @Override
    public void deleteRental(Rental rental) {
        try (Session session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();

            session.remove(rental);
            tx.commit();
        }
    }

    public Optional<Rental> findById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Rental.class, id));
        }
    }

    public Optional<Rental> findByRentalObjectId(long objectId, String rentalType) {
        try (Session session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();
            Optional<Rental> result = session.createNativeQuery("SELECT * FROM rentals WHERE rentalObjectId = :id AND rentalType = :type AND endTime = ''", Rental.class)
                    .setParameter("id", objectId)
                    .setParameter("type", rentalType)
                    .uniqueResultOptional();
            tx.commit();
            return result;
        }
    }
    public double getTotalRevenueDB() {
        try (Session session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();
            Object result = session.createNativeQuery("SELECT SUM(totalPrice) FROM rentals").getSingleResult();
            tx.commit();

            if (result == null) {
                return 0.0;
            }
            return ((Number) result).doubleValue();
        }
    }
}
