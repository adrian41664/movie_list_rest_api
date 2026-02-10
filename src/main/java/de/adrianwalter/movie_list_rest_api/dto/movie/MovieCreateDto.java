package de.adrianwalter.movie_list_rest_api.dto.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public abstract class MovieCreateDto implements MovieCreateSubTypeMarker {

    @Schema( description = "Id of the movie-list to which this movie should be added", example = "1" )
    @NotNull( message = "movieListId must be valid!" )
    private Long movieListId;

    @Schema( description = "User-rating of a movie", example = "4" )
    @NotNull
    private int userRating;

    @Schema( description = "Unique title (per movie-list) of a movie", example = "Matrix" )
    @NotBlank
    private String movieTitle;

    @Schema( description = "Platform on which the movie was watched", example = "Prime" )
    private String seenOn;

    @Schema( description = "Release year of a movie", example = "2001" )
    private Integer releaseYear;

    @Schema( description = "Date on which the movie was watched", example = "2026-09-02" )
    private LocalDate seenAt;

}
