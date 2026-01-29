package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListCreateDto;
import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListMovieOneLineResponseDto;
import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListUpdateDto;
import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.service.movielist.MovieListService;
import de.adrianwalter.movie_list_rest_api.service.movielist.sortandsearch.MovieFilter;
import de.adrianwalter.movie_list_rest_api.service.movielist.sortandsearch.MovieListSortAndSearchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity< MovieListMovieOneLineResponseDto > createNewMovieList(
            @Valid @RequestBody MovieListCreateDto requestBody ) {

        //@ToDo: Create StubResponse and return it

        MovieListMovieOneLineResponseDto oneLineMovieList = movieListService.create( requestBody );

        return ResponseEntity.ok( oneLineMovieList );
    }


    // ToDo: in test; ok
    @GetMapping( "/{movieListId}/search" )
    public ResponseEntity< ? > getMovieListSearch(
            @PathVariable Long movieListId,
            @RequestParam( required = false ) String title,
            @RequestParam( required = false ) Boolean isRated,
            @RequestParam( required = false ) Integer minRating,
            @RequestParam( required = false ) Integer maxRating,
            @RequestParam( required = false ) Integer year,
            @RequestParam( required = false ) Integer minYear,
            @RequestParam( required = false ) Integer maxYear,
            @RequestParam( required = false ) String genre,
            @RequestParam( required = false ) String seenOn,
            @RequestParam( required = false ) String userNoteKeyword,
            @RequestParam( defaultValue = "title" ) String sortBy,
            @RequestParam( defaultValue = "true" ) Boolean ascending ) {

        // @ToDo: create and implement optional detailed response

        // List to search in and/or filter

        MovieListMovieOneLineResponseDto movieList = movieListService.findByIdSortAndSearch(
                movieListId,
                title,
                isRated,
                minRating,
                maxRating,
                year,
                minYear,
                maxYear,
                genre,
                seenOn,
                userNoteKeyword,
                sortBy,
                ascending );

        return ResponseEntity.ok( movieList );
    }


    @GetMapping( "/{movieListId}" )
    public ResponseEntity< MovieListMovieOneLineResponseDto > getMovieList( @PathVariable Long movieListId ) {

        // @ToDo: create and implement optional detailed response

        MovieListMovieOneLineResponseDto mappedMovieList = movieListService.findByIdCompact( movieListId );

        return ResponseEntity.ok( mappedMovieList );
    }


    // all lists of a certain user
    // ToDo: in test; ok
    @GetMapping( "/user/{userName}" )
    public ResponseEntity< List< MovieListMovieOneLineResponseDto > > getMovieListsOfUser( @PathVariable String userName ) {

        // @ToDo: Create MovieListStub as response
        // @ToDo: create and implement optional detailed response

        List< MovieListMovieOneLineResponseDto > usersOneLineMovieLists = movieListService.findByUserName( userName );

        return ResponseEntity.ok( usersOneLineMovieLists );
    }


    // ToDo: in test; ok
    @GetMapping( "/{movieListName}/user/{userName}" )
    public ResponseEntity< MovieListMovieOneLineResponseDto > getMovieListByNameAndUserName(
            @PathVariable String userName,
            @PathVariable String movieListName ) {

        // @ToDo create and implement optional detailed response

        MovieListMovieOneLineResponseDto oneLineMovieList = movieListService.findByNameAndUserName(
                movieListName, userName
        );


        return ResponseEntity.ok( oneLineMovieList );
    }


    // ToDo: in test; ok
    @DeleteMapping( "/{movieListId}" )
    public ResponseEntity< MovieListMovieOneLineResponseDto > deleteMovieList( @PathVariable Long movieListId ) {

        MovieListMovieOneLineResponseDto oneLineMovieList = movieListService.deleteById( movieListId );

        return ResponseEntity.ok( oneLineMovieList );
    }


    // ToDo: in test; ok
    @PutMapping( "/{movieListId}" )
    public ResponseEntity< MovieListMovieOneLineResponseDto > updateMovieList(
            @PathVariable Long movieListId, @RequestBody @Valid MovieListUpdateDto movieListUpdateDto ) {

        MovieListMovieOneLineResponseDto oneLineMovieList = movieListService.update( movieListId, movieListUpdateDto );

        // @ToDo: Create MovieListStub as response
        return ResponseEntity.ok( oneLineMovieList );
    }


}
