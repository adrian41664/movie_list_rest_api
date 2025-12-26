package de.adrianwalter.movie_list_rest_api.payload;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class CreateMovieListByUserIdDTO extends CreateMovieListDTO {

    private String type = "by_id";

    @NotNull
    //@JsonProperty("user_id")
    @JsonAlias({"userId", "user_id"})
    private Long userId;

    public Long getUserId() {
        return userId;
    }
}
