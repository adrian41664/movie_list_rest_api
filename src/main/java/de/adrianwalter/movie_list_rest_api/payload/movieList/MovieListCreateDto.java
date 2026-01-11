package de.adrianwalter.movie_list_rest_api.payload.movieList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MovieListCreateByUserIdBodyDto.class, name = "byId"),
        @JsonSubTypes.Type(value = MovieListCreateByUserNameBodyDto.class, name = "byName")
})
public abstract class MovieListCreateDto {

    @NotBlank
    @JsonProperty("movieListName")
    private String movieListName;

    @JsonProperty("description")
    private String description;

    public String getMovieListName() {
        return movieListName;
    }

    public String getDescription() {
        return description;
    }
}
