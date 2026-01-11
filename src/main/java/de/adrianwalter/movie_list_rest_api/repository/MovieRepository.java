package de.adrianwalter.movie_list_rest_api.repository;

import de.adrianwalter.movie_list_rest_api.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByMovieName( String name );

    Optional< Movie> findByMovieList_MovieListIdAndMovieName( long movieListId, String movieName );

}