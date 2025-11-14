package de.adrianwalter.movie_list_rest_api.payload;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class CreateUserRequest {

    @NotBlank
    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName(){

        return this.userName;
    }
}
