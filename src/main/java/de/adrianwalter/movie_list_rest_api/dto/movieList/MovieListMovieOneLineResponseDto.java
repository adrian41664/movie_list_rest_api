package de.adrianwalter.movie_list_rest_api.dto.movieList;

import de.adrianwalter.movie_list_rest_api.dto.movie.MovieResponseOneLineDto;
import lombok.Data;

import java.util.List;

@Data
public class MovieListMovieOneLineResponseDto extends MovieListResponseDto {

    private List< MovieResponseOneLineDto > movies;


    public List< MovieResponseOneLineDto > getMovies() {
        return movies;
    }


    public void setMovies( List< MovieResponseOneLineDto > movieResponseOneLineDtos ) {
        this.movies = movieResponseOneLineDtos;
    }

}
