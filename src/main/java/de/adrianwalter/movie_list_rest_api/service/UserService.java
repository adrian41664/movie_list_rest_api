package de.adrianwalter.movie_list_rest_api.service;

import de.adrianwalter.movie_list_rest_api.entity.User;
import de.adrianwalter.movie_list_rest_api.exception.NameAlreadyExistsException;
import de.adrianwalter.movie_list_rest_api.exception.ResourceNotFoundException;
import de.adrianwalter.movie_list_rest_api.payload.UserCreateDto;
import de.adrianwalter.movie_list_rest_api.payload.UserShortResponseDto;
import de.adrianwalter.movie_list_rest_api.payload.UserUpdateDto;
import de.adrianwalter.movie_list_rest_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;


    public UserService( UserRepository userRepository ) {

        this.userRepository = userRepository;
    }


    public Page< User > findAll( Pageable pageable ) {

        return userRepository.findAll( pageable );
    }

    public UserShortResponseDto findByIdAndMapToShortResponse( Long userId ) {

        User user = this.findById( userId );

        return mapToShortResponseDto( user );
    }

    private User findById( Long userId ) {

        Optional< User > user = userRepository.findById( userId );

        if ( user.isEmpty() ) {
            throw new ResourceNotFoundException();
        }

        return user.get();
    }


    public UserShortResponseDto deleteByIdAndMapToShortResponse( Long userId ) {

        User user = this.findById( userId );

        userRepository.deleteById( userId );

        return this.mapToShortResponseDto( user );
    }


    public Optional< User > findByUserName( String userName ) {

        return userRepository.findByUserName( userName );
    }


    public UserShortResponseDto update( Long userId, UserUpdateDto userUpdateDTO ) {

        User user = userRepository.findById( userId )
                .orElseThrow( () -> new EntityNotFoundException(
                        "cant find User with ID " + userId ) );

        if ( userNameIsAlreadyExisting( userUpdateDTO.getUserName() ) ) {

            throw new NameAlreadyExistsException();
        }

        user.setUserName( userUpdateDTO.getUserName() );
        userRepository.save( user );

        return this.mapToShortResponseDto( user );
    }


    private UserShortResponseDto mapToShortResponseDto( User user ) {

        UserShortResponseDto dto = new UserShortResponseDto();
        dto.setUserId( user.getUserId() );
        dto.setUserName( user.getUserName() );

        return dto;
    }


    private boolean userNameIsAlreadyExisting( String name ) {

        Optional< User > existingUser = this.findByUserName( name );

        return existingUser.isPresent();
    }

    public User create( UserCreateDto userCreateDTO ) {

        if ( this.userNameIsAlreadyExisting( userCreateDTO.getUserName() ) ) {

            throw new NameAlreadyExistsException();
        }

        User user = new User();
        user.setUserName( userCreateDTO.getUserName() );

        userRepository.save( user );

        return user;
    }


}
