package de.adrianwalter.movie_list_rest_api.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Schema( description = "DTO to retrieve most important user data" )
@Setter
@Getter
public class UserResponseShortDto {

    @Schema( description = "Unique identifier", example = "1" )
    private long userId;

    @Schema( description = "Unique name of a user", example = "User123" )
    private String userName;


}
