package guru.springframework.repositories;

import guru.springframework.models.Author;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends Neo4jRepository<Author, Long> {

    @Query ("MATCH (author:Author)-[:BOOK_AUTHOR]-(book:Book) " +
            "WHERE id(book)= $book_id " +
            "RETURN author  ")
    List<Author> findAuthorsListByBookId(@Param("book_id") Long bookId);

    @Query("MATCH (author:Author)" +
            "RETURN author ORDER BY author.name ")
    List<Author> findAllAuthorsOrderByName();


    @Query("CREATE (author:Author {name:$name})")
    void saveAuthor(@Param("name") String name);

    @Query("CREATE (author:Author {name:$name, surname:$surname})")
    void saveAuthor(@Param("name") String name, @Param("surname") String surname);

}
