package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import de.adrianwalter.movie_list_rest_api.dto.movie.MovieResponseOneLineDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema( description = "DTO for the response of a batch-creation of movies in a compact and readable way " )
@Getter
@Setter
public class MovieResponseBatchCreateOneLineDtos {

    @Schema( description = "Unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY )
    @NotNull
    private Long movieListId;

    @Schema( description = "List of movie-objects to create" )
    @NotNull
    private List< MovieResponseOneLineDto > movies;

}
