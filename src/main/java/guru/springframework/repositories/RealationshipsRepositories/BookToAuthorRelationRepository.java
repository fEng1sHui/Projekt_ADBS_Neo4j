package guru.springframework.repositories.RealationshipsRepositories;

import guru.springframework.models.Relationships.BookToAuthor;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface BookToAuthorRelationRepository extends Neo4jRepository<BookToAuthor, Long> {

    //tworzenie relacji książka - autor
    @Query("MATCH (book), (author) " +
            "WHERE id(book)= $id_book AND id(author)= $id_author " +
            "CREATE (book)-[:BOOK_AUTHOR{}]->(author)")
    void saveRelationship(@Param("id_book") Long bookId, @Param("id_author") Long authorId);


}
