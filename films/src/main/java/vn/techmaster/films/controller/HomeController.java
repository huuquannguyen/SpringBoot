package vn.techmaster.films.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.films.model.Film;



@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping()
    public String showHomePage(Model model){
        model.addAttribute("name", "Quan");
        return "index";
    }

    @GetMapping("about")
    public String showAboutPage(Model model){
        model.addAttribute("name", "Quan Huu");
        return "about";
    }

    @GetMapping("films")
    public String listFilm (Model model){
        List<Film> films = List.of(
            new Film("BÀN TAY DIỆT QUỶ", "Hành Động", " Park Seo Joon", 2021),
            new Film("GODZILLA VS KONG", "Hành Động", "Millie Bobby Brown", 2021),
            new Film("MORTAL KOMBAT: CUỘC CHIẾN SINH TỬ", "Hành Động", "Lewis Tan", 2021),
            new Film("CRUELLA", "Hài, Tâm Lý", "Craig Gillespie", 2021),
            new Film("FAST & FURIOUS 9", "Hành Động, Phiêu Lưu", "Justin Lin", 2021)
        );
        model.addAttribute("films", films);
        return "film";
    }
}
