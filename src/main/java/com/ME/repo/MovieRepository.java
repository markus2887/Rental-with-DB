package com.ME.repo;

import com.ME.entity.Movie;
import javafx.collections.ObservableList;
import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    List<Movie> readMovie();
    void saveMovie(Movie movie);
    void updateMovie(Movie movie);
    void deleteMovie(Movie movie);
    Optional<Movie> findById(long id);
}