package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.dto.movie.*;
import de.adrianwalter.movie_list_rest_api.dto.moviebatch.MovieBatchCreateSubTypeMarker;
import de.adrianwalter.movie_list_rest_api.dto.moviebatch.MovieResponseBatchCreateOneLineDtos;
import de.adrianwalter.movie_list_rest_api.dto.user.UserResponseShortDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserUpdateDto;
import de.adrianwalter.movie_list_rest_api.service.MovieService;
import de.adrianwalter.movie_list_rest_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/movies" )
public class MovieController {


    private final MovieService movieService;
    private final UserService userService;


    public MovieController( MovieService movieService, UserService userService ) {

        this.movieService = movieService;
        this.userService = userService;
    }


    // ToDo: in test; all three types of requestBody ok
    @PostMapping( "" )
    public ResponseEntity< MovieResponseBasicFullOwnershipDto > createNewMovie(
            @Valid @RequestBody MovieCreateSubTypeMarker requestBody ) {

        MovieResponseBasicFullOwnershipDto movie = movieService.createAndMapToResponse( requestBody );

        return ResponseEntity.ok( movie );
    }


    // ToDo: in test; all three types of requestBody ok
    @PostMapping( "/batch" )
    public ResponseEntity< MovieResponseBatchCreateOneLineDtos > createNewMovies(
            @Valid @RequestBody MovieBatchCreateSubTypeMarker requestBody ) {

        MovieResponseBatchCreateOneLineDtos movies = movieService.createAndMapToResponse( requestBody );

        return ResponseEntity.ok( movies );
    }


    // ToDo: in test
    @GetMapping( "" )
    public ResponseEntity< List< UserResponseShortDto > > getAllUsers( Pageable pageable ) {

        List< UserResponseShortDto > users = userService.findAll( pageable );

        return ResponseEntity.ok( users );
    }


    // ToDo: test
    @PutMapping( "/{movieId}" )
    public ResponseEntity< MovieResponseBasicFullOwnershipDto > updateMovie(
            @PathVariable Long movieId, @RequestBody @Valid MovieUpdateDto movieUpdateDto ) {

        MovieResponseBasicFullOwnershipDto responseDto = movieService.update( movieId, movieUpdateDto );

        return ResponseEntity.ok( responseDto );
    }


    // ToDo: test
    @GetMapping( "/{movieId}" )
    public ResponseEntity< MovieResponseBasicFullOwnershipDto > getMovie( @PathVariable Long movieId ) {

        MovieResponseBasicFullOwnershipDto responseDto = movieService.findMovie( movieId );

        return ResponseEntity.ok( responseDto );
    }


    // ToDo: test
    @DeleteMapping( "/{movieId}" )
    public ResponseEntity< MovieResponseBasicFullOwnershipDto > deleteMovie( @PathVariable Long movieId ) {

        MovieResponseBasicFullOwnershipDto responseDto = movieService.delete( movieId );

        return ResponseEntity.ok( responseDto );
    }

}

