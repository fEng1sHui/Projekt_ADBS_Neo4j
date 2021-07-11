package guru.springframework.repositories.RealationshipsRepositories;

import guru.springframework.models.Relationships.PersonToBook;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface PersonToBookRelationshipRepository extends Neo4jRepository<PersonToBook, Long> {

    //usuwanie relacji osoba - książka
    @Query("MATCH (person:Person)-[r:READED]-(book:Book) " +
            "WHERE id(book)= $book_id AND id(person)= $person_id " +
            "DELETE r")
    PersonToBook deleteByPersonIdANDBookId(@Param("book_id") Long bookId, @Param("person_id") Long personId);

    //tworzenie relacji osoba - książka
    //waga - dodatkowy parametr który może być użyty do obliczania ważności połączenia
    @Query("MATCH (person), (book) " +
            "WHERE id(person)= $id_person AND id(book)= $id_book " +
            "CREATE (person)-[:READED{weight: 2}]->(book)")
    void saveReadedBook(@Param("id_person") Long personId, @Param("id_book") Long bookId);






}
