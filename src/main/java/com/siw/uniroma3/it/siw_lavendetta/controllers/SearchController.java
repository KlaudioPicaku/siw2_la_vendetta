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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class SearchController {

    @Autowired
    FilmService filmService;

    @Autowired
    ActorService actorService;

    @Autowired
    DirectorService directorService;


    @GetMapping("/search")
    public String searchResults(@RequestParam("term") String term,
                                @RequestParam("page")Integer page,
                                Model model){
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


        int pageSize = 10;
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, searchResults.size());
        System.out.println(startIndex);
        System.out.println(endIndex);
        System.out.println(searchResults.size());
        for(SearchResult s:searchResults){
            System.out.println(s.getName());
        }
        List<SearchResult> searchResultsSublist;
        if (startIndex <= endIndex) {
            searchResultsSublist=searchResults.subList(startIndex, endIndex);
        } else {
            searchResultsSublist= Collections.emptyList();
        }
        int maxNumberOfPages=0;
        if(searchResultsSublist.size()>0){
            maxNumberOfPages=(int) Math.ceil((double) searchResults.size() / pageSize);
        }
        System.out.println(searchResultsSublist);

        model.addAttribute("searchResults",searchResultsSublist);
        model.addAttribute("page",page);
        model.addAttribute("term",term);
        model.addAttribute("maxNumberOfPages",maxNumberOfPages);

        return "search";

    }

}
