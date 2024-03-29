package com.siw.uniroma3.it.siw_lavendetta.models;

import com.siw.uniroma3.it.siw_lavendetta.constants.DefaultSaveLocations;
import com.siw.uniroma3.it.siw_lavendetta.constants.StaticURLs;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "actor")
public class Actor {
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Id
    private Long id;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "death_date", nullable = true)
    private LocalDate deathDate;

    @Column(name = "image",nullable = true)
    private String image;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "actors")
    private Set<Film> films = new HashSet<>();


    // costruttori, getter, setter, equals, hashCode, toString
    public Actor(){

    }

    public Actor(String firstName, String lastName, LocalDate birthDate, LocalDate deathDate, String image) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor)) return false;
        Actor actor = (Actor) o;
        return Objects.equals(id, actor.id) && Objects.equals(firstName, actor.firstName) && Objects.equals(lastName, actor.lastName) && Objects.equals(birthDate, actor.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getFullName(){
        return this.firstName + " " +this.lastName;
    }

    public String getLifeSpan(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedBirthDate = this.birthDate.format(formatter);
        String formattedDeathDate = this.deathDate != null ? this.deathDate.format(formatter) : "";
        if(formattedDeathDate.isEmpty()){
            return formattedBirthDate;
        }
        String formattedDates = formattedBirthDate + "-" + formattedDeathDate;
        return formattedDates;
    }
    @Transient
    public String getImagePath() {
        if (this.image == null || id == null) return null;

        return "/"+ DefaultSaveLocations.DEFAULT_ACTORS_IMAGE_SAVE + this.image;
    }
    @Transient
    public String getAbsoluteImageUrl() {
        if (this.image == null || id == null) return null;

        return DefaultSaveLocations.DEFAULT_ACTORS_IMAGE_SAVE + this.image;
    }

    public Set<Film> getFilms() {
        return this.films;
    }

    public void addFilm(Film film) {
        this.films.add(film);
    }

    public String getAbsoluteUrl() {
        return StaticURLs.ACTOR_DETAIL_URL+this.getId();
    }
}
