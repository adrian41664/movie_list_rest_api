package de.adrianwalter.movie_list_rest_api.repository;

import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieListRepository extends JpaRepository<MovieList, Long> {

    Optional<MovieList> findByMovieListId( Long id );

    // Optional<MovieList> findByMovieListName( String name );
}
