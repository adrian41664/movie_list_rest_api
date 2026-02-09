package de.adrianwalter.movie_list_rest_api.controller;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import de.adrianwalter.movie_list_rest_api.dto.movie.*;
import de.adrianwalter.movie_list_rest_api.dto.moviebatch.*;
import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListCreateByUserIdBodyDto;
import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListCreateByUserNameBodyDto;
import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListMovieOneLineResponseDto;
import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListUpdateDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserResponseShortDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserUpdateDto;
import de.adrianwalter.movie_list_rest_api.service.MovieService;
import de.adrianwalter.movie_list_rest_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag( name = "movies", description = "Manage movies " )
@RestController
@RequestMapping( "/movies" )
public class MovieController {


    private final MovieService movieService;


    public MovieController( MovieService movieService ) {

        this.movieService = movieService;
    }


    @Operation(
            summary = "Create a new movie",
            description = "Create a new movie. Name must be unique per movie-list. Use one of the three types " +
                    "for creating a new movie. " )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully created a new movie",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema( implementation = MovieResponseBasicFullOwnershipDto.class )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict - Movie-list already contains movie with the given name",
                    content = @Content( schema = @Schema( hidden = true ) )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content( schema = @Schema( hidden = true ) )
            )
    } )
    @PostMapping( "" )
    public ResponseEntity< MovieResponseBasicFullOwnershipDto > createNewMovie(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Create a new movie on a given movie-list. " +
                            "'type'-value can be 'oneLine', 'basic' or 'complete'." +
                            " Make sure you use the following syntax for the value of 'movieInformation' of " +
                            "the oneLine-type: <br><b>'" +
                            "userRating (int or blank) / movieTitle (String) / releaseYear (int or blank) /" +
                            " genre (String or blank) / seenOn (String or blank) / userNotes (String or blank) /" +
                            " seenAt (YYYY-MM-DD or blank)'"
                            + "</b>",
            required = true,
                    content = @Content(
                            schema = @Schema( oneOf = {
                                    MovieCreateBasicDto.class,
                                    MovieCreateOneLineDto.class,
                                    MovieCreateCompleteDto.class
                            } ) ) )
            @Valid @RequestBody MovieCreateSubTypeMarker requestBody ) {

        MovieResponseBasicFullOwnershipDto movie = movieService.createAndMapToResponse( requestBody );

        return ResponseEntity.ok( movie );
    }


    @Operation(
            summary = "Create a batch of new movies at once.",
            description = "Create a batch of new movies at once. Names must be unique per movie-list." +
                "Use one of the three types for creating a batch of new movies. In case of error no movie is added."
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully created a batch of new movies",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema( implementation = MovieResponseBasicFullOwnershipDto.class )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict - Movie-list already contains one or more movies with the given names",
                    content = @Content( schema = @Schema( hidden = true ) )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content( schema = @Schema( hidden = true ) )
            )
    } )
    @PostMapping( "/batch" )
    public ResponseEntity< MovieResponseBatchCreateOneLineDtos > createNewMovies(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Create a batch of new movies on a given movie-list. " +
                            "'type'-value of a batch can be 'oneLine', 'basic' or 'complete'." +
                            " Make sure you use the following syntax for the value of 'movieInformation' of " +
                            "the oneLine-type: <br><b>'" +
                            "userRating (int or blank) / movieTitle (String) / releaseYear (int or blank) /" +
                            " genre (String or blank) / seenOn (String or blank) / userNotes (String or blank) /" +
                            " seenAt (YYYY-MM-DD or blank)'"
                            + "</b>",
                    required = true,
                    content = @Content(
                            schema = @Schema( oneOf = {
                                    MovieBatchCreateBasicDtos.class,
                                    MovieBatchCreateOneLineDtos.class,
                                    MovieBatchCreateCompleteDtos.class
                            } ) ) )
            @Valid @RequestBody MovieBatchCreateSubTypeMarker requestBody ) {

        MovieResponseBatchCreateOneLineDtos movies = movieService.createAndMapToResponse( requestBody );

        return ResponseEntity.ok( movies );
    }


    @Operation(
            summary = "Update a movie",
            description = "Change attributes of a movie. Name must be unique."
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully applied changes",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema( implementation = MovieListMovieOneLineResponseDto.class )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - No movie with given ID",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict - Name is already taken",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    } )
    @PutMapping( "/{movieId}" )
    public ResponseEntity< MovieResponseBasicFullOwnershipDto > updateMovie(
            @Parameter( description = "ID of movie-list to update", required = true )
            @PathVariable Long movieId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New attributes of a movie. Name must be unique. Empty values leads to no changes.",
                    required = true,
                    content = @Content( schema = @Schema( implementation = MovieUpdateDto.class ) ) )
            @RequestBody @Valid MovieUpdateDto movieUpdateDto ) {

        MovieResponseBasicFullOwnershipDto responseDto = movieService.update( movieId, movieUpdateDto );

        return ResponseEntity.ok( responseDto );
    }


    @Operation( summary = "Get a movie by ID" )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Movie retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema( implementation = MovieResponseBasicFullOwnershipDto.class ) )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - No movie with given ID",
                    content = @Content( schema = @Schema( hidden = true ) )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content( schema = @Schema( hidden = true ) )
            )
    } )
    @GetMapping( "/{movieId}" )
    public ResponseEntity< MovieResponseBasicFullOwnershipDto > getMovie(
            @Parameter( description = "ID of movie to retrieve", required = true )
            @PathVariable Long movieId ) {

        MovieResponseBasicFullOwnershipDto responseDto = movieService.findMovie( movieId );

        return ResponseEntity.ok( responseDto );
    }


    @Operation( summary = "Delete a movie by ID" )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Movie deleted successfully",
                    content = @Content( schema = @Schema( implementation = MovieResponseBasicFullOwnershipDto.class ) )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - No movie with given ID",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    } )
    @DeleteMapping( "/{movieId}" )
    public ResponseEntity< MovieResponseBasicFullOwnershipDto > deleteMovie(
            @Parameter( description = "ID of movie to delete", required = true )
            @PathVariable Long movieId ) {

        MovieResponseBasicFullOwnershipDto responseDto = movieService.delete( movieId );

        return ResponseEntity.ok( responseDto );
    }

}

