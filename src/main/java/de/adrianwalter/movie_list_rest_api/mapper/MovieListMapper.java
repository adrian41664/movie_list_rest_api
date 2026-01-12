package de.adrianwalter.movie_list_rest_api.mapper;

import de.adrianwalter.movie_list_rest_api.dto.movieList.MovieListCreateDto;
import de.adrianwalter.movie_list_rest_api.dto.movieList.MovieListMovieOneLineResponseDto;
import de.adrianwalter.movie_list_rest_api.entity.Movie;
import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.entity.User;
import de.adrianwalter.movie_list_rest_api.service.MovieListService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieListMapper {

    private MovieMapper movieMapper;


    public MovieListMapper( MovieMapper movieMapper ) {
        this.movieMapper = movieMapper;
    }


    public MovieListMovieOneLineResponseDto mapToMovieListMovieOneLineResponseDto( MovieList movieList, MovieListService movieListService ) {

        MovieListMovieOneLineResponseDto dto = new MovieListMovieOneLineResponseDto();

        dto.setMovieListId( movieList.getMovieListId() );
        dto.setMovieListName( movieList.getMovieListName() );

        dto.setUserId( movieList.getUser().getUserId() );
        dto.setUserName( movieList.getUser().getUserName() );

        dto.setDescription( movieList.getDescription() );

        dto.setMovies( movieList.getMovies().
                stream()
                .map( ( Movie movie ) -> this.movieMapper.mapToMovieOneLineResponseDto( movie ) )
                .toList() );

        return dto;
    }


    public MovieList mapToMovieList( MovieListCreateDto movieListCreateDto, User user ) {

        MovieList movieList = new MovieList();

        movieList.setUser( user );
        movieList.setMovieListName( movieListCreateDto.getMovieListName() );
        movieList.setDescription( movieListCreateDto.getDescription() );

        return movieList;
    }


    public List< MovieListMovieOneLineResponseDto > mapToResponseDtoLists( List< MovieList > movieLists, MovieListService movieListService ) {

        // ToDo: Expect nested-JSON issue, if Movies of each MovieList is not longer empty
        // ToDo: Create specific DTO [?] cause every MovieList repeats UserName und UserId

        return movieLists
                .stream()
                .map( ( MovieList movieList ) -> mapToMovieListMovieOneLineResponseDto( movieList, movieListService ) )
                .toList();
    }
}
