package de.adrianwalter.movie_list_rest_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( HttpStatus.UNPROCESSABLE_ENTITY )
public class InvalidBodyException extends RuntimeException {

    public InvalidBodyException( String message ) {
        super( message );
    }

    public InvalidBodyException() {

    }
}
