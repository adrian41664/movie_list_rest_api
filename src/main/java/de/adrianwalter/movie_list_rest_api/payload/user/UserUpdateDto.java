package de.adrianwalter.movie_list_rest_api.payload.user;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateDto {

    @NotBlank
    private String userName;

    public void setUserName( String userName ) {

        this.userName = userName;
    }

    public String getUserName() {

        return this.userName;
    }

}
