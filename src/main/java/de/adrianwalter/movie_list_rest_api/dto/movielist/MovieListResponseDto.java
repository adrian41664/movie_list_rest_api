package de.adrianwalter.movie_list_rest_api.dto.movielist;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class MovieListResponseDto {

    @Schema( description = "Unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY )
    private long movieListId;

    @Schema( description = "Unique name (per user) of the movie-list", example = "Movies last year" )
    private String movieListName;

    @Schema( description = "Id of user owning the movie-list", example = "1" )
    private Long userId;

    @Schema( description = "Name of user owning the movie-list", example = "User123" )
    private String userName;

    @Schema( description = "Description of the movie-list", example = "all movies watched at home last year" )
    private String description;


}
