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
        @JsonSubTypes.Type(value = CreateMovieListByUserIdDTO.class, name = "by_id"),
        @JsonSubTypes.Type(value = CreateMovieListByUserNameDTO.class, name = "by_name")
})
public abstract class CreateMovieListDTO {

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
