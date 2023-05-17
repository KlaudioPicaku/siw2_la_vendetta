package com.siw.uniroma3.it.siw_lavendetta.impl;

import com.siw.uniroma3.it.siw_lavendetta.models.Director;
import com.siw.uniroma3.it.siw_lavendetta.repositories.ActorRepository;
import com.siw.uniroma3.it.siw_lavendetta.repositories.DirectorRepository;
import com.siw.uniroma3.it.siw_lavendetta.repositories.FilmRepository;
import com.siw.uniroma3.it.siw_lavendetta.services.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("directorServiceImpl")
public class DirectorServiceImpl implements DirectorService {


    private DirectorRepository directorRepository;

    private ActorRepository actorRepository;

    private FilmRepository filmRepository;


    public DirectorServiceImpl(DirectorRepository directorRepository, ActorRepository actorRepository, FilmRepository filmRepository) {
        super();
        this.directorRepository = directorRepository;
        this.actorRepository = actorRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    public List<Director> findAll() {
        return directorRepository.findAll();
    }

    @Override
    public Optional<Director> findById(Long id) {
        return directorRepository.findById(id);
    }

    @Override
    public List<Director> findByName(String name) {
        List<Director> directors = directorRepository.findByFirstNameContainingOrLastNameContaining(name,name);
        return directors;
    }

    @Override
    public void saveOrUpdate(Director director) {
        directorRepository.save(director);
    }

    @Override
    public void delete(Long id) {
        directorRepository.deleteById(id);
    }

    @Override
    public void deleteDirectorImage(String absoluteImageUrl) {
        String filePath="";
        if(absoluteImageUrl!=null || !absoluteImageUrl.isEmpty()){
            filePath=absoluteImageUrl;
        }
        if(!filePath.isEmpty()){
            String baseDirectory = System.getProperty("user.dir");
            Path path = Paths.get(baseDirectory,filePath);
//            System.out.println(path);

            try {
                Files.delete(path);
                System.out.println("File deleted successfully.");
            } catch (IOException e) {
                System.out.println("Failed to delete the file: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
