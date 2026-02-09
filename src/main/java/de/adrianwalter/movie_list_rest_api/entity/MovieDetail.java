package de.adrianwalter.movie_list_rest_api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Schema( description = "MovieDetail entity" )
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MovieDetail {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id", unique = true )
    @Setter( AccessLevel.NONE )
    private long id;

    @OneToOne
    private Movie movie;

    @Column( nullable = true )
    private int movieLength;

    //	> SYNOPSIS {nullable}
    @Column( nullable = true )
    private String synopsis;

    //	> IMDB-RATING_SCORE {nullable}
    private float imdbScore;

    //	> RT-RATING_SCORE {nullable}
    private int rottenTomatoesScore;

    //	> IMDB-ID {nullable}
    @Column( nullable = true )
    private String imdbId;

    //	> DIRECTOR {nullable}
    @Column( nullable = true )
    private String directorName;

    //	> CAST_NAMES {nullable}
    @Column( nullable = true )
    private String castNames;


    public void setMovieLength( int movieLength ) {
        this.movieLength = movieLength;
    }


    public void setMovie( Movie movie ) {
        this.movie = movie;
    }


    public void setId( long id ) {
        this.id = id;
    }


    public void setSynopsis( String synopsis ) {
        this.synopsis = synopsis;
    }


    public void setImdbScore( float imdbScore ) {
        this.imdbScore = imdbScore;
    }


    public void setRottenTomatoesScore( int rottenTomatoesScore ) {
        this.rottenTomatoesScore = rottenTomatoesScore;
    }


    public void setImdbId( String imdbId ) {
        this.imdbId = imdbId;
    }


    public void setDirectorName( String directorName ) {
        this.directorName = directorName;
    }


    public void setCastNames( String castNames ) {
        this.castNames = castNames;
    }
}
