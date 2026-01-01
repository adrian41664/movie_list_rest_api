package de.adrianwalter.movie_list_rest_api.payload;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class UserCreateDto {

    @NotBlank
    @JsonAlias({"userName", "user_name"})
    private String userName;

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public String getUserName(){

        return this.userName;
    }

}
