package de.adrianwalter.movie_list_rest_api.entity;

import de.adrianwalter.movie_list_rest_api.dto.movie.MovieResponseOneLineDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Schema( description = "Movie-list entity" )
@Entity
@Data
public class MovieList {

    @Schema( description = "Unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY )
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id", unique = true )
    private long movieListId;

    @Schema( description = "List of movies" )
    @OneToMany( mappedBy = "movieList", cascade = CascadeType.ALL )
    private List< Movie > movies;

    @Schema( description = "Id of user owning the movie-list", example = "1" )
    @ManyToOne
    @JoinColumn( name = "user_id", nullable = false )
    private User user;

    @Schema( description = "Unique name (per user) of the movie-list", example = "Movies last year" )
    @NotBlank
    private String movieListName;

    @Schema( description = "Description of the movie-list", example = "all movies watched at home last year" )
    private String description;


    public long getMovieListId() {
        return movieListId;
    }


    public void setMovieListId( long movieListId ) {
        this.movieListId = movieListId;
    }


    public User getUser() {
        return user;
    }


    public void setUser( User user ) {
        this.user = user;
    }


    public List< Movie > getMovies() {
        return movies;
    }


    public void setMovies( List< Movie > movies ) {
        this.movies = movies;
    }


    public String getMovieListName() {
        return movieListName;
    }


    public void setMovieListName( String movieListName ) {
        this.movieListName = movieListName;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription( String description ) {
        this.description = description;
    }

}
