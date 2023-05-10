package com.siw.uniroma3.it.siw_lavendetta.controllers;

import com.siw.uniroma3.it.siw_lavendetta.constants.DefaultSaveLocations;
import com.siw.uniroma3.it.siw_lavendetta.dto.DirectorDto;
import com.siw.uniroma3.it.siw_lavendetta.dto.FilmDto;
import com.siw.uniroma3.it.siw_lavendetta.impl.FilmServiceImpl;
import com.siw.uniroma3.it.siw_lavendetta.models.Actor;
import com.siw.uniroma3.it.siw_lavendetta.models.Director;
import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.models.FilmImage;
import com.siw.uniroma3.it.siw_lavendetta.repositories.*;
import com.siw.uniroma3.it.siw_lavendetta.services.*;
import com.siw.uniroma3.it.siw_lavendetta.utils.FileNameGenerator;
import com.siw.uniroma3.it.siw_lavendetta.utils.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FilmController {

    private FilmService filmService;

    private DirectorService directorService;


    private ActorService actorService;

    private ReviewService reviewService;

    private FilmImageService filmImageService;
    private FilmRepository filmRepository;

    private DirectorRepository directorRepository;


    private ActorRepository actorRepository;

    private ReviewRepository reviewRepository;

    private UserRepository userRepository;

    private FilmImageRepository filmImageRepository;

    private FilmServiceImpl filmServiceImpl;

    private FilmDto filmDto;
    @Autowired
    public FilmController(FilmService filmService, DirectorService directorService,
                          ActorService actorService, ReviewService reviewService,
                          FilmRepository filmRepository, DirectorRepository directorRepository,
                          ActorRepository actorRepository, ReviewRepository reviewRepository,
                          UserRepository userRepository, FilmImageRepository filmImageRepository,
                          FilmImageService filmImageService,FilmServiceImpl filmServiceImpl) {
        this.filmService = filmService;
        this.directorService = directorService;
        this.actorService = actorService;
        this.reviewService = reviewService;
        this.filmRepository = filmRepository;
        this.directorRepository = directorRepository;
        this.actorRepository = actorRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.filmImageRepository=filmImageRepository;
        this.filmImageService=filmImageService;
        this.filmServiceImpl=filmServiceImpl;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/films/list_view")
    public String adminFilmList(Model model){
        List<Film> films = filmRepository.findAll();
        model.addAttribute("films",films);
        return "admin_film_list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/films/create")
    public String adminFilmCreate(Model model){
        model.addAttribute("film",new FilmDto());
        List<Actor> actors=actorRepository.findAll();
        List<Director> directors= directorRepository.findAll();
        model.addAttribute("directors",directors);
        model.addAttribute("actors",actors);
        return "admin_film_create";
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/films/create")
    public String adminMovieCreation(@Valid @ModelAttribute("film") FilmDto filmDto, BindingResult result){
        if (result.hasErrors()) {
            return "/admin_film_create";
        }
        List<MultipartFile> files= filmDto.getImages();
        String uploadDirectory= DefaultSaveLocations.DEFAULT_FILMS_IMAGE_SAVE;
        Film localFilm= new Film(filmDto.getTitle(),filmDto.getReleaseYear(),filmDto.getDirector());
        List<Film> films=new ArrayList<>(filmRepository.findByTitleContainingIgnoreCase(localFilm.getTitle()));
        Film existingFilm = films.stream()
                .filter(d->d.equalTitle(localFilm))
                .findFirst()
                .orElse(null);
        if(existingFilm!=null){
            result.rejectValue("title", "film.exists", "A film with the same title and/or release year already exists");
            return "/admin_film_create";
        }


        Film filmSaved=filmRepository.save(localFilm);
        try{
            if (files!=null){
                for (MultipartFile file: files) {
                    String fileExtension= FileNameGenerator.getFileExtension(file.getOriginalFilename());
                    String filename=FileNameGenerator.generateFileName(filmDto,file)+fileExtension;
                    FileUploader.saveFileToLocalDirectory(file,uploadDirectory,filename);
                    FilmImage filmImage= new FilmImage(filename,filmSaved);
                    filmSaved.addImage( filmImageRepository.save(filmImage));
                    filmRepository.save(filmSaved);
                }
            }
        }catch (IOException e){
            System.out.println("There was a problem Saving the image S K I P Z "+ e);
            System.out.print("CAUTION: Deleting Film Instance!");
            filmRepository.delete(filmSaved);
            e.printStackTrace();
            System.out.println("-------------------");
        }

        return "redirect:/admin/films/list_view";
    }


}
