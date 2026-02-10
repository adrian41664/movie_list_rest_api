package de.adrianwalter.movie_list_rest_api.dto.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Schema( description = "DTO to create a new user" )
@Setter
@Getter
public class UserCreateDto {

    @Schema( description = "Unique name of user", example = "User123" )
    @NotBlank
    @JsonAlias( { "userName" } )
    private String userName;


}
