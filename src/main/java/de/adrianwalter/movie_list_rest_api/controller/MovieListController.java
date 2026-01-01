package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.payload.GetMovieListResponseDTO;
import de.adrianwalter.movie_list_rest_api.payload.PostMovieListDTO;
import de.adrianwalter.movie_list_rest_api.service.MovieListService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie-list")
public class MovieListController {

    private final MovieListService movieListService;

    public MovieListController(MovieListService movieListService) {

        this.movieListService = movieListService;
    }


    // ToDo: in test; ok
    @PostMapping("")
    public ResponseEntity<MovieList> createNewMovieList(@Valid @RequestBody PostMovieListDTO requestBody) {

        return ResponseEntity.ok(movieListService.create(requestBody));
    }


    // single list (of a certain user, because every list is tied to one user)
    // ToDo: in test; ok
    @GetMapping("/{movieListId}")
    public ResponseEntity<GetMovieListResponseDTO> getMovieList(@PathVariable Long movieListId ){

        GetMovieListResponseDTO movieList = movieListService.findById( movieListId );

        return ResponseEntity.ok(movieList);
    }


    // all lists of a certain user
    // ToDo: Implement method in movieListService
    // ToDo: Create DTO to avoid nested response json
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<MovieList>> getUsersMovieLists(@PathVariable Long userId ){
//
//        List<MovieList> usersMovieLists = movieListService.getUsersMovieLists( userId );
//
//        return ResponseEntity.ok(usersMovieLists);
//    }


}
