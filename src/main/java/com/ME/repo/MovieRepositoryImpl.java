package com.ME.repo;

import com.ME.entity.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class MovieRepositoryImpl implements MovieRepository {
    private final SessionFactory sessionFactory;

    public MovieRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Movie> readMovie() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createNativeQuery("SELECT * FROM movies", Movie.class)
                    .getResultList();
        }
    }

    @Override
    public void saveMovie(Movie movie) {
        try (Session session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();
            session.persist(movie);
            tx.commit();
        }
    }

    @Override
    public void updateMovie(Movie movie) {
        try (Session session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();
            session.merge(movie);
            tx.commit();
        }
    }

    @Override
    public void deleteMovie(Movie movie) {
        try (Session session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();

            session.remove(movie);
            tx.commit();
        }
    }
}
