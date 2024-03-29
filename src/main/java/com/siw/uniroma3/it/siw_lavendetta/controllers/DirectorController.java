package com.siw.uniroma3.it.siw_lavendetta.controllers;
import com.siw.uniroma3.it.siw_lavendetta.constants.DefaultSaveLocations;
import com.siw.uniroma3.it.siw_lavendetta.constants.GUIconstants;
import com.siw.uniroma3.it.siw_lavendetta.dto.DirectorDto;
import com.siw.uniroma3.it.siw_lavendetta.models.Director;
import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.models.Review;
import com.siw.uniroma3.it.siw_lavendetta.models.User;
import com.siw.uniroma3.it.siw_lavendetta.repositories.DirectorRepository;
import com.siw.uniroma3.it.siw_lavendetta.repositories.FilmRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.DirectorService;
import com.siw.uniroma3.it.siw_lavendetta.services.FilmService;
import com.siw.uniroma3.it.siw_lavendetta.services.UserService;
import com.siw.uniroma3.it.siw_lavendetta.utils.FileNameGenerator;
import com.siw.uniroma3.it.siw_lavendetta.utils.FileUploader;
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
public class DirectorController {

    private DirectorService directorService;

    private FilmService filmService;
    private UserService userService;

    private FilmRepository filmRepository;

    private DirectorRepository directorRepository;

    @Autowired
    public DirectorController(FilmService filmService, DirectorService directorService,
                              FilmRepository filmRepository, DirectorRepository directorRepository, UserService userService){
        super();
        this.directorRepository=directorRepository;
        this.filmRepository=filmRepository;
        this.filmService=filmService;
        this.directorService=directorService;
        this.userService=userService;

    }


    @GetMapping("/directors/list_view")
    public String getDirectorsList(Model model){
        List<Director> directors= directorRepository.findAll();
        model.addAttribute("directors",directors);
        return "admin_directors_list";
    }
    @GetMapping("/director/detail")
    public String getDirectorDetail(@RequestParam("id") Long id,Model model){
        Optional<Director> directorOptional=directorService.findById(id);
        if (directorOptional.isPresent()){
            model.addAttribute("director",directorOptional.get());
            List<Film> films= filmService.findByDirector(directorOptional.get());
            Map<Film, String> filmRatings = new HashMap<>();
            films.stream().forEach(film ->{
                String rating=filmService.getAverageRating(film);
                filmRatings.put(film,rating);
            });
//        model.addAttribute("films", filmRatings);

            model.addAttribute("films",filmRatings);
            return "director_detail";
        }
        return "error/404";
    }
    @GetMapping("/admin/director/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String directorEdit(@RequestParam("id") Long directorId,Model model){
        Optional<Director> director =directorService.findById(directorId);
        if (director.isPresent()){
            model.addAttribute("director",director.get());
            return "admin_director_edit";
        }
        return "error/404";

    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/directors/create")
    public String createDirector (Model model){
        model.addAttribute("director", new DirectorDto());
        return "admin_director_create";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/director/edit")
    public String directorUpdate(@RequestParam("directorId") Long directorId,
                                 @Valid @ModelAttribute("director") Director director,
                                 @RequestParam("selectedImageIds") String selectedImageIds,
                                 @RequestParam("uploadedImage")MultipartFile file){
        boolean notDefaultPic= !(directorRepository.findById(directorId).get().getImage().equals(GUIconstants.DEFAULT_DIRECTOR_PICTURE));
//        System.out.println("--------------");
//        System.out.println(director.toString());
//        System.out.println(selectedImageIds);
//        System.out.println(file.isEmpty());
//        System.out.println(file.isEmpty() && notDefaultPic && (selectedImageIds.length()<=2));
//        System.out.println(file.isEmpty());
//        System.out.println(notDefaultPic);
//        System.out.println(selectedImageIds.isEmpty());
//        System.out.println(selectedImageIds.length());
//        System.out.println(selectedImageIds.toString() );
//        System.out.println("-----------------");
        director.setId(directorId);
        /*se non si sta cercando di eliminare l'immagine di default*/
        if(selectedImageIds.length()>2 && notDefaultPic){
            directorService.deleteDirectorImage(directorRepository.findById(directorId).get().getAbsoluteImageUrl());
//          director.setImage(GUIconstants.DEFAULT_DIRECTOR_PICTURE);
            director.setImage(GUIconstants.DEFAULT_DIRECTOR_PICTURE);
            directorService.saveOrUpdate(director);
        }
        director.setImage(GUIconstants.DEFAULT_DIRECTOR_PICTURE);
        System.out.println(notDefaultPic);
        System.out.println(!file.isEmpty());
        if(!file.isEmpty()){
            String fileExtension=FileNameGenerator.getFileExtension(file.getOriginalFilename());
            String filename=FileNameGenerator.generateFileName(director,file)+fileExtension;
            String uploadDirectory=DefaultSaveLocations.DEFAULT_DIRECTORS_IMAGE_SAVE;
            director.setImage(filename);
            try {
                if (!file.isEmpty()) {
                    FileUploader.saveFileToLocalDirectory(file, uploadDirectory, filename);
                }
                if (notDefaultPic) {
                    directorService.deleteDirectorImage(directorRepository.findById(directorId).get().getAbsoluteImageUrl());
                }
            }catch (IOException e){
                System.out.println("There was a problem Saving the image S K I P Z "+ e);
                e.printStackTrace();
                System.out.println("-------------------");
            }
        }
        else if(file.isEmpty() && notDefaultPic && (selectedImageIds.length()<=2) ){
            director.setImage(directorRepository.findById(directorId).get().getImage());
        }

        directorService.saveOrUpdate(director);
        return "redirect:/director/detail?id="+directorId;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/directors/create")
    public String directorCreate(@Valid @ModelAttribute("director") DirectorDto directorDto, BindingResult result){
//        System.out.println("POST ESEGUITA!");
//        directorDto.setBirthDate("2023-05-08");
//        System.out.println(directorDto.toString());
//        System.out.println(result);
        if (result.hasErrors()) {
            return "/admin_director_create";
        }

        MultipartFile file =directorDto.getImage();
        String fileExtension=FileNameGenerator.getFileExtension(file.getOriginalFilename());
        String filename=FileNameGenerator.generateFileName(directorDto,directorDto.getImage())+fileExtension;
        String uploadDirectory=DefaultSaveLocations.DEFAULT_DIRECTORS_IMAGE_SAVE;

//        System.out.println(imageLocation);
//        System.out.println(uploadDirectory);

        Director localDirector=new Director(directorDto.getFirstName(),directorDto.getLastName(),directorDto.getBirthDate(),directorDto.getDeathDate(), filename);
        List<Director> directors = new ArrayList<>(directorRepository.findByFirstNameContainingOrLastNameContaining(directorDto.getFirstName(), directorDto.getLastName()));
        System.out.println(directors);
        Director existingDirector = directors.stream()
                .filter(d -> d.equals(localDirector))
                .findFirst()
                .orElse(null);
        if (existingDirector != null) {
            result.rejectValue("firstName", "director.exists", "This Director already exists");
            return "/admin_director_create";
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
        directorRepository.save(localDirector);
        return "redirect:/directors/list_view";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/director/delete_confirm")
    public String deleteDirectorConfirm(@RequestParam("id") Long Id,Model model){
        Optional<Director> director=directorService.findById(Id);

        if(director.isPresent()){
            model.addAttribute("object",director.get());
            return "director_delete_confirm";

        }else{
            return "error/404";
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/director/delete")
    public String deleteDirector(@RequestParam("id") Long id){
        Optional<Director> director= directorService.findById(id);
        if(!director.isPresent()){
            return "error/404";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        boolean isAdmin=auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if(!isAdmin){
            return "error/403";
        }
        directorService.delete(director.get().getId());
        return "redirect: /directors/list_view";
    }

}
