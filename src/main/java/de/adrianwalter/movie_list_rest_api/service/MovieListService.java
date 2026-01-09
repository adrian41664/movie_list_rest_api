package de.adrianwalter.movie_list_rest_api.service;

import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.entity.User;
import de.adrianwalter.movie_list_rest_api.exception.InvalidBodyException;
import de.adrianwalter.movie_list_rest_api.exception.NameAlreadyExistsException;
import de.adrianwalter.movie_list_rest_api.exception.ResourceNotFoundException;
import de.adrianwalter.movie_list_rest_api.payload.*;
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
    private final MovieListRepository movieListRepository;

    private final UserRepository userRepository;


    @Autowired
    public MovieListService( MovieListRepository movieListRepository, UserRepository userRepository ) {

        this.movieListRepository = movieListRepository;
        this.userRepository = userRepository;
    }


    private MovieListResponseDto mapToMovieListResponseDto( MovieList movieList ) {

        MovieListResponseDto dto = new MovieListResponseDto();

        dto.setMovieListId( movieList.getMovieListId() );
        dto.setMovieListName( movieList.getMovieListName() );

        dto.setUserId( movieList.getUser().getUserId() );
        dto.setUserName( movieList.getUser().getUserName() );

        dto.setDescription( movieList.getDescription() );
        dto.setMovies( movieList.getMovies() );

        return dto;
    }


    private MovieList findById( Long movieListId ) {

        MovieList movieList = movieListRepository.findByMovieListId( movieListId )
                .orElseThrow( () -> new ResourceNotFoundException(
                        "cant find MovieList with ID " + movieListId ) );

        return movieList;
    }


    public MovieListResponseDto findByIdAndMapToResponse( Long movieListId ) {

        MovieList movieList = this.findById( movieListId );
        return mapToMovieListResponseDto( movieList );
    }


    public MovieListResponseDto deleteByIdAndMapToResponse( Long movieListId ) {

        MovieList movieList = this.findById( movieListId );

        movieListRepository.deleteById( movieListId );

        return mapToMovieListResponseDto( movieList );
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


    public MovieListResponseDto create( MovieListCreateDto movieListCreateDto ) {

        MovieList movieList = this.mapToMovieList( movieListCreateDto );

        long userId = movieList.getUser().getUserId();
        String newMovieListName = movieList.getMovieListName();

        if ( this.userHasMovieListWithName( userId, newMovieListName ) ) {

            throw new NameAlreadyExistsException(
                    "Cant create new MovieList; User already owns MovieList with the given name" );
        }

        movieListRepository.save( movieList );

        return this.mapToMovieListResponseDto( movieList );
    }


    private MovieList mapToMovieList( MovieListCreateDto movieListCreateDto ) {

        User user = this.findUser( movieListCreateDto );

        MovieList movieList = new MovieList();

        movieList.setUser( user );
        movieList.setMovieListName( movieListCreateDto.getMovieListName() );
        movieList.setDescription( movieListCreateDto.getDescription() );

        return movieList;
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
    public List< MovieListResponseDto > getUsersMovieLists( long userId ) {

        User user = this.userService.findUserById( userId );

        return mapToResponseDtoLists( user.getMovieLists() );
    }


    public List< MovieListResponseDto > getUsersMovieLists( String userName ) {

        User user = this.userService.findUserByName( userName );

        return mapToResponseDtoLists( user.getMovieLists() );
    }


    private List< MovieListResponseDto > mapToResponseDtoLists( List< MovieList > movieLists ) {

        // ToDo: Expect nested-JSON issue, if Movies of each MovieList is not longer empty
        // ToDo: Create specific DTO [?] cause every MovieList repeats UserName und UserId

        return movieLists
                .stream()
                .map( ( MovieList movieList ) -> this.mapToMovieListResponseDto( movieList ) )
                .toList();
    }


    public MovieListResponseDto findByNameAndUserName( String movieListName, String userName ) {

        if ( this.userService.userIsExisting( userName ) ) {

            MovieList movieList = this.findMovieListByNameAndUserName( movieListName, userName );

            return this.mapToMovieListResponseDto( movieList );

        } else {

            throw new ResourceNotFoundException( "cant find UserName " + userName );
        }
    }


    // ToDo: delete
    public MovieListResponseDto findByNameAndUserId( String movieListName, long userId ) {

        if ( this.userService.userIsExisting( userId ) ) {

            System.out.print( "User is existing" );
            MovieList movieList = this.findMovieListByUserIdAndMovieListName( movieListName, userId );

            return this.mapToMovieListResponseDto( movieList );

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


    public MovieListResponseDto update( Long movieListId, @Valid MovieListUpdateDto movieListUpdateDto ) {

        MovieList movieList = this.updateMovieList( movieListId, movieListUpdateDto );
        this.movieListRepository.save( movieList );

        return this.mapToMovieListResponseDto( movieList );
    }


    private MovieList updateMovieList( Long movieListId, @Valid MovieListUpdateDto movieListUpdateDto ) {

        MovieList movieList = this.findById( movieListId );

        if ( !movieListUpdateDto.getMovieListName().isBlank() ) {

            long userId = movieList.getUser().getUserId();

            if ( this.userHasMovieListWithName( userId, movieListUpdateDto.getMovieListName() ) ) {

                throw new NameAlreadyExistsException(
                        "Cant change name of MovieList; User already owns MovieList with the given name" );
            } else {

                movieList.setMovieListName( movieListUpdateDto.getMovieListName() );
            }
        } else if ( !movieListUpdateDto.getMovieListDescription().isBlank() ) {

            movieList.setDescription( movieListUpdateDto.getMovieListDescription() );
        } else {

            throw new InvalidBodyException();
        }

        return movieList;
    }

}
