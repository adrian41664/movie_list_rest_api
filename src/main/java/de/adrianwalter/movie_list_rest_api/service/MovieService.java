package de.adrianwalter.movie_list_rest_api.service;

import de.adrianwalter.movie_list_rest_api.entity.Movie;
import de.adrianwalter.movie_list_rest_api.exception.NameAlreadyExistsException;
import de.adrianwalter.movie_list_rest_api.exception.ResourceNotFoundException;
import de.adrianwalter.movie_list_rest_api.payload.movie.MovieCreateDto;
import de.adrianwalter.movie_list_rest_api.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;


    public MovieService( MovieRepository movieRepository ) {
        this.movieRepository = movieRepository;
    }


    public Page< Movie > findAll( Pageable pageable ) {
        return movieRepository.findAll( pageable );
    }


    public Movie findById( Long id ) {

        Optional< Movie > movie = movieRepository.findById( id );

        if ( movie.isEmpty() ) {
            throw new ResourceNotFoundException();
        }

        return movie.get();
    }


    public void deleteById( Long id ) {
        movieRepository.deleteById( id );
    }


    public Optional< Movie > findByMovieName( String movieName ) {
        return movieRepository.findByMovieName( movieName );
    }


    public Movie create( MovieCreateDto request ) {

        // toDo: How to handle? Name is not unique. Movie should be unique per MovieList.
        Optional< Movie > existingMovie = findByMovieName( request.getMovieName() );

        if ( existingMovie.isPresent() ) {
            throw new NameAlreadyExistsException();
        }

        Movie movie = new Movie();
        movie.setMovieName( request.getMovieName() );

        return movieRepository.save( movie );
    }
}
