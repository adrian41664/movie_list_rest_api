package de.adrianwalter.movie_list_rest_api.dto.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema( description = "DTO to create a new user" )
@Data
public class UserCreateDto {

    @Schema( description = "Unique name of user", example = "User123" )
    @NotBlank
    @JsonAlias( { "userName" } )
    private String userName;


    public void setUserName( String userName ) {

        this.userName = userName;
    }


    public String getUserName() {

        return this.userName;
    }

}
