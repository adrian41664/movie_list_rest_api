package de.adrianwalter.movie_list_rest_api.dto.movie;

import lombok.Data;

@Data
public class MovieResponseBasicFullOwnershipDto extends MovieResponseBasicDto {

    private Long movieListId;
    private String movieListName;

    private Long userId;
    private String userName;


    public MovieResponseBasicFullOwnershipDto() {
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
}
