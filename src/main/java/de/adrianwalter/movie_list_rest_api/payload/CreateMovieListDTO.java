package de.adrianwalter.movie_list_rest_api.payload;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateMovieListByIdDTO.class, name = "by_id"),
        @JsonSubTypes.Type(value = CreateMovieListByNameDTO.class, name = "by_name")
})
public abstract class CreateMovieListDTO {

    @NotBlank
    private String movieListName;

    private String description;

    public String getMovieListName() {
        return movieListName;
    }

    public String getDescription() {
        return description;
    }
}
