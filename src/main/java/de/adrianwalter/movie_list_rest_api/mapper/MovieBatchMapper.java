package de.adrianwalter.movie_list_rest_api.mapper;

import de.adrianwalter.movie_list_rest_api.dto.moviebatch.*;
import de.adrianwalter.movie_list_rest_api.entity.Movie;
import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.exception.InvalidBodyException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieBatchMapper {

    private MovieMapper movieMapper;


    public MovieBatchMapper( MovieMapper movieMapper ) {
        this.movieMapper = movieMapper;
    }


    public List< Movie > mapToMovies( MovieBatchCreateDtos<?> movieBatchCreateDtos, MovieList movieList ) {

        System.out.println(" MovieBatchMapper, mapToMovies(), 19 ");


        if ( movieBatchCreateDtos instanceof MovieBatchCreateOneLineDtos movieBatchCreateOneLineDtos ) {

            System.out.println(" instanceof MovieBatchCreateOneLineDtos ");

            return movieBatchCreateOneLineDtos.getMovieTypes().stream()
                    .map( batchOneLineDto -> this.movieMapper.mapToMovie( batchOneLineDto, movieList ) )
                    .toList();

        } else if ( movieBatchCreateDtos instanceof MovieBatchCreateBasicDtos movieBatchCreateBasicDtos ) {

            return movieBatchCreateBasicDtos.getMovieTypes().stream()
                    .map( batchBasicDto -> this.movieMapper.mapToMovie( batchBasicDto, movieList ) )
                    .toList();

        } else if ( movieBatchCreateDtos instanceof MovieBatchCreateCompleteDtos movieBatchCreateCompleteDtos ) {

            return movieBatchCreateCompleteDtos.getMovieTypes().stream()
                    .map( batchCompleteDto -> this.movieMapper.mapToMovie( batchCompleteDto, movieList ) )
                    .toList();

        } else {

            throw new InvalidBodyException( "Body is invalid!" );
        }
    }


//        public List< Movie > mapToMovies ( MovieBatchCreateCompleteDtos movieBatchCreateCompleteDtos, MovieList
//        movieList ){
//
//            return movieBatchCreateCompleteDtos.getMovieCreateDtos().stream()
//                    .map( batchOneLineDto -> this.mapToMovie( batchOneLineDto, movieList ) )
//                    .toList();
//        }
//
//
//        public List< Movie > mapToMovies ( MovieBatchCreateOneLineDtos movieBatchCreateOneLineDtos, MovieList movieList )
//        {
//
//            return movieBatchCreateOneLineDtos.getMovieCreateDtos().stream()
//                    .map( batchOneLineDto -> this.mapToMovie( batchOneLineDto, movieList ) )
//                    .toList();
//        }
    }
