package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.payload.CreateMovieListDTO;
import de.adrianwalter.movie_list_rest_api.service.MovieListService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie-list")
public class MovieListController {

    private final MovieListService movieListService;

    public MovieListController(MovieListService movieListService) {
        this.movieListService = movieListService;
    }

    // all lists of all users
    // not sure if needed
//    @GetMapping("")
//    public ResponseEntity<Page<MovieList>> getAllMovieLists(Pageable pageable){
//
//        return ResponseEntity.ok(movieListService.findAll(pageable));
//    }

    // all lists of a certain user
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<MovieList>> getUsersMovieLists(@PathVariable Long userId ){
//
//        List<MovieList> usersMovieLists = movieListService.getUsersMovieLists( userId );
//
//        return ResponseEntity.ok(usersMovieLists);
//    }

    // in test: OK
    @PostMapping("")
    public ResponseEntity<MovieList> createNewMovieList(@Valid @RequestBody CreateMovieListDTO requestBody) {

        return ResponseEntity.ok(movieListService.create(requestBody));
    }


    // single list (of a certain user, because every list is tied to one user)
    // ToDo: Response nested with User
    // ToDo: create test in Postman
    @GetMapping("/{movieListId}")
    public ResponseEntity<MovieList> getMovieList(@PathVariable Long movieListId ){

        MovieList movieList = movieListService.findById( movieListId );

        return ResponseEntity.ok(movieList);
    }
}
