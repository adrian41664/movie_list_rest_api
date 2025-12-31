package de.adrianwalter.movie_list_rest_api.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PostMovieShortDTO {

    // toDo: Create more Versions (short, full, detailed)

    @NotNull(message = "movieListId must be valid!")
    private long movieListId;

    @NotBlank
    private String userRating;

    @NotBlank
    private String movieName;

    // String is nullable
    private String seenOn;

    // Integer is nullable
    private Integer releaseYear;

    // should not be null, but is required as null in special cases
    private LocalDate seenAt;

}
