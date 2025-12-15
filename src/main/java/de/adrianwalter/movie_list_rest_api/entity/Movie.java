package de.adrianwalter.movie_list_rest_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Movie {

    //  > MOVIE_ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    //	> MOVIE_LIST_ID (FK)
    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "movieList_id")
    private MovieList movieList;

    //	> USER_RATING
    @Column(nullable = false)
    private String userRating;

    //	> MOVIE_NAME
    @NotBlank
    @Column(unique = true)
    private String name;

    //	> RELEASE_YEAR {nullable}
    @Column(nullable = true)
    private int releaseYear;

    //	> ADDED TO LIST DATE TIME / Seen
    @Column(nullable = false)
    private LocalDateTime seenAt;

    //	> PLATFORM (SEEN ON) {nullable}
    @Column(nullable = true)
    private String seenOn;

    //	> USER NOTE {nullable, freies Textfeld für alle möglichen Infos des Users}
    @Column(nullable = true)
    private String userNote;

    // MovieDetail Instanz für optionale Infos
    @OneToOne
    private MovieDetail movieDetail;

}
