package de.adrianwalter.movie_list_rest_api.payload;

import lombok.Data;

@Data
public class UserResponseDto {

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
