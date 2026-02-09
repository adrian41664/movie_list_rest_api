package de.adrianwalter.movie_list_rest_api.dto.movie;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, property = "type"
)
@JsonSubTypes( {
        @JsonSubTypes.Type( value = MovieCreateOneLineDto.class, name = "oneLine" ),
        @JsonSubTypes.Type( value = MovieCreateBasicDto.class, name = "basic" ),
        @JsonSubTypes.Type( value = MovieCreateCompleteDto.class, name = "complete" )
} )

public interface MovieCreateSubTypeMarker {
}
