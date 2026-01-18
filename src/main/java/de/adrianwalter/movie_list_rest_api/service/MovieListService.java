package de.adrianwalter.movie_list_rest_api.service;

import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.entity.User;
import de.adrianwalter.movie_list_rest_api.exception.InvalidBodyException;
import de.adrianwalter.movie_list_rest_api.exception.NameAlreadyExistsException;
import de.adrianwalter.movie_list_rest_api.exception.ResourceNotFoundException;
import de.adrianwalter.movie_list_rest_api.dto.movielist.*;
import de.adrianwalter.movie_list_rest_api.mapper.MovieListMapper;
import de.adrianwalter.movie_list_rest_api.repository.MovieListRepository;
import de.adrianwalter.movie_list_rest_api.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieListService {

    @Autowired
    private UserService userService;
    @Autowired
    private MovieListMapper movieListMapper;

    @Autowired
    private final MovieListRepository movieListRepository;

    private final UserRepository userRepository;


    @Autowired
    public MovieListService( MovieListRepository movieListRepository, UserRepository userRepository ) {

        this.movieListRepository = movieListRepository;
        this.userRepository = userRepository;
    }


    public MovieList findById( Long movieListId ) {

        return movieListRepository.findByMovieListId( movieListId )
                .orElseThrow( () -> new ResourceNotFoundException(
                        "cant find MovieList with ID " + movieListId ) );
    }


    public MovieListMovieOneLineResponseDto findByIdAndMapToResponse( Long movieListId ) {

        MovieList movieList = this.findById( movieListId );
        return movieListMapper.mapToMovieListMovieOneLineResponseDto( movieList, this );
    }


    public MovieListMovieOneLineResponseDto deleteByIdAndMapToResponse( Long movieListId ) {

        MovieList movieList = this.findById( movieListId );

        movieListRepository.deleteById( movieListId );

        return movieListMapper.mapToMovieListMovieOneLineResponseDto( movieList, this );
    }


    private boolean userHasMovieListWithName( long userId, String movieListName ) {

        Optional< MovieList > movieListSearch = movieListRepository
                .findByUser_UserIdAndMovieListName( userId, movieListName );

        return movieListSearch.isPresent();
    }


    // ToDo: delete
    private boolean userHasMovieListWithName( String userName, String movieListName ) {

        Optional< MovieList > movieListSearch = movieListRepository
                .findByUser_UserNameAndMovieListName( userName, movieListName );

        return movieListSearch.isPresent();
    }


    public MovieListMovieOneLineResponseDto createAndMapToResponse( MovieListCreateDto movieListCreateDto ) {

        User user = this.findUser( movieListCreateDto );

        MovieList movieList = this.movieListMapper.mapToMovieList( movieListCreateDto, user );

        long userId = movieList.getUser().getUserId();
        String newMovieListName = movieList.getMovieListName();

        if ( this.userHasMovieListWithName( userId, newMovieListName ) ) {

            throw new NameAlreadyExistsException(
                    "Cant create new MovieList; User already owns MovieList with the given name" );
        }

        movieListRepository.save( movieList );

        return this.movieListMapper.mapToMovieListMovieOneLineResponseDto( movieList, this );
    }


    private User findUser( MovieListCreateDto movieListCreateDto ) {

        if ( movieListCreateDto instanceof MovieListCreateByUserNameBodyDto nameDto ) {

            return this.userService.findUserByName( nameDto.getUserName() );

        } else if ( movieListCreateDto instanceof MovieListCreateByUserIdBodyDto idDto ) {

            return this.userService.findUserById( idDto.getUserId() );
        }

        throw new InvalidBodyException( "Body is invalid!" );
    }


    // ToDo: delete
    public List< MovieListMovieOneLineResponseDto > getUsersMovieLists( long userId ) {

        User user = this.userService.findUserById( userId );

        return movieListMapper.mapToResponseDtoLists( user.getMovieLists(), this );
    }


    public List< MovieListMovieOneLineResponseDto > getUsersMovieLists( String userName ) {

        User user = this.userService.findUserByName( userName );

        return movieListMapper.mapToResponseDtoLists( user.getMovieLists(), this );
    }


    public MovieListMovieOneLineResponseDto findByNameAndUserName( String movieListName, String userName ) {

        if ( this.userService.userIsExisting( userName ) ) {

            MovieList movieList = this.findMovieListByNameAndUserName( movieListName, userName );

            return this.movieListMapper.mapToMovieListMovieOneLineResponseDto( movieList, this );

        } else {

            throw new ResourceNotFoundException( "cant find UserName " + userName );
        }
    }


    // ToDo: delete
    public MovieListMovieOneLineResponseDto findByNameAndUserId( String movieListName, long userId ) {

        if ( this.userService.userIsExisting( userId ) ) {

            System.out.print( "User is existing" );
            MovieList movieList = this.findMovieListByUserIdAndMovieListName( movieListName, userId );

            return this.movieListMapper.mapToMovieListMovieOneLineResponseDto( movieList, this );

        } else {

            System.out.print( "User is not existing" );
            throw new ResourceNotFoundException( "cant find UserId " + userId );
        }
    }


    private MovieList findMovieListByNameAndUserName( String movieListName, String userName ) {

        return this.movieListRepository.findByUser_UserNameAndMovieListName( userName, movieListName )
                .orElseThrow( () -> new ResourceNotFoundException( "cant find MovieListName " + movieListName ) );
    }


    private MovieList findMovieListByUserIdAndMovieListName( String movieListName, long userId ) {

        return this.movieListRepository.findByUser_UserIdAndMovieListName( userId, movieListName )
                .orElseThrow( () -> new ResourceNotFoundException( "cant find MovieListName " + movieListName ) );
    }


    public MovieListMovieOneLineResponseDto update( Long movieListId, @Valid MovieListUpdateDto movieListUpdateDto ) {

        MovieList movieList = this.updateMovieList( movieListId, movieListUpdateDto );
        this.movieListRepository.save( movieList );

        return this.movieListMapper.mapToMovieListMovieOneLineResponseDto( movieList, this );
    }


    private MovieList updateMovieList( Long movieListId, @Valid MovieListUpdateDto movieListUpdateDto ) {

        MovieList movieList = this.findById( movieListId );

        String newMovieListName = movieListUpdateDto.getMovieListName();

        if ( ! newMovieListName.isBlank() ) {

            this.changeNameIfNotExisting( movieList, newMovieListName );

        } else if ( !movieListUpdateDto.getMovieListDescription().isBlank() ) {

            movieList.setDescription( movieListUpdateDto.getMovieListDescription() );

        } else {

            throw new InvalidBodyException();
        }

        return movieList;
    }


    void changeNameIfNotExisting( MovieList movieList, String movieListName ) {

        long userId = movieList.getUser().getUserId();

        if ( this.userHasMovieListWithName( userId, movieListName ) ) {

            throw new NameAlreadyExistsException(
                    "Cant change name of MovieList; User already owns MovieList with the given name" );
        } else {

            movieList.setMovieListName( movieListName );
        }
    }

}
