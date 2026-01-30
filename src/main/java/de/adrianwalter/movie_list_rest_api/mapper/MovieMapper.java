package de.adrianwalter.movie_list_rest_api.mapper;

import de.adrianwalter.movie_list_rest_api.dto.movie.*;
import de.adrianwalter.movie_list_rest_api.dto.moviebatch.MovieBatchCreateCompleteDto;
import de.adrianwalter.movie_list_rest_api.dto.moviebatch.MovieBatchCreateDto;
import de.adrianwalter.movie_list_rest_api.dto.moviebatch.MovieBatchCreateOneLineDto;
import de.adrianwalter.movie_list_rest_api.dto.moviebatch.MovieResponseBatchCreateOneLineDtos;
import de.adrianwalter.movie_list_rest_api.entity.Movie;
import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.exception.InvalidBodyException;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class MovieMapper {


    private static final String REGEX_DIVIDER = "/";
    private static final String FIELD_DIVIDER = " / ";
    private static final int FIELD_COUNT = 7;


    public MovieResponseOneLineDto mapToMovieOneLineResponseDto( Movie movie ) {

        MovieResponseOneLineDto movieDto = new MovieResponseOneLineDto();

        // "Rating / Titel / ReleaseYear / Genre / Streamer / UserNote / SeenDate: 2023-11-13"

        // ToDo: Delete -1 test
        // ToDo: Optional verwenden
        String releaseYear = ( movie.getReleaseYear() == null || movie.getReleaseYear() == -1 ) ?
                "" : movie.getReleaseYear().toString();

        String seenAt = movie.getSeenAt() == null ? "" : movie.getSeenAt().toString();


        String movieInformation = String.valueOf( movie.getUserRating() );
        movieInformation += FIELD_DIVIDER + movie.getMovieTitle();
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
        responseDto.setMovieTitle( movie.getMovieTitle() );

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
        movie.setMovieTitle( movieCreateDto.getMovieTitle() );
        movie.setSeenOn( movieCreateDto.getSeenOn() );
        movie.setReleaseYear( movieCreateDto.getReleaseYear() );

        LocalDate seenAt = movieCreateDto.getSeenAt();
        movie.setSeenAt( seenAt != null ? seenAt : LocalDate.now() );


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


    public Movie mapToMovie( String[] oneLineMovieFields ) {

        Movie movie = new Movie();

        try {
            movie.setUserRating( Integer.parseInt( oneLineMovieFields[0] ) );
        } catch ( Exception e ) {
            movie.setUserRating( null );
        }
        movie.setMovieTitle( oneLineMovieFields[1] );

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


    Movie mapToMovie( MovieBatchCreateDto movieBatchCreateDto, MovieList movieList ) {

        Movie movie = new Movie();

        movie.setMovieList( movieList );
        movie.setUserRating( movieBatchCreateDto.getUserRating() );
        movie.setMovieTitle( movieBatchCreateDto.getMovieTitle() );
        movie.setSeenOn( movieBatchCreateDto.getSeenOn() );
        movie.setReleaseYear( movieBatchCreateDto.getReleaseYear() );

        LocalDate seenAt = movieBatchCreateDto.getSeenAt();
        movie.setSeenAt( seenAt != null ? seenAt : LocalDate.now() );

        if ( movieBatchCreateDto instanceof MovieBatchCreateCompleteDto movieBatchCreateCompleteDto ) {

            movie.setGenre( movieBatchCreateCompleteDto.getGenre() );
            movie.setUserNote( movieBatchCreateCompleteDto.getUserNote() );
        }

        return movie;
    }


    Movie mapToMovie( MovieBatchCreateOneLineDto movieBatchCreateOneLineDto, MovieList movieList ) {

        MovieCreateOneLineDto movieCreateOneLineDto = new MovieCreateOneLineDto();
        movieCreateOneLineDto.setMovieListId( movieList.getMovieListId() );
        movieCreateOneLineDto.setMovieInformation( movieBatchCreateOneLineDto.getMovieInformation() );

        return mapToMovie( movieCreateOneLineDto, movieList );
    }


    public Movie mapToMovie( Movie movie, @Valid MovieUpdateDto movieUpdateDto ) {

        if ( movieUpdateDto.getUserRating() != null ) {
            movie.setUserRating( movieUpdateDto.getUserRating() );
        }
        if ( movieUpdateDto.getMovieTitle() != null && !movieUpdateDto.getMovieTitle().isBlank() ) {
            movie.setMovieTitle( movieUpdateDto.getMovieTitle() );
        }
        if ( movieUpdateDto.getReleaseYear() != null ) {
            movie.setReleaseYear( movieUpdateDto.getReleaseYear() );
        }
        if ( movieUpdateDto.getSeenAt() != null ) {
            movie.setSeenAt( movieUpdateDto.getSeenAt() );
        }
        if ( movieUpdateDto.getSeenOn() != null && !movieUpdateDto.getSeenOn().isBlank() ) {
            movie.setSeenOn( movieUpdateDto.getSeenOn() );
        }
        if ( movieUpdateDto.getUserNote() != null && !movieUpdateDto.getUserNote().isBlank() ) {
            movie.setUserNote( movieUpdateDto.getUserNote() );
        }
        if ( movieUpdateDto.getGenre() != null && !movieUpdateDto.getGenre().isBlank() ) {
            movie.setGenre( movieUpdateDto.getGenre() );
        }

        return movie;
    }


    public MovieResponseBatchCreateOneLineDtos mapToMovieResponseBatchCreateOneLineDtos(
            long movieListId,
            @NonNull List< MovieResponseOneLineDto > oneLineDtos ) {

        MovieResponseBatchCreateOneLineDtos movieResponseBatch = new MovieResponseBatchCreateOneLineDtos();
        movieResponseBatch.setMovieListId( movieListId );
        movieResponseBatch.setMovies( oneLineDtos );

        return movieResponseBatch;
    }
}
