package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.dto.movie.*;
import de.adrianwalter.movie_list_rest_api.dto.moviebatch.MovieBatchCreateSubTypeMarker;
import de.adrianwalter.movie_list_rest_api.dto.moviebatch.MovieResponseBatchCreateOneLineDtos;
import de.adrianwalter.movie_list_rest_api.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/movies" )
public class MovieController {


    private final MovieService movieService;


    public MovieController( MovieService movieService ) {

        this.movieService = movieService;
    }


    // ToDo: in test; all three types of requestBody ok
    @PostMapping( "" )
    public ResponseEntity< MovieResponseBasicFullOwnershipDto > createNewMovie(
            @Valid @RequestBody MovieCreateSubTypeMarker requestBody ) {

        MovieResponseBasicFullOwnershipDto movie = movieService.createAndMapToResponse( requestBody );

        return ResponseEntity.ok( movie );
    }


    @PostMapping( "/batch" )
    public ResponseEntity< MovieResponseBatchCreateOneLineDtos > createNewMovies(
            @Valid @RequestBody MovieBatchCreateSubTypeMarker requestBody ) {

        MovieResponseBatchCreateOneLineDtos movies = movieService.createAndMapToResponse( requestBody );

        return ResponseEntity.ok( movies );
    }


//    @GetMapping( "/{movieId}" )
//    public ResponseEntity< MovieListResponseDto > getMovieList( @PathVariable Long movieListId ) {
//
//        MovieListResponseDto movieList = movieListService.findByIdAndMapToResponse( movieListId );
//
//        return ResponseEntity.ok( movieList );
//    }


    // all movies of a certain user
//    @GetMapping( "/user/{userName}" )
//    public ResponseEntity< List< MovieResponseDto > > getMoviesOfUser( @PathVariable String userName ) {
//
//        List< MovieResponseDto > usersMovieResponseDtos = movieService.getUsersMovies( userName );
//
//        return ResponseEntity.ok( usersMovieResponseDtos );
//    }


//    @PutMapping( "/{movieId}" )
//    public ResponseEntity< MovieResponseDto > updateMovie(
//            @PathVariable Long movieId, @RequestBody @Valid MovieUpdateDto movieUpdateDto ) {
//
//        MovieResponseDto responseDto = movieListService.update( movieListId, movieListUpdateDto );
//        return ResponseEntity.ok( responseDto );
//    }


//    @PutMapping( "/{movieName}/movie-list/{movieListName}/user/{userName}" )
//    public ResponseEntity< MovieResponseDto > updateMovie(
//            @PathVariable Long movieId, @RequestBody @Valid MovieUpdateDto movieUpdateDto ) {
//
//        MovieResponseDto responseDto = movieListService.update( movieListId, movieListUpdateDto );
//        return ResponseEntity.ok( responseDto );
//    }


//    @DeleteMapping( "/{movieId}" )
//    public ResponseEntity< MovieResponseDto > deleteMovie( @PathVariable Long movieId ) {
//
//        MovieResponseDto responseDto = movieService.deleteByIdAndMapToResponse( movieId );
//        return ResponseEntity.ok( responseDto );
//    }


}

