package de.adrianwalter.movie_list_rest_api.dto.movielist;

import de.adrianwalter.movie_list_rest_api.dto.movie.MovieResponseOneLineDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Schema( description = "DTO for responding multiple movies in a compact and readable way" )
@Getter
@Setter
@EqualsAndHashCode( callSuper = true )
public class MovieListMovieOneLineResponseDto extends MovieListResponseDto {

    @Schema( description = "A list of movies with basic information displayed in one single line" )
    private List< MovieResponseOneLineDto > movies;


}
