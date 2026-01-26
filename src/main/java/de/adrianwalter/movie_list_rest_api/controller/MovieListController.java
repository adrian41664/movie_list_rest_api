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
    @Autowired
    private MovieListSortAndSearchService sortAndSearchService;


    public MovieListController( MovieListService movieListService,
                                MovieListSortAndSearchService sortAndSearchService ) {

        this.movieListService = movieListService;

        // @ToDo: move SortAndSearchService into movieListService
        this.sortAndSearchService = sortAndSearchService;
    }


    // ToDo: in test; ok
    @PostMapping( "" )
    public ResponseEntity< MovieListMovieOneLineResponseDto > createNewMovieList(
            @Valid @RequestBody MovieListCreateDto requestBody ) {

        MovieListMovieOneLineResponseDto oneLineMovieList = movieListService.create( requestBody );

        //@ToDo: Create StubResponse and return it
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
            @RequestParam( defaultValue = "true" ) Boolean ascending,
            @RequestParam( required = false, defaultValue = "false" ) boolean detailed ) {

        // @ToDo:
        // movieListService.findByIdFilteredDetails -> MapToDetailedResponse
        // movieListService.findByIdFilteredCompact -> MapToOneLineResponse

        // List to search in and/or filter
        MovieList movieList = movieListService.findById( movieListId );

        // createFilter
        MovieFilter filter = sortAndSearchService.buildFilter(
                title, isRated, minRating, maxRating, year, minYear, maxYear, genre, seenOn, userNoteKeyword
        );

        // filtering and sorting
        movieList.setMovies( sortAndSearchService.filterAndSort(
                movieList.getMovies(),
                filter,
                sortBy,
                ascending
        ) );


        MovieListMovieOneLineResponseDto oneLineMovieList =
                movieListService.mapToCompact( movieList );

        if ( detailed ) {

            // @ToDo
            return ResponseEntity.ok( oneLineMovieList );
        }

        return ResponseEntity.ok( oneLineMovieList );
    }


    @GetMapping( "/{movieListId}" )
    public ResponseEntity< MovieListMovieOneLineResponseDto > getMovieList(
            @PathVariable Long movieListId,
            @RequestParam( required = false, defaultValue = "false" ) boolean detailed ) {

        // @ToDo:
        //  movieListService.findByIdCompact -> MapToOneLineResponse

        MovieListMovieOneLineResponseDto mappedMovieList = movieListService.findByIdCompact( movieListId );

        if ( detailed ) {

            // @ToDo
            return ResponseEntity.ok( mappedMovieList );
        }

        return ResponseEntity.ok( mappedMovieList );
    }


    // all lists of a certain user
    // ToDo: in test; ok
    @GetMapping( "/user/{userName}" )
    public ResponseEntity< List< MovieListMovieOneLineResponseDto > > getMovieListsOfUser(
            @PathVariable String userName,
            @RequestParam( required = false, defaultValue = "false" ) boolean detailed ) {

        // @ToDo:
        // movieListService.findByUserNameCompact -> MapToOneLineResponses
        // movieListService.findByUserNameDetails -> MapToStubResponses

        List< MovieListMovieOneLineResponseDto > usersOneLineMovieLists = movieListService.findByUserName( userName );

        if ( detailed ) {

            return ResponseEntity.ok( usersOneLineMovieLists );
        }

        // @ToDo: Create MovieListStub as response
        return ResponseEntity.ok( usersOneLineMovieLists );
    }


    // ToDo: in test; ok
    @GetMapping( "/{movieListName}/user/{userName}" )
    public ResponseEntity< MovieListMovieOneLineResponseDto > getMovieListByNameAndUserName(
            @PathVariable String userName,
            @PathVariable String movieListName,
            @RequestParam( required = false, defaultValue = "false" ) boolean detailed ) {

        // @ToDo:
        // movieListService.findByNameAndUserNameCompact -> MapToOneLineResponses
        // movieListService.findByNameAndUserNameDetails -> MapToDetailledResponses


        MovieListMovieOneLineResponseDto oneLineMovieList = movieListService.findByNameAndUserName(
                movieListName, userName
        );

        if ( detailed ) {

            // @ToDo
            return null;
        }

        return ResponseEntity.ok( oneLineMovieList );
    }


    // ToDo: in test; ok
    @DeleteMapping( "/{movieListId}" )
    public ResponseEntity< MovieListMovieOneLineResponseDto > deleteMovieList( @PathVariable Long movieListId ) {

        MovieListMovieOneLineResponseDto oneLineMovieList = movieListService.deleteById(
                movieListId
        );
        
        return ResponseEntity.ok( oneLineMovieList );
    }


    // ToDo: in test; ok
    @PutMapping( "/{movieListId}" )
    public ResponseEntity< MovieListMovieOneLineResponseDto > updateMovieList(
            @PathVariable Long movieListId, @RequestBody @Valid MovieListUpdateDto movieListUpdateDto ) {

        MovieListMovieOneLineResponseDto oneLineMovieList = movieListService.update(
                movieListId, movieListUpdateDto
        );

        // @ToDo: Create MovieListStub as response
        return ResponseEntity.ok( oneLineMovieList );
    }


}
