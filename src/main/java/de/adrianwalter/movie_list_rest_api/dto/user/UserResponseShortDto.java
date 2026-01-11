package de.adrianwalter.movie_list_rest_api.dto.user;

import lombok.Data;

@Data
public class UserResponseShortDto {

    private long userId;
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
