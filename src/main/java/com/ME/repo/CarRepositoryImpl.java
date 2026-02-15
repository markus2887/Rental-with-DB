package com.ME.repo;
import com.ME.entity.Car;
import com.ME.entity.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class CarRepositoryImpl implements CarRepository {

    private final SessionFactory sessionFactory;

    public CarRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Car> readCar() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createNativeQuery("SELECT * FROM cars", Car.class)
                    .getResultList();
        }
    }

    @Override
    public void saveCar(Car car) {
        try (Session session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();
            session.persist(car);
            tx.commit();
        }
    }

    @Override
    public void updateCar(Car car) {
        try (Session session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();
            session.merge(car);
            tx.commit();
        }
    }

    @Override
    public void deleteCar(Car car) {
        try (Session session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();

            session.remove(car);
            tx.commit();
        }
    }


}