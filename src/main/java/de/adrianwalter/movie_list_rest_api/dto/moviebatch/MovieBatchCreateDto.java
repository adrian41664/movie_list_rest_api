package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public abstract class MovieBatchCreateDto {

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
