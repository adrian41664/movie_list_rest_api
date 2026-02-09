package de.adrianwalter.movie_list_rest_api.dto.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Schema( description = "DTO to create a single movie in a compact and readable way" )
@Getter
@Setter
public class MovieCreateOneLineDto implements MovieCreateSubTypeMarker {

    @Schema( description = "Type of creation", example = "oneLine", accessMode = Schema.AccessMode.READ_ONLY )
    private final String type = "oneLine";

    @Schema( description = "Id of the movie-list to which this movie should be added", example = "1" )
    @NotNull
    private Long movieListId;

    @Schema( description = "Text field containing all movie information",
            example = "4 / The Killer / 2023 / Thriller / Netflix / " +
                    "unfortunately not as good as other movies by Fincher, but still a good movie / 2023-12-26" )
    private String movieInformation;

}
