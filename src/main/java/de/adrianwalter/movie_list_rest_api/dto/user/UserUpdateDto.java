package de.adrianwalter.movie_list_rest_api.dto.user;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema( description = "DTO to change the username" )
@Data
public class UserUpdateDto {

    @Schema( description = "Unique name of a user", example = "User123" )
    @NotBlank
    private String userName;


    public void setUserName( String userName ) {

        this.userName = userName;
    }


    public String getUserName() {

        return this.userName;
    }

}
