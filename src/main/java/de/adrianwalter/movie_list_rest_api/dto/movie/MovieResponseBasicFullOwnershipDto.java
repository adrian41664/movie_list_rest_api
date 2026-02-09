package de.adrianwalter.movie_list_rest_api.dto.movie;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode( callSuper = true )
public class MovieResponseBasicFullOwnershipDto extends MovieResponseBasicDto {

    private Long movieListId;
    private String movieListName;

    private Long userId;
    private String userName;


    public MovieResponseBasicFullOwnershipDto() {
    }

}
