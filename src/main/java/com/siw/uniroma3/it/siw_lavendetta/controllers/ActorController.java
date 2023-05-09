package com.siw.uniroma3.it.siw_lavendetta.controllers;

import com.siw.uniroma3.it.siw_lavendetta.constants.DefaultSaveLocations;
import com.siw.uniroma3.it.siw_lavendetta.dto.ActorDto;
import com.siw.uniroma3.it.siw_lavendetta.models.Actor;

import com.siw.uniroma3.it.siw_lavendetta.repositories.ActorRepository;
import com.siw.uniroma3.it.siw_lavendetta.repositories.FilmRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.ActorService;
import com.siw.uniroma3.it.siw_lavendetta.services.FilmService;
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

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ActorController {

    private ActorService actorService;

    private ActorRepository actorRepository;

    private FilmService filmService;

    private FilmRepository filmRepository;

    @Autowired
    public ActorController(ActorService actorService, ActorRepository actorRepository,
                           FilmService filmService, FilmRepository filmRepository) {
        super();
        this.actorService = actorService;
        this.actorRepository = actorRepository;
        this.filmService = filmService;
        this.filmRepository = filmRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/actors/list_view")
    public String getActorsList(Model model){
        List<Actor> actors= actorRepository.findAll();
        model.addAttribute("actors",actors);
        return "admin_actors_list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/actors/create")
    public String createActor (Model model){
        model.addAttribute("actor", new ActorDto());
        return "admin_actor_create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/actors/create")
    public String actorCreate(@Valid @ModelAttribute("actor") ActorDto actorDto, BindingResult result){

        if (result.hasErrors()) {
            return "/admin_actor_create";
        }

        MultipartFile file =actorDto.getImage();
        String fileExtension= FileNameGenerator.getFileExtension(file.getOriginalFilename());
        String filename=FileNameGenerator.generateFileName(actorDto,actorDto.getImage())+fileExtension;
        String uploadDirectory= DefaultSaveLocations.DEFAULT_ACTORS_IMAGE_SAVE;


        Actor localActor=new Actor(actorDto.getFirstName(),actorDto.getLastName(),actorDto.getBirthDate(),actorDto.getDeathDate(), filename);
        List<Actor> actors = new ArrayList<>(actorRepository.findByFirstNameContainingOrLastNameContaining(actorDto.getFirstName(), actorDto.getLastName()));

        Actor existingActor = actors.stream()
                .filter(d -> d.equals(localActor))
                .findFirst()
                .orElse(null);
        if (existingActor != null) {
            result.rejectValue("firstName", "actor.exists", "This Actor already exists");
            return "/admin_actor_create";
        }

        try {
            if (file != null) {
                FileUploader.saveFileToLocalDirectory(file, uploadDirectory, filename);
            }
        }catch (IOException e){
            System.out.println("There was a problem Saving the image S K I P Z "+ e);
            e.printStackTrace();
            System.out.println("-------------------");
        }
        actorRepository.save(localActor);
        return "redirect:/actors/list_view";
    }
}
