package de.adrianwalter.movie_list_rest_api.service;

import de.adrianwalter.movie_list_rest_api.entity.User;
import de.adrianwalter.movie_list_rest_api.exception.InvalidBodyException;
import de.adrianwalter.movie_list_rest_api.exception.NameAlreadyExistsException;
import de.adrianwalter.movie_list_rest_api.exception.ResourceNotFoundException;
import de.adrianwalter.movie_list_rest_api.payload.UserCreateDto;
import de.adrianwalter.movie_list_rest_api.payload.UserShortResponseDto;
import de.adrianwalter.movie_list_rest_api.payload.UserUpdateDto;
import de.adrianwalter.movie_list_rest_api.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {


    private final UserRepository userRepository;


    public UserService( UserRepository userRepository ) {

        this.userRepository = userRepository;
    }


    public Page< UserShortResponseDto > UNUSED_findAll( Pageable pageable ) {

        Page< User > allUsersPage = userRepository.findAll( pageable );

        return allUsersPage.map( ( User user ) -> this.mapToShortResponseDto( user ) );
    }


    public List< UserShortResponseDto > findAll( Pageable pageable ) {

        Page< User > allUsersPage = userRepository.findAll( pageable );

        return allUsersPage.getContent()
                .stream()
                .map( ( User user ) -> this.mapToShortResponseDto( user ) )
                .collect( Collectors.toList() );
    }


    public UserShortResponseDto findUserAndMapToShortResponse( Long userId ) {

        User user = this.findUserById( userId );

        return mapToShortResponseDto( user );
    }


    public UserShortResponseDto deleteByIdAndMapToShortResponse( Long userId ) {

        User user = this.findUserById( userId );

        userRepository.deleteById( userId );

        return this.mapToShortResponseDto( user );
    }


    public UserShortResponseDto update( Long userId, UserUpdateDto userUpdateDTO ) {

        User user = this.updateUser( userId, userUpdateDTO );

        userRepository.save( user );

        return this.mapToShortResponseDto( user );

    }

    private User updateUser( Long userId, UserUpdateDto userUpdateDTO ){

        User user = this.findUserById( userId );

        if ( !userUpdateDTO.getUserName().isBlank() ) {

            String newUserName = userUpdateDTO.getUserName();

            if ( this.userIsExisting( newUserName ) ) {

                throw new NameAlreadyExistsException( "User with the name " + newUserName + " already exists!" );
            }

            user.setUserName( userUpdateDTO.getUserName() );

        } else {

            throw new InvalidBodyException();
        }

        return user;
    }


    private UserShortResponseDto mapToShortResponseDto( User user ) {

        UserShortResponseDto dto = new UserShortResponseDto();
        dto.setUserId( user.getUserId() );
        dto.setUserName( user.getUserName() );

        return dto;
    }



    public User findUserById( long userId ) {

        return userRepository.findByUserId( userId )
                .orElseThrow( () -> new ResourceNotFoundException(
                        "cant find User with ID " + userId ) );

    }


    public User findUserByName( String userName ) {

        return userRepository.findByUserName( userName )
                .orElseThrow( () -> new ResourceNotFoundException(
                        "cant find User with name " + userName ) );
    }


    public boolean userIsExisting( String userName ) {

        Optional< User > existingUser = userRepository.findByUserName( userName );

        return existingUser.isPresent();
    }


    public boolean userIsExisting( long userId ) {

        Optional< User > existingUser = userRepository.findByUserId( userId );

        return existingUser.isPresent();
    }


    public UserShortResponseDto create( UserCreateDto userCreateDTO ) {

        if ( this.userIsExisting( userCreateDTO.getUserName() ) ) {

            throw new NameAlreadyExistsException();
        }

        User user = new User();
        user.setUserName( userCreateDTO.getUserName() );

        userRepository.save( user );

        return this.mapToShortResponseDto( user );
    }


}
