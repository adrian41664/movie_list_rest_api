package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema( description = "DTO for the batch-creation of movies with basic details" )
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class MovieBatchCreateBasicDtos extends MovieBatchCreateDtos< MovieBatchCreateBasicDto >
        implements MovieBatchCreateSubTypeMarker {

    @Schema( description = "Type of creation", example = "basic", accessMode = Schema.AccessMode.READ_ONLY )
    private String type = "basic";



    }
