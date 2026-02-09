package de.adrianwalter.movie_list_rest_api.dto.movie;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class MovieUpdateDto {

    //	> USER_RATING
    @Column( nullable = true )
    private Integer userRating;

    //	> MOVIE_NAME - must be non-unique, cause rating is tied to a single movie
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
    // @OneToOne( optional = true )
    // @JoinColumn( nullable = true )
    // private MovieDetail movieDetail;


}
