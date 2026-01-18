package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MovieBatchCreateOneLineDto.class, name = "oneLine"),
        @JsonSubTypes.Type(value = MovieBatchCreateBasicDto.class, name = "basic"),
        @JsonSubTypes.Type(value = MovieBatchCreateCompleteDto.class, name = "complete")
})

public interface MovieBatchCreateSubTypeMarker {
}


