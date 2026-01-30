package de.adrianwalter.movie_list_rest_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Schema( description = "User entity" )
@Entity
@Data
public class User {

    @Schema( description = "Unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY )
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id", unique = true )
    private long userId;

    @Schema( description = "Unique name of a user", example = "User123" )
    @NotBlank
    @Column( unique = true )
    private String userName;

    @Schema( description = "movie lists a user" )
    // movieLists is a List of Lists containing Movie-Objects
    @OneToMany( mappedBy = "user", cascade = CascadeType.ALL )
    private List< MovieList > movieLists;


    public List< MovieList > getMovieLists() {
        return movieLists;
    }


    // ToDo: Apply functionality
    @JsonIgnore
    private String readAndWriteKey;


    public String getReadAndWriteKey() {
        return readAndWriteKey;
    }


    public void setReadAndWriteKey( String readKey ) {
        this.readAndWriteKey = readKey;
    }


    public void setMovieLists( List< MovieList > movieLists ) {
        this.movieLists = movieLists;
    }


    public long getUserId() {
        return userId;
    }


    public void setUserId( long userId ) {
        this.userId = userId;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName( String userName ) {
        this.userName = userName;
    }
}
