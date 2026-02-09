package de.adrianwalter.movie_list_rest_api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Schema( description = "Movie entity" )
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movie {

    @Schema( description = "Unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY )
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id", unique = true )
    @Setter( AccessLevel.NONE )
    private long movieId;

    @Schema( description = "Id of the movie-list at which this movie is listed", example = "1" )
    @ManyToOne
    @JoinColumn( name = "movie_list_id", nullable = false )
    private MovieList movieList;

    @Schema( description = "User-rating of a movie", example = "4" )
    @Column( nullable = true )
    private Integer userRating;

    @Schema( description = "Unique title (per movie-list) of a movie", example = "Matrix" )
    @NotBlank
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

    @Schema( description = "More details of the movie. Unused at the moment." )
    @OneToOne( optional = true )
    @JoinColumn( nullable = true )
    private MovieDetail movieDetail;


}
