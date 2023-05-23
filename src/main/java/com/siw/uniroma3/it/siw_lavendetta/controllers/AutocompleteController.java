package com.siw.uniroma3.it.siw_lavendetta.controllers;

import com.siw.uniroma3.it.siw_lavendetta.models.Actor;
import com.siw.uniroma3.it.siw_lavendetta.models.Director;
import com.siw.uniroma3.it.siw_lavendetta.models.Film;
import com.siw.uniroma3.it.siw_lavendetta.models.SearchResult;
import com.siw.uniroma3.it.siw_lavendetta.services.ActorService;
import com.siw.uniroma3.it.siw_lavendetta.services.DirectorService;
import com.siw.uniroma3.it.siw_lavendetta.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@RestController
public class AutocompleteController {
    @Autowired
    FilmService filmService;

    @Autowired
    ActorService actorService;

    @Autowired
    DirectorService directorService;


    @GetMapping("/api/searchbar/autocomplete")
    @ResponseBody
    public ResponseEntity<List<SearchResult>> searchResultResponseEntity(@RequestParam("term") String term){
        List<SearchResult> searchResults= new ArrayList<>();

        List<Film> films = filmService.searchByTerm(term);

        for(Film f: films){
            searchResults.add(new SearchResult(f));
        }

        List<Actor> actors = actorService.searchByterm(term);

        for(Actor a: actors){
            searchResults.add(new SearchResult(a));
        }

        List<Director> directors= directorService.searchByTerm(term);

        for(Director d: directors){
            searchResults.add(new SearchResult(d));
        }

        return ResponseEntity.ok(searchResults);

    }

}
