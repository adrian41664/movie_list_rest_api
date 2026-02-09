package de.adrianwalter.movie_list_rest_api.dto.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema( description = "DTO to respond a single movie in a compact and readable way" )
@Getter
@Setter
public class MovieResponseOneLineDto {

    @Schema( description = "Text field containing all movie information",
            example = "4 / The Killer / 2023 / Thriller / Netflix / " +
                    "unfortunately not as good as other movies by Fincher, but still a good movie / 2023-12-26" )
    private String movieInformation;
}
