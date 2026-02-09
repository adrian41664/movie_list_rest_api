package de.adrianwalter.movie_list_rest_api.dto.movielist;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode( callSuper = true )
public class MovieListCreateByUserIdBodyDto extends MovieListCreateDto {

    @Schema( description = "Type of creation.", example = "byId" )
    private final String type = "byId";

    @Schema( description = "The ID of a user", example = "1" )
    @NotNull
    @JsonProperty( "userId" )
    @JsonAlias( { "userId" } )
    private Long userId;

}
