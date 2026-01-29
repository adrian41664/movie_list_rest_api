package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.dto.movie.*;
import de.adrianwalter.movie_list_rest_api.dto.moviebatch.MovieBatchCreateSubTypeMarker;
import de.adrianwalter.movie_list_rest_api.dto.moviebatch.MovieResponseBatchCreateOneLineDtos;
import de.adrianwalter.movie_list_rest_api.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/movies" )
public class MovieController {


    private final MovieService movieService;


    public MovieController( MovieService movieService ) {

        this.movieService = movieService;
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


}

