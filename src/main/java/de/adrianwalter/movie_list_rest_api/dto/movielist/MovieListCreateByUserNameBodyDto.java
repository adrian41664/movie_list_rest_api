package de.adrianwalter.movie_list_rest_api.dto.movielist;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode( callSuper = true )
public class MovieListCreateByUserNameBodyDto extends MovieListCreateDto {

    @Schema( description = "Type of creation.", example = "byName" )
    private final String type = "byName";

    @Schema( description = "The unique name of a user", example = "User123" )
    @NotBlank
    @JsonProperty( "userName" )
    @JsonAlias( { "userName" } )
    private String userName;

}
