package de.adrianwalter.movie_list_rest_api.dto.movielist;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class MovieListCreateByUserNameBodyDto extends MovieListCreateDto {

    private String type = "byName";

    @NotBlank
    @JsonProperty( "userName" )
    @JsonAlias( { "userName" } )
    private String userName;


    public String getUserName() {
        return userName;
    }
}
