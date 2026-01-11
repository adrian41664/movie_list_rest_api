package de.adrianwalter.movie_list_rest_api.service;

import de.adrianwalter.movie_list_rest_api.dto.movie.*;
import de.adrianwalter.movie_list_rest_api.entity.Movie;
import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.exception.InvalidBodyException;
import de.adrianwalter.movie_list_rest_api.exception.NameAlreadyExistsException;
import de.adrianwalter.movie_list_rest_api.exception.ResourceNotFoundException;
import de.adrianwalter.movie_list_rest_api.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    private MovieListService movieListService;


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


    public MovieResponseBasicFullOwnershipDto createAndMapToResponse( MovieCreateSubTypeMarker movieCreateSubType ) {

        Movie movie = this.mapToMovie( movieCreateSubType );

        long movieListId = movie.getMovieList().getMovieListId();
        String movieName = movie.getMovieName();

        if ( this.movieListHasMovieWithSameName( movieListId, movieName ) ) {

            throw new NameAlreadyExistsException(
                    "Cant create new Movie; MovieList already owns Movie with the given name" );
        }
        if ( movieName.isBlank() ) {

            throw new InvalidBodyException(
                    "Cant create new Movie; Given name is blank" );
        }

        movieRepository.save( movie );

        return this.mapToMovieResponseDto( movie );
    }


    private MovieResponseBasicFullOwnershipDto mapToMovieResponseDto( Movie movie ) {

        MovieResponseBasicFullOwnershipDto responseDto = new MovieResponseBasicFullOwnershipDto();

        responseDto.setMovieId( movie.getMovieId() );
        responseDto.setMovieName( movie.getMovieName() );

        responseDto.setUserRating( movie.getUserRating() );
        responseDto.setReleaseYear( movie.getReleaseYear() );
        responseDto.setGenre( movie.getGenre() );
        responseDto.setSeenOn( movie.getSeenOn() );
        responseDto.setSeenAt( movie.getSeenAt() );
        responseDto.setUserNote( movie.getUserNote() );

        responseDto.setMovieListId( movie.getMovieList().getMovieListId() );
        responseDto.setMovieListName( movie.getMovieList().getMovieListName() );

        responseDto.setUserId( movie.getMovieList().getUser().getUserId() );
        responseDto.setUserName( movie.getMovieList().getUser().getUserName() );

        return responseDto;
    }


    private boolean movieListHasMovieWithSameName( long movieListId, String movieName ) {

        Optional< Movie > movieNameSearch = movieRepository
                .findByMovieList_MovieListIdAndMovieName( movieListId, movieName );

        return movieNameSearch.isPresent();
    }


    private Movie mapToMovie( MovieCreateSubTypeMarker movieCreateSubTypeDto ) {

        if ( movieCreateSubTypeDto instanceof MovieCreateDto movieCreateDto ) {

            return this.mapToMovie( movieCreateDto );

        } else if ( movieCreateSubTypeDto instanceof MovieCreateOneLineDto movieCreateOneLineDto ) {

            return this.mapToMovie( movieCreateOneLineDto );

        } else {

            throw new InvalidBodyException( "Body is invalid!" );
        }

    }


    private Movie mapToMovie( MovieCreateDto movieCreateDto ) {

        Movie movie = new Movie();

        MovieList movieList = this.movieListService.findById( movieCreateDto.getMovieListId() );

        movie.setMovieList( movieList );
        movie.setUserRating( movieCreateDto.getUserRating() );
        movie.setMovieName( movieCreateDto.getMovieName() );
        movie.setSeenOn( movieCreateDto.getSeenOn() );
        movie.setReleaseYear( movieCreateDto.getReleaseYear() );

        if ( movieCreateDto.getSeenAt() != null ) {

            movie.setSeenAt( movieCreateDto.getSeenAt() );
        } else {

            movie.setSeenAt( LocalDate.now() );
        }

        if ( movieCreateDto instanceof MovieCreateCompleteDto movieCreateCompleteDto ) {

            movie.setGenre( movieCreateCompleteDto.getGenre() );
            movie.setUserNote( movieCreateCompleteDto.getUserNote() );
        }

        return movie;
    }


    private Movie mapToMovie( MovieCreateOneLineDto movieCreateOneLineDto ) {

        String[] oneLineMovieFields = this.splitAndTrim( movieCreateOneLineDto );

        final int expectedFieldCount = 7;

        if ( oneLineMovieFields.length == expectedFieldCount ) {

            Movie movie = this.mapToMovie( oneLineMovieFields );
            MovieList movieList = this.movieListService.findById( movieCreateOneLineDto.getMovieListId() );

            movie.setMovieList( movieList );

            return movie;

        } else {

            throw new InvalidBodyException( "" );
        }

    }


    private String[] splitAndTrim( MovieCreateOneLineDto movieCreateOneLineDto ){
        String[] oneLineMovieFields = movieCreateOneLineDto.getMovieInformation().split( ";" );

        return Arrays.stream( oneLineMovieFields )
                .map( String::trim )
                .toArray( String[]::new );
    }

    private Movie mapToMovie( String[] oneLineMovieFields ){

        Movie movie = new Movie();

        try {

            movie.setUserRating( Integer.parseInt( oneLineMovieFields[0] ) );
            movie.setMovieName( oneLineMovieFields[1] );
            movie.setReleaseYear( Integer.parseInt( oneLineMovieFields[2] ) );
            movie.setGenre( oneLineMovieFields[3] );
            movie.setSeenOn( oneLineMovieFields[4] );
            movie.setUserNote( oneLineMovieFields[5] );

            if ( !oneLineMovieFields[6].isBlank() ) {

                movie.setSeenAt( LocalDate.parse( oneLineMovieFields[6] ) );
            } else {

                movie.setSeenAt( LocalDate.now() );
            }
        } catch ( Exception e ) {

            throw new InvalidBodyException( "" );
        }
        return movie;
    }


}