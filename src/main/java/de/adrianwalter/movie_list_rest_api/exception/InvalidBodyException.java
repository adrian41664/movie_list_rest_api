package de.adrianwalter.movie_list_rest_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( HttpStatus.UNPROCESSABLE_ENTITY )
public class InvalidBodyException extends RuntimeException {

    /* @toDo: Implement validation logic for JSON-Bodys.
    Exception currently never gets thrown but a DefaultHandlerExceptionResolver.
    Response is
        {
            "timestamp": "2026-01-05T16:12:37.344+00:00",
            "status": 400,
            "error": "Bad Request",
            "path": "/movie-lists"
        }

        and not "status 422, Unprocessable Entity"
    */
    public InvalidBodyException( String message ) {
        super( message );
    }

    public InvalidBodyException() {

    }
}
