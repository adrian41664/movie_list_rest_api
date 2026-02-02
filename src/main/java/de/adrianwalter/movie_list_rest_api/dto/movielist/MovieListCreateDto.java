package de.adrianwalter.movie_list_rest_api.dto.movielist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema( description = "The name of the new movie-list. Must be unique per user",
            example = "Movies last year" )
    @NotBlank
    @JsonProperty("movieListName")
    private String movieListName;

    @Schema( description = "Description of the movie-list", example = "all movies watched at home last year" )
    @JsonProperty("description")
    private String description;

    public String getMovieListName() {
        return movieListName;
    }

    public String getDescription() {
        return description;
    }
}
