package de.adrianwalter.movie_list_rest_api.service;

import de.adrianwalter.movie_list_rest_api.dto.movie.*;
import de.adrianwalter.movie_list_rest_api.dto.moviebatch.*;
import de.adrianwalter.movie_list_rest_api.entity.Movie;
import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.exception.BadRequestException;
import de.adrianwalter.movie_list_rest_api.exception.NameAlreadyExistsException;
import de.adrianwalter.movie_list_rest_api.exception.ResourceNotFoundException;
import de.adrianwalter.movie_list_rest_api.mapper.MovieBatchMapper;
import de.adrianwalter.movie_list_rest_api.mapper.MovieMapper;
import de.adrianwalter.movie_list_rest_api.repository.MovieRepository;
import de.adrianwalter.movie_list_rest_api.service.movielist.MovieListService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    private MovieListService movieListService;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private MovieBatchMapper movieBatchMapper;


    public MovieService( MovieRepository movieRepository ) {
        this.movieRepository = movieRepository;
    }


    public Movie createAndSave( MovieCreateSubTypeMarker movieCreateSubType ) {

        Movie movie = this.mapSubtypeToMovie( movieCreateSubType );

        return this.createAndSave( movie );
    }


    public MovieResponseBasicFullOwnershipDto createAndMapToResponse( MovieCreateSubTypeMarker movieCreateSubType ) {

        Movie newMovie = this.createAndSave( movieCreateSubType );

        return this.movieMapper.mapToMovieResponseDto( newMovie );
    }


    @Transactional
    public MovieResponseBatchCreateOneLineDtos createAndMapToResponse( MovieBatchCreateSubTypeMarker movieBatchCreateSubTypeDtos ) {

        if ( movieBatchCreateSubTypeDtos instanceof MovieBatchCreateDtos< ? > movieBatchCreateDtos ) {

            MovieList movieListToAddTo = this.movieListService.findById( movieBatchCreateDtos.getMovieListId() );

            if ( movieBatchCreateDtos.getMovies().isEmpty() ) {

                throw new BadRequestException();
            }

            List< MovieResponseOneLineDto > oneLineResponses =
                    this.movieBatchMapper.mapToMovies( movieBatchCreateDtos, movieListToAddTo ).stream()
                            .map( this::createAndSave )
                            .map( this.movieMapper::mapToMovieOneLineResponseDto )
                            .toList();

            return movieMapper.mapToMovieResponseBatchCreateOneLineDtos( movieListToAddTo.getMovieListId(), oneLineResponses );

        } else {

            throw new BadRequestException();
        }
    }


    public MovieResponseBasicFullOwnershipDto update( Long movieId, @Valid MovieUpdateDto movieUpdateDto ) {

        Movie movie = this.findById( movieId );
        Movie updatedMovie = this.movieMapper.mapToMovie( movie, movieUpdateDto );

        this.createAndSave( updatedMovie );

        return this.movieMapper.mapToMovieResponseDto( updatedMovie );
    }


    public MovieResponseBasicFullOwnershipDto findMovie( Long movieId ) {

        Movie movie = this.findMovieById( movieId );

        return this.movieMapper.mapToMovieResponseDto( movie );
    }


    public MovieResponseBasicFullOwnershipDto delete( Long movieId ) {

        Movie movie = this.findById( movieId );

        this.movieRepository.deleteById( movie.getMovieId() );

        return this.movieMapper.mapToMovieResponseDto( movie );
    }


    private Movie findById( Long movieId ) {

        return movieRepository.findById( movieId )
                .orElseThrow( () -> new ResourceNotFoundException(
                        "cant find Movie with ID " + movieId ) );
    }


    private Movie createAndSave( Movie movie ) {

        long movieListId = movie.getMovieList().getMovieListId();
        String movieName = movie.getMovieTitle();

        if ( this.movieListHasMovieWithSameName( movieListId, movieName ) ) {

            throw new NameAlreadyExistsException(
                    "Cant create new Movie; MovieList already owns Movie with the given name" );
        }
        if ( movieName.isBlank() ) {

            throw new BadRequestException();
        }

        this.keywordFieldsToUpperCase( movie );

        movieRepository.save( movie );

        return movie;
    }


    private void keywordFieldsToUpperCase( Movie movie ) {

        Optional.ofNullable( movie.getSeenOn() )
                .map( String::toUpperCase )
                .ifPresent( movie::setSeenOn );

        Optional.ofNullable( movie.getGenre() )
                .map( String::toUpperCase )
                .ifPresent( movie::setGenre );
    }


    private boolean movieListHasMovieWithSameName( long movieListId, String movieName ) {

        Optional< Movie > movieNameSearch = movieRepository
                .findByMovieList_MovieListIdAndMovieTitle( movieListId, movieName );

        return movieNameSearch.isPresent();
    }


    private Movie mapSubtypeToMovie( MovieCreateSubTypeMarker movieCreateSubTypeDto ) {

        if ( movieCreateSubTypeDto instanceof MovieCreateDto movieCreateDto ) {

            return this.movieMapper.mapToMovie(
                    movieCreateDto,
                    this.movieListService.findById( movieCreateDto.getMovieListId() ) );
        }

        // movieCreateSubTypeDto must be of type: MovieCreateOneLineDto
        MovieCreateOneLineDto movieCreateOneLineDto = (MovieCreateOneLineDto) movieCreateSubTypeDto;
        return this.movieMapper.mapToMovie(
                movieCreateOneLineDto,
                this.movieListService.findById( movieCreateOneLineDto.getMovieListId() ) );

    }


    private Movie findMovieById( long movieId ) {

        return this.movieRepository.findById( movieId )
                .orElseThrow( () -> new ResourceNotFoundException(
                        "cant find Movie with ID " + movieId ) );
    }


}