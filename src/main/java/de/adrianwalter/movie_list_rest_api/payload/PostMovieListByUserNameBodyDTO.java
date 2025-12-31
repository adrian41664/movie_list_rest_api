package de.adrianwalter.movie_list_rest_api.payload;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public class PostMovieListByUserNameBodyDTO extends PostMovieListDTO {

    private String type = "by_name";

    @NotBlank
    //@JsonProperty("user_name")
    @JsonAlias({"userName", "user_name"})
    private String userName;


    public String getUserName() {
        return userName;
    }
}
