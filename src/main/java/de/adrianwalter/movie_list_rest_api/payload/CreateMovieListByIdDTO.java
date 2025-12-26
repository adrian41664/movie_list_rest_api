package de.adrianwalter.movie_list_rest_api.payload;

import jakarta.validation.constraints.NotNull;

public class CreateMovieListByIdDTO extends CreateMovieListDTO {

    @NotNull
    private Long userId;

    public Long getUserId() {
        return userId;
    }
}
