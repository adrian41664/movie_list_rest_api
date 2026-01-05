package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.payload.MovieListResponseDto;
import de.adrianwalter.movie_list_rest_api.payload.MovieListCreateDto;
import de.adrianwalter.movie_list_rest_api.service.MovieListService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/movie-lists" )
public class MovieListController {

    private final MovieListService movieListService;


    public MovieListController( MovieListService movieListService ) {

        this.movieListService = movieListService;
    }


    // ToDo: in test; ok
    @PostMapping( "" )
    public ResponseEntity< MovieListResponseDto > createNewMovieList( @Valid @RequestBody MovieListCreateDto requestBody ) {

        MovieListResponseDto movieList = movieListService.create( requestBody );

        return ResponseEntity.ok( movieList );
    }


    // ToDo: in test; ok
    @GetMapping( "/{movieListId}" )
    public ResponseEntity< MovieListResponseDto > getMovieList( @PathVariable Long movieListId ) {

        MovieListResponseDto movieList = movieListService.findById( movieListId );

        return ResponseEntity.ok( movieList );
    }


    // all lists of a certain user
    // ToDo: in test; ok
    @GetMapping( "/user/{userId}" )
    public ResponseEntity< List< MovieListResponseDto > > getMovieListsOfUser( @PathVariable Long userId ) {

        List< MovieListResponseDto > usersMovieLists = movieListService.getUsersMovieLists( userId );

        return ResponseEntity.ok( usersMovieLists );
    }


    // all lists of a certain user
    // ToDo: in test; ok
    @GetMapping( "/user/{userName}" )
    public ResponseEntity< List< MovieListResponseDto > > getMovieListsOfUser( @PathVariable String userName ) {

        List< MovieListResponseDto > usersMovieLists = movieListService.getUsersMovieLists( userName );

        return ResponseEntity.ok( usersMovieLists );
    }


    // ToDo: in test; ok
    @GetMapping( "/{movieListName}/user/{userName}" )
    public ResponseEntity< MovieListResponseDto > getMovieListByNameAndUserName(
            @PathVariable String userName,
            @PathVariable String movieListName ) {

        MovieListResponseDto responseDto = movieListService.findByNameAndUserName( movieListName, userName );
        return ResponseEntity.ok( responseDto );
    }


    /*
    Spring Boot has problems handling "/{movieListName}/user/{userId}" and "/{movieListName}/user/{userName}"
    at the same time, by same mapping annotation
     */
    // @GetMapping( "/{movieListName}/user/{userId}" )
    public ResponseEntity< MovieListResponseDto > UNUSED_getMovieListByNameAndUserId(
            @PathVariable String movieListName,
            @PathVariable long userId ) {

        MovieListResponseDto responseDto = movieListService.findByNameAndUserId( movieListName, userId );
        return ResponseEntity.ok( responseDto );
    }


}
