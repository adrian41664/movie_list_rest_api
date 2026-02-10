package de.adrianwalter.movie_list_rest_api.dto.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Schema( description = "DTO to respond a single movie with basic details" )
@Getter
@Setter
public class MovieResponseBasicDto {

    @Schema( description = "Id of the movie-list at which this movie is listed", example = "1" )
    private long movieId;

    @Schema( description = "Unique title (per movie-list) of a movie", example = "Matrix" )
    private String movieTitle;

    @Schema( description = "User-rating of a movie", example = "4" )
    private Integer userRating;

    @Schema( description = "Release year of a movie", example = "2001" )
    private Integer releaseYear;

    @Schema( description = "Platform on which the movie was watched", example = "Prime" )
    private String seenOn;

    @Schema( description = "Text field for user notes", example = "Terrific movie that i should recommend to XY" )
    private String userNote;

    @Schema( description = "The genre(s) of the movie", example = "Horror, Thriller" )
    private String genre;

    @Schema( description = "Date on which the movie was watched", example = "2026-09-02" )
    private LocalDate seenAt;

}
