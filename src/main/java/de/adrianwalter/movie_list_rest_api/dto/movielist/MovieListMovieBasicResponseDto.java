package de.adrianwalter.movie_list_rest_api.dto.movielist;

import de.adrianwalter.movie_list_rest_api.dto.movie.MovieResponseBasicDto;
import lombok.Data;

import java.util.List;

@Data
public class MovieListMovieBasicResponseDto extends MovieListResponseDto {

    public List< MovieResponseBasicDto > getMovies() {
        return movies;
    }


    public void setMovies( List< MovieResponseBasicDto > movies ) {
        this.movies = movies;
    }


    private List< MovieResponseBasicDto > movies;
}
