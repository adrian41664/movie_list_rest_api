package de.adrianwalter.movie_list_rest_api.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PostMovieListByUserIdDTO.class, name = "by_id"),
        @JsonSubTypes.Type(value = PostMovieListByUserNameDTO.class, name = "by_name")
})
public abstract class PostMovieListDTO {

    @NotBlank
    @JsonProperty("movie_list_name")
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
