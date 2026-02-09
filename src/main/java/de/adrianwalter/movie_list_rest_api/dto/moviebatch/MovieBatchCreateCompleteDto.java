package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Schema( description = "DTO for a single movie of a batch-creation of movies with the most details" )
@Setter
@Getter
public class MovieBatchCreateCompleteDto extends MovieBatchCreateDto {

    @Schema( description = "Text field for user notes", example = "Terrific movie that i should recommend to XY" )
    @Column( nullable = true )
    private String userNote;

    @Schema( description = "The genre(s) of the movie", example = "Horror, Thriller" )
    @Column( nullable = true )
    private String genre;


}
