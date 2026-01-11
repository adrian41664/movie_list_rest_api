package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.dto.movieList.MovieListCreateDto;
import de.adrianwalter.movie_list_rest_api.dto.movieList.MovieListResponseDto;
import de.adrianwalter.movie_list_rest_api.dto.movieList.MovieListUpdateDto;
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

        MovieListResponseDto movieList = movieListService.createAndMapToResponse( requestBody );

        return ResponseEntity.ok( movieList );
    }


    // ToDo: in test; ok
    @GetMapping( "/{movieListId}" )
    public ResponseEntity< MovieListResponseDto > getMovieList( @PathVariable Long movieListId ) {

        MovieListResponseDto movieList = movieListService.findByIdAndMapToResponse( movieListId );

        return ResponseEntity.ok( movieList );
    }


    // all lists of a certain user
    // ToDo: in test; ok
    @GetMapping( "/user/{userName}" )
    public ResponseEntity< List< MovieListResponseDto > > getMovieListsOfUser( @PathVariable String userName ) {

        List< MovieListResponseDto > usersMovieListResponseDtos = movieListService.getUsersMovieLists( userName );

        return ResponseEntity.ok( usersMovieListResponseDtos );
    }


    // ToDo: in test; ok
    @GetMapping( "/{movieListName}/user/{userName}" )
    public ResponseEntity< MovieListResponseDto > getMovieListByNameAndUserName(
            @PathVariable String userName,
            @PathVariable String movieListName ) {

        MovieListResponseDto responseDto = movieListService.findByNameAndUserName( movieListName, userName );
        return ResponseEntity.ok( responseDto );
    }


    // ToDo: in test; ok
    @DeleteMapping( "/{movieListId}" )
    public ResponseEntity< MovieListResponseDto > deleteMovieList( @PathVariable Long movieListId ) {

        MovieListResponseDto responseDto = movieListService.deleteByIdAndMapToResponse( movieListId );
        return ResponseEntity.ok( responseDto );
    }


    // ToDo: in test; ok
    @PutMapping( "/{movieListId}" )
    public ResponseEntity< MovieListResponseDto > updateMovieList(
            @PathVariable Long movieListId, @RequestBody @Valid MovieListUpdateDto movieListUpdateDto ) {

        MovieListResponseDto responseDto = movieListService.update( movieListId, movieListUpdateDto );
        return ResponseEntity.ok( responseDto );
    }


}
