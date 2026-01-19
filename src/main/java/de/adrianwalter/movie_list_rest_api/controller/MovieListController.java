package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListCreateDto;
import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListMovieOneLineResponseDto;
import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListUpdateDto;
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
    public ResponseEntity< MovieListMovieOneLineResponseDto > createNewMovieList( @Valid @RequestBody MovieListCreateDto requestBody ) {

        MovieListMovieOneLineResponseDto movieList = movieListService.createAndMapToResponse( requestBody );

        return ResponseEntity.ok( movieList );
    }


    // ToDo: in test; ok
    /* ToDo: Add optional sorting filters and search filters

        Filter:
        - Detailed or OneLineMovies
        - Sorting for every field value
        - Search for every field value (show only Thrillers for Example)
     */
    @GetMapping( "/{movieListId}/search" )
    public ResponseEntity< MovieListMovieOneLineResponseDto > getMovieListSearch(
            @PathVariable Long movieListId,
            @RequestParam( required = false ) String name,
            @RequestParam( required = false ) int rating,
            @RequestParam( required = false ) int releaseYear ,
            @RequestParam( required = false ) String seenOn,
            @RequestParam( required = false ) String userNote,
            @RequestParam( required = false ) String genre,
            @RequestParam( defaultValue = "seenAt" ) String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
            ) {

        MovieListMovieOneLineResponseDto movieList = movieListService.findByIdAndMapToResponse( movieListId );




        return ResponseEntity.ok( movieList );
    }


    @GetMapping( "/{movieListId}" )
    public ResponseEntity< MovieListMovieOneLineResponseDto > getMovieList( @PathVariable Long movieListId ) {

        MovieListMovieOneLineResponseDto movieList = movieListService.findByIdAndMapToResponse( movieListId );

        return ResponseEntity.ok( movieList );
    }


    // all lists of a certain user
    // ToDo: in test; ok
    @GetMapping( "/user/{userName}" )
    public ResponseEntity< List< MovieListMovieOneLineResponseDto > > getMovieListsOfUser( @PathVariable String userName ) {

        List< MovieListMovieOneLineResponseDto > usersMovieListMovieOneLineResponseDtos = movieListService.getUsersMovieLists( userName );

        return ResponseEntity.ok( usersMovieListMovieOneLineResponseDtos );
    }


    // ToDo: in test; ok
    @GetMapping( "/{movieListName}/user/{userName}" )
    public ResponseEntity< MovieListMovieOneLineResponseDto > getMovieListByNameAndUserName(
            @PathVariable String userName,
            @PathVariable String movieListName ) {

        MovieListMovieOneLineResponseDto responseDto = movieListService.findByNameAndUserName( movieListName, userName );
        return ResponseEntity.ok( responseDto );
    }


    // ToDo: in test; ok
    @DeleteMapping( "/{movieListId}" )
    public ResponseEntity< MovieListMovieOneLineResponseDto > deleteMovieList( @PathVariable Long movieListId ) {

        MovieListMovieOneLineResponseDto responseDto = movieListService.deleteByIdAndMapToResponse( movieListId );
        return ResponseEntity.ok( responseDto );
    }


    // ToDo: in test; ok
    @PutMapping( "/{movieListId}" )
    public ResponseEntity< MovieListMovieOneLineResponseDto > updateMovieList(
            @PathVariable Long movieListId, @RequestBody @Valid MovieListUpdateDto movieListUpdateDto ) {

        MovieListMovieOneLineResponseDto responseDto = movieListService.update( movieListId, movieListUpdateDto );
        return ResponseEntity.ok( responseDto );
    }


}
