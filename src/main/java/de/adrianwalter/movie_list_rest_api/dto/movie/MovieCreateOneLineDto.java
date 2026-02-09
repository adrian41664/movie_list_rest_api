package de.adrianwalter.movie_list_rest_api.dto.movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieCreateOneLineDto implements MovieCreateSubTypeMarker {

    private final String type = "oneLine";


    long movieListId;
    String movieInformation;

}
