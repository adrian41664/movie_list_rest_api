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

    //  > MOVIE_ID (PK)
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id", unique = true )
    @Setter( AccessLevel.NONE )
    private long movieId;

    //	> MOVIE_LIST_ID (FK)
    @ManyToOne
    @JoinColumn( name = "movie_list_id", nullable = false )
    private MovieList movieList;

    //	> USER_RATING
    @Column( nullable = true )
    private Integer userRating;

    //	> MOVIE_NAME - must be non-unique, cause rating is tied to a single movie
    @NotBlank
    @Column( unique = false )
    private String movieTitle;

    //	> RELEASE_YEAR {nullable}
    @Column( nullable = true )
    private Integer releaseYear;

    //	> ADDED TO LIST DATE TIME / Seen
    @Column( nullable = true )
    private LocalDate seenAt;

    //	> PLATFORM (SEEN ON) {nullable}
    @Column( nullable = true )
    private String seenOn;

    //	> USER NOTE {nullable} text field for user
    @Column( nullable = true )
    private String userNote;

    @Column( nullable = true )
    private String genre;

    // MovieDetail - Instance for optional details
    @OneToOne( optional = true )
    @JoinColumn( nullable = true )
    private MovieDetail movieDetail;


}
