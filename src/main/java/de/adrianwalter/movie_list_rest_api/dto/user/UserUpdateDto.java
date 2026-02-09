package de.adrianwalter.movie_list_rest_api.dto.user;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Schema( description = "DTO to change the username" )
@Setter
@Getter
public class UserUpdateDto {

    @Schema( description = "Unique name of a user", example = "User123" )
    @NotBlank
    private String userName;


}
