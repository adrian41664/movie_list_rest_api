package de.adrianwalter.movie_list_rest_api.dto.movie;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode( callSuper = true )
public class MovieCreateCompleteDto extends MovieCreateDto {

    private final String type = "complete";

    private String userNote;
    private String genre;

}
