package de.adrianwalter.movie_list_rest_api.dto.movielist;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class MovieListCreateByUserIdBodyDto extends MovieListCreateDto {

    private String type = "byId";

    @NotNull
    @JsonProperty( "userId" )
    @JsonAlias( { "userId" } )
    private Long userId;

    public Long getUserId() {
        return userId;
    }
}
