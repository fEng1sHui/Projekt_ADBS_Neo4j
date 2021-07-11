package guru.springframework.controllers;

import guru.springframework.repositories.AuthorRepository;
import guru.springframework.repositories.BookRepository;
import guru.springframework.repositories.PersonRepository;
import guru.springframework.repositories.RealationshipsRepositories.PersonToBookRelationshipRepository;
import guru.springframework.repositories.RealationshipsRepositories.PersonToPersonRealtionRepository;
import guru.springframework.services.MainService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@AllArgsConstructor
@Controller
public class PersonController {

    PersonRepository personRepository;
    PersonToPersonRealtionRepository personToPersonRealtionRepository;
    BookRepository bookRepository;
    AuthorRepository authorRepository;
    PersonToBookRelationshipRepository personToBookRelationshipRepository;
    MainService mainService;


    @GetMapping("/user_form")
    public String userForm(Model model) {

        return "user-form.html";
    }

    @PostMapping("/user_form_save")
    public String userFormSave(Model model,
                               @RequestParam(value = "name") String name,
                               @RequestParam(value = "surname") String surname,
                               @RequestParam(value = "age") Long age) {
        personRepository.savePerson(name, surname, age);
        return "redirect:/";
    }

    @GetMapping("/person_data")
    public String personData(Model model, @RequestParam(value = "personId") Long personId) {
        //        Dane przeglądanej osoby
        model.addAttribute("person", personRepository.findPersonById(personId));
        //        Wszystkie książki
        model.addAttribute("books", bookRepository.findAll());
        //        Znajomi uzytkownicy
        model.addAttribute("friends", personRepository.findFriendsByPersonId(personId));
        //      Proponowani znajomi
        model.addAttribute("proposed_friends", personRepository.findProposedPeopleForFriendship(personId));
        //      Użytkownicy których możesz dodać
        model.addAttribute("people", personRepository.findPeopleForFriendship(personId));
        //        Proponowane książki
        model.addAttribute("suggested_books", bookRepository.findSuggestedBooks(personId));



        return "person_data";
    }


    @PostMapping("/person_add_viewed_books")
    public String personAddReadedBook(Model model,
                                      @RequestParam(value = "personId") Long personId,
                                      @RequestParam(value = "bookId") Long bookId) {
        personToBookRelationshipRepository.saveReadedBook(personId, bookId);
        return "redirect:person_data?personId=" + personId;
    }

    @PostMapping("/person_add_friend_relationship")
    public String personAddFriendRealtionship(Model model,
                                              @RequestParam(value = "personId") Long personId,
                                              @RequestParam(value = "newFriendId") Long new_friend_id) {


        mainService.saveRelationshipPersonToPerson(personId, new_friend_id);
        return "redirect:person_data?personId=" + personId;
    }

    @PostMapping("/delete_readed_book")
    public String deleteReadedBook(Model model,
                                   @RequestParam(value = "personId") Long personId,
                                   @RequestParam(value = "bookToDelete") Long bookToDelete) {
        personToBookRelationshipRepository.deleteByPersonIdANDBookId(bookToDelete, personId);
        return "redirect:person_data?personId=" + personId;
    }

//    @PostMapping("/delete_interesing_book")
//    public String deleteInterestingBook(Model model,
//                                        @RequestParam(value = "personId") Long personId,
//                                        @RequestParam(value = "bookToDelete") Long bookToDelete) {
//
//        personToBookRelationshipRepository.deleteInteresingBookByPersonIdANDBookId(bookToDelete, personId);
//        return "redirect:person_data?personId=" + personId;
//    }

}