package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema( description = "DTO for a single movie of a batch-creation of movies in a compact and readable way" )
@Setter
@Getter
public class MovieBatchCreateOneLineDto {

    @Schema( description = "Text field containing all movie information",
            example = "4 / The Killer / 2023 / Thriller / Netflix / " +
                    "unfortunately not as good as other movies by Fincher, but still a good movie / 2023-12-26" )
    private String movieInformation;
}
