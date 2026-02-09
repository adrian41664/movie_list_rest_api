package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema( description = "DTO for the batch-creation of movies with the most details" )
@Getter
@Setter
@EqualsAndHashCode( callSuper = true )
public class MovieBatchCreateCompleteDtos extends MovieBatchCreateDtos< MovieBatchCreateCompleteDto >
        implements MovieBatchCreateSubTypeMarker {

    @Schema( description = "Type of creation", example = "complete", accessMode = Schema.AccessMode.READ_ONLY )
    private final String type = "complete";
}
