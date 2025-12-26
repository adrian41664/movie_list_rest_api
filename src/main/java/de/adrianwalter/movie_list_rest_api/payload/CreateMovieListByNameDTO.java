package de.adrianwalter.movie_list_rest_api.payload;

import jakarta.validation.constraints.NotBlank;

public class CreateMovieListByNameDTO extends CreateMovieListDTO {

    @NotBlank
    private String userName;


    public String getUserName() {
        return userName;
    }
}
