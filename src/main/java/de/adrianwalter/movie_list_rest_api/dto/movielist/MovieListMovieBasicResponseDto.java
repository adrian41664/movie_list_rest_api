package de.adrianwalter.movie_list_rest_api.dto.movielist;

import de.adrianwalter.movie_list_rest_api.dto.movie.MovieResponseBasicDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class MovieListMovieBasicResponseDto extends MovieListResponseDto {

    @Schema( description = "A list of movies with basic information on each" )
    private List< MovieResponseBasicDto > movies;


    public void setMovies( List< MovieResponseBasicDto > movies ) {

        this.movies = movies;
    }


    public List< MovieResponseBasicDto > getMovies() {

        return movies;
    }
}
