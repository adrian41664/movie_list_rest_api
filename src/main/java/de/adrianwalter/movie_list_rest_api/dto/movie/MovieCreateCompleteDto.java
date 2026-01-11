package de.adrianwalter.movie_list_rest_api.dto.movie;

public class MovieCreateCompleteDto extends MovieCreateDto {

    private String type = "complete";

    private String userNote;
    private String genre;


    public String getUserNote() {
        return userNote;
    }


    public void setUserNote( String userNote ) {
        this.userNote = userNote;
    }


    public String getGenre() {
        return genre;
    }


    public void setGenre( String genre ) {
        this.genre = genre;
    }
}
