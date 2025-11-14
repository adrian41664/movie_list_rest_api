package de.adrianwalter.movie_list_rest_api.service;

import de.adrianwalter.movie_list_rest_api.entity.User;
import de.adrianwalter.movie_list_rest_api.exception.NameAlreadyExistsException;
import de.adrianwalter.movie_list_rest_api.exception.ResourceNotFoundException;
import de.adrianwalter.movie_list_rest_api.payload.CreateUserRequest;
import de.adrianwalter.movie_list_rest_api.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService( UserRepository userRepository ){
        this.userRepository = userRepository;
    }

    public Page<User> findAll(Pageable pageable){
        return userRepository.findAll( pageable );
    }

    public User findById( String id ){

        Optional<User> user = userRepository.findById( id );

        if( user.isEmpty() ){
            throw new ResourceNotFoundException();
        }

        return user.get();
    }

    public void deleteById(String id) {
        userRepository.deleteById( id );
    }

    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User create(CreateUserRequest request) {
        Optional<User> existingCustomer = findByUserName(request.getUserName());

        if (existingCustomer.isPresent()) {
            throw new NameAlreadyExistsException();
        }

        User user = new User();
        user.setUserName(request.getUserName());

        return userRepository.save(user);
    }

}
