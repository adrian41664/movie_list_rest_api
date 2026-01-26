package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListCreateDto;
import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListMovieOneLineResponseDto;
import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListUpdateDto;
import de.adrianwalter.movie_list_rest_api.entity.Movie;
import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.service.MovieListService;
import de.adrianwalter.movie_list_rest_api.sortandsearch.MovieFilter;
import de.adrianwalter.movie_list_rest_api.sortandsearch.MovieListSortAndSearchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/movie-lists" )
public class MovieListController {

    private final MovieListService movieListService;
    @Autowired
    private MovieListSortAndSearchService sortAndSearchService;


    public MovieListController( MovieListService movieListService,
                                MovieListSortAndSearchService sortAndSearchService ) {

        this.movieListService = movieListService;
        this.sortAndSearchService = sortAndSearchService;
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
    public ResponseEntity< MovieList > getMovieListSearch(
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


        // MovieListMovieOneLineResponseDto movieList = movieListService.findByIdAndMapToResponse( movieListId );


        // List to search in and/or filter
        MovieList movieList = movieListService.findById( movieListId );

        // createFilter
        MovieFilter filter = sortAndSearchService.buildFilter(
                title, isRated, minRating, maxRating, year, minYear, maxYear, genre, seenOn, userNoteKeyword
        );

        // filter and sorting
        List<Movie> filteredMovies = sortAndSearchService.filterAndSort(
                movieList.getMovies(),
                filter,
                sortBy,
                ascending
        );

        movieList.setMovies( filteredMovies );

        // @ToDo: Create mapping option for detailed and non detailed (OneLineResponse per Movie) movieList

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
