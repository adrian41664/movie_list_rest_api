package de.adrianwalter.movie_list_rest_api.dto.movielist;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema( description = "DTO to change description or name of a movie-list" )
@Setter
@Getter
public class MovieListUpdateDto {

    @Schema( description = "Unique name (per user) of the movie-list", example = "Movies last year" )
    private String movieListName;

    @Schema( description = "Description of the movie-list", example = "all movies watched at home last year" )
    private String movieListDescription;


}
