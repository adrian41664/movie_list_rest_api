package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public abstract class MovieBatchCreateDtos< T > implements MovieBatchCreateSubTypeMarker {

    @Schema( description = "Unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY )
    @NotNull
    private Long movieListId;

    @Schema( description = "List of movie-objects to create" )
    @NotNull
    private List< T > movies;
}
