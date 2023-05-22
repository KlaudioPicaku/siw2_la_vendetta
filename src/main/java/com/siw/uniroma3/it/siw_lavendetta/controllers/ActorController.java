package com.siw.uniroma3.it.siw_lavendetta.controllers;

import com.siw.uniroma3.it.siw_lavendetta.constants.DefaultSaveLocations;
import com.siw.uniroma3.it.siw_lavendetta.constants.GUIconstants;
import com.siw.uniroma3.it.siw_lavendetta.dto.ActorDto;
import com.siw.uniroma3.it.siw_lavendetta.models.Actor;

import com.siw.uniroma3.it.siw_lavendetta.models.Director;
import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.repositories.ActorRepository;
import com.siw.uniroma3.it.siw_lavendetta.repositories.FilmRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.ActorService;
import com.siw.uniroma3.it.siw_lavendetta.services.FilmService;
import com.siw.uniroma3.it.siw_lavendetta.utils.FileNameGenerator;
import com.siw.uniroma3.it.siw_lavendetta.utils.FileUploader;
import org.h2.engine.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

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


    @GetMapping("/actors/list_view")
    public String getActorsList(@RequestParam("page") Integer page, Model model){
        List<Actor> actors= actorRepository.findAll();
        int pageSize = 10;
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, actors.size());
        List<Actor> actorSublist;
        if (startIndex <= endIndex) {
            actorSublist=actors.subList(startIndex, endIndex);
        } else {
            actorSublist= Collections.emptyList();
        }
        int maxNumberOfPages=0;
        if(actorSublist.size()>0){
            maxNumberOfPages=(int) Math.ceil((double) actors.size() / pageSize);;
        }
        model.addAttribute("page",page);
        System.out.println("----NumberOfPages");
        System.out.println(maxNumberOfPages);
        System.out.println(actors.size());
        System.out.println("-----------------");
        model.addAttribute("maxNumberOfPages",maxNumberOfPages);
        model.addAttribute("actors",actorSublist);
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
        return "redirect:/actors/list_view?page=1";
    }
    @GetMapping("/actor/detail")
    public String getActorDetail(@RequestParam("id") Long id,Model model){

        Optional<Actor> actorOptional=actorService.findById(id);
        if (actorOptional.isPresent()){
            model.addAttribute("actor",actorOptional.get());
            List<Film> films= filmService.findByActor(actorOptional.get());
            Map<Film, String> filmRatings = new HashMap<>();
            films.stream().forEach(film ->{
                String rating=filmService.getAverageRating(film);
                filmRatings.put(film,rating);
            });
//        model.addAttribute("films", filmRatings);

            model.addAttribute("films",filmRatings);
            return "actor_detail";
        }
        return "error/404";
    }
    @GetMapping("/actors/film")
    public String getFilmCast(@RequestParam("id") Long filmId,
                              @RequestParam("page") Integer page,
                              Model model){
        Optional<Film> film= filmService.findById(filmId);

        if(!film.isPresent()){
            return  "error/404";
        }
        List<Actor> actors= actorService.findByFilm(film.get());
        int pageSize = 10;
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, actors.size());
        List<Actor> actorSublist;
        if (startIndex <= endIndex) {
            actorSublist=actors.subList(startIndex, endIndex);
        } else {
            actorSublist= Collections.emptyList();
        }
        int maxNumberOfPages=0;
        if(actorSublist.size()>0){
            maxNumberOfPages=(int) Math.ceil((double) actors.size() / pageSize);;
        }
        model.addAttribute("page",page);
//        System.out.println("----NumberOfPages");
//        System.out.println(maxNumberOfPages);
//        System.out.println(actors.size());
//        System.out.println("-----------------");
        model.addAttribute("film",film.get());
        model.addAttribute("maxNumberOfPages",maxNumberOfPages);
        model.addAttribute("actors",actorSublist);
        return "film_cast";
    }
    @GetMapping("/admin/actor/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String actorEdit(@RequestParam("id") Long actorId, Model model){
        Optional<Actor> actor= actorService.findById(actorId);

        if(actor.isPresent()){
            model.addAttribute("actor",actor.get());
            return  "admin_actor_edit";
        }
        return "error/404";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/actor/edit")
    public String directorUpdate(@RequestParam("actorId") Long actorId,
                                 @Valid @ModelAttribute("actor") Actor actor,
                                 @RequestParam("selectedImageIds") String selectedImageIds,
                                 @RequestParam("uploadedImage")MultipartFile file){
        boolean notDefaultPic= !(actorRepository.findById(actorId).get().getImage().equals(GUIconstants.DEFAULT_ACTOR_PICTURE));

        actor.setId(actorId);
        /*se non si sta cercando di eliminare l'immagine di default*/
        if(selectedImageIds.length()>2 && notDefaultPic){
            actorService.deleteActorImage(actorRepository.findById(actorId).get().getAbsoluteImageUrl());
            actor.setImage(GUIconstants.DEFAULT_ACTOR_PICTURE);
            actorService.saveOrUpdate(actor);
        }
        actor.setImage(GUIconstants.DEFAULT_ACTOR_PICTURE);
//        System.out.println(notDefaultPic);
//        System.out.println(!file.isEmpty());
        if(!file.isEmpty()){
            String fileExtension=FileNameGenerator.getFileExtension(file.getOriginalFilename());
            String filename=FileNameGenerator.generateFileName(actor,file)+fileExtension;
            String uploadDirectory=DefaultSaveLocations.DEFAULT_ACTORS_IMAGE_SAVE;
            actor.setImage(filename);
            try {
                if (!file.isEmpty()) {
                    FileUploader.saveFileToLocalDirectory(file, uploadDirectory, filename);
                }
                if (notDefaultPic) {
                    actorService.deleteActorImage(actorRepository.findById(actorId).get().getAbsoluteImageUrl());
                }
            }catch (IOException e){
                System.out.println("There was a problem Saving the image S K I P Z "+ e);
                e.printStackTrace();
                System.out.println("-------------------");
            }
        }
        else if(file.isEmpty() && notDefaultPic && (selectedImageIds.length()<=2) ){
            actor.setImage(actorRepository.findById(actorId).get().getImage());
        }

        actorService.saveOrUpdate(actor);
        return "redirect:/actor/detail?id="+actorId;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/actor/delete_confirm")
    public String deleteActorConfirm(@RequestParam("id") Long Id,Model model){
        Optional<Actor> actor=actorService.findById(Id);

        if(actor.isPresent()){
            model.addAttribute("object",actor.get());
            return "actor_delete_confirm";

        }else{
            return "error/404";
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/actor/delete")
    public String deleteActor(@RequestParam("id") Long id){
        Optional<Actor> actor= actorService.findById(id);
        if(!actor.isPresent()){
            return "error/404";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        boolean isAdmin=auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if(!isAdmin){
            return "error/403";
        }
        actorService.delete(actor.get().getId());
        return "redirect:/actors/list_view?page=1";
    }

}
