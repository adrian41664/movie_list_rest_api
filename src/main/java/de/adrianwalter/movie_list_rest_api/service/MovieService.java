package de.adrianwalter.movie_list_rest_api.service;

import de.adrianwalter.movie_list_rest_api.entity.Movie;
import de.adrianwalter.movie_list_rest_api.exception.NameAlreadyExistsException;
import de.adrianwalter.movie_list_rest_api.exception.ResourceNotFoundException;
import de.adrianwalter.movie_list_rest_api.dto.movie.MovieCreateDto;
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


    public Movie create( MovieCreateDto movieCreateDto ) {

        // toDo: How to handle? Name is not unique. Movie should be unique per MovieList.
        // Optional< Movie > existingMovie = findByMovieName( movieCreateDto.getMovieName() );

        Movie movie = this.mapToMovie( movieCreateDto );

        long movieListId = movie.getMovieList().getMovieListId();
        String movieName = movie.getMovieName();

        if ( this.movieListHasMovieWithSameName( movieListId, movieName ) ) {

            throw new NameAlreadyExistsException(
                    "Cant create new Movie; MovieList already owns Movie with the given name" );
        }

//        Movie movie = new Movie();
//        movie.setMovieName( movieCreateDto.getMovieName() );

        movieRepository.save( movie );

        return this.mapToMovieResponseDto( movie );
    }


    private Movie mapToMovieResponseDto( Movie movie ) {

        return null;
    }


    private boolean movieListHasMovieWithSameName( long movieListId, String movieName ) {

        return false;
    }


    private Movie mapToMovie( MovieCreateDto movieCreateDto ) {

        return null;
    }
}
