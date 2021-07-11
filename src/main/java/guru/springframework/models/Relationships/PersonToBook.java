package guru.springframework.models.Relationships;

import guru.springframework.models.Book;
import guru.springframework.models.Person;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;


@RelationshipEntity(type = "VIEWED")
@Getter
@Setter
public class PersonToBook {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Person person;

    @EndNode
    private Book book;


}
