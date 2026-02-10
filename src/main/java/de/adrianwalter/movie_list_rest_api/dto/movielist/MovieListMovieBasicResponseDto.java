package de.adrianwalter.movie_list_rest_api.dto.movielist;

import de.adrianwalter.movie_list_rest_api.dto.movie.MovieResponseBasicDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Schema( description = "DTO for responding multiple movies with basic details" )
@Getter
@Setter
@EqualsAndHashCode( callSuper = true )
public class MovieListMovieBasicResponseDto extends MovieListResponseDto {

    @Schema( description = "A list of movies with basic information on each" )
    private List< MovieResponseBasicDto > movies;

}
