package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MovieBatchCreateOneLineDtos.class, name = "oneLine"),
        @JsonSubTypes.Type(value = MovieBatchCreateBasicDtos.class, name = "basic"),
        @JsonSubTypes.Type(value = MovieBatchCreateCompleteDtos.class, name = "complete")
})

public interface MovieBatchCreateSubTypeMarker {
}


