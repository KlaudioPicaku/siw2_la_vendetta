package com.siw.uniroma3.it.siw_lavendetta.controllers;

import com.siw.uniroma3.it.siw_lavendetta.constants.DefaultSaveLocations;
import com.siw.uniroma3.it.siw_lavendetta.dto.DirectorDto;
import com.siw.uniroma3.it.siw_lavendetta.models.Director;
import com.siw.uniroma3.it.siw_lavendetta.repositories.DirectorRepository;
import com.siw.uniroma3.it.siw_lavendetta.repositories.FilmRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.DirectorService;
import com.siw.uniroma3.it.siw_lavendetta.services.FilmService;
import com.siw.uniroma3.it.siw_lavendetta.utils.FileNameGenerator;
import com.siw.uniroma3.it.siw_lavendetta.utils.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Controller
public class DirectorController {

    private DirectorService directorService;

    private FilmService filmService;

    private FilmRepository filmRepository;

    private DirectorRepository directorRepository;

    @Autowired
    public DirectorController(FilmService filmService, DirectorService directorService,
                              FilmRepository filmRepository, DirectorRepository directorRepository){
        super();
        this.directorRepository=directorRepository;
        this.filmRepository=filmRepository;
        this.filmService=filmService;
        this.directorService=directorService;

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/directors/list_view")
    public String getDirectorsList(Model model){
        List<Director> directors= directorRepository.findAll();
        model.addAttribute("directors",directors);
        return "admin_directors_list";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/directors/create")
    public String createDirector (Model model){
        model.addAttribute("director", new DirectorDto());
        return "admin_director_create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/directors/create")
    public String directorCreate(@Valid @ModelAttribute("director") DirectorDto directorDto, BindingResult result){
        System.out.println("POST ESEGUITA!");
        directorDto.setBirthDate("2023-05-08");
        System.out.println(directorDto.toString());
        System.out.println(result);
        if (result.hasErrors()) {
            return "/admin_director_create";
        }

        MultipartFile file =directorDto.getImage();
        String filename=FileNameGenerator.generateFileName(directorDto,directorDto.getImage());
        String imageLocation=DefaultSaveLocations.DEFAULT_DIRECTORS_IMAGE_SAVE+filename;
        Director localDirector=new Director(directorDto.getFirstName(),directorDto.getLastName(),directorDto.getBirthDate(),directorDto.getDeathDate(), imageLocation);
        List<Director> directors = new ArrayList<>(directorRepository.findByFirstNameContainingOrLastNameContaining(directorDto.getFirstName(), directorDto.getLastName()));

        Director existingDirector = directors.stream()
                .filter(d -> d.equals(localDirector))
                .findFirst()
                .orElse(null);
        if (existingDirector != null) {
            result.addError(new ObjectError("errorMessage", "This Director already exists"));
            return "/admin_director_create";
        }

        directorRepository.save(localDirector);
        try {
            if (file != null) {
                FileUploader.saveFileToLocalDirectory(file, imageLocation, filename);
            }
        }catch (IOException e){
            System.out.println("There was a problem Saving the image S K I P Z "+ e);
        }
        return "/admin/directors/list_view";
    }
}
