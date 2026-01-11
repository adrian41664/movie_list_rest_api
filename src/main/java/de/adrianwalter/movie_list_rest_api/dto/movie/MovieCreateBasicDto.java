package de.adrianwalter.movie_list_rest_api.dto.movie;

import lombok.Data;

@Data
public class MovieCreateBasicDto extends MovieCreateDto {

    private String type = "basic";
}
