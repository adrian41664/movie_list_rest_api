package de.adrianwalter.movie_list_rest_api.mapper;

import de.adrianwalter.movie_list_rest_api.dto.movie.*;
import de.adrianwalter.movie_list_rest_api.entity.Movie;
import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.exception.InvalidBodyException;
import de.adrianwalter.movie_list_rest_api.service.MovieService;

import java.time.LocalDate;
import java.util.Arrays;

public class MovieMapper {

    public Movie mapToMovie( String[] oneLineMovieFields ) {

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

    public MovieResponseOneLineDto mapToMovieOneLineResponseDto( Movie movie ) {

        MovieResponseOneLineDto movieDto = new MovieResponseOneLineDto();

        movieDto.setMovieId( movie.getMovieId() );

        // "Rating / Titel / ReleaseYear / Genre / Streamer / UserNote / SeenDate: 2023-11-13"

        String divider = " / ";
        String movieInformation = String.valueOf( movie.getUserRating() );
        movieInformation +=  divider + movie.getMovieName();
        movieInformation +=  divider + movie.getReleaseYear();
        movieInformation +=  divider + movie.getGenre();
        movieInformation +=  divider + movie.getSeenOn();
        movieInformation +=  divider + movie.getUserNote();
        movieInformation +=  divider + movie.getSeenAt();

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


    public Movie mapToMovie( MovieCreateOneLineDto movieCreateOneLineDto, MovieList movieList, MovieService movieService ) {

        String[] oneLineMovieFields = movieService.splitAndTrim( movieCreateOneLineDto );

        final int expectedFieldCount = 7;

        if ( oneLineMovieFields.length == expectedFieldCount ) {

            Movie movie = mapToMovie( oneLineMovieFields );
            // MovieList movieList = this.movieListService.findById( movieCreateOneLineDto.getMovieListId() );

            movie.setMovieList( movieList );

            return movie;

        } else {

            throw new InvalidBodyException( "" );
        }

    }


    private String[] splitAndTrim( MovieCreateOneLineDto movieCreateOneLineDto ) {
        String[] oneLineMovieFields = movieCreateOneLineDto.getMovieInformation().split( "/" );

        return Arrays.stream( oneLineMovieFields )
                .map( String::trim )
                .toArray( String[]::new );
    }
}
