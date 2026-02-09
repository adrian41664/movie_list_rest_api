package de.adrianwalter.movie_list_rest_api.dto.movie;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class MovieCreateBasicDto extends MovieCreateDto {

    private String type = "basic";
}
