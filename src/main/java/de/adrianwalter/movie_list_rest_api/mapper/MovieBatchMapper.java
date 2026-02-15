package de.adrianwalter.movie_list_rest_api.mapper;

import de.adrianwalter.movie_list_rest_api.dto.moviebatch.*;
import de.adrianwalter.movie_list_rest_api.entity.Movie;
import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieBatchMapper {

    private MovieMapper movieMapper;


    public MovieBatchMapper( MovieMapper movieMapper ) {
        this.movieMapper = movieMapper;
    }


    public List< Movie > mapToMovies( MovieBatchCreateDtos< ? > movieBatchCreateDtos, MovieList movieList ) {

        if ( movieBatchCreateDtos instanceof MovieBatchCreateOneLineDtos movieBatchCreateOneLineDtos ) {

            return movieBatchCreateOneLineDtos.getMovies().stream()
                    .map( batchOneLineDto -> this.movieMapper.mapToMovie( batchOneLineDto, movieList ) )
                    .toList();

        } else if ( movieBatchCreateDtos instanceof MovieBatchCreateBasicDtos movieBatchCreateBasicDtos ) {

            return movieBatchCreateBasicDtos.getMovies().stream()
                    .map( batchBasicDto -> this.movieMapper.mapToMovie( batchBasicDto, movieList ) )
                    .toList();

        } else if ( movieBatchCreateDtos instanceof MovieBatchCreateCompleteDtos movieBatchCreateCompleteDtos ) {

            return movieBatchCreateCompleteDtos.getMovies().stream()
                    .map( batchCompleteDto -> this.movieMapper.mapToMovie( batchCompleteDto, movieList ) )
                    .toList();

        } else {

            throw new BadRequestException();
        }
    }

}
