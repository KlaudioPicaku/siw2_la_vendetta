package com.siw.uniroma3.it.siw_lavendetta.dto;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DirectorDto {

    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private String birthDate;
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    @Nullable
    private String deathDate;
    private MultipartFile image;

    // Constructors, getters, setters

    public DirectorDto() {
    }

    public DirectorDto( String firstName, String lastName, String birthDate, String deathDate, MultipartFile image) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.image = image;
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {

        this.birthDate = birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {

        this.deathDate = deathDate;
    }

    public MultipartFile getImage() {
        return this.image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "DirectorDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", deathDate=" + deathDate +
                ", image=" + image +
                '}';
    }
    // Getters and setters
    // ...
}
