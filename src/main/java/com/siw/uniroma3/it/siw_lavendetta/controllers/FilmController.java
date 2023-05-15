package com.siw.uniroma3.it.siw_lavendetta.controllers;

import com.siw.uniroma3.it.siw_lavendetta.constants.DefaultSaveLocations;
import com.siw.uniroma3.it.siw_lavendetta.dto.DirectorDto;
import com.siw.uniroma3.it.siw_lavendetta.dto.FilmDescriptionDto;
import com.siw.uniroma3.it.siw_lavendetta.dto.FilmDto;
import com.siw.uniroma3.it.siw_lavendetta.impl.FilmDescriptionServiceImpl;
import com.siw.uniroma3.it.siw_lavendetta.impl.FilmServiceImpl;
import com.siw.uniroma3.it.siw_lavendetta.models.*;
import com.siw.uniroma3.it.siw_lavendetta.repositories.*;
import com.siw.uniroma3.it.siw_lavendetta.services.*;
import com.siw.uniroma3.it.siw_lavendetta.utils.FileNameGenerator;
import com.siw.uniroma3.it.siw_lavendetta.utils.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class FilmController {

    private FilmDescriptionService filmDescriptionService;

    private FilmDescriptionServiceImpl filmDescriptionServiceImpl;
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
                          FilmImageService filmImageService, FilmServiceImpl filmServiceImpl,
                          FilmDescriptionService filmDescriptionService, FilmDescriptionServiceImpl filmDescriptionServiceImpl) {
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
        this.filmDescriptionService=filmDescriptionService;
        this.filmDescriptionServiceImpl=filmDescriptionServiceImpl;
    }
    @GetMapping("/admin/film/edit")
    public String filmEdit(@RequestParam("id") Long id,Model model){
        Film film=filmService.findById((long)id).get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN") );
        if(!isAdmin) {
            return "error/403";
        }
        if(film!=null){
            boolean filmDescription= filmDescriptionService.findByFilmId((long)id).isPresent();
            if(filmDescription){
                model.addAttribute("filmDescription",filmDescriptionService.findByFilmId((long)id).get());
            }else{
                model.addAttribute("filmDescription",new FilmDescriptionDto());
            }
            model.addAttribute("film",film);
            model.addAttribute("directors",directorService.findAll());
            model.addAttribute("actors",actorService.findAll());
        }
        return "admin_film_edit";
    }
    @GetMapping("/film/detail")
    public String filmDetail(@RequestParam("id") Long id,Model model){
        Optional<Film> film=filmRepository.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user= userRepository.findByUsername(authentication.getName());

        boolean reviewLeft= reviewRepository.findAllByUserAndFilm(user,film).size() > 0 ;

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN") );
        if(isAdmin) {
            model.addAttribute("reviewLeft", false);

        }else{
            model.addAttribute("reviewLeft", reviewLeft);
        }

        int number_of_reviews = reviewRepository.findAllByFilm(film.get()).size();

        model.addAttribute("number_of_reviews",number_of_reviews);

        if (film !=null){
            model.addAttribute("film", film.get());
            return "film_detail";
        }
        return "error/404";
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/films/list_view")
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
    public String adminMovieCreation( @Valid @ModelAttribute("film") FilmDto filmDto, BindingResult result){
        if (result.hasErrors()) {
            return "/admin_film_create";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "error/403";
        }
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN") );
        if (!isAdmin){
            return "error/403";
        }
        List<MultipartFile> files= filmDto.getImages();
        System.out.println(files);
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

        localFilm.setActors(filmDto.getActors());
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
