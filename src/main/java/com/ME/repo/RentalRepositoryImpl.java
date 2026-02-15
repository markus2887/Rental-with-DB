package com.ME.repo;

import com.ME.entity.Rental;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

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
}
