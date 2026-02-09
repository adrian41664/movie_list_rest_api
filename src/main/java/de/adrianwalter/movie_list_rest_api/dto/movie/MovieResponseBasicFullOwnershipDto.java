package de.adrianwalter.movie_list_rest_api.dto.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema( description = "DTO to respond a single movie with basic details and its ownership" )
@Getter
@Setter
@EqualsAndHashCode( callSuper = true )
public class MovieResponseBasicFullOwnershipDto extends MovieResponseBasicDto {

    @Schema( description = "Id of the movie-list at which this movie is listed", example = "1" )
    private long movieListId;

    @Schema( description = "Unique name (per user) of the movie-list at which this movie is listed",
            example = "Movies last year" )
    private String movieListName;

    @Schema( description = "Unique identifier of a user which owns the movie-list of this movie",
            example = "1" )
    private Long userId;

    @Schema( description = "Unique name of a user which owns the movie-list of this movie",
            example = "User123" )
    private String userName;


    public MovieResponseBasicFullOwnershipDto() {
    }

}
