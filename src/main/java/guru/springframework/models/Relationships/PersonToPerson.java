package guru.springframework.models.Relationships;


import guru.springframework.models.Person;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "FRIEND")
@Getter
@Setter
public class PersonToPerson {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Person person;

    @EndNode
    private Person person_2;


}
