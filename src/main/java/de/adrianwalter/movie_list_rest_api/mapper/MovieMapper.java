package de.adrianwalter.movie_list_rest_api.mapper;

import de.adrianwalter.movie_list_rest_api.dto.movie.*;
import de.adrianwalter.movie_list_rest_api.entity.Movie;
import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.exception.InvalidBodyException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class MovieMapper {


    private static final String REGEX_DIVIDER = "/";
    private static final String FIELD_DIVIDER = " / ";
    private static final int FIELD_COUNT = 7;


    public MovieResponseOneLineDto mapToMovieOneLineResponseDto( Movie movie ) {

        MovieResponseOneLineDto movieDto = new MovieResponseOneLineDto();

        movieDto.setMovieId( movie.getMovieId() );

        // "Rating / Titel / ReleaseYear / Genre / Streamer / UserNote / SeenDate: 2023-11-13"

        // ToDo: Delete -1 test
        String releaseYear = ( movie.getReleaseYear() == null || movie.getReleaseYear() == -1 ) ?
                "" : movie.getReleaseYear().toString();

        String seenAt = movie.getSeenAt() == null ? "" : movie.getSeenAt().toString();


        String movieInformation = String.valueOf( movie.getUserRating() );
        movieInformation += FIELD_DIVIDER + movie.getMovieName();
        movieInformation += FIELD_DIVIDER + releaseYear;
        movieInformation += FIELD_DIVIDER + movie.getGenre();
        movieInformation += FIELD_DIVIDER + movie.getSeenOn();
        movieInformation += FIELD_DIVIDER + movie.getUserNote();
        movieInformation += FIELD_DIVIDER + seenAt;

        movieDto.setMovieInformation( movieInformation );

        return movieDto;
    }


    public MovieResponseBasicFullOwnershipDto mapToMovieResponseDto( Movie movie ) {

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


    public Movie mapToMovie( MovieCreateDto movieCreateDto, MovieList movieList ) {

        Movie movie = new Movie();

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


    public Movie mapToMovie( MovieCreateOneLineDto movieCreateOneLineDto, MovieList movieList ) {

        String[] oneLineMovieFields = this.splitAndTrim( movieCreateOneLineDto );

        if ( oneLineMovieFields.length != FIELD_COUNT ) {

            throw new InvalidBodyException( "" );


        }
        Movie movie = mapToMovie( oneLineMovieFields );
        movie.setMovieList( movieList );

        return movie;
    }


    private Movie mapToMovie( String[] oneLineMovieFields ) {

        Movie movie = new Movie();

        try {
            movie.setUserRating( Integer.parseInt( oneLineMovieFields[0] ) );
        } catch ( Exception e ) {
            movie.setUserRating( null );
        }
        movie.setMovieName( oneLineMovieFields[1] );

        try {
            movie.setReleaseYear( Integer.parseInt( oneLineMovieFields[2] ) );
        } catch ( Exception e ) {
            movie.setReleaseYear( null );
        }

        movie.setGenre( oneLineMovieFields[3] );
        movie.setSeenOn( oneLineMovieFields[4] );
        movie.setUserNote( oneLineMovieFields[5] );

        if ( !oneLineMovieFields[6].isBlank() ) {
            try {
                movie.setSeenAt( LocalDate.parse( oneLineMovieFields[6] ) );
            } catch ( Exception e ) {
                movie.setSeenAt( null );
            }
        }

        return movie;
    }


    private String[] splitAndTrim( MovieCreateOneLineDto movieCreateOneLineDto ) {
        String[] oneLineMovieFields = movieCreateOneLineDto.getMovieInformation().split( REGEX_DIVIDER );

        return Arrays.stream( oneLineMovieFields )
                .map( String::trim )
                .toArray( String[]::new );
    }
}
