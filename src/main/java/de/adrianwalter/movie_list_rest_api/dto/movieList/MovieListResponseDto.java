package de.adrianwalter.movie_list_rest_api.dto.movieList;

import de.adrianwalter.movie_list_rest_api.entity.Movie;
import lombok.Data;

import java.util.List;

@Data
public class MovieListResponseDto {

    private Long movieListId;
    private String movieListName;

    private Long userId;
    private String userName;

    private String description;
    private List< Movie > movies;

    public MovieListResponseDto() {
    }

    public Long getMovieListId() {
        return movieListId;
    }

    public void setMovieListId( Long movieListId ) {
        this.movieListId = movieListId;
    }

    public String getMovieListName() {
        return movieListName;
    }

    public void setMovieListName( String movieListName ) {
        this.movieListName = movieListName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId( Long userId ) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName( String userName ) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public List< Movie > getMovies() {
        return movies;
    }

    public void setMovies( List< Movie > movies ) {
        this.movies = movies;
    }
}
