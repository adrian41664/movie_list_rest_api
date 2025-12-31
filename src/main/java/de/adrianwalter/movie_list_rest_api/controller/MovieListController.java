package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.payload.GetMovieListResponseDTO;
import de.adrianwalter.movie_list_rest_api.payload.PostMovieListDTO;
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

    // in test: OK
    @PostMapping("")
    public ResponseEntity<MovieList> createNewMovieList(@Valid @RequestBody PostMovieListDTO requestBody) {

        return ResponseEntity.ok(movieListService.create(requestBody));
    }


    // single list (of a certain user, because every list is tied to one user)
    // in test: OK,
    // Done: Response nested with User
    @GetMapping("/{movieListId}")
    public ResponseEntity<GetMovieListResponseDTO> getMovieList(@PathVariable Long movieListId ){

        GetMovieListResponseDTO movieList = movieListService.findById( movieListId );

        return ResponseEntity.ok(movieList);
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


}
