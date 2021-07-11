package guru.springframework.models.Relationships;


import guru.springframework.models.Book;
import guru.springframework.models.Author;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "MOVIE_TYPE")
@Getter
@Setter
public class BookToAuthor {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Book book;

    @EndNode
    private Author author;

}
