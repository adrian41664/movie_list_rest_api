package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListCreateDto;
import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListMovieOneLineResponseDto;
import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListUpdateDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserResponseShortDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserUpdateDto;
import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.service.movielist.MovieListService;
import de.adrianwalter.movie_list_rest_api.service.movielist.sortandsearch.MovieFilter;
import de.adrianwalter.movie_list_rest_api.service.movielist.sortandsearch.MovieListSortAndSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag( name = "movie-lists", description = "Manage Users Movie-Lists" )
@RestController
@RequestMapping( "/movie-lists" )
public class MovieListController {

    private final MovieListService movieListService;


    public MovieListController( MovieListService movieListService ) {

        this.movieListService = movieListService;
    }


    @Operation(
            summary = "Create a new movie-list",
            description = "Create a new movie-list. Name must be unique per user."
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully created a new movie-list",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema( implementation = UserResponseShortDto.class )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict - User already owns a movie-list with the given name",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    } )
    @PostMapping( "" )
    public ResponseEntity< MovieListMovieOneLineResponseDto > createNewMovieList(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Create a new movie-list for a given user. User can be given by name or ID",
                    required = true,
                    content = @Content( schema = @Schema( implementation = MovieListCreateDto.class ) ) )
            @Valid @RequestBody MovieListCreateDto requestBody ) {

        //@ToDo: Create StubResponse and return it

        MovieListMovieOneLineResponseDto oneLineMovieList = movieListService.create( requestBody );

        return ResponseEntity.ok( oneLineMovieList );
    }



    @Operation(
            summary = "Filter and search movies in a given movie list",
            description = "Search and filter movies within a specific movie list using various optional criteria"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Search results retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MovieListMovieOneLineResponseDto.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Movie list not found",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping( "/{movieListId}/search" )
    public ResponseEntity< MovieListMovieOneLineResponseDto > getMovieListSearch(

            @Parameter(description = "ID of the movie list", required = true, example = "1")
            @PathVariable Long movieListId,
            @Parameter(description = "Filter by movie title (partial match)", example = "Matrix")
            @RequestParam( required = false ) String title,
            @Parameter(description = "Filter by user rating status (rating equals zero or greater)", example = "true")
            @RequestParam( required = false ) Boolean isRated,
            @Parameter(description = "Filter by minimum rating", example = "3")
            @RequestParam( required = false ) Integer minRating,
            @Parameter(description = "Filter by maximum rating", example = "7")
            @RequestParam( required = false ) Integer maxRating,
            @Parameter(description = "Filter by specific year", example = "1999")
            @RequestParam( required = false ) Integer year,
            @Parameter(description = "Filter by minimum release year", example = "2000")
            @RequestParam( required = false ) Integer minYear,
            @Parameter(description = "Filter by maximum release year", example = "2010")
            @RequestParam( required = false ) Integer maxYear,
            @Parameter(description = "Filter by genre (partial match)", example = "Thriller")
            @RequestParam( required = false ) String genre,
            @Parameter(description = "Filter by streaming platform (partial match)", example = "Prime")
            @RequestParam( required = false ) String seenOn,
            @Parameter(description = "Search in user notes (partial match)", example = "terrific")
            @RequestParam( required = false ) String userNoteKeyword,
            @Parameter(
                    description = "Sort field",
                    example = "user-rating",
                    schema = @Schema(allowableValues = {"title, rating OR user-rating, year OR release-year, genre, date"})
            )
            @RequestParam( defaultValue = "title" ) String sortBy,
            @Parameter(description = "Sort direction (true = ascending, false = descending)", example = "true")
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
