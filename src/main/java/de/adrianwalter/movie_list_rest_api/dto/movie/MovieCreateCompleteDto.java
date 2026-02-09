package de.adrianwalter.movie_list_rest_api.dto.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema( description = "DTO to create a single movie with the most details" )
@Getter
@Setter
@EqualsAndHashCode( callSuper = true )
public class MovieCreateCompleteDto extends MovieCreateDto {

    @Schema( description = "Type of creation", example = "complete", accessMode = Schema.AccessMode.READ_ONLY )
    private final String type = "complete";

    @Schema( description = "Text field for user notes", example = "Terrific movie that i should recommend to XY" )
    private String userNote;

    @Schema( description = "The genre(s) of the movie", example = "Horror, Thriller" )
    private String genre;

}
