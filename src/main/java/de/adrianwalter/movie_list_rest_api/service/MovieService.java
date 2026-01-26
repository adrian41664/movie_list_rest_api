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
import de.adrianwalter.movie_list_rest_api.service.movielist.MovieListService;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
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
        return movieRepository.findByMovieTitle( movieName );
    }


    public Movie createAndSaveMovie( MovieCreateSubTypeMarker movieCreateSubType ) {

        Movie movie = this.mapToMovie( movieCreateSubType );

        return this.createAndSaveMovie( movie );
    }


    public MovieResponseBasicFullOwnershipDto createAndMapToResponse( MovieCreateSubTypeMarker movieCreateSubType ) {

        Movie newMovie = this.createAndSaveMovie( movieCreateSubType );

        return this.movieMapper.mapToMovieResponseDto( newMovie );
    }


    @Transactional
    public MovieResponseBatchCreateOneLineDtos createAndMapToResponse( MovieBatchCreateSubTypeMarker movieBatchCreateSubTypeDtos ) {

        if ( movieBatchCreateSubTypeDtos instanceof MovieBatchCreateDtos< ? > movieBatchCreateDtos ) {

            MovieList movieListToAddTo = this.movieListService.findById( movieBatchCreateDtos.getMovieListId() );

            List< MovieResponseOneLineDto > oneLineResponses =
                    this.movieBatchMapper.mapToMovies( movieBatchCreateDtos, movieListToAddTo ).stream()
                    .map( this::createAndSaveMovie )
                    .map( this.movieMapper::mapToMovieOneLineResponseDto )
                    .toList();

            return mapToMovieResponseBatchCreateOneLineDtos( movieListToAddTo.getMovieListId(), oneLineResponses );

        } else {

            throw new InvalidBodyException( "Body is invalid!" );
        }

    }


    private Movie createAndSaveMovie( Movie movie ) {

        long movieListId = movie.getMovieList().getMovieListId();
        String movieName = movie.getMovieTitle();

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


    private MovieResponseBatchCreateOneLineDtos mapToMovieResponseBatchCreateOneLineDtos(
            long movieListId,
            @NonNull List< MovieResponseOneLineDto > oneLineDtos ) {

        MovieResponseBatchCreateOneLineDtos movieResponseBatch = new MovieResponseBatchCreateOneLineDtos();
        movieResponseBatch.setMovieListId( movieListId );
        movieResponseBatch.setMovies( oneLineDtos );

        return movieResponseBatch;
    }
}