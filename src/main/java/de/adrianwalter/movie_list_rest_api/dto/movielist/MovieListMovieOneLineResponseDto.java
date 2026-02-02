package de.adrianwalter.movie_list_rest_api.dto.movielist;

import de.adrianwalter.movie_list_rest_api.dto.movie.MovieResponseOneLineDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class MovieListMovieOneLineResponseDto extends MovieListResponseDto {

    @Schema( description = "A list of movies with basic information displayed in one single line" )
    private List< MovieResponseOneLineDto > movies;


    public List< MovieResponseOneLineDto > getMovies() {
        return movies;
    }


    public void setMovies( List< MovieResponseOneLineDto > movieResponseOneLineDtos ) {
        this.movies = movieResponseOneLineDtos;
    }

}
