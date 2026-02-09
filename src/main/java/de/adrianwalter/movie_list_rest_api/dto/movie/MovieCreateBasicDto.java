package de.adrianwalter.movie_list_rest_api.dto.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema( description = "DTO to create a single movie with basic details" )
@Getter
@Setter
@EqualsAndHashCode( callSuper = true )
public class MovieCreateBasicDto extends MovieCreateDto {

    @Schema( description = "Type of creation", example = "basic", accessMode = Schema.AccessMode.READ_ONLY )
    private final String type = "basic";
}
