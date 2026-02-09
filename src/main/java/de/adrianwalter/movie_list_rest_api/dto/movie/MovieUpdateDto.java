package de.adrianwalter.movie_list_rest_api.dto.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Schema( description = "DTO for updating a movie's information" )
@Setter
@Getter
public class MovieUpdateDto {

    @Schema( description = "User-rating of a movie", example = "4" )
    @Column( nullable = true )
    private Integer userRating;

    @Schema( description = "Unique title (per movie-list) of a movie", example = "Matrix" )
    @Column( unique = false )
    private String movieTitle;

    @Schema( description = "Release year of a movie", example = "2001" )
    @Column( nullable = true )
    private Integer releaseYear;

    @Schema( description = "Date on which the movie was watched", example = "2026-09-02" )
    @Column( nullable = true )
    private LocalDate seenAt;

    @Schema( description = "Platform on which the movie was watched", example = "Prime" )
    @Column( nullable = true )
    private String seenOn;

    @Schema( description = "Text field for user notes", example = "Terrific movie that i should recommend to XY" )
    @Column( nullable = true )
    private String userNote;

    @Schema( description = "The genre(s) of the movie", example = "Horror, Thriller" )
    @Column( nullable = true )
    private String genre;


    // MovieDetail - Instance for optional details
    // @OneToOne( optional = true )
    // @JoinColumn( nullable = true )
    // private MovieDetail movieDetail;

}
