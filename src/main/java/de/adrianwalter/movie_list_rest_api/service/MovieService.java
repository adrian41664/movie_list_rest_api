package de.adrianwalter.movie_list_rest_api.service;

import de.adrianwalter.movie_list_rest_api.dto.movie.*;
import de.adrianwalter.movie_list_rest_api.dto.moviebatch.*;
import de.adrianwalter.movie_list_rest_api.entity.Movie;
import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.exception.InvalidBodyException;
import de.adrianwalter.movie_list_rest_api.exception.NameAlreadyExistsException;
import de.adrianwalter.movie_list_rest_api.exception.ResourceNotFoundException;
import de.adrianwalter.movie_list_rest_api.mapper.MovieBatchMapper;
import de.adrianwalter.movie_list_rest_api.mapper.MovieMapper;
import de.adrianwalter.movie_list_rest_api.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    public Page< Movie > findAll( Pageable pageable ) {
        return movieRepository.findAll( pageable );
    }


    public Movie findById( Long id ) {

        Optional< Movie > movie = movieRepository.findById( id );

        if ( movie.isEmpty() ) {
            throw new ResourceNotFoundException();
        }

        return movie.get();
    }


    public void deleteById( Long id ) {
        movieRepository.deleteById( id );
    }


    public Optional< Movie > findByMovieName( String movieName ) {
        return movieRepository.findByMovieName( movieName );
    }


    @Transactional
    private Movie createMovie( Movie movie ) {

        long movieListId = movie.getMovieList().getMovieListId();
        String movieName = movie.getMovieName();

        if ( this.movieListHasMovieWithSameName( movieListId, movieName ) ) {

            throw new NameAlreadyExistsException(
                    "Cant create new Movie; MovieList already owns Movie with the given name" );
        }
        if ( movieName.isBlank() ) {

            throw new InvalidBodyException(
                    "Cant create new Movie; Given name is blank" );
        }

        this.keywordFieldsToUpperCase( movie );

        movieRepository.save( movie );

        return movie;
    }


    public Movie createMovie( MovieCreateSubTypeMarker movieCreateSubType ) {

        Movie movie = this.mapToMovie( movieCreateSubType );

        return this.createMovie( movie );
    }


    public MovieResponseBasicFullOwnershipDto createAndMapToResponse( MovieCreateSubTypeMarker movieCreateSubType ) {

        Movie newMovie = this.createMovie( movieCreateSubType );

        return this.movieMapper.mapToMovieResponseDto( newMovie );
    }


    private Movie keywordFieldsToUpperCase( Movie movie ) {

        if ( movie.getSeenOn() != null ) {
            movie.setSeenOn( movie.getSeenOn().toUpperCase() );
        }
        if ( movie.getGenre() != null ) {
            movie.setGenre( movie.getGenre().toUpperCase() );
        }
        return movie;
    }


    private boolean movieListHasMovieWithSameName( long movieListId, String movieName ) {

        Optional< Movie > movieNameSearch = movieRepository
                .findByMovieList_MovieListIdAndMovieName( movieListId, movieName );

        return movieNameSearch.isPresent();
    }


    private Movie mapToMovie( MovieCreateSubTypeMarker movieCreateSubTypeDto ) {

        if ( movieCreateSubTypeDto instanceof MovieCreateDto movieCreateDto ) {

            MovieList movieList = this.movieListService.findById( movieCreateDto.getMovieListId() );

            return this.movieMapper.mapToMovie( movieCreateDto, movieList );

        } else if ( movieCreateSubTypeDto instanceof MovieCreateOneLineDto movieCreateOneLineDto ) {

            MovieList movieList = this.movieListService.findById( movieCreateOneLineDto.getMovieListId() );

            return this.movieMapper.mapToMovie( movieCreateOneLineDto, movieList );

        } else {

            throw new InvalidBodyException( "Body is invalid!" );
        }

    }


    public List< MovieResponseOneLineDto > createAndMapToResponse( MovieBatchCreateSubTypeMarker movieBatchCreateSubTypeDtos ) {

        System.out.println(" MovieService, createAndMapToResponse(), 155 ");

        if ( movieBatchCreateSubTypeDtos instanceof MovieBatchCreateDtos< ? > movieBatchCreateDtos ) {

            MovieList movieList = this.movieListService.findById( movieBatchCreateDtos.getMovieListId() );

            List< Movie > movies =
                    this.movieBatchMapper.mapToMovies( movieBatchCreateDtos, movieList ).stream()
                            .map( this::createMovie )
                            .toList();

            List< MovieResponseOneLineDto > oneLineDtos = movies.stream().
                    map( this.movieMapper::mapToMovieOneLineResponseDto )
                    .toList();

            return oneLineDtos;
        } else {

            throw new InvalidBodyException( "Body is invalid!" );
        }
    }
}