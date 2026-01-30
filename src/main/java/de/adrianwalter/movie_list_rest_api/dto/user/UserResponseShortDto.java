package de.adrianwalter.movie_list_rest_api.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema( description = "DTO to retrieve most important user data" )
@Data
public class UserResponseShortDto {

    @Schema( description = "Unique identifier", example = "1" )
    private long userId;

    @Schema( description = "Unique name of a user", example = "User123" )
    private String userName;


    public long getUserId() {

        return userId;
    }


    public void setUserId( long userId ) {

        this.userId = userId;
    }


    public String getUserName() {

        return userName;
    }


    public void setUserName( String userName ) {

        this.userName = userName;
    }
}
