package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.payload.MovieListResponseDto;
import de.adrianwalter.movie_list_rest_api.payload.MovieListCreateDto;
import de.adrianwalter.movie_list_rest_api.service.MovieListService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    /* ToDo: Implement:
    @GetMapping("/user/{userId})
    public ResponseEntity<List<MovieListReadResponseDto>> getUsersMovieLists(@PathVariable Long userId ){

        List<MovieListReadResponseDto> usersMovieLists = movieListService.getUsersMovieLists( userId );

        return ResponseEntity.ok(usersMovieLists);
    }
    */


    /* ToDo: Implement:
    @GetMapping("/{movieListName}/user/{userName}/")
    public ResponseEntity<MovieListReadResponseDto> getMovieListByNameAndUserId(
            @PathVariable String userName,
            @PathVariable String movieListName) {

        MovieListReadResponseDto movieList = movieListService
                .findByUserAndName(userName, movieListName);
        return ResponseEntity.ok(movieList);
    }
    */


}
