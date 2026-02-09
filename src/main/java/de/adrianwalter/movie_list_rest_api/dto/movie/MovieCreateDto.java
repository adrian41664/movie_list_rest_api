package de.adrianwalter.movie_list_rest_api.dto.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public abstract class MovieCreateDto implements MovieCreateSubTypeMarker {


    @NotNull( message = "movieListId must be valid!" )
    private long movieListId;

    @NotNull
    private int userRating;

    @NotBlank
    private String movieTitle;

    // String is nullable
    private String seenOn;

    // Integer is nullable
    private Integer releaseYear;

    // should not be null, but is required as null in some cases
    private LocalDate seenAt;

}
