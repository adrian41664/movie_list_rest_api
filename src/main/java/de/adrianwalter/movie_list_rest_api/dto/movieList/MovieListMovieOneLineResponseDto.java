package de.adrianwalter.movie_list_rest_api.dto.movieList;

import de.adrianwalter.movie_list_rest_api.dto.movie.MovieResponseOneLineDto;
import lombok.Data;

import java.util.List;

@Data
public class MovieListMovieOneLineResponseDto extends MovieListResponseDto {

    private List< MovieResponseOneLineDto > movieResponseOneLineDtos;


    public List< MovieResponseOneLineDto > getMovieResponseOneLineDtos() {
        return movieResponseOneLineDtos;
    }


    public void setMovieResponseOneLineDtos( List< MovieResponseOneLineDto > movieResponseOneLineDtos ) {
        this.movieResponseOneLineDtos = movieResponseOneLineDtos;
    }

}
