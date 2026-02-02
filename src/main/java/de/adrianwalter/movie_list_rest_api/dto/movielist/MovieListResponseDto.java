package de.adrianwalter.movie_list_rest_api.dto.movielist;

import io.swagger.v3.oas.annotations.media.Schema;

public abstract class MovieListResponseDto {

    @Schema( description = "Unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY )
    private Long movieListId;

    @Schema( description = "Unique name (per user) of the movie-list", example = "Movies last year" )
    private String movieListName;

    @Schema( description = "Id of user owning the movie-list", example = "1" )
    private Long userId;

    @Schema( description = "Name of user owning the movie-list", example = "User123" )
    private String userName;

    @Schema( description = "Description of the movie-list", example = "all movies watched at home last year" )
    private String description;


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

}
