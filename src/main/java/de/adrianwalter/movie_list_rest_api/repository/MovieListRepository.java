package de.adrianwalter.movie_list_rest_api.repository;

import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieListRepository extends JpaRepository<MovieList, Long> {

    Optional<MovieList> findByMovieListId( Long movieListId );

    Optional<MovieList> findByUser_UserIdAndMovieListName( Long userId, String movieListName );

    Optional<MovieList> findByUser_UserNameAndMovieListName( String userName, String movieListName );

    // Optional<MovieList> findByMovieListName( String name );
}
