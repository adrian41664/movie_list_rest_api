package de.adrianwalter.movie_list_rest_api.service;

import de.adrianwalter.movie_list_rest_api.entity.User;
import de.adrianwalter.movie_list_rest_api.exception.InvalidBodyException;
import de.adrianwalter.movie_list_rest_api.exception.NameAlreadyExistsException;
import de.adrianwalter.movie_list_rest_api.exception.ResourceNotFoundException;
import de.adrianwalter.movie_list_rest_api.dto.user.UserCreateDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserResponseShortDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserUpdateDto;
import de.adrianwalter.movie_list_rest_api.mapper.UserMapper;
import de.adrianwalter.movie_list_rest_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private UserMapper userMapper;


    public UserService( UserRepository userRepository, UserMapper userMapper ) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    public Page< UserResponseShortDto > UNUSED_findAll( Pageable pageable ) {

        Page< User > allUsersPage = userRepository.findAll( pageable );

        return allUsersPage.map( ( User user ) -> this.userMapper.mapToShortResponseDto( user ) );
    }


    public List< UserResponseShortDto > findAll( Pageable pageable ) {

        Page< User > allUsersPage = userRepository.findAll( pageable );

        return allUsersPage.getContent()
                .stream()
                .map( ( User user ) -> this.userMapper.mapToShortResponseDto( user ) )
                .collect( Collectors.toList() );
    }


    public UserResponseShortDto findUserDetails( Long userId ) {

        User user = this.findUserById( userId );

        return userMapper.mapToShortResponseDto( user );
    }


    public UserResponseShortDto deleteById( Long userId ) {

        User user = this.findUserById( userId );

        userRepository.deleteById( userId );

        return this.userMapper.mapToShortResponseDto( user );
    }


    public UserResponseShortDto update( Long userId, UserUpdateDto userUpdateDTO ) {

        User user = this.updateUser( userId, userUpdateDTO );

        userRepository.save( user );

        return this.userMapper.mapToShortResponseDto( user );

    }


    private User updateUser( Long userId, UserUpdateDto userUpdateDto ) {

        if ( userUpdateDto.getUserName().isBlank() ) {

            throw new InvalidBodyException();
        }

        User user = this.findUserById( userId );
        this.mapToUserIfNameNotExisting( user, userUpdateDto );

        return user;
    }


    private User mapToUserIfNameNotExisting( User user, UserUpdateDto userUpdateDto ) {

        if ( this.userIsExisting( userUpdateDto.getUserName() ) ) {

            throw new NameAlreadyExistsException( "User with the name " + userUpdateDto.getUserName() + " already exists!" );
        }

        return this.userMapper.mapToUser( user, userUpdateDto );
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


    public UserResponseShortDto create( UserCreateDto userCreateDTO ) {

        if ( this.userIsExisting( userCreateDTO.getUserName() ) ) {

            throw new NameAlreadyExistsException();
        }

        User user = this.userMapper.mapToUser( userCreateDTO );

        userRepository.save( user );

        return this.userMapper.mapToShortResponseDto( user );
    }


}
