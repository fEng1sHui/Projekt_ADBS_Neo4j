package guru.springframework.controllers;

import guru.springframework.repositories.AuthorRepository;
import guru.springframework.repositories.BookRepository;
import guru.springframework.repositories.PersonRepository;
import guru.springframework.services.MainService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@Controller
public class HomeController {
    PersonRepository personRepository;
    BookRepository bookRepository;
    AuthorRepository authorRepository;
    MainService mainService;

    @GetMapping("/")
    public String homePage(Model model,
                           @Param(value = "failed_loading_database") String failedLoadingDatabase,
                           @Param(value = "failed_loading_database") String failedLoadingBooks) {

        model.addAttribute("users", personRepository.findAllOrOrderByName());
        model.addAttribute("books", bookRepository.findAllBooksOrderByTitle());
        model.addAttribute("authors", authorRepository.findAllAuthorsOrderByName());

//        model.addAttribute("default_path", "all.json");
        model.addAttribute("failed_loading_database", failedLoadingDatabase);
        model.addAttribute("failed_loading_books", failedLoadingBooks);
        return "index";
    }

    @PostMapping("/import_data")
    public String importData(Model model,
                                   @RequestParam(value = "path") String path) {

        try {
            mainService.LoadDataFromJSONFile(path);
        }
        catch(Exception e) {
            if(e.getMessage() == "Scalar response queries must only return one column. Make sure your cypher query only returns one item.")
            return "redirect:/?failed_loading_database=False";
            else
                return "redirect:/?failed_loading_database=True";
        }
        return "redirect:/";
    }

    @PostMapping("/import_library")
    public String importBooksFromJSON(Model model,
                                      @RequestParam(value = "path") String path) {

            bookRepository.importBooksFromJSONFile(path);

        return "redirect:/";
    }

    @GetMapping("/delete_data_from_database")
    public String deleteData(Model model) {
        personRepository.deleteAllRelationshipsAndNodes();
        return "redirect:/";
    }


}