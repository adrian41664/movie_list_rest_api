package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.adrianwalter.movie_list_rest_api.dto.movie.MovieCreateBasicDto;
import de.adrianwalter.movie_list_rest_api.dto.movie.MovieCreateCompleteDto;
import de.adrianwalter.movie_list_rest_api.dto.movie.MovieCreateOneLineDto;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MovieCreateOneLineDto.class, name = "oneLine"),
        @JsonSubTypes.Type(value = MovieCreateBasicDto.class, name = "basic"),
        @JsonSubTypes.Type(value = MovieCreateCompleteDto.class, name = "complete")
})

public interface MovieBatchCreateSubTypeMarker {
}


