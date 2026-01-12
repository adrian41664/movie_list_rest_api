package de.adrianwalter.movie_list_rest_api.mapper;

import de.adrianwalter.movie_list_rest_api.dto.user.UserCreateDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserResponseShortDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserUpdateDto;
import de.adrianwalter.movie_list_rest_api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private MovieListMapper movieListMapper;


    public User mapToUser( UserCreateDto userCreateDTO ) {

        User user = new User();
        user.setUserName( userCreateDTO.getUserName() );

        return user;
    }


    public UserResponseShortDto mapToShortResponseDto( User user ) {

        UserResponseShortDto dto = new UserResponseShortDto();
        dto.setUserId( user.getUserId() );
        dto.setUserName( user.getUserName() );

        return dto;
    }


    public User mapToUser( User user, UserUpdateDto userUpdateDTO ) {

        user.setUserName( userUpdateDTO.getUserName() );

        return user;
    }
}
