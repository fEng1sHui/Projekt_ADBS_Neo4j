package guru.springframework.controllers;

import guru.springframework.repositories.AuthorRepository;
import guru.springframework.repositories.BookRepository;
import guru.springframework.repositories.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@AllArgsConstructor
@Controller
public class AuthorController {

    PersonRepository personRepository;
    BookRepository bookRepository;
    AuthorRepository authorRepository;

    @GetMapping("/author_form")
    public String authorForm(Model model) {
        return "author_form.html";
    }

    @PostMapping("/author_form_save")
    public String authorFormSave(Model model,
                                 @RequestParam(value = "name") String name,
                                 @RequestParam(value = "surname") String surname) {
        authorRepository.saveAuthor(name, surname);
        return "redirect:/author_form";
    }


}
