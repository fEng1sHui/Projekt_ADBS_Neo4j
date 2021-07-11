package guru.springframework.controllers;

import guru.springframework.repositories.AuthorRepository;
import guru.springframework.repositories.BookRepository;
import guru.springframework.repositories.PersonRepository;
import guru.springframework.repositories.RealationshipsRepositories.BookToAuthorRelationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@AllArgsConstructor
@Controller
public class BookController {

    PersonRepository personRepository;

    BookRepository bookRepository;
    AuthorRepository authorRepository;
    BookToAuthorRelationRepository bookToAuthorRelationRepository;


    @GetMapping("/book_form")
    public String bookForm(Model model) {

        return "book_form.html";
    }

    @PostMapping("/book_form_save")
    public String bookFormSave(Model model,
                               @RequestParam(value = "title") String title,
                               @RequestParam(value = "year_of_production") Long year_of_production) {

        bookRepository.saveBook(title, year_of_production);


        return "redirect:/book_form";
    }

    @GetMapping("/book_data")
    public String bookData(Model model, @RequestParam(value = "bookId") Long bookId){
        model.addAttribute("book", bookRepository.findBookById(bookId));
        model.addAttribute("all_authors", authorRepository.findAllAuthorsOrderByName());
        model.addAttribute("book_author", authorRepository.findAuthorsListByBookId(bookId));
        model.addAttribute("people", personRepository.findByBookId(bookId));

        return "book_data";
    }


    @PostMapping("/book_add_author")
    public String bookAddAuthor(Model model,
                                @RequestParam(value = "bookId") Long bookId,
                                @RequestParam(value = "authorId") Long authorId) {

        bookToAuthorRelationRepository.saveRelationship(bookId, authorId);
        return "redirect:/book_data?bookId=" +bookId;
    }



}
